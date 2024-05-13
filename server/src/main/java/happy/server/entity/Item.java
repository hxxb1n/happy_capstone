package happy.server.entity;

import happy.server.exception.NotEnoughStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 재고 수량 증가
    public void addStockQuantity(int quantity) {
        this.stockQuantity += quantity;
    }

    // 재고 감소
    public void removeStockQuantity(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("재고가 0보다 작아질 수 없습니다.");
        }
        this.stockQuantity = restStock;
    }

}
