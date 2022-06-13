package ru.darkpro.customer.service;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.darkpro.customer.dto.OrderDto;
import ru.darkpro.customer.entity.Order;
import ru.darkpro.customer.repository.OrderRepository;

@Service
@AllArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    public boolean create(Order order){
        try {
            orderRepository.save(order);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Order get(long orderId){
        return orderRepository.findById(orderId);
    }

    public boolean delete(Order order){
        try {
            orderRepository.delete(order);
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
