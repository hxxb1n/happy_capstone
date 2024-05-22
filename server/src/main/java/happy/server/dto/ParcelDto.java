package happy.server.dto;

import lombok.Data;

@Data
public class ParcelDto {
    private String trackingNumber;

    public ParcelDto(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
