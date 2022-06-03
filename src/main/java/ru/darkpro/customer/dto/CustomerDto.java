package ru.darkpro.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Component;
import ru.darkpro.customer.entity.Order;

import java.util.List;
import java.lang.Long;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class CustomerDto {

    private Long id;
    private String firstName;
    private String lastName;
    @JsonIgnore
    private String phone;
    @JsonIgnore
    private List<OrderDto> orders;

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, firstName='%s', lastName='%s']",
                id, firstName, lastName);
    }
}
