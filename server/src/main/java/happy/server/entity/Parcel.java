package happy.server.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parcel_id")
    private Long id;

    @OneToOne(mappedBy = "parcel")
    private Delivery delivery;

    private String trackingNumber;

    public void generateTrackingNumber() {
        trackingNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);
    }

}
