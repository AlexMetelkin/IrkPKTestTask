
package ru.irkpk.test.ui;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.Set;
import ru.irkpk.test.entity.impl.AuthorEntity;
import ru.irkpk.test.entity.impl.BookEntity;
import ru.irkpk.test.model.AuthorSetModel;
import ru.irkpk.test.model.BookSetModel;
import ru.irkpk.test.service.ServiceProvider;
import ru.irkpk.test.service.impl.AuthorService;
import ru.irkpk.test.service.impl.BookService;


/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (18.11.2013)
 */
class MainUIAction {
   
/**
 * 
 */
    private MainUI mainUI = null;
    
/**
 * 
 * @param ui 
 */
    public MainUIAction(MainUI ui) {
        mainUI = ui;
    }
    
/**
 * 
 */
    public void removeAuthor(Object authorId) {
        assert ( authorId instanceof Integer ) : "An instance of ["
                + Integer.class.getName() + "] expected, but ["
                + ((authorId!=null) ? authorId.getClass().getName() : "null")
                + "] acually provided";
        //Warning
        Window w = authorRemovalWarning(authorId);
        UI.getCurrent().addWindow(w);
    }
        
/**
 * 
 */
    public void removeAuthorNoConfirm(Object authorId, boolean deleteBooks) {
        if ( authorId==null ) return;
        AuthorService as = ServiceProvider.getProvider().getService(AuthorService.class);
        BookService bs = ServiceProvider.getProvider().getService(BookService.class);
        AuthorEntity ae = as.read((Integer)authorId);
        Set<BookEntity> books = as.getBooks(ae);
        
        Table bookTable = mainUI.getMainLayout().getBookTable();
        for ( BookEntity book : books ) {
            if ( deleteBooks ) {
                bs.delete(book);
                bookTable.removeItem(book.getId());
            } else {
                bs.setAuthor(book, null);
            }
        }
        
        as.delete(ae);
        mainUI.getMainLayout().getAuthorTable().removeItem(authorId);
    }
    
/**
 * 
 */
    public Window authorRemovalWarning(final Object authorId) {
        final Window w = new Window("Предупреждение");
        w.setResizable(false);
        w.setModal(true);
        
        VerticalLayout content = new VerticalLayout();
        w.setContent(content);
        content.setMargin(true);
// Warning area
        HorizontalLayout layout = new HorizontalLayout();
        content.addComponent(layout);
        Label label = new Label();
        AuthorEntity ae = ServiceProvider.getProvider().getService(AuthorService.class).read((Integer)authorId);
        label.setContentMode(ContentMode.HTML);
        label.setValue("Подтвердите удаление автора \"<b>"
                + ae.getName()
                + "</b>\".<br/>Выбранный автор будет удален из базы без возможности восстановления!");
        layout.addComponent(label);
// Selection area: delete author's book as well or no
        layout = new HorizontalLayout();
        content.addComponent(layout);
        layout.setMargin(true);
        final CheckBox chb = new CheckBox("Удалить также все книги указанного автора.", false);
        layout.addComponent(chb);
        //layout.setSizeFull();
// Ok, Cancel buttons
        layout = new HorizontalLayout();
        content.addComponent(layout);
        layout.setSpacing(true);
        layout.setSizeFull();
        
        FormLayout filler = new FormLayout();
        layout.addComponent(filler);
        filler.setWidth("100%");
        layout.setExpandRatio(filler, 1);
        
        Button btnOk = new Button("Принять");
        btnOk.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                w.close();
                removeAuthorNoConfirm(authorId, chb.getValue());
            }
        });
        layout.addComponent(btnOk);
        
        Button btnCancel = new Button("Отмена");
        btnCancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                w.close();
            }
        });
        layout.addComponent(btnCancel);
        
        return w;
     }
    
