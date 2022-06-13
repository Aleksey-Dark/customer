package ru.darkpro.customer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.darkpro.customer.entity.Order;
import ru.darkpro.customer.repository.OrderRepository;

import java.sql.SQLException;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @ParameterizedTest
    @MethodSource("getArgsForOrderServiceCreate")
    void createTest(Order order, boolean response){

        if (response) {
            Mockito.doReturn(order)
                    .when(orderRepository).save(ArgumentMatchers.eq(order));
        } else {
            Mockito.doReturn(new  Exception("Test"))
                    .when(orderRepository).save(ArgumentMatchers.eq(order));
        }

        var result = orderService.create(order);

        Assertions.assertEquals(result, response);
    }

    @ParameterizedTest
    @MethodSource("getArgsForOrderServiceGet")
    void getTest(long orderId, Order order){

        Mockito.doReturn(order)
                .when(orderRepository).findById(ArgumentMatchers.eq(orderId));

        var result = orderService.get(orderId);

        Assertions.assertEquals(result, order);
    }

    @ParameterizedTest
    @MethodSource("getArgsForOrderServiceDelete")
    void deleteTest(Order order, boolean response){

        if (response) {
            Mockito.doNothing()
                    .when(orderRepository).delete(ArgumentMatchers.eq(order));
        } else {
            Mockito.doThrow(new RuntimeException("Delete"))
                    .when(orderRepository).delete(ArgumentMatchers.eq(order));
        }

        var result = orderService.delete(order);

        Assertions.assertEquals(result, response);
    }

    public static Stream<Arguments> getArgsForOrderServiceCreate(){
        return Stream.of(
                Arguments.of(
                        new Order(1L, "value", "comment"), true
                ),
                Arguments.of(
                        new Order(2L, "value", "comment"), false
                )
        );
    }

    public static Stream<Arguments> getArgsForOrderServiceGet(){
        return Stream.of(
                Arguments.of(
                        1L, new Order(1L, "value", "comment")
                ),
                Arguments.of(
                        2L, new Order(2L, "value", "comment")
                ),
                Arguments.of(
                        3L, null
                )
        );
    }

    public static Stream<Arguments> getArgsForOrderServiceDelete(){
        return Stream.of(
                Arguments.of(
                        new Order(1L, "value", "comment"), true
                ),
                Arguments.of(
                        new Order(2L, "value", "comment"), false
                )
        );
    }
}
