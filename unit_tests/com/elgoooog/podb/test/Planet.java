package com.elgoooog.podb.test;

import com.elgoooog.podb.annotation.Column;
import com.elgoooog.podb.annotation.PrimaryKey;
import com.elgoooog.podb.annotation.Table;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 8:10 PM
 */
@Table
public class Planet {
    @PrimaryKey
    @Column
    private String name;

    @Column
    private int radius;

    @Column
    private int population;

    public Planet() {

    }

    public Planet(String n, int r, int p) {
        name = n;
        radius = r;
        population = p;
    }

    public String getName() {
        return name;
    }

    public int getRadius() {
        return radius;
    }

    public int getPopulation() {
        return population;
    }

    public String toString() {
        return "Planet[name:" + name + ",radius:" + radius + ",population:" + population + "]";
    }
}
