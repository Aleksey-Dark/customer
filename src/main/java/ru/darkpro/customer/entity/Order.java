package ru.darkpro.customer.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

//@Entity
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String value;
    private String comment;
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Timestamp createdAt;

    protected Order() {}

    public Order(String firstName, String lastName) {
        this.value = firstName;
        this.comment = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Order[id=%d, value='%s', comment='%s', createdAt='%s']",
                id, value, comment, createdAt);
    }

    public Long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
