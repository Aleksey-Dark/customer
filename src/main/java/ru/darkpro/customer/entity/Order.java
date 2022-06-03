package ru.darkpro.customer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "T_ORDER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="order_seq")
//    @SequenceGenerator(name="order_seq", sequenceName="SEQ_ORDER", allocationSize=10)
    @Column(name="id", updatable=false, nullable=false)
    private Long id;
    @Column(name = "val")
    private String value;
    private String comment;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
//    @ManyToOne
//    @JsonIgnore
    @JoinColumn(name = "customer")
    private long customer;

    public Order(Long customer, String value, String comment) {
        this.customer = customer;
        this.value = value;
        this.comment = comment;
//        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
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

    public Long getCustomer() {
        return customer;
    }
}
