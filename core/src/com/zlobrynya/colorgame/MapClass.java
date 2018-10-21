package com.zlobrynya.colorgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.abs;
import static java.lang.Math.cbrt;
import static java.lang.Math.round;

public class MapClass {

    private CellMatrix[][] map;

    private int sizeHeight;
    private int sizeWigth;
    private int colum;
    private int row;
    private int direction;
    private int extrimExitRecurs;

    private float wigthCell;
    private float heigthCell;
    private float maxAriaHeight;
    private float maxAriaWigth;

    private boolean motion;

    final Random random = new Random();

    public MapClass(int sizeHeight, int sizeWigth, float wigthCell, float heigthCell){
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        this.wigthCell = 50;//wigthCell;
        this.heigthCell = 50;//heigthCell;
        this.sizeHeight = sizeHeight;
        this.sizeWigth = sizeWigth;
        motion = false;

        colum = row = -1;
        extrimExitRecurs = 0;

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
        map[2][4] = new CellMatrix("",4);

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
            direction = -1;
        else direction = 1;
        colum = -1;
        row = (int) Math.floor(y / heigthCell);
    }

    public void  motionStop(){
        motionCell();
        motion = false;
        direction = 0;
        extrimExitRecurs = 0;
        //RANDOM BOLL
        //addAction(3);
    }

    private void motionCell(){
        if (colum > -1 && row < 12){
            CellMatrix array[] = getArray(direction,colum);
            if (direction > 0)
                recursMove(1, false, array);
            else recursMove(11,false, array);
        }else if (row > -1 && colum < 12){
            CellMatrix array[] = getArray(direction,row);
            if (direction > 0)
                recursMove(1, false, array);
            else recursMove(11,false, array);
        }
    }

    private CellMatrix[] getArray(int direction, int number){
        CellMatrix[] array = new CellMatrix[sizeHeight];
        for (int i = 0; i < sizeHeight; i++) {
            if (colum > -1 && row < 12){
                array[i] = map[number][i];
            }else {
                array[i] = map[i][number];
            }
            Gdx.app.log("array", i + " " + array[i].getId());
        }
        return array;
    }

    private void recursMove(int cell, boolean emptyCell,CellMatrix[] array){
        if (extrimExitRecurs > 12)
            return;
        extrimExitRecurs++;

        if (!(cell+direction < sizeWigth && cell > 0))
            return;

        if (array[cell].getId() == 0){
        recursMove(cell+direction,true,array);
        } else {
            CellMatrix cellCerrent = array[cell];
            CellMatrix cellPrevius = null;
            if (direction > 0){
                if (cell == 1) {
                    recursMove(cell + direction, false, array);
                    return;
                }
                cellPrevius = array[cell-direction];
            } else {
                if (cell == 11) {
                    recursMove(cell + direction, false, array);
                    return;
                }
                cellPrevius = array[cell+abs(direction)];
            }

            if (emptyCell){
                cellPrevius.setId(cellCerrent.getId());
                cellCerrent.setId(0);
                recursMove(cell+direction,true,array);
            } else {
                if (cellPrevius.getId() == cellCerrent.getId()){
                    recursMove(cell+direction,false,array);
                } else {
                    int mixId = cellPrevius.getId() + cellCerrent.getId();
                    cellPrevius.setId(mixId);
                    cellCerrent.setId(0);
                    recursMove(cell+direction,true,array);
                }
            }
        }
    }


    private void recursMoveRow(int row, boolean emptyCell){
        if (extrimExitRecurs > 12)
            return;
        extrimExitRecurs++;
        if (row+direction < sizeWigth && row > 0){
            if (map[colum][row].getId() == 0){
                recursMoveRow(row+direction, true);
            }else {
                if (emptyCell) {
                    CellMatrix cellMatrix = map[colum][row];
                    CellMatrix cellMatrix1;
                    if (direction > 0)
                        cellMatrix1 = map[colum][row-direction];
                    else cellMatrix1 = map[colum][row+abs(direction)];
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
        if (extrimExitRecurs > 12)
            return;
        extrimExitRecurs++;
        //Gdx.app.log("colum", String.valueOf(colum) + " " + direction);
        if (colum+direction < sizeHeight && colum > 0){
            if (map[colum][row].getId() == 0){
                recursMoveColum(colum+direction, true);
            }else {
                CellMatrix cellMatrix = map[colum][row];
                CellMatrix cellMatrix2 = null;
                if (direction > 0)
                    cellMatrix2 = map[colum-direction][row];
                else if (colum < 11)
                    cellMatrix2 = map[colum+abs(direction)][row];
                else     recursMoveColum(colum + direction, false);
                if (cellMatrix2 != null) {
                    if (emptyCell) {
                        cellMatrix2.setId(cellMatrix.getId());
                        cellMatrix.setId(0);
                        recursMoveColum(colum + direction, true);
                    } else {
                        if (cellMatrix.getId() != cellMatrix2.getId()) {
                            int mixId = cellMatrix.getId() + cellMatrix2.getId();
                            cellMatrix.setId(mixId);
                            cellMatrix2.setId(0);
                            recursMoveColum(colum + direction, true);
                        }
                        recursMoveColum(colum + direction, false);
                    }
                }
            }
        }
    }
    private void addAction(int count){
        for (int i = 0; i < count; i++){
            int colum = random(9)+1;
            int row = random(9)+1;
            if (map[colum][row].getId() == 0)
                map[colum][row].setId(3);
            //Gdx.app.log("add",colum + " " + row + " " + map[colum][row].getId());
        }
    }

    ////////////////////////////////////////
    //Metods to get variable cell

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
    public Color getColorCell(int x, int y){
        return map[x][y].getColor();
    }

    /////////////////////////////////
}
