package happy.server.repository;

import happy.server.entity.Parcel;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class ParcelRepository {

    private final EntityManager em;

    public Parcel findParcelByOrderId(final Long orderId) {
        return em.createQuery("select p from Parcel p join p.delivery d join d.order o where o.id = :orderId", Parcel.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }

    public Parcel findParcelByTrackingNumber(final String trackingNumber) {
        return em.createQuery("select p from Parcel p where p.trackingNumber = :trackingNumber", Parcel.class)
                .setParameter("trackingNumber", trackingNumber)
                .getSingleResult();
    }

    @Transactional
    public void updateParcelStatusToExpired(final String trackingNumber) {
        final Parcel p = findParcelByTrackingNumber(trackingNumber);
        p.setTrackingNumber("EXPIRED");
        em.merge(p);
    }

}
