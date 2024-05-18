package happy.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {

    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty
    private String name;

    @Column(name = "password", nullable = false)
    @NotEmpty
    private String password;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeAddress(Address address) {
        this.address = address;
    }

}
