package happy.server.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zip;

    protected Address() {
    }

    public Address(String city, String street, String zip) {
        this.city = city;
        this.street = street;
        this.zip = zip;
    }

}
