package happy.server.repository;

import happy.server.entity.Item;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    // 아이템 등록, 수정
    public void save(final Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    // 아이템 하나 찾기
    public Item fineOne(final long id) {
        return em.find(Item.class, id);
    }

    // 아이템 전부 찾기
    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }

}
