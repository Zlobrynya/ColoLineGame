package com.zlobrynya.colorgame.mechanics;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputHandler implements GestureDetector.GestureListener {
    private MapClass mapClass;
    private OrthographicCamera camera;
    private float firstTouchDownX;
    private float firstTouchDownY;

    public InputHandler(MapClass mapClass,OrthographicCamera cam){
        this.mapClass = mapClass;
        camera = cam;
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        Gdx.app.log("touchDown", x+" "+y);
        firstTouchDownX = x;
        firstTouchDownY = y;
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
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
      /* //  rope.setStartPosition(x-deltaX,y-deltaY);
        Vector3 vector3 = convertCoord(x,y);
        Gdx.app.log("pan", "x: " + x +" y: "+ y);
        mapClass.motionCell(vector3.x,vector3.y,deltaX,deltaY);*/
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
       /* Gdx.app.log("-------", "-------");
        Gdx.app.log("panStop", x+" "+y);
        Gdx.app.log("DeltaX", String.valueOf(x-firstTouchDownX));
        Gdx.app.log("DeltaY", String.valueOf(y-firstTouchDownY));*/
        Vector3 vector3 = convertCoord(x,y);
        mapClass.motionCell(vector3.x,vector3.y,x-firstTouchDownX,y-firstTouchDownY);
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

