package com.jason.scenes;

import android.graphics.PointF;
import android.graphics.RectF;
import android.view.MotionEvent;
import com.jason.R;
import ice.animation.Interpolator.LinearInterpolator;
import ice.animation.RotateAnimation;
import ice.engine.App;
import ice.engine.EngineContext;
import ice.engine.Scene;
import ice.graphic.Primitives;
import ice.graphic.Texture;
import ice.node.Drawable;
import ice.node.mesh.Grid;
import ice.node.mesh.Mesh;
import ice.node.mesh.vertex.ObjLoader;
import ice.node.mesh.vertex.VertexArray;
import ice.node.mesh.vertex.VertexBufferObject;
import ice.node.mesh.vertex.VertexData;
import ice.node.particle_system.TestParticleSystem;
import ice.res.Res;

import javax.microedition.khronos.opengles.GL11;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static javax.microedition.khronos.opengles.GL11.*;

/**
 * User: ice
 * Date: 12-1-6
 * Time: 下午5:07
 */
public class MainScene extends Scene {

    private static final String TAG = MainScene.class.getSimpleName();

    public MainScene() {
        App app = EngineContext.getInstance().getApp();

        Grid grid = new Grid(50, 50);
        grid.setPos(app.getWidth() >> 1, app.getHeight() >> 1, 200);
        grid.setCallFace(false);
        grid.setTexture(new Texture(Res.getBitmap(R.drawable.star)));

        Drawable objMesh = objMeshTest();

        TestParticleSystem testParticleSystem = particleTest();


        addChildren(grid, objMesh, testParticleSystem);
    }

    private TestParticleSystem particleTest() {
        TestParticleSystem testParticleSystem = new TestParticleSystem(50, new Texture(Res.getBitmap(R.drawable.spark)));
        testParticleSystem.setPos(300, 400, 150);

        RotateAnimation rotateAnimation = new RotateAnimation(10000, 0, 360);
        rotateAnimation.setRotateVector(1, 1, 1);
        rotateAnimation.setLoop(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        testParticleSystem.startAnimation(rotateAnimation);
        return testParticleSystem;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        if (action == MotionEvent.ACTION_DOWN) {
            lastMotionY = y;
            lastMotionX = x;
        }
        else if (action == MotionEvent.ACTION_MOVE) {
            float deltaX = x - lastMotionX;
            float deltaY = lastMotionY - y;

            setPos(getPosX() + deltaX / 10, getPosY() + deltaY / 10, 0);
        }


        return true;
    }

    private Drawable objMeshTest() {
        ObjLoader objLoader = new ObjLoader();
        objLoader.loadObj(Res.openAssets("teaport.obj"));

        VertexData vertexData = new VertexBufferObject(objLoader.getVertexNum(), objLoader.getAttributes());
        vertexData.setVertices(objLoader.getVertexData());

        Mesh objMesh = new Mesh(vertexData);

        objMesh.setPos(600, 400, 100);

        objMesh.setCallFace(false);

        objMesh.bindTexture(new Texture(Res.getBitmap(R.drawable.mask1)));

        RotateAnimation rotateAnimation = new RotateAnimation(10000, 0, 360);
        rotateAnimation.setRotateVector(1, 1, 1);
        rotateAnimation.setLoop(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        objMesh.startAnimation(rotateAnimation);
        objMesh.setCallFace(false);

        return objMesh;
    }

    private float lastMotionX, lastMotionY;
}
