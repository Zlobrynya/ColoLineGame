package com.zlobrynya.colorgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.zlobrynya.colorgame.enume.StatusScreen;

public class GameOverScreen extends AbstractScreen implements InputProcessor {
    private SpriteBatch spriteBatch;
    private BitmapFont font = new BitmapFont();


    GameOverScreen(OrthographicCamera camera, ExtendViewport viewport, MainGameClass gameClass){
        super(camera,viewport, gameClass);
        spriteBatch = getBatch();
    }

    @Override
    public void show() {
        Gdx.app.log("GameOver", "Show");
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        drawBackground();
        drawGameOver();
    }

    private void drawBackground(){
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void drawGameOver(){
        spriteBatch.begin();
        font.getData().setScale(4);
        font.draw(spriteBatch, "Game Over ", 150 , 150);
        spriteBatch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        setGameClass(StatusScreen.GameScreen);
        //Gdx.app.log("GameOver","touch");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

}
