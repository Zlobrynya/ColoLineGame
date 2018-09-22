package com.zlobrynya.colorgame;

//https://www.codeandweb.com/texturepacker/tutorials/libgdx-physics

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import java.util.HashMap;

public class MainGameClass extends ApplicationAdapter {
	private SpriteBatch batch;
    final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	private TextureAtlas textureAtlas;
    private OrthographicCamera camera;
    private ExtendViewport viewport;

    private MapClass mapClass;
    private DrawGame drawGame;

    //const variable
    private final float worldWidth = 600;
    private final float worldHeight = 800;
    private final int sizeScreenBlockH = 12;
    private final int sizeScreenBlockW = 12;


    @Override
	public void create () {
	    camera = new OrthographicCamera();
	    viewport = new ExtendViewport(worldWidth, worldHeight, camera);

		batch = new SpriteBatch();
		textureAtlas = new TextureAtlas("sprite.txt");

		//sprite = textureAtlas.createSprite("crate");
		mapClass = new MapClass(sizeScreenBlockH,sizeScreenBlockW,50,50);
        drawGame = new DrawGame(batch);

        Gdx.input.setInputProcessor((new GestureDetector(new InputHandler(mapClass,camera))));

    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width,height,true);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
	public void render () {
        Gdx.gl.glClearColor(0.57f, 0.77f, 0.85f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawGame.drawMap(mapClass);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		//img.dispose();
	}
}
