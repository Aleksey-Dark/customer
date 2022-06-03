package ru.darkpro.customer.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.entity.Order;
import ru.darkpro.customer.repository.CustomerRepository;
import ru.darkpro.customer.repository.OrderRepository;
import ru.darkpro.customer.service.WebHookService;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final WebHookService webHookService;

    @GetMapping(path = "/order/{customerId}")
    public List<Order> getAllOrders(@PathVariable long customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer != null) {
            return customer.getOrders();
        }
        return null;
    }

    @PostMapping(path = "/order/new")
    public Order addOrder(@RequestBody Order order) {
        Optional<Customer> customer = customerRepository.findById(order.getCustomer());
        if (customer.isPresent()) {
            orderRepository.save(order);
            webHookService.send(order);
            return order;
        }
        return null;
    }

    @DeleteMapping(path = "/order/{customerId}/{orderId}")
    public String deleteOrder(@PathVariable long customerId, @PathVariable long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order != null && order.getCustomer().equals(customerId)) {
            orderRepository.delete(order);
            return String.format("{\"id\":%s}", order.getId());
        }
        return "{\"id\":null}";
    }
}
