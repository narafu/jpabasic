package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // DB당 1개
        EntityManager em = emf.createEntityManager(); // 고객의 요청이 올 때마다 사용하고 버린다.

        /** JPA 모든 데이터 변경은 트랜잭션 안에서 실행 */
        EntityTransaction tx = em.getTransaction(); // 트랜잭션
        tx.begin();

        try {
//            Member member = new Member();
//            member.setId(1L);
//            member.setName("HelloA");
//            em.persist(member); // insert
            /**
             * JPA를 통해 앤티티를 가져오면, JPA가 관리를 한다.
             * 값을 set하면 DB 데이터를 update한다.
             * */
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("update");
            tx.commit();

            // JPQL : 테이블이 아닌 '객체'를 대상으로 탐색
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .setFirstResult(5) // 페이징
                    .setMaxResults(8) // 페이징
                    .getResultList();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
