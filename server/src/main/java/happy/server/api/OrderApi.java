package happy.server.api;

import happy.server.dto.DeliveryResponseDto;
import happy.server.dto.MemberRequestDto;
import happy.server.dto.OrderDto;
import happy.server.dto.OrderResponseDto;
import happy.server.entity.Address;
import happy.server.entity.Order;
import happy.server.repository.OrderRepository;
import happy.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderApi {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/api/order")
    public String createOrder(@RequestBody OrderDto dto) {
        log.info("Create order : {}", dto);
        orderService.order(dto.getMemberId(), dto.getItemId(), dto.getCount());
        return "1";
    }

    @PostMapping("/api/myOrder")
    public List<OrderResponseDto> getMyOrder(@RequestBody MemberRequestDto dto) {
        log.info("Get my order : {}", dto);
        return orderService.findOrdersDtoById(dto.getMemberId());
    }

    @PostMapping("/api/orderCancel/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        log.info("Cancel order : {}", orderId);
        orderService.cancelOrder(orderId);
        return "1";
    }

    @PostMapping("/api/deliveryOrder")
    public List<DeliveryResponseDto> deliveryOrder() {
        List<Order> allDeliveryOrders = orderRepository.findAllDeliveryOrder();
        return allDeliveryOrders.stream()
                .map(order -> {
                    Address address = orderRepository.findAddressByOrderId(order.getId());
                    String trackingNumber = order.getDelivery().getParcel().getTrackingNumber();
                    return new DeliveryResponseDto(
                            order.getId(),
                            address.getCity(),
                            address.getStreet(),
                            address.getZip(),
                            order.getStatus().toString(),
                            trackingNumber
                    );
                })
                .collect(Collectors.toList());
    }

}
