
package ru.irkpk.test.ui;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.Collection;
import ru.irkpk.test.model.AuthorSetModel;
import ru.irkpk.test.model.BookFilterByAuthor;
import ru.irkpk.test.model.BookSetModel;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (18.11.2013)
 */
public class MainUILayout {
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
    private MainUI mainUI = null;
    
/**
 * 
 */
    private Table tblAuthorTable = null;
        
/**
 * 
 */
    private Table tblBookTable = null;
    
/**
 * 
 */
    private Button btnAddEditAuthor = null;
    
/**
 * 
 */
    private Button btnRemoveAuthor = null;
    
/**
 * 
 */
    private Button btnAddEditBook = null;
    
/**
 * 
 */
    private Button btnRemoveBook = null;
    
/**
 * 
 */
    private Button btnFilterBookByAuthor = null;
    
/**
 * 
 * @param ui 
 */
    public MainUILayout(MainUI ui) {
        mainUI = ui;
    }
    
/**
 * 
 */
    public Layout createAndSetLayout() {
        VerticalLayout layout = new VerticalLayout();
        mainUI.setContent(layout);
        layout.setMargin(true);
        layout.setSizeFull();
        
        HorizontalSplitPanel tables = new HorizontalSplitPanel();
        layout.addComponent(tables);
        tables.setSizeFull();

        Component authorSection = createAuthorSection(tables);
        authorSection.setSizeFull();
        Component bookSection = createBookSection(tables);
        bookSection.setSizeFull();
        
        return layout;
     }

/**
 * 
     * @param container
     * @return 
 */
    public Component createBookSection(AbstractComponentContainer container) {
        VerticalLayout layout = new VerticalLayout();
        container.addComponent(layout);
        
        Component bookControlPanel = createBookControlPanel(layout);
        bookControlPanel.setWidth("100%");
        
        Component bookTable = createBookTable(layout);
        bookTable.setSizeFull();
        layout.setExpandRatio(bookTable, 1);
        
        return layout;
     }

/**
 * 
     * @param container
     * @return 
 */
    public Component createBookTable(AbstractComponentContainer container) {
        tblBookTable = new Table();
        container.addComponent(tblBookTable);
        tblBookTable.setSelectable(true);
        tblBookTable.setImmediate(true);
        
        tblBookTable.setContainerDataSource(BookSetModel.getModel().getContainer());
        
        tblBookTable.setVisibleColumns(new Object[] { BookSetModel.BOOK_TITLE, BookSetModel.BOOK_GENRE });
        tblBookTable.setColumnAlignment(BookSetModel.BOOK_TITLE, Table.Align.CENTER);
        tblBookTable.setColumnExpandRatio(BookSetModel.BOOK_TITLE, 1);
        tblBookTable.setColumnWidth(BookSetModel.BOOK_GENRE, 200);
                
        return tblBookTable;
     }

/**
 * 
     * @param container
     * @return 
 */
    public Component createBookControlPanel(AbstractComponentContainer container) {
        HorizontalLayout layout = new HorizontalLayout();
        container.addComponent(layout);
        layout.setSpacing(true);
        
        btnAddEditBook = new Button("Добавить...");
        layout.addComponent(btnAddEditBook);
        btnRemoveBook = new Button("Удалить...");
        layout.addComponent(btnRemoveBook);
        
        Component filler = new FormLayout();
        layout.addComponent(filler);
        filler.setWidth("100%");
        layout.setExpandRatio(filler, 1);
        
        return layout;
     }

/**
 * 
 * @return 
 */
    private Component createAuthorSection(AbstractComponentContainer container) {
        VerticalLayout layout = new VerticalLayout();
        container.addComponent(layout);
        
        Component authorControlPanel = createAuthorControlPanel(layout);
        authorControlPanel.setWidth("100%");
        Component authorTable = createAuthorTable(layout);
        authorTable.setSizeFull();
        layout.setExpandRatio(authorTable, 1);
        return layout;
    }
    
/**
 * 
     * @param container
     * @return 
 */
    public Component createAuthorControlPanel(AbstractComponentContainer container) {
        HorizontalLayout layout = new HorizontalLayout();
        container.addComponent(layout);
        layout.setSpacing(true);
        
        btnAddEditAuthor = new Button("Добавить...");
        layout.addComponent(btnAddEditAuthor);
        
        btnRemoveAuthor = new Button("Удалить...");
        layout.addComponent(btnRemoveAuthor);
        
        Component filler = new FormLayout();
        layout.addComponent(filler);
        filler.setWidth("100%");
        layout.setExpandRatio(filler, 1);
        
        btnFilterBookByAuthor = new Button("Фильтр по автору ►");
        layout.addComponent(btnFilterBookByAuthor);
        
        return layout;
     }
    
/**
 * 
     * @param container
     * @return 
 */
    public Component createAuthorTable(AbstractComponentContainer container) {
        tblAuthorTable = new Table();
        container.addComponent(tblAuthorTable); 
        tblAuthorTable.setSelectable(true);
        tblAuthorTable.setImmediate(true);
        
        tblAuthorTable.setContainerDataSource(AuthorSetModel.getModel().getContainer());
        
        tblAuthorTable.setVisibleColumns(new Object[] { AuthorSetModel.AUTHOR_NAME, AuthorSetModel.AUTHOR_COUNTRY });
        tblAuthorTable.setColumnAlignment(AuthorSetModel.AUTHOR_COUNTRY, Table.Align.CENTER);
        tblAuthorTable.setColumnExpandRatio(AuthorSetModel.AUTHOR_NAME, 1);
        tblAuthorTable.setColumnWidth(AuthorSetModel.AUTHOR_COUNTRY, 200);

        tblAuthorTable.addValueChangeListener(new AbstractField.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                BookFilterByAuthor bf = ((MainUI)UI.getCurrent()).getBookFilterByAuthor();
                IndexedContainer ic = (IndexedContainer)getBookTable().getContainerDataSource();
                Collection<Container.Filter> filters = ic.getContainerFilters();
                if ( !filters.contains(bf) ) return;
                ic.removeContainerFilter(bf);
                bf.setAuthorId(event.getProperty().getValue());
                ic.addContainerFilter(bf);
            }
        });

        return tblAuthorTable;
     }

/**
 * 
 * @return 
 */
    Button getRemoveAuthorButton() {
        return btnRemoveAuthor;
    }

/**
 * 
 * @return 
 */
    Button getRemoveBookButton() {
        return btnRemoveBook;
    }

/**
 * 
 * @return 
 */
    Table getAuthorTable() {
        return tblAuthorTable;
    }

/**
 * 
 * @return 
 */
    Table getBookTable() {
        return tblBookTable;
    }

/**
 * 
 * @return 
 */
    Button getAddEditAuthorButton() {
        return btnAddEditAuthor;
    }

/**
 * 
 * @return 
 */
    Button getAddEditBookButton() {
        return btnAddEditBook;
    }

/**
 * 
 * @return 
 */
    Button getFilterBookByAuthor() {
        return btnFilterBookByAuthor;
    }
    
}