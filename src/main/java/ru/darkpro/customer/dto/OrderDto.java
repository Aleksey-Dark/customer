package ru.darkpro.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class OrderDto {

    private Long id;
    private String value;
    private String comment;
    private Timestamp createdAt;
//    @JsonIgnore
    private long customer;

    @Override
    public String toString() {
        return String.format(
                "Order[id=%d, value='%s', comment='%s', createdAt='%s']",
                id, value, comment, createdAt);
    }

}
