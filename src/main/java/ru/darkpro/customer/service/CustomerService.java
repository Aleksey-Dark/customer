package ru.darkpro.customer.service;

import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.repository.CustomerRepository;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;
    private final WebHookService webHook;

    public Customer get(@PathVariable long customerId) {
        return customerRepository.findById(customerId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String register(Customer customer){
        if (customer != null) {
            try {
                customerRepository.save(customer);

                return String.format("{\"id\":%s}", customer.getId());
            }
            catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return "{\"id\":null}";
    }

    public Customer validation(JSONObject customerJson){
        Customer customer = customerRepository.findByPhone(customerJson.getString("phone"));
        if (customer == null){
            try {
                customer = new Customer(customerJson.getString("firstName"),
                        customerJson.getString("lastName"), customerJson.getString("phone"));
                webHook.send();
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }
            return customer;
        }
        return null;
    }
}
