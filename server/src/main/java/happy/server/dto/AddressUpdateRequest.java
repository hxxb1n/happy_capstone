package happy.server.dto;

import happy.server.entity.Address;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddressUpdateRequest {

    @NotEmpty
    private Long memberId;
    private Address address;

}
