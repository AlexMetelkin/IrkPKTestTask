
package ru.irkpk.test.ui;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.List;
import ru.irkpk.test.entity.impl.AuthorEntity;
import ru.irkpk.test.entity.impl.BookEntity;
import ru.irkpk.test.entity.impl.CountryEntity;
import ru.irkpk.test.service.ServiceProvider;
import ru.irkpk.test.service.impl.AuthorService;
import ru.irkpk.test.service.impl.CountryService;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (19.11.2013)
 */
public class BookEditWindow extends Window {
   
/**
 * 
 */
    AuthorService authorService = ServiceProvider.getProvider().getService(AuthorService.class);
    
/**
 * 
 */
    private BookEntity book = null;
    
/**
 * 
 */
    private TextField fldBookTitle = null;
    
/**
 * 
 */
    private final TextField fldBookGenre;
    
/**
 * 
 */
    private ComboBox cbBookAuthor = null;
    
/**
 * 
 */
    public BookEditWindow() {
        super("Редактирование данных книги");
        center();
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        content.setMargin(true);
        
        FormLayout fl = new FormLayout();
        content.addComponent(fl);
        fl.setSizeFull();
        content.setExpandRatio(fl, 1);
        FieldGroup group = new FieldGroup();
        
        String label = "Автор";
        cbBookAuthor = new ComboBox(label);
        fl.addComponent(cbBookAuthor);
        cbBookAuthor.setWidth("100%");
        group.bind(cbBookAuthor, label);
        fillAuthorComboBox(cbBookAuthor);
        
        label = "Название";
        fldBookTitle = new TextField(label);
        fl.addComponent(fldBookTitle);
        fldBookTitle.setWidth("100%");
        group.bind(fldBookTitle, label);

        label = "Жанр";
        fldBookGenre = new TextField(label);
        fl.addComponent(fldBookGenre);
        fldBookGenre.setWidth("100%");
        group.bind(fldBookGenre, label);
        
        HorizontalLayout hl = new HorizontalLayout();
        content.addComponent(hl);
        hl.setSpacing(true);
        hl.setWidth("100%");
        
        final Button btnReset = new Button("Сброс");
        hl.addComponent(btnReset);
        btnReset.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                fillDataFromBook(book);
            }
        });
        
        FormLayout filler = new FormLayout();
        hl.addComponent(filler);
        filler.setSizeFull();
        hl.setExpandRatio(filler, 1);
        
        final Button btnOk = new Button("Принять");
        hl.addComponent(btnOk);
        btnOk.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if ( !isContentValid() ) {
                    Notification.show("Некорректные данные или отсутствие данных" 
                            +" в поле \"Название \". Действие отклонено.", 
                            Notification.Type.WARNING_MESSAGE);
                    return;
                }
                fillDataToBook(book); 
                ((MainUI)UI.getCurrent()).getMainAction().saveBook(book);
                close();
            }
        }); 
        
        final Button btnCancel = new Button("Отмена");
        hl.addComponent(btnCancel);
        btnCancel.addClickListener(new Button.ClickListener() { 
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });
        
        setBook(new BookEntity());
        fillDataFromBook(book);
/*/To avoid a set jf verifications triggered by the setting initial data
        fldBookTitle.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                btnOk.setEnabled(isContentValid());
            }
        });
  */      
     }

/**
 * 
 * @param ae 
 */
    void setBook(BookEntity be) {
        book = ( be!=null ) ? be : new BookEntity();
    }

    private void fillAuthorComboBox(ComboBox cb) {
        List<AuthorEntity> aes = authorService.readAll();
        cb.setItemCaptionMode(AbstractSelect.ItemCaptionMode.EXPLICIT);
        for ( AuthorEntity ae : aes ) {
            cb.addItem(ae.getId());
            cb.setItemCaption(ae.getId(), ae.getName());
        }
    }

/**
 * 
 */
    private void fillDataFromBook(BookEntity book) {
        fldBookTitle.setValue( (book.getTitle()!=null) ? book.getTitle() : "");
        fldBookGenre.setValue( (book.getGenre()!=null) ? book.getGenre() : "");
        AuthorEntity ae = authorService.getAuthor(book);
        cbBookAuthor.setValue( (ae!=null) ? ae.getId() : null);
    }

/**
 * 
 */
    private void fillDataToBook(BookEntity book) {
        book.setTitle(fldBookTitle.getValue());
        book.setGenre(fldBookGenre.getValue());
        AuthorEntity ae = authorService.read(cbBookAuthor.getValue()); 
        book.setAuthor(ae); 
    }
    
/**
 * 
 */
    public boolean isContentValid() {
        boolean res = (fldBookTitle.getValue() != null) && (!fldBookTitle.getValue().isEmpty());
        return res;
     }
    
}
