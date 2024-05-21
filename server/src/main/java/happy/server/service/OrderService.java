package happy.server.service;

import happy.server.dto.OrderResponseDto;
import happy.server.entity.*;
import happy.server.repository.ItemRepository;
import happy.server.repository.MemberRepository;
import happy.server.repository.OrderRepository;
import happy.server.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문 서비스
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.fineOne(memberId);
        Item item = itemRepository.fineOne(itemId);
        // 배송정보 만들기
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        // 주문상품 만들기
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // 주문 생성
        Order order = Order.crateOrder(member, delivery, orderItem);
        // 데이터베이스에 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // 주문 검색
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByString(orderSearch);
    }

    public List<OrderResponseDto> findOrdersDtoById(Long id) {
        List<Order> orders = orderRepository.findAllById(id);
        return orders.stream()
                .flatMap(order -> order.getOrderItems().stream()
                        .map(orderItem -> new OrderResponseDto(
                                order.getId(),
                                order.getOrderDate(),
                                orderItem.getItem().getName(),
                                orderItem.getCount(),
                                orderItem.getOrderPrice(),
                                order.getStatus().toString()
                        )))
                .collect(Collectors.toList());
    }

}
