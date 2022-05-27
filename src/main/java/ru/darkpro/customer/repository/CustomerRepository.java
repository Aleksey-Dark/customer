package ru.darkpro.customer.repository;

import org.springframework.data.repository.CrudRepository;
import ru.darkpro.customer.entity.Customer;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
//    Customer registerCustomer(String firstName, String lastName);

    Customer findById(long id);

    Customer findByPhone(String phone);
}
