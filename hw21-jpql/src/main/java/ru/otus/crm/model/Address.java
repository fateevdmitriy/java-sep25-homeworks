package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column // (name = "street")
    private String street;

    /*
    @OneToOne(mappedBy = "address")
    private Client client;
    */

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    /*
    public Address(Long id, String street, Client client) {
        this.id = id;
        this.street = street;
        this.client = client;
    }
     */
}
