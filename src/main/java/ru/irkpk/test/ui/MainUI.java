package ru.irkpk.test.ui;


import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.Collection;
import ru.irkpk.test.model.AuthorSetModel;
import ru.irkpk.test.model.BookFilterByAuthor;
import ru.irkpk.test.model.BookSetModel;

/**
 *
 * @author Alexander(user)
 */
@Theme("mytheme")
@Title("Справочник книг - тестовый пример")
@SuppressWarnings("serial")
public class MainUI extends UI {
    
 //Run without "web.xml", requiers rebuild.
/*
@WebServlet(value = "/*", asyncSupported = true)
@VaadinServletConfiguration(productionMode = false, ui = AppUI.class, widgetset = "ru.irkpk.test.ui.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }
*/
    
/**
 * 
 */
    private static AuthorSetModel authorSetModel = null;
    
/**
 * 
 */
    private static BookSetModel bookSetModel = null;
    
/**
 * 
 */
    private BookFilterByAuthor bookFilterByAuthor = new BookFilterByAuthor();

/**
 * 
 */
    private MainUILayout mainLayout = null;


/**
 * 
 */
    private MainUIAction mainAction = null;
    
/**
 * 
 * @param request 
 */
@Override
    protected void init(VaadinRequest request) {
        mainLayout = new MainUILayout(this);
        mainAction = new MainUIAction(this);
        createLayout();
        createActions();
    }
    
/**
 * 
 */
    public MainUILayout getMainLayout() {
        return mainLayout;
     }
    
/**
 * 
 */
    public MainUIAction getMainAction() {
        return mainAction;
     }
    
/**
 * 
 */
    public void createLayout() {
        Layout layout = mainLayout.createAndSetLayout();
     }

/**
 * 
 */
    public void createActions() {
        Button button = mainLayout.getRemoveAuthorButton();
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Table at = mainLayout.getAuthorTable();
                Object aid = at.getValue();
                if ( aid==null ) return;
                mainAction.removeAuthor(aid);
            }
        });
        
        button = mainLayout.getRemoveBookButton();
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Table bt = mainLayout.getBookTable();
                Object bookId = bt.getValue();
                if ( bookId==null ) return;
                mainAction.removeBook(bookId);
            }
        });
        
        button = mainLayout.getAddEditAuthorButton();
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Object authorId = null;//mainLayout.getAuthorTable().getValue();
                mainAction.editOrCreateAuthor(authorId); //Null means a creation of new author
            }
        });
        
        button = mainLayout.getAddEditBookButton();
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Object bookId = null;//mainLayout.getAuthorTable().getValue();
                mainAction.editOrCreateBook(bookId); //Null means a creation of new author
            }
        });
        
        button = mainLayout.getFilterBookByAuthor();
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Container c = getMainLayout().getBookTable().getContainerDataSource();
                if ( !(c instanceof IndexedContainer) ) return;
                IndexedContainer ic = (IndexedContainer)c;
                Collection<Filter> filters = ic.getContainerFilters();
                BookFilterByAuthor bf = getBookFilterByAuthor();
                if ( filters.contains(bf) ) {
                    ic.removeContainerFilter(bf);
                    mainLayout.getFilterBookByAuthor().setCaption("Фильтр по автору ►");
                    
                } else {
                    bf.setAuthorId(getMainLayout().getAuthorTable().getValue());
                    ic.addContainerFilter(bf);
                    mainLayout.getFilterBookByAuthor().setCaption("Сбросить фильтр ◄");
                }
            }
        });
     }
    
/**
 * 
 * @return 
 */
    public BookFilterByAuthor getBookFilterByAuthor() {
        return bookFilterByAuthor;
     }

}
