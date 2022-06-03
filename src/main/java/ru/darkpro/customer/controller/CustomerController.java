package ru.darkpro.customer.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.darkpro.customer.dto.CustomerDto;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.service.CustomerService;
import ru.darkpro.customer.service.WebHookService;


@RestController
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final WebHookService webHookService;
    private ModelMapper modelMapper;

    @GetMapping(path = "/customer/{customerId}")
    public CustomerDto findById(@PathVariable long customerId) {
        return modelMapper.map(customerService.get(customerId), CustomerDto.class);
    }

    @PostMapping(path = "/customer/register")
    public CustomerDto registerCustomer(@RequestBody Customer customer) {
        if (customerService.validation(customer)) {
            webHookService.send(customer);
            if (customerService.register(customer)){
                return modelMapper.map(customer, CustomerDto.class);
            }
        }
        return new CustomerDto();
    }
}
