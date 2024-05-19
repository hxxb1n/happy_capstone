package happy.server.controller;

import lombok.Data;

@Data
public class ItemForm {

    private Long id;
    private String name;
    private int price;
    private int stockQuantity;
    private String description;

}
