package happy.server.api;

import happy.server.dto.OrderDto;
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

}
