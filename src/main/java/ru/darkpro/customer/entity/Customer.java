package ru.darkpro.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "T_CUSTOMER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="customer_seq")
//    @SequenceGenerator(name="customer_seq", sequenceName="SEQ_CUSTOMER", allocationSize=10)
    @Column(name="id", updatable=false, nullable=false)
    private Long id;
    private String firstName;
    private String lastName;
    @JsonIgnore
    @Column(unique=true)
    private String phone;

    @OneToMany
    @JoinColumn(name = "customer_id")
    private final List<Order> orders = new ArrayList<>();

    public Customer(String firstName, String lastName, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

}
