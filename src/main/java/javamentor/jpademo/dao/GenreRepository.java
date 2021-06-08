package javamentor.jpademo.dao;

import javamentor.jpademo.model.Genre;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genre, Long> {
}
