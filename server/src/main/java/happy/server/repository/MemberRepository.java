package happy.server.repository;

import happy.server.entity.Address;
import happy.server.entity.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(final Member member) {
        em.persist(member);
    }

    public void update(final Member member) {
        em.merge(member);
    }

    public Member fineOne(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public Member findById(final long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }

    public void delete(final Member member) {
        em.remove(member);
    }

    public List<Member> fineByName(final String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name).getResultList();
    }

    public Optional<Member> findByLoginId(final Long memberId) {
        return findAll().stream().filter(m -> Objects.equals(m.getId(), memberId)).findFirst();
    }

    public Address findAddressByOrderId(Long orderId) {
        return em.createQuery("select m.address from Order o join o.member m where o.id = :orderId", Address.class)
                .setParameter("orderId", orderId)
                .getSingleResult();
    }

}
