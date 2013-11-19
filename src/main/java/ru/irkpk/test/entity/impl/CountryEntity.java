
package ru.irkpk.test.entity.impl;

import java.util.Collections;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.irkpk.test.entity.IEntity;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 1.0.0 (16.11.2013)
 */
@Entity
@Table(name = "country")
public class CountryEntity implements IEntity {

/**
 * 
 */
@Id
@Column(name = "country_id")
@GeneratedValue(strategy = GenerationType.AUTO) // Has determined by RDBMS itself; .SEQUENCE requires name
    protected Integer id;
    
/**
 * 
 */
@Column(name = "country_name")
    protected String name;

/**
 * 
 */
 // "mappedBy" is a field name in the related entity
@OneToMany(mappedBy="country", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    protected Set<AuthorEntity> authors;

/**
 * 
 */
    public CountryEntity() {
        super();
     }

/**
 * 
 * @param name
 */
    public CountryEntity(String name) {
        this();
        setName(name);
     }
    
/**
 * 
 * @return 
 */
@Override
    public String getTableName() {
        return CountryEntity.class.getAnnotation(Table.class).name();
    }
    
/**
 * 
 */
@Override
    public Integer getId() {
        return id;
     }
    
/**
 *
 */
    public String getName() {
        return name;
    }

/**
 *
 */
    public void setName(String newName) {
        this.name = newName;
    }


/**
 * 
     * @return 
 */
    public Set<AuthorEntity> getAuthors() {
        return ( authors!=null ) ? Collections.unmodifiableSet((Set<? extends AuthorEntity>) authors) : Collections.EMPTY_SET;
     }

/**
 * 
 */
@Override
    public String toString() {
        return super.toString() + " [id=" + getId() + ", name=" + getName() + "]";
     }

}
