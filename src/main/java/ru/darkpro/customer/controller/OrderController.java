package ru.darkpro.customer.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.darkpro.customer.dto.CustomerDto;
import ru.darkpro.customer.dto.OrderDto;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.entity.Order;
import ru.darkpro.customer.repository.CustomerRepository;
import ru.darkpro.customer.repository.OrderRepository;
import ru.darkpro.customer.service.WebHookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final WebHookService webHookService;
    private ModelMapper modelMapper;

    @GetMapping(path = "/order/{customerId}")
    public List<OrderDto> getAllOrders(@PathVariable long customerId) {
        Customer customer = customerRepository.findById(customerId);
        if (customer != null) {
            return modelMapper.map(customerRepository.findById(customerId), CustomerDto.class).getOrders();
        }
        return new ArrayList<>();
    }

    @PostMapping(path = "/order/new")
    public OrderDto addOrder(@RequestBody Order order) {
        Optional<Customer> customer = customerRepository.findById(order.getCustomer());
        if (customer.isPresent()) {
            orderRepository.save(order);
            webHookService.send(order);
            return modelMapper.map(order, OrderDto.class);
        }
        return new OrderDto();
    }

    @DeleteMapping(path = "/order/{customerId}/{orderId}")
    public OrderDto deleteOrder(@PathVariable long customerId, @PathVariable long orderId) {
        Order order = orderRepository.findById(orderId);
        if (order != null && order.getCustomer().equals(customerId)) {
            orderRepository.delete(order);
            return modelMapper.map(order, OrderDto.class);
        }
        return new OrderDto();
    }
}
