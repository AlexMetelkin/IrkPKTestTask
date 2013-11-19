
package ru.irkpk.test.ui;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.List;
import ru.irkpk.test.entity.impl.AuthorEntity;
import ru.irkpk.test.entity.impl.CountryEntity;
import ru.irkpk.test.service.ServiceProvider;
import ru.irkpk.test.service.impl.CountryService;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (19.11.2013)
 */
public class AuthorEditWindow extends Window {
   
/**
 * 
 */
    CountryService countryService = ServiceProvider.getProvider().getService(CountryService.class);
    
/**
 * 
 */
    private AuthorEntity author = null;
    
/**
 * 
 */
    private TextField fldAuthorName = null;
    
/**
 * 
 */
    private ComboBox cbAuthorCountry = null;
    
/**
 * 
 */
    public AuthorEditWindow() {
        super("Редактирование данных автора");
        center();
        VerticalLayout content = new VerticalLayout();
        setContent(content);
        content.setMargin(true);
        
        FormLayout fl = new FormLayout();
        content.addComponent(fl);
        fl.setSizeFull();
        content.setExpandRatio(fl, 1);
        FieldGroup group = new FieldGroup();
        
        String label = "Страна";
        cbAuthorCountry = new ComboBox(label);
        fl.addComponent(cbAuthorCountry);
        cbAuthorCountry.setWidth("100%");
        group.bind(cbAuthorCountry, label);
        fillCountryComboBox(cbAuthorCountry);
        
        label = "Ф.И.О.";
        fldAuthorName = new TextField(label);
        fl.addComponent(fldAuthorName);
        fldAuthorName.setWidth("100%");
        group.bind(fldAuthorName, label);
        
        HorizontalLayout hl = new HorizontalLayout();
        content.addComponent(hl);
        hl.setSpacing(true);
        hl.setWidth("100%");
        
        Button btnReset = new Button("Сброс");
        hl.addComponent(btnReset);
        btnReset.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                fillDataFromAuthor(author);
            }
        });
        
        FormLayout filler = new FormLayout();
        hl.addComponent(filler);
        filler.setSizeFull();
        hl.setExpandRatio(filler, 1);
        
        Button btnOk = new Button("Принять");
        hl.addComponent(btnOk);
        btnOk.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if ( !isContentValid() ) {
                    Notification.show("Некорректные данные или отсутствие данных" 
                            +" в поле \"Ф.И.О.\". Действие отклонено.", 
                            Notification.Type.WARNING_MESSAGE);
                    return;
                }
                fillDataToAuthor(author);
                ((MainUI)UI.getCurrent()).getMainAction().saveAuthor(author);
                close();
            }
        });
        
        Button btnCancel = new Button("Отмена");
        hl.addComponent(btnCancel);
        btnCancel.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                close();
            }
        });
        
        setAuthor(new AuthorEntity());
        fillDataFromAuthor(author);
     }

/**
 * 
 * @param ae 
 */
    void setAuthor(AuthorEntity ae) {
        author = ( ae!=null ) ? ae : new AuthorEntity();
    }

    private void fillCountryComboBox(ComboBox cb) {
        List<CountryEntity> ces = countryService.readAll();
        cb.setItemCaptionMode(AbstractSelect.ItemCaptionMode.EXPLICIT);
        for ( CountryEntity ce : ces ) {
            cb.addItem(ce.getId());
            cb.setItemCaption(ce.getId(), ce.getName());
        }
    }

/**
 * 
 */
    private void fillDataFromAuthor(AuthorEntity author) {
        fldAuthorName.setValue( (author.getName()!=null) ? author.getName() : "");
        CountryEntity ce = countryService.getCountry(author);
        cbAuthorCountry.setValue( (ce!=null) ? ce.getId() : -1);
    }

/**
 * 
 */
    private void fillDataToAuthor(AuthorEntity author) {
        author.setName(fldAuthorName.getValue());
        CountryEntity ce = countryService.read(cbAuthorCountry.getValue());
        author.setCountry(ce); 
    }
    
/**
 * 
 */
    public boolean isContentValid() {
        boolean res = (fldAuthorName.getValue() != null) && (!fldAuthorName.getValue().isEmpty());
        return res;
     }
    
}
