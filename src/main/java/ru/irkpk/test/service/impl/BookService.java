
package ru.irkpk.test.service.impl;

import ru.irkpk.test.entity.impl.AuthorEntity;
import ru.irkpk.test.entity.impl.BookEntity;
import ru.irkpk.test.entity.impl.CountryEntity;
import ru.irkpk.test.service.ServiceProvider;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (16.11.2013)
 */
public class BookService extends BaseService<BookEntity> {

/**
 * 
 * @return 
 */
@Override
    protected Class<BookEntity> getBlankClass() {
        return BookEntity.class;
    }

/**
 * 
 * @param book
 */
@Override
    public void save(BookEntity book) {
        AuthorService as = ServiceProvider.getProvider().getService(AuthorService.class);
        AuthorEntity author = ((BookEntity)book).getAuthor();
        if ( author!=null && !as.contains(author) ) as.save(author);
        super.save(book);
     }

/**
 * 
 * @param book 
 */
@Override
    public void update(BookEntity book) {
        if ( book==null ) return;
        AuthorEntity author = ((BookEntity)book).getAuthor();
        AuthorService as = ServiceProvider.getProvider().getService(AuthorService.class);
        if ( !as.contains(author) ) as.save(author);
        super.update(book);
    }

/**
 * 
 * @param book
 * @param author 
 */
    public void setAuthor(BookEntity book, AuthorEntity author) { 
        book.setAuthor(author);
    }
    
}
