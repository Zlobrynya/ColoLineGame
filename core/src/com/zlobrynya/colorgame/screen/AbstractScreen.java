package com.zlobrynya.colorgame.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.zlobrynya.colorgame.status.StatusScreen;

public abstract class AbstractScreen implements Screen {
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private SpriteBatch batch;
    private MainGameClass gameClass;

    AbstractScreen(OrthographicCamera camera, ExtendViewport viewport, MainGameClass gameClass){
        this.camera = camera;
        this.viewport = viewport;
        this.batch = new SpriteBatch();
        this.gameClass = gameClass;
    }

    public void setGameClass(StatusScreen name){
        gameClass.setGameScreen(name);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
    }

}
