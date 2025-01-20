package seulgi.bookbookclub.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import seulgi.bookbookclub.domain.Book;
import seulgi.bookbookclub.domain.Member;

@Repository
public class BookRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Book book){
        em.persist(book);
    }

    public Book findByBookSeq(Long bookSeq){
        return em.find(Book.class, bookSeq);
    }
}
