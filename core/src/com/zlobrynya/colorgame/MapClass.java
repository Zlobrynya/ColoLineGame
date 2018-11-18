package com.zlobrynya.colorgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import java.util.Random;

import static com.badlogic.gdx.math.MathUtils.degRad;
import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.abs;
import static java.lang.Math.round;

public class MapClass {

    private CellMatrix[][] map;
    private Player playerData;

    private int size;
    private int colum;
    private int row;
    private int direction;
    private int extrimExitRecurs;
    private int freeCell;

    private float wigthCell;
    private float heigthCell;
    private float maxAriaHeight;
    private float maxAriaWigth;

    private StatusDrawGame status;

    //final Random random = new Random();

    public MapClass(int size, float wigthCell, float heigthCell, Player player){
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        playerData = player;

        this.wigthCell = wigthCell;
        this.heigthCell = heigthCell;
        this.size = size;
        //this.sizeWigth = sizeWigth;
        status = StatusDrawGame.MOUTION;

        colum = row = -1;
        extrimExitRecurs  = 0;
        freeCell = size * size;

        maxAriaHeight = size * this.heigthCell;
        maxAriaWigth = size * this.wigthCell;
        Gdx.app.log("Max Heigth: ", String.valueOf(maxAriaHeight));
        Gdx.app.log("Max Wigth: ", String.valueOf(maxAriaWigth));

        map = new CellMatrix[size][size];
        createMap(size);
    }

    private void createMap(int size){
        for (int x = 0; x < size; x++){
            for(int y = 0; y < size;y++){
                map[y][x] = new CellMatrix("",0);
            }
        }
        for (int x = 0; x < size; x++) {
            map[0][x] = new CellMatrix("crate",1);
            map[size - 1][x] = new CellMatrix("crate",1);
        }
        for (int y = 0; y < size; y++){
            map[y][0] = new CellMatrix("crate",1);
            map[y][size - 1] = new CellMatrix("crate",1);
        }

        addAction(4);
        addBlock(3,2);
        debugOutMatrix();
    }

    private void addBlock(int x, int y){
        map[x][y].setId(1);
        map[x][y].setNameSprite("crate");
    }

    private void debugOutMatrix(){
        StringBuilder debug = new StringBuilder();
        for (int heigth = 0; heigth < size; heigth++){
            for (int wigth = 0; wigth < size; wigth++)
                debug.append(map[wigth][heigth].getId()).append(" ");
            Gdx.app.log("Matrix: ", debug.toString());
            debug = new StringBuilder();
        }
    }

    public boolean isArea(float x, float y){
        return ((x > 0 && x < maxAriaWigth) && (y > 0 && y < maxAriaHeight));
    }

    public void motionCell(float x, float y, float deltaX, float deltaY){
        if (status == StatusDrawGame.STOP){
            if (deltaX > 60 || deltaX < -60){
                motionRow(y,deltaX);
            }else if (deltaY > 60 || deltaY < -60){
                motionColum(x,deltaY);
            }
        }
    }

    private void motionColum(float x, float deltaY){
        status = StatusDrawGame.MOUTION;
        if (deltaY > 0)
            direction = 1;
        else direction = -1;
        row = -1;
        colum = (int) Math.floor(x / wigthCell);
    }

    private void motionRow(float y, float deltaX){
        status = StatusDrawGame.MOUTION;
        if (deltaX > 0)
            direction = -1;
        else direction = 1;
        colum = -1;
        row = (int) Math.floor((y - DrawGame.INDENT) / heigthCell);
    }

    public void  motionStop(){
        motionCell();if (colum > -1 && row < size && colum < size) {
            checkMatch3(colum,false);
        }else if (row > -1 && colum < size && row < size){
            checkMatch3(row,true);
        }
        status = StatusDrawGame.MOUTION;
        direction = 0;
        extrimExitRecurs = 0;
        //RANDOM BOLL
        addAction(2);
    }

    private void motionCell(){
        if (colum > -1 && row < size && colum < size){
            CellMatrix array[] = getArray(colum);
            if (direction > 0)
                recursMove(1, false, array);
            else recursMove(size -1,false, array);
        }else if (row > -1 && colum < size && row < size){
            CellMatrix array[] = getArray(row);
            if (direction > 0)
                recursMove(1, false, array);
            else recursMove(size -1,false, array);
        }
    }

    private CellMatrix[] getArray(int number){
        CellMatrix[] array = new CellMatrix[size];
        for (int i = 0; i < size; i++) {
            if (colum > -1 && row < 12){
                array[i] = map[number][i];
            }else {
                array[i] = map[i][number];
            }
        }
        return array;
    }

    private CellMatrix[][] transMatrix(){
        CellMatrix[][] array = new CellMatrix[size][size];
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                array[i][j] = map[j][i];
            }
        }
        return array;
    }

    private void checkMatch3(int numberLine, boolean trans){
        CellMatrix[][] areaMatrix = null;

        if (trans){
            areaMatrix = transMatrix();
        }else {
            areaMatrix = map;
        }

        int countColor = 1;
        for (int line = 2; line < size; line++){
            if (areaMatrix[numberLine][line].getId() > 5){
                if (areaMatrix[numberLine][line].getId() == areaMatrix[numberLine][line-1].getId()){
                    countColor++;
                    if (countColor == 3){
                        areaMatrix[numberLine][line].setDelete(true);
                        areaMatrix[numberLine][line-1].setDelete(true);
                        areaMatrix[numberLine][line-2].setDelete(true);
                    }
                    if (countColor > 3){
                        areaMatrix[numberLine][line].setDelete(true);
                    }
                }else{
                    countColor = 0;
                }
            }
            int countColorVertical = 0;
            for (int lineVer = 2; lineVer < size; lineVer++){
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
        sumFreeCell(areaMatrix);
    }

    //amount of free blocks
    private void sumFreeCell(CellMatrix[][] areaMatrix){
        freeCell = 0;
        for(int line = 1; line < size -1; line++)
            for (int lineVer = 1; lineVer < size; lineVer++)
                if (areaMatrix[line][lineVer].getId() == 0)
                    freeCell++;
    }

    private void deleteBools(CellMatrix[][] areaMatrix){
        for(int line = 0; line < size -1; line++){
            for (int lineVer = 0; lineVer < size; lineVer++){
                if (areaMatrix[lineVer][line].isDelete()){
                    playerData.setScore(areaMatrix[lineVer][line].getId());
                    areaMatrix[lineVer][line].setId(0);
                    areaMatrix[lineVer][line].setDelete(false);
                }
            }
        }
    }


    private void recursMove(int cell, boolean emptyCell,CellMatrix[] array){
        if (extrimExitRecurs > size)
            return;
        extrimExitRecurs++;

        if (!(cell+direction < size && cell > 0))
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
                    if (cell == size -1) {
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
        for (int i = 0; i < count && freeCell > 0;){
            int colum = random(size -2)+1;
            int row = random(size -2)+1;
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
                i++;
                freeCell--;
            }
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

    public int getSize() {
        return size;
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

    public StatusDrawGame getStatus() {
        return status;
    }

    public void setStatus(StatusDrawGame status) {
        this.status = status;
    }

    /////////////////////////////////
}
