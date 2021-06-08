package javamentor.jpademo.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "authors")
@NamedQuery(name = "findByMiddleName", query = "select a from Author a where a.fullName.middleName like :middleName")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    @Embedded
    private FullName fullName = new FullName();

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Transient
    private Date birthDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
        this.fullName = fullName;
    }

    public void addBook(Book book) {
        book.setAuthor(this);
        getBooks().add(book);
    }
}
