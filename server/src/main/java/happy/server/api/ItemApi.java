package happy.server.api;

import happy.server.entity.Item;
import happy.server.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemApi {

    private final ItemService itemService;

    @GetMapping("/api/items")
    public List<Item> list() {
        return itemService.findItems();
    }

    @GetMapping("/api/item/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemService.fineOne(id);
    }

}