/**
 * 
 */
    public void removeBook(Object bookId) {
        assert ( bookId instanceof Integer ) : "An instance of ["
                + Integer.class.getName() + "] expected, but ["
                + ((bookId!=null) ? bookId.getClass().getName() : "null")
                + "] acually provided";
        //Warning
        Window w = bookRemovalWarning(bookId);
        UI.getCurrent().addWindow(w);
    }
        
/**
 * 
 */
    public void removeBookNoConfirm(Object bookId) {
        if ( bookId==null ) return;
        BookService bs = ServiceProvider.getProvider().getService(BookService.class);
        BookEntity be = bs.read((Integer)bookId);
        bs.delete(be);
        mainUI.getMainLayout().getBookTable().removeItem(bookId);
    }
    
/**
 * 
 */
    public Window bookRemovalWarning(final Object bookId) {
        final Window w = new Window("Предупреждение");
        w.setResizable(false);
        w.setModal(true);
        
        VerticalLayout content = new VerticalLayout();
        w.setContent(content);
        content.setMargin(true);
        content.setSpacing(true);
// Warning area
        HorizontalLayout layout = new HorizontalLayout();
        content.addComponent(layout);
        Label label = new Label();
        BookEntity be = ServiceProvider.getProvider().getService(BookService.class).read((Integer)bookId);
        label.setContentMode(ContentMode.HTML);
        label.setValue("Подтвердите удаление книги \"<b>"
                + be.getTitle()
                + "</b>\".<br/>Выбранная книга будет удалена из базы без возможности восстановления!");
        layout.addComponent(label);
// Ok, Cancel buttons
        layout = new HorizontalLayout();
        content.addComponent(layout);
        layout.setSpacing(true);
        layout.setSizeFull();
        
        FormLayout filler = new FormLayout();
        layout.addComponent(filler);
        filler.setWidth("100%");
        layout.setExpandRatio(filler, 1);
        
        Button btnOk = new Button("Принять");
        btnOk.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                w.close();
                removeBookNoConfirm(bookId);
            }
        });
        layout.addComponent(btnOk);
        
        Button btnCancel = new Button("Отмена");
        btnCancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                w.close();
            }
        });
        layout.addComponent(btnCancel);
        
        return w;
     }

/**
 * 
 * @param authorId 
 */
    public void editOrCreateAuthor(Object authorId) {
        AuthorEntity ae = null;
        AuthorService as = ServiceProvider.getProvider().getService(AuthorService.class);
        if ( authorId!=null ) ae = as.read((Integer)authorId);
        if ( ae==null ) ae = as.create();
        AuthorEditWindow aew = new AuthorEditWindow();
        aew.setAuthor(ae);
        UI.getCurrent().addWindow(aew); 
    }

/**
 * 
 */
    public void saveAuthor(AuthorEntity ae) {
        if ( ae==null ) return;
        AuthorService as = ServiceProvider.getProvider().getService(AuthorService.class);
        as.save(ae);
        Container cnt = ((MainUI)UI.getCurrent()).getMainLayout().getAuthorTable().getContainerDataSource();
        AuthorSetModel.addItem(cnt, ae);
     }

/**
 * 
 * @param authorId 
 */
    public void editOrCreateBook(Object bookId) {
        BookEntity be = null;
        BookService bs = ServiceProvider.getProvider().getService(BookService.class);
        if ( bookId!=null ) be = bs.read(bookId);
        if ( be==null ) be = bs.create();
        BookEditWindow bew = new BookEditWindow();
        bew.setBook(be);
        UI.getCurrent().addWindow(bew);
    }
    
/**
 * 
 * @param book 
 */
    void saveBook(BookEntity be) {
        if ( be==null ) return;
        BookService bs = ServiceProvider.getProvider().getService(BookService.class);
        bs.save(be);
        Container cnt = ((MainUI)UI.getCurrent()).getMainLayout().getBookTable().getContainerDataSource();
        BookSetModel.addItem(cnt, be);
    }

}
