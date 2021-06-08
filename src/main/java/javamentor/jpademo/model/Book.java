package javamentor.jpademo.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.SortedSet;

@Entity
@Table(name = "books")
@NamedEntityGraph(name = "with-author", attributeNodes = { @NamedAttributeNode("author") })
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = { @JoinColumn(name = "book_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "genre_id", referencedColumnName = "id") }
    )
    @OrderBy("title")
    private Set<Genre> genres = new LinkedHashSet<>();

    private Integer pageCount;

    private String isbn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
