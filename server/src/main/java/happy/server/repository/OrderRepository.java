package happy.server.repository;


import happy.server.entity.Order;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAllByString(OrderSearch orderSearch) {
        StringBuilder jpql = new StringBuilder("select o From Order o join o.member m");
        boolean isFirstCondition = true;
        // 주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            jpql.append(" where").append(" o.status = :status");
            isFirstCondition = false;
        }
        // 회원 이름 검색
        if (StringUtils.isNotBlank(orderSearch.getMemberName())) {
            jpql.append(isFirstCondition ? " where" : " and").append(" m.name like :name");
        }
        TypedQuery<Order> query = em.createQuery(jpql.toString(), Order.class)
                .setMaxResults(1000); // 최대 1000건
        if (orderSearch.getOrderStatus() != null) {
            query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.isNotBlank(orderSearch.getMemberName())) {
            query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }

}
