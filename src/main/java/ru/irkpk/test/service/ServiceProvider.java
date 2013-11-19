
package ru.irkpk.test.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import ru.irkpk.test.service.impl.AuthorService;
import ru.irkpk.test.service.impl.BaseService;
import ru.irkpk.test.service.impl.BookService;
import ru.irkpk.test.service.impl.CountryService;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (16.11.2013)
 */
public class ServiceProvider {

/**
 * Only one possible solution to control over closing of persistence managers 
 * without external efforts.
 */
    private static int instance_counter = 0;
    
/**
 * 
 */
    private static EntityManagerFactory emf = null; //Created lazily

/**
 * 
 */
    private static EntityManager em = null; //Created lazily
    
/**
 * 
 */
    private static final Map<Class, Object> service_map = new HashMap<>();

/**
 * 
 * @return 
 */
    public static ServiceProvider getProvider() {
        return new ServiceProvider();
     }

/**
 * 
 */
    {
        instance_counter ++;
        service_map.put(CountryService.class, new CountryService());
        service_map.put(AuthorService.class, new AuthorService());
        service_map.put(BookService.class, new BookService());
    }     
    
/**
 * 
 * @return 
 */
    public Logger getLogger() {
        return Logger.getAnonymousLogger();
    }

/**
 * 
 * @param forObject
 * @return 
 */
    public Logger getLogger(Object forObject) {
        if ( forObject==null ) return getLogger();
        return Logger.getLogger(forObject.getClass().getName());
     }
    
/**
 * 
 * @return 
 */
    public EntityManagerFactory getEntityManagerFactory() {
        if ( emf==null ) emf = Persistence.createEntityManagerFactory(IService.PERSISTENCE_UNIT);
        return emf;
     }

/**
 * 
 * @return 
 */
    public EntityManager getEntityManager() {
        if ( em==null ) {
            em = getEntityManagerFactory().createEntityManager();
            runInitialScriptFromFile("/sql/create.sql");
            runInitialScriptFromFile("/sql/init.sql");
        }
        return em;
     }
    
/**
 * 
 */
    private void verifyAssertions(IService forService) {
        assert ( forService != null ) : "A subclass of ["
                + BaseService.class.getName()
                + "] expected, but [null] value provided";
        assert ( forService instanceof BaseService ) : "A subclass of ["
                + BaseService.class.getName()
                + "] expected, but ["
                + forService.getClass().getName()
                + "] value provided";
     }
    
/**
 * 
 * @param forService
 * @return 
 */
    public EntityManager getEntityManager(IService forService) {
        verifyAssertions(forService);
        return getEntityManager();
     }

/**
 * 
 * @param forService
 * @return 
 */
    public EntityManagerFactory getEntityManagerFactory(IService forService) {
        verifyAssertions(forService);
        return getEntityManagerFactory();
     }

/**
 * 
     * @param <T>
     * @param serviceClass
     * @return 
 */
    public <T extends IService> T getService(Class<T> serviceClass) {
        assert ( serviceClass != null ) : "A subclass of ["
                + BaseService.class.getName()
                + "] expected, but [null] value provided";
        assert ( BaseService.class.isAssignableFrom(serviceClass) ) : "A subclass of ["
                + BaseService.class.getName()
                + "] expected, but ["
                + serviceClass.getName()
                + "] value provided";
        return (T)service_map.get(serviceClass);
     }
    
/**
 * 
 */
    private void runInitialScriptFromFile(String filePath) {
        Scanner sqlScanner;
        try {
            InputStream is = this.getClass().getResource(filePath).openStream();
            sqlScanner = new Scanner(is, "UTF-8").useDelimiter(";");
        }catch(FileNotFoundException ex) {
            Logger.getLogger(this.getClass().getName())
                    .log(Level.SEVERE, "Initial SQL script file not found", ex);
            return;
        }catch(IOException ex1) {
            Logger.getLogger(this.getClass().getName())
                    .log(Level.SEVERE, "Initial SQL script file read error", ex1);
            return;
        }
        getEntityManager().getTransaction().begin();
        while ( sqlScanner.hasNext() ) {
            String sql = sqlScanner.next();
            if ( sql.isEmpty() ) continue; //Some empty comments lines
            getEntityManager().createNativeQuery(sql).executeUpdate();
        }
        getEntityManager().getTransaction().commit();
     }

    
/**
 * 
 * @throws java.lang.Throwable
 */
@Override
@SuppressWarnings("FinalizeDeclaration")
    public void finalize() throws Throwable {
        instance_counter --;
        if ( instance_counter==0 ) {
            try {
                if ( em!=null ) em.close();
            }catch(Exception ex) {}
            try {
                if ( emf!=null ) emf.close();
            }catch(Exception ex) {}
        }
        super.finalize();
    }
    
}
