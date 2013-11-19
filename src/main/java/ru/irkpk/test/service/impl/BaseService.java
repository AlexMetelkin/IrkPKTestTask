
package ru.irkpk.test.service.impl;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.logging.Logger;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import ru.irkpk.test.entity.IEntity;
import ru.irkpk.test.service.IService;
import ru.irkpk.test.service.ServiceProvider;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (15.11.2013)
 * @param <T>
 */
public abstract class BaseService<T extends IEntity> implements IService<T> {

/**
 * 
 */
    private static final ServiceProvider sp = ServiceProvider.getProvider();

/**
 * 
 * @return 
 */
    protected abstract Class<? extends T> getBlankClass();
    
/**
 * 
 * @return 
 */
    public EntityManagerFactory getEntityManagerFactory() {
        return sp.getEntityManagerFactory(this);
     }


/**
 * 
 * @return 
 */
    public EntityManager getEntityManager() {
        return sp.getEntityManager(this);
     }

/**
 * 
 * @return 
 */
    public Logger getLogger() {
        return sp.getLogger(this.getClass().getName());
     }

/**
 * 
 * @return 
 */
@Override
    public T create() {
        try {
             T result = getBlankClass().newInstance();
             return result;
        }catch(Exception ex) {
            getLogger().log(Level.SEVERE, "An attempt to create an ["
                    +getBlankClass().getName()
                    +"] instance raised an exception", ex);
        }
        return null;
    }
    
/**
 * 
 * @param theEntity 
 */
@Override
    public void save(T theEntity) {
        if ( theEntity==null ) return;
        EntityManager em = getEntityManager();
        assert( em!=null ) : "An ["+EntityManager.class.getName()+"] expected, but [null] value obtained";
        try {
            em.getTransaction().begin();
            em.persist(theEntity);
            em.getTransaction().commit();
        }catch(Exception ex) {
            getLogger().log(Level.SEVERE, "An attempt to persist a ["
                    +theEntity.getClass().getName()
                    +"] entity raised an exception", ex);
        }
     }
    
/**
 * 
 */
@Override
    public boolean contains(T entity) {
        return getEntityManager().contains(entity);
     }
    
/**
 * 
 */
@Override
    public T read(Object id) {
        assert( id instanceof Integer ) : "A value of ["
                + Integer.class.getName() + "] class expected, but ["
                + ((id!=null)?id.getClass().getName():"null") +"] actually provided";
        if ( id==null ) return null;
        EntityManager em = getEntityManager();
        assert( em!=null ) : "An ["+EntityManager.class.getName()+"] expected, but [null] value obtained";
        return em.find(getBlankClass(), id);
     }

/**
 * 
 * @return 
 */
@Override
    public List<T> readAll() {
        EntityManager em = getEntityManager();
        assert( em!=null ) : "An ["+EntityManager.class.getName()+"] expected, but [null] value obtained";
        TypedQuery q = em.createQuery("FROM " + getBlankClass().getSimpleName(), getBlankClass());
        List<T> list = q.getResultList();
        return list;
    }

/**
 * 
 * @param entity 
 */
@Override
    public void update(T entity) {
        if ( entity==null ) {
            return;
        }
        EntityManager em = getEntityManager();
        assert( em!=null ) : "An ["+EntityManager.class.getName()+"] expected, but [null] value obtained";
        if ( !em.contains(entity) ) {
            save(entity);
        } else {
          em.refresh(entity);
        }
     }

@Override
    public void delete(T entity) {
        EntityManager em = getEntityManager();
        assert( em!=null ) : "An ["+EntityManager.class.getName()+"] expected, but [null] value obtained";
        getEntityManager().getTransaction().begin();
        em.remove(entity);
        getEntityManager().getTransaction().commit();
        System.out.println("Hello from " + entity.toString());
        
    }
    
}
