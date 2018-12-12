package com.zlobrynya.colorgame.mechanics;

//whis call is cell on MapMatrix

import com.badlogic.gdx.graphics.Color;
import com.zlobrynya.colorgame.enume.NameColorTexture;

public class CellMatrix {
    private String nameSprite;
    private int id;
    private NameColorTexture color;
    private boolean delete;

    CellMatrix(String nameSpritt, int id){
        this.nameSprite = nameSpritt;
        this.id = id;
        delete = false;
        setColor();
    }

    public NameColorTexture getColor() {
        return color;
    }

    private void setColor() {
        switch (id){
            case 3:
                color = NameColorTexture.RED;
                break;
            case 4:
                color = NameColorTexture.BLUE;
                break;
            case 5:
                color = NameColorTexture.YELLOW;
                break;
            case 7:
                color = NameColorTexture.VIOLET;
                break;
            case 8:
                color = NameColorTexture.ORANGE;
                break;
            case 9:
                color = NameColorTexture.GREEN;
                break;
             default:
                color = NameColorTexture.RED;
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
