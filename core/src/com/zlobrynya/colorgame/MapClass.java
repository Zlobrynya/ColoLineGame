package com.zlobrynya.colorgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import static java.lang.Math.abs;

public class MapClass {

    private CellMatrix[][] map;

    private int sizeHeight;
    private int sizeWigth;
    private int colum;
    private int row;
    private int direction;

    private float wigthCell;
    private float heigthCell;
    private float maxAriaHeight;
    private float maxAriaWigth;

    private boolean motion;

    public MapClass(int sizeHeight, int sizeWigth, float wigthCell, float heigthCell){
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        this.wigthCell = 50;//wigthCell;
        this.heigthCell = 50;//heigthCell;
        this.sizeHeight = sizeHeight;
        this.sizeWigth = sizeWigth;
        motion = false;

        colum = row = -1;

        maxAriaHeight = sizeHeight * this.heigthCell;
        maxAriaWigth = sizeWigth * this.wigthCell;
        Gdx.app.log("Max Heigth: ", String.valueOf(maxAriaHeight));
        Gdx.app.log("Max Wigth: ", String.valueOf(maxAriaWigth));



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
        map[3][4] = new CellMatrix("",3);

        //debugOutMatrix();
    }

    private void debugOutMatrix(){
        StringBuilder debug = new StringBuilder();
        for (int heigth = 0; heigth < sizeHeight; heigth++){
            for (int wigth = 0; wigth < sizeWigth; wigth++)
                debug.append(map[wigth][heigth].getId()).append(" ");
            Gdx.app.log("Matrix: ", debug.toString());
            debug = new StringBuilder();
        }
    }

    public float getHeigthCell() {
        return heigthCell;
    }

    public float getWigthCell() {
        return wigthCell;
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

    public boolean isArea(float x, float y){
        return ((x > 0 && x < maxAriaWigth) && (y > 0 && y < maxAriaHeight));
    }

    public void motionCell(float x, float y, float deltaX, float deltaY){
        if (!motion){
            if (deltaX > 60 || deltaX < -60){
                motionRow(y,deltaX);
            }else if (deltaY > 60 || deltaY < -60){
                motionColum(x,deltaY);
            }
        }
    }

    private void motionColum(float x, float deltaY){
        motion = true;
        if (deltaY > 0)
            direction = 1;
        else direction = -1;
        row = -1;
        colum = (int) Math.floor(x / wigthCell);
    }

    private void motionRow(float y, float deltaX){
        motion = true;
        if (deltaX > 0)
            direction = 1;
        else direction = -1;
        colum = -1;
        row = (int) Math.floor(y / heigthCell);
    }

    public void  motionStop(){
        motionCell();
        motion = false;
        direction = 0;
    }

    private void motionCell(){
        if (colum > -1 && row < 12){
            if (direction > 0)
                recursMoveRow(0, false);
            else recursMoveRow(11,false);
        }else if (row > -1 && colum < 12){
            if (direction > 0)
                recursMoveColum(0, false);
            else recursMoveColum(11,false);
        }
    }

    private void recursMoveRow(int row, boolean emptyCell){
      //  Gdx.app.log("mov",colum + " " + row);
        if (row < sizeWigth && row > 0){
            if (map[colum][row].getId() == 0){
                recursMoveRow(row+direction, true);
            }else {
                if (emptyCell) {
                    CellMatrix cellMatrix = map[colum][row];
                    CellMatrix cellMatrix1 = map[colum][row+abs(direction)];
                    cellMatrix1.setId(cellMatrix.getId());
                    cellMatrix.setId(0);
                    recursMoveRow(row+direction, true);
                } else{
                    recursMoveRow(row+direction,false);
                }
            }
        }
     }

    private void recursMoveColum(int colum, boolean emptyCell){
        if (colum < sizeHeight && colum > 0){
            if (map[colum][row].getId() == 0){
                recursMoveColum(colum+direction, true);
            }else {
                if (emptyCell) {
                    CellMatrix cellMatrix = map[colum][row];
                    CellMatrix cellMatrix2 = map[colum+abs(direction)][row];
                    cellMatrix2.setId(cellMatrix.getId());
                    cellMatrix.setId(0);
                    recursMoveColum(colum+direction, true);
                } else{
                    recursMoveColum(colum+direction,false);
                }
            }
        }
    }


}
