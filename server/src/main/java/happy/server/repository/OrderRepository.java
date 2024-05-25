package happy.server.repository;


import happy.server.entity.Address;
import happy.server.entity.Order;
import happy.server.entity.OrderStatus;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    @Transactional
    public void save(Order order) {
        if (order.getId() == null) {
            em.persist(order);
        } else {
            em.merge(order);
        }
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

    public List<Order> findAllById(Long id) {
        return em.createQuery("select o from Order o join o.member m where m.id = :id", Order.class)
                .setParameter("id", id).getResultList();
    }

    public Order findOrderIdByTrackingNumber(String trackingNumber) {
        return em.createQuery("select o from Order o join o.delivery d join d.parcel p where p.trackingNumber = :trackingNumber", Order.class)
                .setParameter("trackingNumber", trackingNumber)
                .getSingleResult();
    }

    public List<Order> findAllDeliveryOrder() {
        return em.createQuery("select o from Order o where o.status = :status", Order.class)
                .setParameter("status", OrderStatus.ORDER)
                .getResultList();
    }

    public List<Order> findAllDeliveredOrder() {
        return em.createQuery("select o from Order o where o.status = :status", Order.class)
                .setParameter("status", OrderStatus.DELIVERED)
                .getResultList();
    }

    public Address findAddressByOrderId(Long orderId) {
        return em.createQuery("select m.address from Order o join o.member m where o.id = :orderId", Address.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }

}
