package happy.server.api;

import happy.server.dto.MemberRequestDto;
import happy.server.dto.OrderDto;
import happy.server.dto.OrderResponseDto;
import happy.server.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    @PostMapping("/api/order")
    public String createOrder(@RequestBody OrderDto dto) {
        orderService.order(dto.getMemberId(), dto.getItemId(), dto.getCount());
        return "1";
    }

    @PostMapping("/api/myOrder")
    public List<OrderResponseDto> getMyOrder(@RequestBody MemberRequestDto dto) {
        return orderService.findOrdersDtoById(dto.getMemberId());
    }

    @PostMapping("/api/orderCancel/{orderId}")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "1";
    }

}
