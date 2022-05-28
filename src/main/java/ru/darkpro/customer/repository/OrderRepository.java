package ru.darkpro.customer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.darkpro.customer.entity.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Order findById(long id);
}
