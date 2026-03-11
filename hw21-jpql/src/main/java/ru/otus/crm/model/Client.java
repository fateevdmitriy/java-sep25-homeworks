package ru.otus.crm.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@SuppressWarnings("this-escape")
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_gen")
    // @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    // @JoinColumn(name = "phone_id")
    private List<Phone> phones;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
        // this.phones = new ArrayList<>();
    }

    public Client(Long id, String name, Address address, List<Phone> phonesList) {
        this.id = id;
        this.name = name;
        this.address = address;
        // this.phones = clonePhonesForClient(phonesList, this);
        this.phones = this.phones == null
                ? new ArrayList<>()
                : phones.stream()
                        .map(phone -> new Phone(phone.getId(), phone.getNumber(), this))
                        .toList();
    }

    @Override
    @SuppressWarnings({"java:S2975", "java:S1182"})
    public Client clone() {
        Address clonedAddress = address == null ? null : new Address(address.getId(), address.getStreet());

        Client clonedClient = new Client(id, name, clonedAddress);
        /*
        if (clonedAddress != null) {
            clonedAddress.setClient(clonedClient);
        }
        */

        // List<Phone> clonedPhones = clonePhonesForClient(phones, clonedClient);
        List<Phone> clonedPhones = this.phones == null
                ? new ArrayList<>()
                : phones.stream()
                        .map(phoneItem -> new Phone(phoneItem.getId(), phoneItem.getNumber(), clonedClient))
                        .toList();
        clonedClient.setPhones(clonedPhones);
        return clonedClient;
        // return new Client(this.id, this.name);
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name='" + name + '\'' + '}';
    }

    /*
    private List<Phone> clonePhonesForClient(List<Phone> sourcePhones, Client targetClient) {
        return sourcePhones == null
                ? new ArrayList<>()
                : sourcePhones.stream()
                        .map(phoneItem -> new Phone(phoneItem.getId(), phoneItem.getNumber(), targetClient))
                        .toList();
    }
     */
}
