package happy.server.service;

import happy.server.entity.Item;
import happy.server.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void save(Item item) {
        itemRepository.save(item);
    }

    // 아이템 전부 찾기
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // 아이템 하나 찾기
    public Item fineOne(Long id) {
        return itemRepository.fineOne(id);
    }

}
