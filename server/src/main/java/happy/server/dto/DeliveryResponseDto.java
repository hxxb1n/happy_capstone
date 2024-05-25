package happy.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseDto {
    private Long orderId;
    private String city;
    private String street;
    private String zip;
    private String status;
    private String trackingNumber;
}
