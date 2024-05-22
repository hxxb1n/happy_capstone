package happy.server.api;

import happy.server.dto.OrderRequestDto;
import happy.server.dto.ParcelDto;
import happy.server.service.ParcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ParcelApi {

    private final ParcelService parcelService;

    @PostMapping("/api/myOrderParcelTrackingNumber")
    public ParcelDto myOrderParcelTrackingNumber(@RequestBody OrderRequestDto orderRequestDto) {
        log.info(orderRequestDto.toString());
        String trackingNumber = parcelService.myOrderParcelTrackingNumber(orderRequestDto.getOrderId());
        return new ParcelDto(trackingNumber);
    }

}
