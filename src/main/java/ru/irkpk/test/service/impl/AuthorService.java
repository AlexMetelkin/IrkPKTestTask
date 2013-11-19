
package ru.irkpk.test.service.impl;

import java.util.Set;
import ru.irkpk.test.entity.impl.AuthorEntity;
import ru.irkpk.test.entity.impl.BookEntity;
import ru.irkpk.test.entity.impl.CountryEntity;
import ru.irkpk.test.service.ServiceProvider;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (15.11.2013)
 */
public class AuthorService extends BaseService<AuthorEntity> {

/** 
 * 
 * @return 
 */
@Override
    protected Class<AuthorEntity> getBlankClass() {
        return AuthorEntity.class;
    }
    
/**
 * 
 * @param author
 */
@Override
    public void save(AuthorEntity author) {
        CountryService cs = ServiceProvider.getProvider().getService(CountryService.class);
        CountryEntity country = author.getCountry();
        if ( country!=null && !cs.contains(country) ) cs.save(country);
        if ( !contains(author) ) {
            super.save(author);
        } else {
            update(author);
        }
     }

/**
 * 
 * @param author 
 */
@Override
    public void update(AuthorEntity author) {
        if ( author==null )  return;
        super.update(author);
    }
        
/**
 * 
 * @param author
 * @param books 
 */
    public void addBook(AuthorEntity author, BookEntity... books) {
        if ( author==null || books==null || books.length==0 ) return;
        if ( !contains(author) ) save(author);
        BookService bs = ServiceProvider.getProvider().getService(BookService.class);
        for (BookEntity book : books) {
            ((BookEntity)book).setAuthor(author);
            bs.update(book);
        }
    }

/**
 * 
 * @param author
 * @return 
 */
    public Set<BookEntity> getBooks(AuthorEntity author) {
        if ( author==null ) throw new IllegalArgumentException("No instance of [" 
                + AuthorEntity.class.getName() 
                + "] provided");
        getEntityManager().refresh(author); 
        return author.getBooks();
    }

/**
 * 
 * @param book
 * @return 
 */
    public AuthorEntity getAuthor(BookEntity book) {
        if ( book==null ) throw new IllegalArgumentException("No instance of [" 
                + BookEntity.class.getName() 
                + "] provided");
        if ( getEntityManager().contains(book) ) {
            getEntityManager().refresh(book);
        }
        return book.getAuthor();
    }

/**
 * All the author's books will be deleted as well.
 * @param authorId 
 */
    public void delete(int authorId) {
        AuthorEntity ae = read(authorId);
        if ( ae==null ) return;
        super.delete(ae);
    }

}
