
package ru.irkpk.test.entity;

import java.io.Serializable;

/**
 *
 * @author Alexander Metelkin (alexmetelkin at narod dot ru)
 * @version 0.0.9 (14.11.2013)
 */
public interface IEntity extends Serializable {
    
/**
 * 
 * @return 
 */
    public String getTableName();
    
/**
 * 
 * @return 
 */
    public Integer getId();

}
