package com.zlobrynya.colorgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class MapClass {

    private CellMatrix[][] map;
    private int sizeHeight;
    private int sizeWigth;
    private float widthCell;
    private float heigthCell;

    public MapClass(int sizeHeight, int sizeWigth, float wigthCell, float heigthCell){
        this.widthCell = 50;//wigthCell;
        this.heigthCell = 50;//heigthCell;
        this.sizeHeight = sizeHeight;
        this.sizeWigth = sizeWigth;

        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        map = new CellMatrix[sizeHeight][sizeWigth];
        createMap(sizeHeight,sizeWigth);

    }

    private void createMap(int sizeHeight, int sizeWigth){
        for (int x = 0; x < sizeWigth; x++){
            for(int y = 0; y < sizeHeight;y++){
                map[y][x] = new CellMatrix("",0);
            }
        }
        for (int x = 0; x < sizeWigth; x++) {
            map[0][x] = new CellMatrix("crate",1);
            map[sizeHeight - 1][x] = new CellMatrix("crate",1);
        }
        for (int y = 0; y < sizeHeight; y++){
            map[y][0] = new CellMatrix("crate",1);
            map[y][sizeWigth-1] = new CellMatrix("crate",1);
        }

        map[4][4] = new CellMatrix("",3);
        debugOutMatrix();
    }

    private void debugOutMatrix(){
        StringBuilder debug = new StringBuilder();
        for (int heigth = 0; heigth < sizeHeight; heigth++){
            for (int wigth = 0; wigth < sizeWigth; wigth++)
                debug.append(map[heigth][wigth].getId()).append(" ");
            Gdx.app.log("Matrix: ", debug.toString());
            debug = new StringBuilder();
        }
    }

    public float getHeigthCell() {
        return heigthCell;
    }

    public float getWidthCell() {
        return widthCell;
    }

    public int getSizeHeight() {
        return sizeHeight;
    }

    public int getSizeWigth() {
        return sizeWigth;
    }

    public int getIdCell(int x, int y){
        return map[x][y].getId();
    }

    public String getNameSprite(int x, int y){
        return map[x][y].getNameSprite();
    }
}
