package happy.server.repository;

import happy.server.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired OrderRepository orderRepository;

    @Test
    void findAllById() {
        List<Order> allById = orderRepository.findAllById(1011112222L);
        for (Order order : allById) {
            System.out.println("order = " + order);
        }
    }
}