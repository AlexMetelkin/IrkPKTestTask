
package ru.irkpk.test.entity.impl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import ru.irkpk.test.entity.IEntity;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 1.0.0 (16.11.2013)
 */
@Entity
@Table(name = "book")
public class BookEntity implements IEntity {
    
/**
 * 
 */
@Id
@Column(name = "book_id")
@GeneratedValue(strategy = GenerationType.AUTO) // Has determined by RDBMS itself; .SEQUENCE requires name
    protected Integer id;

/**
 * 
 */
@Column(name = "book_title")
    protected String title;

/**
 * 
 */
@Column(name = "book_genre")
    protected String genre;

/**
 * 
 */
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name="book_author_id")
    private AuthorEntity author;

/**
 * 
 */
    public BookEntity() {
        super();
    }

/**
 * 
 * @param bookName
 */
    public BookEntity(String bookName) {
        this();
        setTitle(bookName);
    }


/**
 * 
     * @param name
     * @param title
 */
    public BookEntity(String name, String title) {
        this(name);
        setTitle(title);
     }
    
/**
 * 
 * @return 
 */
@Override
    public String getTableName() {
        return BookEntity.class.getAnnotation(Table.class).name();
    }
    
/**
 * 
 * @return 
 */
@Override
    public Integer getId() {
        return id;
    }

/**
 * 
 * @return 
 */
    public String getTitle() {
        return title;
    }

/**
 * 
 * @param newTitle 
 */
    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

/**
 * 
 * @return 
 */
    public String getGenre() {
        return genre;
    }

/**
 * 
 * @param newGenre 
 */
    public void setGenre(String newGenre) {
        this.genre = newGenre;
    }


/**
 * 
 * @return 
 */
    public AuthorEntity getAuthor() {
        return author;
     }


/**
 * 
 * @param newAuthor
 */
    public void setAuthor(AuthorEntity newAuthor) {
        assert ( newAuthor instanceof AuthorEntity ) : "An instance of ["
                + AuthorEntity.class.getName()
                + "] expected, but ["
                + newAuthor.getClass().getName()
                + "] value provided";
        this.author = (AuthorEntity)newAuthor;
     }
    
/**
 * 
 */
@Override
    public String toString() {
        return super.toString() + " [id=" + getId() + ", title=" + getTitle() 
                + ", genre=" + getGenre() + "]";
     }

}
