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

        addAction(26);
       /* map[2][5] = new CellMatrix("",7);
        map[2][4] = new CellMatrix("",7);
        map[3][6] = new CellMatrix("",7);*/
        map[6][4].setId(1);
        map[6][4].setNameSprite("crate");
        debugOutMatrix();
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
//        debugOutMatrix();
        if (colum > -1 && row < 12) {
            checkMatch3(colum);
        }else if (row > -1 && colum < 12){
            checkMatch3(row);
        }
        motion = false;
        direction = 0;
        extrimExitRecurs = 0;
        //RANDOM BOLL
        addAction(3);
    }

    private void motionCell(){
        if (colum > -1 && row < 12){
            CellMatrix array[] = getArray(colum);
            if (direction > 0)
                recursMove(1, false, array);
            else recursMove(10,false, array);
        }else if (row > -1 && colum < 12){
            CellMatrix array[] = getArray(row);
            if (direction > 0)
                recursMove(1, false, array);
            else recursMove(10,false, array);
        }
    }

    private CellMatrix[] getArray(int number){
        CellMatrix[] array = new CellMatrix[sizeHeight];
        for (int i = 0; i < sizeHeight; i++) {
            if (colum > -1 && row < 12){
                array[i] = map[number][i];
            }else {
                array[i] = map[i][number];
            }
        }
        return array;
    }


    private void checkMatch3(int numberLine){
        CellMatrix[][] areaMatrix = new CellMatrix[5][];

        for(int i = 0, j = -2; i < 5; i++,j++)
            areaMatrix[i] = getArray(numberLine-j);

        int countColor = 1;
        for (int line = 2; line < sizeHeight; line++){
            if (areaMatrix[2][line].getId() > 5){
                if (areaMatrix[2][line].getId() == areaMatrix[2][line-1].getId()){
                    countColor++;
                    if (countColor == 3){
                        areaMatrix[2][line].setDelete(true);
                        areaMatrix[2][line-1].setDelete(true);
                        areaMatrix[2][line-2].setDelete(true);
                    }
                    if (countColor > 3){
                        areaMatrix[2][line].setDelete(true);
                    }
                }else{
                    countColor = 0;
                }
            }
            int countColorVertical = 0;
            for (int lineVer = 1; lineVer < 5; lineVer++){
                if (areaMatrix[lineVer][line].getId() > 5){
                    if (areaMatrix[lineVer][line].getId() == areaMatrix[lineVer-1][line].getId()){
                        countColorVertical++;
                        if (countColorVertical == 2){
                            areaMatrix[lineVer][line].setDelete(true);
                            areaMatrix[lineVer-1][line].setDelete(true);
                            areaMatrix[lineVer-2][line].setDelete(true);
                        }
                        if (countColorVertical > 3){
                            areaMatrix[lineVer][line].setDelete(true);
                        }
                    }else{
                        countColorVertical = 0;
                    }
                }
            }
        }
        deleteBools(areaMatrix);
    }

    private void deleteBools(CellMatrix[][] areaMatrix){
        for(int line = 1; line < sizeHeight-1; line++){
            for (int lineVer = 0; lineVer < 5; lineVer++){
                if (areaMatrix[lineVer][line].isDelete()){
                    areaMatrix[lineVer][line].setId(0);
                    areaMatrix[lineVer][line].setDelete(false);
                }
            }
        }
    }


    private void recursMove(int cell, boolean emptyCell,CellMatrix[] array){
        if (extrimExitRecurs > 12)
            return;
        extrimExitRecurs++;

        if (!(cell+direction < 10 && cell > 0))
            return;

        if (array[cell].getId() == 0){
            recursMove(cell+direction,true,array);
        } else {
            if (array[cell].getId() == 1){
                recursMove(cell+direction,false,array);
            }else {
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
                        int previusId = cellPrevius.getId();
                        int cerrentId = cellCerrent.getId();
                        //VREMENNO
                        if (previusId == 1){
                            recursMove(cell+direction,false,array);
                            return;
                        }
                        if (previusId != cerrentId){
                            int mixId = cellPrevius.getId() + cellCerrent.getId();
                            if (mixId > 6 && mixId < 10){
                                cellPrevius.setId(mixId);
                                cellCerrent.setId(0);
                                recursMove(cell+direction,true,array);
                            }else{
                                recursMove(cell+direction,false,array);
                            }

                        }
                    }
                }

            }
        }
    }

    private void addAction(int count){
        for (int i = 0; i < count; i++){
            int colum = random(9)+1;
            int row = random(9)+1;
            if (map[colum][row].getId() == 0){
                int color = random(2);
                switch (color){
                    case 0:
                        map[colum][row].setId(3);
                        break;
                    case 1:
                        map[colum][row].setId(4);
                        break;
                    case 2:
                        map[colum][row].setId(5);
                        break;
                }
            }
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
