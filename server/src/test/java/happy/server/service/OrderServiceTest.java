package happy.server.service;

import happy.server.entity.*;
import happy.server.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = new Member(1234L, "park", "1234");
        member.changeAddress(new Address("마산", "월영", "44554"));
        em.persist(member);
        Item item = new Item();
        item.setName("티켓");
        item.setPrice(12000);
        item.setStockQuantity(10);
        em.persist(item);

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태: ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야함");
        assertEquals(12000 * 2, getOrder.getTotalPrice(), "가격은 상품 * 수량");
        assertEquals(8, item.getStockQuantity(), "주문한 수량만큼 재고가 줄어야함");
    }

    @Test
    public void 주문취소() throws Exception {
        //given

        //when

        //then
    }

    @Test
    public void 재고수량초과() throws Exception {
        //given

        //when

        //then
    }

}