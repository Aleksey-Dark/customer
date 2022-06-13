package ru.darkpro.customer.controller;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import ru.darkpro.customer.dto.CustomerDto;
import ru.darkpro.customer.dto.OrderDto;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.entity.Order;
import ru.darkpro.customer.service.CustomerService;
import ru.darkpro.customer.service.OrderService;
import ru.darkpro.customer.service.WebHookService;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final WebHookService webHookService;
    private ModelMapper modelMapper;

    @GetMapping(path = "/order/{customerId}")
    public List<OrderDto> getAllOrders(@PathVariable long customerId) {
        Customer customer = customerService.get(customerId);
        if (customer != null) {
            return modelMapper.map(customer, CustomerDto.class).getOrders();
        }
        return new ArrayList<>();
    }

    @PostMapping(path = "/order/new")
    public OrderDto create(@RequestBody Order order) {
        Customer customer = customerService.get(order.getCustomer());
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        if (customer != null) {
            if (orderService.create(order)){
//                OrderDto orderDto = modelMapper.map(order, OrderDto.class);
                webHookService.send(orderDto);
                return orderDto;
            }
        }
        return new OrderDto();
    }

    @DeleteMapping(path = "/order/{customerId}/{orderId}")
    public OrderDto deleteOrder(@PathVariable long customerId, @PathVariable long orderId) {
        Order order = orderService.get(orderId);
        if (order != null && order.getCustomer() == customerId) {
            if (orderService.delete(order)) {
                return modelMapper.map(order, OrderDto.class);
            }
        }
        return new OrderDto();
    }
}
