package com.zlobrynya.colorgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.zlobrynya.colorgame.status.StatusDrawGame;
import com.zlobrynya.colorgame.mechanics.DrawGame;
import com.zlobrynya.colorgame.mechanics.InputHandler;
import com.zlobrynya.colorgame.mechanics.MapClass;
import com.zlobrynya.colorgame.Player;
import com.zlobrynya.colorgame.status.StatusScreen;

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
       // Gdx.app.log("render","render");
        drawGame.drawGame(mapClass, playerData, mapClass.getStatus());
        if (mapClass.getStatus() == StatusDrawGame.GAME_OVER){
            setGameClass(StatusScreen.GameOverScreen);
        }
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
    }
}
