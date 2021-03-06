package com.elgoooog.podb.test.objects;

import com.elgoooog.podb.annotation.Column;
import com.elgoooog.podb.annotation.PrimaryKey;
import com.elgoooog.podb.annotation.Table;

/**
 * @author Nicholas Hauschild
 *         Date: 5/4/11
 *         Time: 10:55 PM
 */
@Table(name = "Planet")
public class AnotherPlanet {
    @PrimaryKey
    @Column(name = "name")
    private String planetName;

    @Column(name = "radius")
    private int planetRadius;

    @Column(name = "population")
    private int planetPopulation;

    public AnotherPlanet() {

    }

    public AnotherPlanet(String n, int r, int p) {
        planetName = n;
        planetRadius = r;
        planetPopulation = p;
    }

    public String getName() {
        return planetName;
    }

    public int getRadius() {
        return planetRadius;
    }

    public int getPopulation() {
        return planetPopulation;
    }

    public String toString() {
        return "AnotherPlanet[name:" + planetName + ",radius:" + planetRadius + ",population:" + planetPopulation + "]";
    }
}
