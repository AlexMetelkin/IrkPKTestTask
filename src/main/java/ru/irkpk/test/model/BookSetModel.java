
package ru.irkpk.test.model;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import java.util.List;
import ru.irkpk.test.entity.impl.AuthorEntity;
import ru.irkpk.test.entity.impl.BookEntity;
import ru.irkpk.test.service.ServiceProvider;
import ru.irkpk.test.service.impl.AuthorService;
import ru.irkpk.test.service.impl.BookService;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (18.11.2013)
 */
public class BookSetModel {
    
/**
 * 
 */
    public static final String BOOK_TITLE = "Название";
   
/**
 * 
 */
    public static final String BOOK_GENRE = "Жанр";
    
/**
 * 
 */
    static Object BOOK_AUTHOR_ID = "Id автора";
    
/**
 * 
 */
    //Caused an error: static container not refreshable
    //private static BookSetModel theModel = null;

/**
 * 
 */
    private IndexedContainer container = null;
    
/**
 * 
 */
    public static BookSetModel getModel() {
        //Caused an error: static container not refreshable
        //if ( theModel==null ) theModel = new BookSetModel();
        //return theModel;
        return new BookSetModel();
     }
    
/**
 * 
 * @return 
 */
    public IndexedContainer getContainer() {
        if ( container!=null ) return container;
        BookService bs = ServiceProvider.getProvider().getService(BookService.class);
        AuthorService as = ServiceProvider.getProvider().getService(AuthorService.class);
        container = new IndexedContainer();
        
        container.addContainerProperty(BOOK_TITLE, String.class, "");
        container.addContainerProperty(BOOK_GENRE, String.class, "");
        container.addContainerProperty(BOOK_AUTHOR_ID, Object.class, "");

        List<BookEntity> list = bs.readAll();
        for (BookEntity be : list) {
            Item item = container.addItem(be.getId());
            item.getItemProperty(BOOK_TITLE).setValue(be.getTitle());
            item.getItemProperty(BOOK_GENRE).setValue(be.getGenre());
            item.getItemProperty(BOOK_AUTHOR_ID).setValue(
                    be.getAuthor()!=null ? be.getAuthor().getId() : null
            );
        }

        return container;
    }
    
/**
 * 
 * @param ae 
 */
    public static void addItem(Container cnt, BookEntity be) {
        if ( be==null ) return;
        if ( cnt.containsId(be.getId()) ) return;
        Item item = cnt.addItem(be.getId());
        item.getItemProperty(BOOK_TITLE).setValue(be.getTitle());
        item.getItemProperty(BOOK_GENRE).setValue(be.getGenre());
        item.getItemProperty(BOOK_AUTHOR_ID).setValue(
                        be.getAuthor()!=null ? be.getAuthor().getId() : null
                );
    }
    
}

