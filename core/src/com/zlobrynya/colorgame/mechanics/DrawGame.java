package com.zlobrynya.colorgame.mechanics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.zlobrynya.colorgame.Player;
import com.zlobrynya.colorgame.enume.NameColorTexture;
import com.zlobrynya.colorgame.enume.StatusDrawGame;

import java.util.Hashtable;

public class DrawGame {

    private SpriteBatch spriteBatch;
    private TextureAtlas textureAtlas;
    private ShapeRenderer shapeRenderer;
    private Animation runningAnimationLeft;
    private Hashtable<NameColorTexture,Animation> hashtable;

    static final int INDENT = 90;
    private static float FRAME_DURATION = .25f;

    // Elapsed time
    private float elapsed_time = .10f;




    //draw Text
    private BitmapFont font = new BitmapFont();

    public DrawGame(SpriteBatch spriteBatch){
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        hashtable = new Hashtable<NameColorTexture, Animation>();
        this.spriteBatch = spriteBatch;
        textureAtlas = new TextureAtlas("sprite.txt");
        shapeRenderer = new ShapeRenderer();
        loadTexture();
    }

    private void loadTexture(){
        TextureAtlas atlas = new TextureAtlas("actor_blue_move.txt");

        Array<TextureAtlas.AtlasRegion> runningFrames;
        NameColorTexture[] names = NameColorTexture.values();
        for (int i = 0; i < 6; i++){
            runningFrames = atlas.findRegions(names[i].name());
            hashtable.put(names[i],new Animation(FRAME_DURATION, runningFrames, Animation.PlayMode.LOOP));
        }
    }

    public void drawGame(MapClass mapClass, Player playerData, float delta){
       /* drawBackground();
        drawMap(mapClass,playerData);*/
       /* switch (statusDrawGame){
            case ANIMATION:
                break;
            case MOUTION:
                drawBackground();
                drawMap(mapClass,playerData);
                mapClass.setStatus(StatusDrawGame.STOP);
                break;
            case STOP:
                break;
            case GAME_OVER:
                //drawBackground();
                //drawGameOver();
                mapClass.setStatus(StatusDrawGame.STOP);
                break;
        }*/
        elapsed_time += delta;
        drawBackground();
        drawMap(mapClass,playerData);
        mapClass.setStatus(StatusDrawGame.STOP);
    }


    private void drawBackground(){
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawMap(MapClass mapClass, Player playerData){
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
        font.getData().setScale(3);
        font.draw(spriteBatch, "Score: " + playerData.getScore(), coordX ,coordY);
        spriteBatch.end();
    }

    private void drawMapCell(float coordX, float coordY, float wigth, float heigth){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.rect(coordX-2,INDENT + coordY-2,wigth-2,heigth-2);
        shapeRenderer.end();
    }

    private void drawActionBall(float coordX, float coordY, float wigth, float heigth, NameColorTexture color){
        /*coordX = coordX  + (heigth / 2) - 2;
        coordY = coordY  + (wigth / 2) - 2;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        shapeRenderer.setColor(color);
        shapeRenderer.circle(coordX, INDENT + coordY,wigth/2-15);
        shapeRenderer.end();*/
        //Gdx.app.log("drawActionBall", String.valueOf(color));
        // Getting the frame we must draw at this moment
        TextureRegion currentFrame = hashtable.get(color).getKeyFrame(elapsed_time, true);


        spriteBatch.begin();
        spriteBatch.setProjectionMatrix(spriteBatch.getProjectionMatrix());
        spriteBatch.draw(currentFrame,coordX+4,INDENT + coordY);
        spriteBatch.end();

    }

    private void drawAnimation(float coordX, float coordY, float wigth, float heigth, Color color){
        //runningAnimation = new Animation(0.033f, atlas.findRegions("running"), Animation.PlayMode.LOOP);
    }


    private void drawSprite(Sprite sprite,float widthCell, float heigthCell, int width, int heigth){
        spriteBatch.begin();
        //sprite.setPosition(heigth*mapClass.getHeigthCell(),wigth* mapClass.getSizeWigth());
        //Gdx.app.log("Draw", widthCell + " " + heigthCell + " " + width + " " + heigth + " " +  sprite);
        sprite.setPosition(heigth*heigthCell-4,INDENT + width*widthCell);
        sprite.setSize(heigthCell,widthCell);
        sprite.draw(spriteBatch);
        spriteBatch.end();
    }

    public void dispose(){
        textureAtlas.dispose();
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
