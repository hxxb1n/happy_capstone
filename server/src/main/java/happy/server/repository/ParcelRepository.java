package happy.server.repository;

import happy.server.entity.Parcel;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ParcelRepository {

    private final EntityManager em;

    public Parcel findParcelByOrderId(final Long orderId) {
        return em.createQuery("SELECT p FROM Parcel p JOIN p.delivery d JOIN d.order o WHERE o.id = :orderId", Parcel.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }

}
