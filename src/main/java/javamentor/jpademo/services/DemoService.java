package javamentor.jpademo.services;

import javamentor.jpademo.dao.AuthorRepository;
import javamentor.jpademo.dao.BookRepository;
import javamentor.jpademo.dao.GenreRepository;
import javamentor.jpademo.model.Author;
import javamentor.jpademo.model.Book;
import javamentor.jpademo.model.FullName;
import javamentor.jpademo.model.Genre;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class DemoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    public DemoService(AuthorRepository authorRepository, BookRepository bookRepository, GenreRepository genreRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional
    public Author createAuthor(String firstName, String middleName, String lastName) {
        FullName fullName = new FullName();
        fullName.setFirstName(firstName);
        fullName.setMiddleName(middleName);
        fullName.setLastName(lastName);

        Author author = new Author();
        author.setFullName(fullName);

        authorRepository.create(author);
        return author;
    }

    public void createAuthorWithoutTransaction(String firstName, String middleName, String lastName) {
        createAuthor(firstName, middleName, lastName);
    }

    @Transactional
    public boolean equalsTest() {
        Author author1 = authorRepository.findById(1L);
        Author author2 = authorRepository.findById(1L);
        authorRepository.findById(2L);
        return author1 == author2;
    }

    @Transactional
    public Book fetchStrategyDemo() {
        Book book = bookRepository.findById(1L).get();
        return book;
    }

    @Transactional
    public int hqlDemo() {
        return authorRepository.findByFirstName("Иван").size();
    }

    @Transactional
    public int jpqlDemo() {
        return authorRepository.findByLastName("Иванов").size();
    }

    @Transactional
    public int criteriaApiDemo() {
        return authorRepository.findByFirstNameAndLastName("Иван", "Иванов").size();
    }

    @Transactional
    public int nativeSqlDemo() {
        return authorRepository.findIdsByFirstNameAndMiddleNameAndLastName("Иван", "Иванович", "Иванов").size();
    }

    @Transactional
    public int namedQueryDemo() {
        return authorRepository.findByMiddleName("Иванович").size();
    }

    @Transactional
    public void entityGraphDemo() {
        for (Author author : authorRepository.findAllWithBooks()) {
            author.getBooks().size();
        }
    }

    @Transactional
    public void testRefresh() {
        Author author = authorRepository.findById(1L);
        author.getBooks().size();

        Book book = new Book();
        book.setAuthor(author);
        book.setTitle("Новая книга");
        book.setPageCount(30);
        book.setIsbn("1234");

        bookRepository.save(book);
        System.out.println("After save: " + author.getBooks().size());

        entityManager.flush();
        System.out.println("After flush: " + author.getBooks().size());

        entityManager.refresh(author);
        System.out.println("After refresh author: " + author.getBooks().size());
    }

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public void nPlusOneDemo() {
        Book book = bookRepository.findById(1L).get();
    }

    @Transactional
    public void listTest() {
        Book book = bookRepository.findById(3L).get();
        Genre genre = book.getGenres().iterator().next();
        book.getGenres().remove(genre);
    }
}
