package ru.darkpro.customer.service;

import lombok.AllArgsConstructor;
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

    public Customer get(@PathVariable long customerId) {
        return customerRepository.findById(customerId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public boolean register(Customer customer){
        try {
            customerRepository.save(customer);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean validation(Customer customer){
        return customer.getFirstName() != null
                && customer.getLastName() != null
                && customer.getPhone() != null
                && customerRepository.findByPhone(customer.getPhone()) == null;
    }
}
