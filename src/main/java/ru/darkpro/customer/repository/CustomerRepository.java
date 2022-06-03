package ru.darkpro.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.darkpro.customer.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findById(long id);

    Customer findByPhone(String phone);

}
