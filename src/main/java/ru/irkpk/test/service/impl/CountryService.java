
package ru.irkpk.test.service.impl;

import java.util.Set;
import javax.persistence.EntityManager;
import ru.irkpk.test.entity.impl.AuthorEntity;
import ru.irkpk.test.entity.impl.CountryEntity;
import ru.irkpk.test.service.ServiceProvider;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (15.11.2013)
 */
public class CountryService extends BaseService<CountryEntity> {

/**
 * 
 * @return 
 */
@Override
    protected Class<CountryEntity> getBlankClass() {
        return CountryEntity.class;
    }
    
/**
 * 
 * @param country 
 */
@Override
    public void update(CountryEntity country) {
        if ( country==null ) {
            return;
        }
        super.update(country);
        Set<AuthorEntity> authors = ((CountryEntity)country).getAuthors();
        if ( authors!=null && !authors.isEmpty() ) {
            AuthorService as = ServiceProvider.getProvider().getService(AuthorService.class);
            for ( AuthorEntity author : authors ) {
                as.update(author);
            }
        }
    }

    public CountryEntity getCountry(AuthorEntity author) {
        if ( author==null ) throw new IllegalArgumentException("No instance of [" 
                + AuthorEntity.class.getName() 
                + "] provided");
        if ( getEntityManager().contains(author) ) {
            getEntityManager().refresh(author);
        }
        return author.getCountry();
    }

}
