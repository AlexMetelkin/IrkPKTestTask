
package ru.irkpk.test.model;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (19.11.2013)
 */
public class BookFilterByAuthor implements Container.Filter {

/**
 * 
 * @return 
 */
    private Object authorId = null;
    
/**
 * 
 * @param id 
 */
    public void setAuthorId(Object id) {
        authorId = id;
    }
    
    @Override
    public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
        if ( authorId==null ) return true;
        return authorId.equals(item.getItemProperty(BookSetModel.BOOK_AUTHOR_ID).getValue());
    }

    @Override
    public boolean appliesToProperty(Object propertyId) {
        return BookSetModel.BOOK_AUTHOR_ID.equals(propertyId);
    }

}
