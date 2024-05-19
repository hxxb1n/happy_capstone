package happy.server.dto;

import lombok.Data;

@Data
public class OrderDto {
    private Long memberId;
    private Long itemId;
    private int count;
}
