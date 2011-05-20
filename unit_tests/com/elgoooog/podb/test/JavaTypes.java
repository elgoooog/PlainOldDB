package com.elgoooog.podb.test;

import com.elgoooog.podb.annotation.Column;
import com.elgoooog.podb.annotation.PrimaryKey;
import com.elgoooog.podb.annotation.Table;

/**
 * @author Nicholas Hauschild
 *         Date: 5/14/11
 *         Time: 12:54 PM
 */
@Table(name = "JavaTypes")
public class JavaTypes {
    @PrimaryKey(autoIncrement = true)
    @Column
    private int id;

    @Column
    private int intt;
    @Column
    private long longg;
    @Column
    private double doublee;
    @Column
    private float floatt;
    @Column
    private byte bytee;
    @Column
    private boolean booleann;
    @Column
    private char charr;
    @Column
    private short shortt;
    @Column
    private byte[] blobb;
    @Column
    private String stringg;

    public JavaTypes() {
        // for podb
    }

    public JavaTypes(int i, long l, double d, float f, byte b, boolean boo, char c, short s, byte[] blob, String str) {
        intt = i;
        longg = l;
        doublee = d;
        floatt = f;
        bytee = b;
        booleann = boo;
        charr = c;
        shortt = s;
        blobb = blob;
        stringg = str;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        id = _id;
    }

    public int getInt() {
        return intt;
    }

    public long getLong() {
        return longg;
    }

    public double getDouble() {
        return doublee;
    }

    public float getFloat() {
        return floatt;
    }

    public byte getByte() {
        return bytee;
    }

    public boolean getBoolean() {
        return booleann;
    }

    public char getChar() {
        return charr;
    }

    public short getShort() {
        return shortt;
    }

    public byte[] getBlob() {
        return blobb;
    }

    public String getString() {
        return stringg;
    }
}
