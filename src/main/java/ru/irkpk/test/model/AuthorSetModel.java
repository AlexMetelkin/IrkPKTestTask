
package ru.irkpk.test.model;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.irkpk.test.entity.impl.AuthorEntity;
import ru.irkpk.test.entity.impl.CountryEntity;
import ru.irkpk.test.service.ServiceProvider;
import ru.irkpk.test.service.impl.AuthorService;
import ru.irkpk.test.service.impl.CountryService;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (17.11.2013)
 */
public class AuthorSetModel {//extends BaseModel {
    
/**
 * 
 */
    public static final String AUTHOR_NAME = "Ф.И.О.";
   
/**
 * 
 */
    public static final String AUTHOR_COUNTRY = "Страна"; 
    
/**
 * 
 */
    //Caused an error: static container not refreshable
    //private static AuthorSetModel theModel;
    
/**
 * 
 */
    private IndexedContainer container = null;

/**
 * 
 */
    public static AuthorSetModel getModel() {
        //Caused an error: static container not refreshable
        //if ( theModel==null ) theModel = new AuthorSetModel();
        //return theModel;
        return new AuthorSetModel();
     }
    
/**
 * 
 * @return 
 */
    public IndexedContainer getContainer() {
        if ( container!=null ) return container;
        AuthorService as = ServiceProvider.getProvider().getService(AuthorService.class);
        CountryService cs = ServiceProvider.getProvider().getService(CountryService.class);
        container = new IndexedContainer();
        
        container.addContainerProperty(AuthorSetModel.AUTHOR_NAME, String.class, "");
        container.addContainerProperty(AuthorSetModel.AUTHOR_COUNTRY, String.class, "");

        List<AuthorEntity> list = as.readAll();
        for (AuthorEntity ae : list) {
            Item item = container.addItem(ae.getId());
            item.getItemProperty(AuthorSetModel.AUTHOR_NAME).setValue(ae.getName());
            item.getItemProperty(AuthorSetModel.AUTHOR_COUNTRY).setValue(
                    ((cs.getCountry(ae)!=null) ? cs.getCountry(ae).getName() : "")
            );
        }

        return container;
    }
    
/**
 * 
 * @param ae 
 */
    public static void addItem(Container cnt, AuthorEntity ae) {
        if ( ae==null ) return;
        if ( cnt.containsId(ae.getId()) ) return;
        CountryService cs = ServiceProvider.getProvider().getService(CountryService.class);
        Item item = cnt.addItem(ae.getId());
        item.getItemProperty(AuthorSetModel.AUTHOR_NAME).setValue(ae.getName());
        item.getItemProperty(AuthorSetModel.AUTHOR_COUNTRY).setValue(
                ((cs.getCountry(ae)!=null) ? cs.getCountry(ae).getName() : "")
        );
    }
    
}
