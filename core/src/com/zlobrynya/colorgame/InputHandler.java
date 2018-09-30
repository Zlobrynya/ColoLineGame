package com.zlobrynya.colorgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements GestureDetector.GestureListener {
    private MapClass mapClass;
    private OrthographicCamera camera;

    public InputHandler(MapClass mapClass,OrthographicCamera cam){
        this.mapClass = mapClass;
        camera = cam;
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        //  Gdx.app.log("touchDown", x+" "+y);

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        //   rope.setEndPosition(x ,y);
        Vector3 vect3 = convertCoord(x,y);
        boolean tapArea = mapClass.isArea(vect3.x,vect3.y);
        //Gdx.app.log("Tap X", String.valueOf(vect3.x));
        //Gdx.app.log("Tap Y", String.valueOf(vect3.y));
        //Gdx.app.log("Tap", String.valueOf(tapArea));
        return false;
    }

    private Vector3 convertCoord(float x, float y){
        Vector3 vect3 = new Vector3(x,y,0);
        camera.unproject(vect3);
        return vect3;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        //Gdx.app.log("pan", velocityX+" "+velocityY);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //  rope.setStartPosition(x-deltaX,y-deltaY);
        Vector3 vector3 = convertCoord(x,y);
        //Gdx.app.log("pan", "x: " + vector3.x +" y: "+vector3.y+" deltaX: "+deltaX+" deltaY: "+deltaY);
        mapClass.motionCell(vector3.x,vector3.y,deltaX,deltaY);
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        Gdx.app.log("-------", "-------");
        Gdx.app.log("panStop", x+" "+y);
        mapClass.motionStop();
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

