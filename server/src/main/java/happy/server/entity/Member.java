package happy.server.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "member")
@Getter
public class Member {

    @Id
    @Column(name = "member_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Embedded
    private Address address;

    protected Member() {
    }

    public Member(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeAddress(Address address) {
        this.address = address;
    }

}
