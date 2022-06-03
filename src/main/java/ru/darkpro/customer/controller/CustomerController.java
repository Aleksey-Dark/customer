package ru.darkpro.customer.controller;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.*;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.service.CustomerService;


@RestController
@AllArgsConstructor
public class CustomerController {

    private CustomerService customerService;

    @GetMapping(path = "/customer/{customerId}")
    public Customer getCustomer(@PathVariable long customerId) {
        return customerService.get(customerId);
    }

    @PostMapping(path = "/customer/register")
    public String registerCustomer(@RequestBody String body) {
        return customerService.register(body);
    }
}
