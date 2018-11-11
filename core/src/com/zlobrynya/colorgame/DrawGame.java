package com.zlobrynya.colorgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DrawGame {
    private SpriteBatch spriteBatch;
    private TextureAtlas textureAtlas;
    private ShapeRenderer shapeRenderer;

    //draw Text
    BitmapFont font = new BitmapFont();

    DrawGame(SpriteBatch spriteBatch){
        this.spriteBatch = spriteBatch;
        textureAtlas = new TextureAtlas("sprite.txt");
        shapeRenderer = new ShapeRenderer();
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }


    public void drawMap(MapClass mapClass, Player playerData){
        float wigthCell = mapClass.getWigthCell();
        float heigthCell = mapClass.getHeigthCell();
        //debugOutMatrix();
        drawText(playerData,150, 750, 0);
        for (int heigth = 0; heigth < mapClass.getSize(); heigth++)
            for (int wigth = 0; wigth < mapClass.getSize(); wigth++) {
                float coordX = heigth*heigthCell;
                float coordY = wigth*wigthCell;

                switch (mapClass.getIdCell(heigth,wigth)){
                    case 1:
                        drawSprite(textureAtlas.createSprite(mapClass.getNameSprite(heigth,wigth)),
                        wigthCell,heigthCell,wigth,heigth);
                        break;
                    case 0:
                        drawMapCell(coordX,coordY,wigthCell,heigthCell);
                        break;
                    default:
                        drawActionBall(coordX,coordY,wigthCell,heigthCell,mapClass.getColorCell(heigth,wigth));
                        drawMapCell(coordX,coordY,wigthCell,heigthCell);
                        break;
                }
            }
     }

     private void drawText(Player playerData, float coordX, float coordY, float size){
         spriteBatch.begin();
         font.draw(spriteBatch, "Score: " + playerData.getScore(), coordX , coordY);
         spriteBatch.end();
     }


     private void drawMapCell(float coordX, float coordY, float wigth, float heigth){
         shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
         shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
         shapeRenderer.setColor(Color.GRAY);
         shapeRenderer.rect(coordX-2,coordY-2,wigth-2,heigth-2);
         shapeRenderer.end();
    }

     private void drawActionBall(float coordX, float coordY, float wigth, float heigth, Color color){
         coordX = coordX  + (heigth / 2) - 2;
         coordY = coordY  + (wigth / 2) - 2;

         shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
         shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
         shapeRenderer.setColor(color);
         shapeRenderer.circle(coordX,coordY,10);
         shapeRenderer.end();
     }

     private void drawSprite(Sprite sprite,float widthCell, float heigthCell, int width, int heigth){
         spriteBatch.begin();
         //sprite.setPosition(heigth*mapClass.getHeigthCell(),wigth* mapClass.getSizeWigth());
         //Gdx.app.log("Draw", widthCell + " " + heigthCell + " " + width + " " + heigth + " " +  sprite);
         sprite.setPosition(heigth*heigthCell-4,width*widthCell);
         sprite.setSize(heigthCell,widthCell);
         sprite.draw(spriteBatch);
         spriteBatch.end();
     }
}
