package happy.server.controller;

import happy.server.entity.Item;
import happy.server.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/item/new")
    public String createForm(Model model) {
        model.addAttribute("from", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/item/new")
    public String create(BookForm form) {

        Item item = new Item();
        item.setId(form.getId());
        item.setName(form.getName());
        item.setPrice(form.getPrice());
        item.setStockQuantity(form.getStockQuantity());

        itemService.save(item);
        return "redirect:/";
    }
}
