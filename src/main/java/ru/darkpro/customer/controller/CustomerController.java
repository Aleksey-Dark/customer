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

//    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path = "/customer/{customerId}")
    public Customer getCustomer(@PathVariable long customerId) {
        return customerRepository.findById(customerId);
    }

    @PostMapping(path = "/customer/register")
    public String registerCustomer(@RequestBody String data) {
        JSONObject customerJson = new JSONObject(new JSONTokener(data));
        Customer customer = customerRepository.findByPhone(customerJson.getString("phone"));
        if (customer == null) {
            customer = new Customer(customerJson.getString("firstName"),
                    customerJson.getString("lastName"), customerJson.getString("phone"));
            customerRepository.save(customer);
        }
        return String.format("{\"id\":%s}", customer.getId());
    }
}
