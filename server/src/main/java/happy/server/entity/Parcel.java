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
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parcel_id")
    private Long id;

    @OneToOne(mappedBy = "parcel")
    private Delivery delivery;

    private String trackingNumber;

    public Parcel() {
        this.trackingNumber = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
    }

}
