package ru.darkpro.customer.service;

import lombok.AllArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.context.annotation.Bean;
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

    public String register(String body){
        JSONObject customerJson = new JSONObject(new JSONTokener(body));
        if (validation(customerJson)) {
            Customer customer = customerRepository.findByPhone(customerJson.getString("phone"));
            if (customer == null) {
                customer = new Customer(customerJson.getString("firstName"),
                        customerJson.getString("lastName"), customerJson.getString("phone"));

                webHook.send();
                try {
                    customerRepository.save(customer);

                    return String.format("{\"id\":%s}", customer.getId());
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return "{\"id\":null}";
    }

//    @Bean
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public String register(JSONObject customerJson){
        Customer customer = customerRepository.findByPhone(customerJson.getString("phone"));
        if (customer == null) {
            customer = new Customer(customerJson.getString("firstName"),
                    customerJson.getString("lastName"), customerJson.getString("phone"));

            webHook.send();
            customerRepository.save(customer);
        }
        return String.format("{\"id\":%s}", customer.getId());
    }

    public boolean validation(JSONObject customerJson){
        try {
            return customerJson.getString("phone") != null
                    && customerJson.getString("firstName") != null
                    && customerJson.getString("lastName") != null;
        } catch (JSONException e) {
            return false;
        }
    }
}
