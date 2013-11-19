
package ru.irkpk.test.service;

import java.util.List;
import java.util.Set;
import ru.irkpk.test.entity.IEntity;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (14.11.2013)
 * @param <T>
 */
public interface IService <T extends IEntity> {

/**
 * 
 */
    public static String PERSISTENCE_UNIT = "ru.irkpk.test.jpa";
    
/**
 * 
 * @return 
 */
    public T create();
    

/**
 * 
 * @param entity
 */
    public void save(T entity);
    
/**
 * 
 * @param entity
 * @return 
 */
    public boolean contains(T entity);
    
/**
 * 
 * @param id
 * @return 
 */
    public T read(Object id);
    
/**
 * 
 * @return 
 */
    public List<T> readAll();

/**
 * 
 * @param entity 
 */
    public void update(T entity);
    
/**
 * 
 * @param entity 
 */
    public void delete(T entity);
    
}
