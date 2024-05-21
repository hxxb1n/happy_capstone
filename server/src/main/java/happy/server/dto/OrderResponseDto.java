package happy.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderResponseDto {
    private Long orderId;
    private LocalDateTime orderDate;
    private String itemName;
    private int count;
    private int orderPrice;
    private String status;
}
