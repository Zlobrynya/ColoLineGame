package com.zlobrynya.colorgame;

//whis call is cell on MapMatrix

import com.badlogic.gdx.graphics.Color;

public class CellMatrix {
    private String nameSprite;
    private int id;
    private Color color;
    private boolean delete;

    CellMatrix(String nameSpritt, int id){
        this.nameSprite = nameSpritt;
        this.id = id;
        delete = false;
        setColor();
    }

    public Color getColor() {
        return color;
    }

    private void setColor() {
        switch (id){
            case 3:
                color = Color.RED;
                break;
            case 4:
                color = Color.BLUE;
                break;
            case 5:
                color = Color.YELLOW;
                break;
            case 7:
                color = Color.VIOLET;
                break;
            case 8:
                color = Color.ORANGE;
                break;
            case 9:
                color = Color.GREEN;
                break;
             default:
                color = Color.WHITE;
                break;
        }
    }

    public int getId() {
        return id;
    }

    public String getNameSprite() {
        return nameSprite;
    }

    public void setNameSprite(String nameSprite) {
        this.nameSprite = nameSprite;
    }

    public void setId(int id) {
        this.id = id;
        setColor();
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
