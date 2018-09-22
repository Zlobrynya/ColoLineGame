package com.zlobrynya.colorgame;

//whis call is cell on MapMatrix

public class CellMatrix {
    private String nameSprite;
    private int id;

    CellMatrix(String nameSpritt, int id){
        this.nameSprite = nameSpritt;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNameSprite() {
        return nameSprite;
    }

    public void setId(int id) {
        this.id = id;
    }
}
