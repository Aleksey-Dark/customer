package ru.darkpro.customer.controller;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.*;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.entity.Order;
import ru.darkpro.customer.repository.CustomerRepository;
import ru.darkpro.customer.repository.OrderRepository;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;

    @GetMapping(path = "/order/{userId}")
    public List<Order> getAllOrders(@PathVariable long userId) {
        Customer customer = customerRepository.findById(userId);
        if (customer != null) {
            return customer.getOrders();
        }
        return null;
    }

    @PostMapping(path = "/order/add/{customerId}")
    public Order addOrder(@PathVariable long customerId, @RequestBody String data) {
        JSONObject customerJson = new JSONObject(new JSONTokener(data));
        Customer customer = customerRepository.findById(customerId);
        if (customer != null) {
            return orderRepository.save(new Order(customer.getId(), customerJson.getString("value"),
                    customerJson.getString("comment")));
        }
        return null;
    }

    @DeleteMapping(path = "/order/delete/{customerId}/{orderId}")
    public Order deleteOrder(@PathVariable long customerId, @PathVariable long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order != null && order.getCustomerId().equals(customerId)) {
            orderRepository.delete(order);
        }
        return null;
    }
}
