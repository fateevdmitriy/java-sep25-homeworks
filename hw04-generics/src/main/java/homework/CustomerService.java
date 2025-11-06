package homework;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

// @SuppressWarnings({"java:S1186", "java:S1135", "java:S1172"}) // при выполнении ДЗ эту аннотацию надо удалить

public class CustomerService {

    // важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны
    private TreeMap<Customer, String> customers = new TreeMap<>(Comparator.comparingLong(Customer::getScores));

    public Map.Entry<Customer, String> getSmallest() {
        // Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk
        Map.Entry<Customer, String> smallest = customers.firstEntry();
        if (smallest == null) {
            return null;
        }
        Customer customer = smallest.getKey();
        String value = smallest.getValue();
        return new AbstractMap.SimpleEntry<>(new Customer(customer), value);
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> next = customers.higherEntry(customer);
        if (next == null) {
            return null;
        }
        Customer customerNext = next.getKey();
        String valueNext = next.getValue();
        return new AbstractMap.SimpleEntry<>(new Customer(customerNext), valueNext);
    }

    public void add(Customer customer, String data) {
        customers.put(customer, data);
    }
}
