package com.zlobrynya.colorgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.zlobrynya.colorgame.mechanics.DrawGame;
import com.zlobrynya.colorgame.mechanics.InputHandler;
import com.zlobrynya.colorgame.mechanics.MapClass;
import com.zlobrynya.colorgame.Player;
import com.zlobrynya.colorgame.enume.StatusScreen;

public class GameScreen extends AbstractScreen {
    private TextureAtlas textureAtlas;
    private MapClass mapClass;
    private DrawGame drawGame;
    private Player playerData;

    public GameScreen(OrthographicCamera camera, ExtendViewport viewport, MainGameClass gameClass){
        super(camera,viewport,gameClass);
    }

    @Override
    public void show() {
        int sizeScreenBlock = 8;

        int wigthCell = (int) Math.floor(MainGameClass.worldWidth/ sizeScreenBlock);
        int heigthCell = (int) Math.floor(MainGameClass.worldWidth/ sizeScreenBlock);

        textureAtlas = new TextureAtlas("sprite.txt");
        playerData = new Player(0);
        mapClass = new MapClass(sizeScreenBlock,wigthCell,heigthCell,playerData);
        drawGame = new DrawGame(getBatch());

        Gdx.input.setInputProcessor((new GestureDetector(new InputHandler(mapClass,getCamera()))));

    }

    @Override
    public void render(float delta) {
        switch (mapClass.getStatus()){
            case GAME_OVER:
                Gdx.app.log("GameOver", "Status");
                setGameClass(StatusScreen.GameOverScreen);
                break;
            case STOP:
                break;
            case MOUTION:

                break;
        }
        drawGame.drawGame(mapClass, playerData,delta);
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
    }
}
