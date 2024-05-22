package happy.server.api;

import happy.server.dto.OrderRequestDto;
import happy.server.dto.ParcelDto;
import happy.server.entity.Order;
import happy.server.entity.Parcel;
import happy.server.repository.OrderRepository;
import happy.server.repository.ParcelRepository;
import happy.server.service.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ParcelApi {

    private final ParcelService parcelService;
    private final ParcelRepository parcelRepository;
    private final OrderRepository orderRepository;

    @PostMapping("/api/myOrderParcelTrackingNumber")
    public ParcelDto myOrderParcelTrackingNumber(@RequestBody OrderRequestDto orderRequestDto) {
        log.info(orderRequestDto.toString());
        String trackingNumber = parcelService.myOrderParcelTrackingNumber(orderRequestDto.getOrderId());
        return new ParcelDto(trackingNumber);
    }

    /**
     * 기사가 문 출입할 때 송장 번호를 일단 받음
     * 송장번호가 parcel 테이블에 존재하는지 먼저 확인
     * 존재하면 주문번호를 찾음
     * 주문 상태를 배송완료로 지정
     */
    @PostMapping("/api/enter")
    public ResponseEntity<Boolean> enter(@RequestBody ParcelDto parcelDto) {
        String trackingNumber = parcelDto.getTrackingNumber();
        log.info(trackingNumber);
        Parcel find = parcelRepository.findParcelByTrackingNumber(trackingNumber);
        if (find != null) {
            Order order = orderRepository.findOrderIdByTrackingNumber(find.getTrackingNumber());
            order.complete();
            parcelRepository.updateParcelStatusToExpired(trackingNumber);
            orderRepository.save(order);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.status(404).body(false);
        }
    }

}
