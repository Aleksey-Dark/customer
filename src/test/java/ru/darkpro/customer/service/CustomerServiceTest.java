package ru.darkpro.customer.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.darkpro.customer.entity.Customer;
import ru.darkpro.customer.repository.CustomerRepository;

import java.util.Objects;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
//    @Spy
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

//    @Captor
//    ArgumentCaptor<Long> longCaptor;

//    @Captor
//    ArgumentCaptor<Customer> customerCaptor;

//    @BeforeEach
//    void setUp() {
//        customerService = new CustomerService(customerRepository);
//    }

//    @Test
    @ParameterizedTest
    @MethodSource("getArgsForCustomerServiceGet")
    void getTest(long customerId, Customer customer){

        Mockito.doReturn(customer)
                .when(customerRepository).findById(ArgumentMatchers.eq(customerId));

        var result = customerService.get(customerId);

        Assertions.assertEquals(result, customer);

//        Mockito.verify(customerRepository).findById(longCaptor.capture());
//        Mockito.verify(customerRepository).findById();

//        if (customer) {
//            Assertions.assertNotNull(result);
//        }
////        Assertions.assertNotNull(result);
//        Assertions.assertEquals(customer, result);
    }

//    @Test
    @ParameterizedTest
    @MethodSource("getArgsForCustomerServiceRegister")
    void registerTest(Customer customer, boolean response){
//        Mockito.when(customerRepository.save(ArgumentMatchers.eq(customer)))
//                .thenReturn(customer);
        if (response) {
            Mockito.doReturn(customer)
                    .when(customerRepository).save(ArgumentMatchers.eq(customer));
        } else {
            Mockito.doThrow(new RuntimeException("Register"))
                    .when(customerRepository).save(ArgumentMatchers.eq(customer));
        }
        var result = customerService.register(customer);

        Assertions.assertEquals(result, response);

//        Mockito.verify(customerRepository).save(customerCaptor.capture());

//        Assertions.assertNotNull(result);
//        Assertions.assertTrue(result);
//        Assertions.assertNotNull(second_result);
//        Assertions.assertFalse(second_result);
//        Assertions.assertEquals(customerCaptor.getValue(), customerCaptor.getValue());
//        System.out.println(customerCaptor.getValue());

    }

    public static boolean customerValidator(Customer customer, Customer result){
        return customer.getFirstName() == null
                && result.getFirstName() != null
                && Objects.equals(customer.getPhone(), result.getPhone());
    }

    public static Stream<Arguments> getArgsForCustomerServiceGet(){
        return Stream.of(
                Arguments.of(
                        1L, new Customer("firstName", "lastName", "+70000000000")
                ),
                Arguments.of(
                        2L, new Customer("firstName", "lastName", "+80000000000")
                ),
                Arguments.of(
                        3L, null
                )
        );
    }

    public static Stream<Arguments> getArgsForCustomerServiceRegister(){
        return Stream.of(
                Arguments.of(
                        new Customer("firstName", "lastName", "+70000000000"), true
                ),
                Arguments.of(
                        new Customer("firstName", "lastName", "+80000000000"), false
                )
        );
    }
}
