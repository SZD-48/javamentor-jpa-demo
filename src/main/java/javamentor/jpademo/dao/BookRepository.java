package javamentor.jpademo.dao;

import javamentor.jpademo.model.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    @EntityGraph(attributePaths = { "author" })
    Optional<Book> findById(Long aLong);
}
