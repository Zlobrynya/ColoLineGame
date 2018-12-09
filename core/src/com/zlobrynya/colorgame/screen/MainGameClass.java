package com.zlobrynya.colorgame.screen;

//https://www.codeandweb.com/texturepacker/tutorials/libgdx-physics

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.zlobrynya.colorgame.screen.GameScreen;
import com.zlobrynya.colorgame.status.StatusScreen;

import java.util.HashMap;

public class MainGameClass extends Game {
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private GameScreen gameScreen;
    private GameOverScreen gameOverScreen;

    //const variable
    public static final float worldWidth = 600;
    public static final float worldHeight = 800;

    @Override
	public void create () {
	    camera = new OrthographicCamera();
	    viewport = new ExtendViewport(worldWidth, worldHeight, camera);
        gameScreen = new GameScreen(camera,viewport, this);
        gameOverScreen = new GameOverScreen(camera,viewport,this);

        setScreen(gameScreen);
    }

    public void setGameScreen(StatusScreen name){
        switch (name){
            case GameScreen:
                setScreen(gameScreen);
                break;
            case GameOverScreen:
                setScreen(gameOverScreen);
                break;
            default:
                break;
        }
    }

    @Override
    public void resize(int width, int height) {
    }

   /* @Override
	public void render () {
	}
	
	@Override
	public void dispose () {
	}*/
}
