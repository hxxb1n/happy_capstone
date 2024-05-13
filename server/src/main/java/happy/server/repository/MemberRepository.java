package happy.server.repository;

import happy.server.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(final Member member) {
        em.persist(member);
    }

    public Member fineOne(final Long id) {
        return em.find(Member.class, id);
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

}
