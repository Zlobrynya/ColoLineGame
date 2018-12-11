package com.zlobrynya.colorgame.mechanics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.zlobrynya.colorgame.Player;
import com.zlobrynya.colorgame.status.StatusDrawGame;

import static com.badlogic.gdx.math.MathUtils.random;
import static java.lang.Math.abs;

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

        addAction(3);
        addBlock(3,2);
        addBlock(1,5);
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
           // Gdx.app.log("motion : ", "_______________________");
           // Gdx.app.log("motion : ", "deltaX: " + deltaX + " deltaY: " + deltaY);
            if (deltaX > wigthCell-5 || deltaX < -(wigthCell-5)){
                motionRow(y,deltaX);
            }else if (deltaY > heigthCell-5 || deltaY < -(heigthCell-5)){
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
        if (status != StatusDrawGame.STOP){
            motionCell();
            checkMatch3();

            status = StatusDrawGame.MOUTION;
            direction = 0;
            extrimExitRecurs = 0;
            addAction(1);
            sumFreeCell(map);
            if (freeCell == 0) {
                Gdx.app.log("GameOver", freeCell + "");
                status = StatusDrawGame.GAME_OVER;
                //checkGameOver();
            }
        }
    }

    private void moveLine(int cell, boolean emptyCell,CellMatrix[] array){
        for (int i = 0; i < size-1; i++){
            extrimExitRecurs = 0;
            recursMove(cell,emptyCell,array);
        }
    }

    private void motionCell(){
        if (colum > -1 && row < size && colum < size){
            CellMatrix array[] = getArray(colum,true);
            if (direction > 0)
                moveLine(1, false, array);
            else moveLine(size -1,false, array);
        }else if (row > -1 && colum < size && row < size){
            CellMatrix array[] = getArray(row,false);
            if (direction > 0)
                moveLine(1, false, array);
            else moveLine(size -1,false, array);
        }
    }

    //@direction: true - vericaly, false horizontaly
    private CellMatrix[] getArray(int number, boolean direction){
        CellMatrix[] array = new CellMatrix[size];
        for (int i = 0; i < size; i++) {
            if (direction){
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

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Исправить
    private void checkMatch3(){
        for (int x = 1, y = 1; x < size - 1; x++, y++){
            CellMatrix[] array = getArray(x, false);
            //Gdx.app.log("findMatch3", "X: " + x );
            findMatch3(array,1,0);
            //Gdx.app.log("findMatch3", "Y: " + y );
            array = getArray(y, true);
            findMatch3(array,1,0);
        }
        deleteBools(map);
    }

    private void findMatch3(CellMatrix[] array, int number, int count){
        //ectrimal exit
        if (number >= size)
            return;

        int curretColor = array[number].getId();
        int previousColor = array[number-1].getId();
        //Gdx.app.log("findMatch3", "currer: " + curretColor + " previous: " + previousColor);
        if (curretColor > 6 && previousColor > 6){
            if (curretColor == previousColor){
                count++;
                if (count == 2){
                    array[number].setDelete(true);
                    array[number-1].setDelete(true);
                    array[number-2].setDelete(true);
                }
                if (count > 2){
                    array[number].setDelete(true);
                }
                findMatch3(array,number + 1,count);
                return;
            }
        }
        findMatch3(array,number + 1,0);
    }

    private void checkGameOver(){
        for (int i = 1; i < size - 1; i++){
            CellMatrix[] array = getArray(i,false);
            Gdx.app.log("findMix[x] ", String.valueOf(i));

            int result = findMix(1,array,2);

            Gdx.app.log("findMix[result] : ", String.valueOf(result));

            if (result == 1){
                return;
            }
        }
        status = StatusDrawGame.GAME_OVER;
    }

    private int findMix(int previousColor, CellMatrix[] array, int number){
        boolean exit = false;
        int curretColor = array[number].getId();
        int sumColor = (previousColor != curretColor) ? 0 : previousColor + curretColor;
        exit = sumColor > 6 && sumColor < 10 ;
        if (exit){
            Gdx.app.log("findMix[y] ", String.valueOf(number));
            return 1;
        }else {
            if (number < size-2){
                findMix(array[number].getId(), array, ++number);
            }
        }
        return 0;
    }

    //amount of free blocks
    private void sumFreeCell(CellMatrix[][] areaMatrix){
        freeCell = 0;
        for(int line = 0; line < size; line++)
            for (int lineVer = 0; lineVer < size; lineVer++)
                if (areaMatrix[line][lineVer].getId() == 0) {
                    freeCell++;
                }
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
        for (int i = 0; freeCell > 0;){
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
                if (i >= count)
                    break;
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
