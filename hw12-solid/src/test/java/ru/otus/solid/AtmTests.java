package ru.otus.solid;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.banknotes.BanknoteImpl;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;

class AtmTests {

    @Test
    @DisplayName("Первичная загрузка банкнот в банкомат и получение баланса")
    void prepareAtm() {
        // given
        int expectedBalanceAtm1 = 886000;
        // when
        Atm atm1 = new Atm();
        // then
        assertThat(atm1.getAllTraysBalance()).isEqualTo(expectedBalanceAtm1);
    }

    @Test
    @DisplayName("Загрузка одной купюры в банкнот")
    void loadOneBanknoteToAtm() {
        // given
        int expectedBalanceAtm2 = 887000;
        int expectedBanknotesAmountAtm2 = 101;
        Banknote banknote1 = new BanknoteImpl(Denomination.ONE_THOUSAND);
        // when
        Atm atm = new Atm();
        // then
        assertThatCode(() -> {
                    atm.putMoney(banknote1);
                })
                .doesNotThrowAnyException();
        assertThat(atm.getAllTraysBalance()).isEqualTo(expectedBalanceAtm2);
        //assertThat(atm.getTray(banknote1.getDenomination()).getAmount()).isEqualTo(expectedBanknotesAmountAtm2);
    }

    @Test
    @DisplayName("Загрузка нескольких разных купюр в банкнот")
    void loadDifferentBanknotesToAtm() {
        // given
        int expectedBalanceAtm3 = 888850;
        Banknote fifty1 = new BanknoteImpl(Denomination.FIFTY);
        Banknote oneHundreed1 = new BanknoteImpl(Denomination.ONE_HUNDRED);
        Banknote twoHundreed1 = new BanknoteImpl(Denomination.TWO_HUNDRED);
        Banknote fiveHundreed1 = new BanknoteImpl(Denomination.FIVE_HUNDRED);
        Banknote twoThousand1 = new BanknoteImpl(Denomination.TWO_THOUSAND);
        Set<Banknote> banknotes1 =
                new HashSet<>(Arrays.asList(fifty1, oneHundreed1, twoHundreed1, fiveHundreed1, twoThousand1));
        // when
        Atm atm = new Atm();
        // then
        assertThatCode(() -> {
                    atm.putMoney(banknotes1);
                })
                .doesNotThrowAnyException();
        assertThat(atm.getAllTraysBalance()).isEqualTo(expectedBalanceAtm3);
    }

    @Test
    @DisplayName("Получение из банкомата запрошенной суммы, которая меньше количества денег в банкомате")
    void getSumFromAtm() {
        // given
        int requestedSum = 2650;
        int expectedBalanceAtm = 883350;
        // when
        Atm atm = new Atm();
        // then
        assertThatCode(() -> {
                    atm.getMoney(requestedSum);
                })
                .doesNotThrowAnyException();
        assertThat(atm.getAllTraysBalance()).isEqualTo(expectedBalanceAtm);
    }

    @Test
    @DisplayName("Получение из банкомата запрошенной суммы, которая больше количества денег в банкомате")
    void getImpossibleSumFromAtm() {
        // given
        int requestedSum = 1000000;
        int expectedBalanceAtm = 886000;
        // when
        Atm atm5 = new Atm();
        // then
        assertThatThrownBy(() -> {
                    atm5.getMoney(requestedSum);
                })
                .isInstanceOf(GetBanknotesFromTrayException.class);
    }
}
