package com.zlobrynya.colorgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.zlobrynya.colorgame.status.StatusScreen;

public class GameOverScreen extends AbstractScreen implements GestureDetector.GestureListener {
    private SpriteBatch spriteBatch;
    private BitmapFont font = new BitmapFont();


    GameOverScreen(OrthographicCamera camera, ExtendViewport viewport, MainGameClass gameClass){
        super(camera,viewport, gameClass);

    }

    @Override
    public void show() {

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
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        setGameClass(StatusScreen.GameScreen);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
