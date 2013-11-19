
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import ru.irkpk.test.entity.IEntity;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 1.0.0 (16.11.2013)
 */
@Entity
@Table(name = "author")
public class AuthorEntity implements IEntity {

/**
 * 
 */
@Id
@Column(name = "author_id")
@GeneratedValue(strategy = GenerationType.AUTO) // Has determined by RDBMS itself; .SEQUENCE requires name
    private Integer id;
    
/**
 * 
 */
@Column(name = "author_name")
    private String name;

/**
 * 
 */
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="author_country_id")
    private CountryEntity country;

/**
 * 
 */
// "mappedBy" is a field name in the related entity
@OneToMany(mappedBy="author", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
private Set<BookEntity> books;

/**
 * 
 */
    public AuthorEntity() {
        super();
     }

/**
 * 
 * @param name
 */
    public AuthorEntity(String name) {
        this();
        setName(name);
     }
    
/**
 * 
 * @return 
 */
    public String getTableName() {
        return AuthorEntity.class.getAnnotation(Table.class).name();
    }
    
/**
 * 
 */
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
    public CountryEntity getCountry() {
        return country;
     }


/**
 * 
 * @param newCountry
 */
    public void setCountry(CountryEntity newCountry) {
        assert ( newCountry instanceof CountryEntity ) : "An instance of ["
                + CountryEntity.class.getName()
                + "] expected, but ["
                + newCountry.getClass().getName()
                + "] value provided";
        this.country = (CountryEntity)newCountry;
     }


/**
 * 
 * @return 
 */
    public Set<BookEntity> getBooks() {
        return ( books!=null ) ? Collections.unmodifiableSet((Set<? extends BookEntity>) books) : Collections.EMPTY_SET;
     }

/**
 * 
 */
@Override
    public String toString() {
        return super.toString() + " [id=" + getId() + ", name=" + getName() + "]";
     }

}
