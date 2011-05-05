package com.elgoooog.podb;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 7:25 PM
 */
public class CrudObject {
    private static Database database = new MySQLDatabase();

    public final void create() {
        database.create(this);
    }

    public final void read() {
        database.read(this);
    }

    public final void update() {
        database.update(this);
    }

    public final void delete() {
        database.delete(this);
    }
}
