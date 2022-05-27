package ru.darkpro.customer.controller;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.*;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.repository.CustomerRepository;

@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerRepository repository;

    @GetMapping(path = "/customer/{customerId}")
    public Customer getCustomer(@PathVariable long customerId) {
        return repository.findById(customerId);
    }

    @PostMapping(path = "/register/customer")
    public Customer registerCustomer(@RequestBody String data) {
        JSONObject customerJson = new JSONObject(new JSONTokener(data));
        Customer customer = repository.findByPhone(customerJson.getString("phone"));
        if (customer == null) {
            return repository.save(new Customer(customerJson.getString("firstName"),
                    customerJson.getString("lastName"), customerJson.getString("phone")));
        }
        return customer;
    }
}
