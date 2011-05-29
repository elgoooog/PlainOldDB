package com.elgoooog.podb.test;

import com.elgoooog.podb.annotation.Column;
import com.elgoooog.podb.annotation.PrimaryKey;
import com.elgoooog.podb.annotation.Table;

/**
 * @author Nicholas Hauschild
 *         Date: 5/25/11
 *         Time: 12:06 AM
 */
@Table
public class Held {
    @PrimaryKey
    @Column
    private int id;
    @Column
    private String name;

    public Held() {
        // for podb
    }

    public Held(int _id, String _name) {
        id = _id;
        name = _name;
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
}
