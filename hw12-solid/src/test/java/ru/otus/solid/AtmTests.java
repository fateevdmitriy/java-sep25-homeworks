package ru.otus.solid;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.exceptions.*;

class AtmTests {

    @Test
    @DisplayName("Первичная загрузка банкнот в банкомат и проверка баланса")
    void prepareAtm() {
        // given
        int expectedBalanceAtm = 886000;
        // when
        Atm atm = new AtmImpl();
        // then
        assertThat(atm.getGeneralBalance()).isEqualTo(expectedBalanceAtm);
    }

    @Test
    @DisplayName("Загрузка одной купюры в банкомат")
    void loadOneBanknoteToAtm() {
        // given
        int expectedBalanceAtm = 887000;
        int expectedBanknotesAmountAtm = 101;
        Banknote banknote1 = new Banknote(Denomination.ONE_THOUSAND);
        // when
        Atm atm = new AtmImpl();
        // then
        assertThatCode(() -> {
                    atm.putMoney(banknote1);
                })
                .doesNotThrowAnyException();
        assertThat(atm.getGeneralBalance()).isEqualTo(expectedBalanceAtm);
        assertThat(atm.getAmount(banknote1.getDenomination())).isEqualTo(expectedBanknotesAmountAtm);
    }

    @Test
    @DisplayName("Загрузка нескольких разных купюр в банкнот")
    void loadDifferentBanknotesToAtm() {
        // given
        int expectedBalanceAtm = 888850;
        Banknote fifty1 = new Banknote(Denomination.FIFTY);
        Banknote oneHundreed1 = new Banknote(Denomination.ONE_HUNDRED);
        Banknote twoHundreed1 = new Banknote(Denomination.TWO_HUNDRED);
        Banknote fiveHundreed1 = new Banknote(Denomination.FIVE_HUNDRED);
        Banknote twoThousand1 = new Banknote(Denomination.TWO_THOUSAND);
        Set<Banknote> banknotes1 =
                new HashSet<>(Arrays.asList(fifty1, oneHundreed1, twoHundreed1, fiveHundreed1, twoThousand1));
        // when
        Atm atm = new AtmImpl();
        // then
        assertThatCode(() -> {
                    atm.putMoney(banknotes1);
                })
                .doesNotThrowAnyException();
        assertThat(atm.getGeneralBalance()).isEqualTo(expectedBalanceAtm);
    }

    @Test
    @DisplayName("Получение из банкомата запрошенной суммы, которая меньше количества денег в банкомате")
    void getSumFromAtm() {
        // given
        int requestedSum = 2650;
        int expectedBalanceAtm = 883350;
        // when
        Atm atm = new AtmImpl();
        // then
        assertThatCode(() -> {
                    atm.getMoney(requestedSum);
                })
                .doesNotThrowAnyException();
        assertThat(atm.getGeneralBalance()).isEqualTo(expectedBalanceAtm);
    }

    @Test
    @DisplayName("Получение из банкомата запрошенной суммы, которая больше количества денег в банкомате")
    void getImpossibleSumFromAtm() {
        // given
        int requestedSum = 1000000;
        int expectedBalanceAtm = 886000;
        // when
        Atm atm = new AtmImpl();
        // then
        assertThatThrownBy(() -> {
                    atm.getMoney(requestedSum);
                })
                .isInstanceOf(GetBanknotesFromTrayException.class);
        assertThat(atm.getGeneralBalance()).isEqualTo(expectedBalanceAtm);
    }
}
