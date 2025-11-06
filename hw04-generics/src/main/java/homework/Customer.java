package homework;

import java.util.Objects;

// @SuppressWarnings({"java:S1135"}) // при выполнении ДЗ эту аннотацию надо удалить

public class Customer {
    private final long id;
    private String name;
    private long scores;
    private final int hashCode;

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
        this.hashCode = Objects.hash(id);
    }

    public Customer(Customer source) {
        this.id = source.id;
        this.name = source.name;
        this.scores = source.scores;
        this.hashCode = Objects.hash(id);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name='" + name + '\'' + ", scores=" + scores + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }
}
