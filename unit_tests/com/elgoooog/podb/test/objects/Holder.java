package com.elgoooog.podb.test.objects;

import com.elgoooog.podb.annotation.Column;
import com.elgoooog.podb.annotation.PrimaryKey;
import com.elgoooog.podb.annotation.Table;

/**
 * @author Nicholas Hauschild
 *         Date: 5/25/11
 *         Time: 12:05 AM
 */
@Table
public class Holder {
    @PrimaryKey
    @Column
    private int id;
    @Column
    private String name;
    @Column(name = "held_id")
    private Held held;

    public Holder() {
        // for podb
    }

    public Holder(int _id, String _name, Held _held) {
        id = _id;
        name = _name;
        held = _held;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Held getHeld() {
        return held;
    }

    public void setHeld(Held held) {
        this.held = held;
    }
}
