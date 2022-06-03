package ru.darkpro.customer.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.service.CustomerService;
import ru.darkpro.customer.service.WebHookService;


@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final WebHookService webHookService;

    @GetMapping(path = "/customer/{customerId}")
    public Customer findById(@PathVariable long customerId) {
        return customerService.get(customerId);
    }

    @PostMapping(path = "/customer/register")
    public String registerCustomer(@RequestBody Customer customer) {
        if (customerService.validation(customer)) {
            webHookService.send(customer);
            if (customerService.register(customer)){
                return String.format("{\"id\":%s}", customer.getId());
            }
        }
        return "{\"id\":null}";
    }
}
