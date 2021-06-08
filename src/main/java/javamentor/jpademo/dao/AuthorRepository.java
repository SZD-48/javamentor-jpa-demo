package javamentor.jpademo.dao;

import javamentor.jpademo.model.Author;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AuthorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Author author) {
        entityManager.persist(author);
    }

    public void delete(Author author) {
        entityManager.remove(author);
    }

    public Author findById(Long id) {
        return entityManager.find(Author.class, id);
    }

    public List<Author> findByFirstName(String firstName) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery("from Author a where fullName.firstName like :firstName")
                .setParameter("firstName", firstName);
        return query.list();
    }

    public List<Author> findByLastName(String lastName) {
        javax.persistence.Query query =
                entityManager.createQuery("select a from Author a where a.fullName.lastName like :lastName")
                        .setParameter("lastName", lastName);
        return query.getResultList();
    }

    public List<Author> findByMiddleName(String middleName) {
        TypedQuery<Author> query = entityManager.createNamedQuery("findByMiddleName", Author.class);
        query.setParameter("middleName", middleName);
        return query.getResultList();
    }

    public List<Author> findByFirstNameAndLastName(String firstName, String lastName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> authorCriteriaQuery = criteriaBuilder.createQuery(Author.class);
        Root<Author> authorRoot = authorCriteriaQuery.from(Author.class);
        authorCriteriaQuery.select(authorRoot).where(
                criteriaBuilder.equal(authorRoot.get("fullName").get("firstName"), firstName),
                criteriaBuilder.equal(authorRoot.get("fullName").get("lastName"), lastName)
        );

        return entityManager.createQuery(authorCriteriaQuery).getResultList();
    }

    public List<BigInteger> findIdsByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName) {
        javax.persistence.Query nativeQuery = entityManager.createNativeQuery(
                "SELECT a.id from authors a WHERE a.first_name = ? and a.middle_name = ? and a.last_name = ?");
        nativeQuery.setParameter(1, firstName);
        nativeQuery.setParameter(2, middleName);
        nativeQuery.setParameter(3, lastName);

        return nativeQuery.getResultList();
    }

    public List<Author> findAllWithBooks() {
        EntityGraph<Author> entityGraph = entityManager.createEntityGraph(Author.class);
        entityGraph.addAttributeNodes("books");

        javax.persistence.Query query = entityManager.createQuery("select a from Author a")
                .setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

}
