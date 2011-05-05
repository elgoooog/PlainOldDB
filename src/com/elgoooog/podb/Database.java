package com.elgoooog.podb;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 7:35 PM
 */
public interface Database {
    void create(CrudObject crudObject);

    void read(CrudObject crudObject);

    void update(CrudObject crudObject);

    void delete(CrudObject crudObject);
}
