package com.zlobrynya.colorgame;

//whis call is cell on MapMatrix

import com.badlogic.gdx.graphics.Color;

public class CellMatrix {
    private String nameSprite;
    private int id;
    private Color color;

    CellMatrix(String nameSpritt, int id){
        this.nameSprite = nameSpritt;
        this.id = id;
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
        }
    }

    public int getId() {
        return id;
    }

    public String getNameSprite() {
        return nameSprite;
    }

    public void setId(int id) {
        this.id = id;
        setColor();
    }
}
