package com.jason.scenes;

import android.view.MotionEvent;
import com.jason.R;
import ice.animation.AlphaAnimation;
import ice.animation.Interpolator.LinearInterpolator;
import ice.animation.RotateAnimation;
import ice.engine.App;
import ice.engine.EngineContext;
import ice.engine.Scene;
import ice.graphic.Texture;
import ice.node.Drawable;
import ice.node.mesh.Grid;
import ice.node.mesh.Mesh;
import ice.util.ObjLoader;
import ice.model.vertex.VertexBufferObject;
import ice.model.vertex.VertexData;
import ice.node.particle_system.TestParticleSystem;
import ice.res.Res;

import static javax.microedition.khronos.opengles.GL11.*;

/**
 * User: ice
 * Date: 12-1-6
 * Time: 下午5:07
 */
public class MainScene extends Scene {

    public MainScene() {
        App app = EngineContext.getInstance().getApp();

        Grid grid = new Grid(50, 50);
        grid.setPos(app.getWidth() >> 1, app.getHeight() >> 1, 200);
        grid.setCallFace(false);
        grid.setTexture(new Texture(R.drawable.star));
        grid.enableBlend(GL_SRC_ALPHA, GL_ONE);

        Drawable objMesh = objMeshTest();

        TestParticleSystem testParticleSystem = particleTest();

        AlphaAnimation alphaAnimation = new AlphaAnimation(3000, 1, 0);
        alphaAnimation.setLoop(true);
        grid.startAnimation(alphaAnimation);

        addChildren(grid, objMesh, testParticleSystem);
    }

    private TestParticleSystem particleTest() {

        TestParticleSystem testParticleSystem = new TestParticleSystem(
                50,
                new Texture(R.drawable.spark)
        );

        testParticleSystem.enableBlend(GL_ONE, GL_ONE);

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

        objMesh.bindTexture(new Texture(R.drawable.mask1));

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
