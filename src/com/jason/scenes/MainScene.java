package com.jason.scenes;

import android.view.MotionEvent;
import com.jason.R;
import ice.animation.AlphaAnimation;
import ice.animation.Interpolator.LinearInterpolator;
import ice.animation.RotateAnimation;
import ice.engine.EngineContext;
import ice.engine.Scene;
import ice.graphic.Texture;
import ice.model.vertex.VertexBufferObject;
import ice.model.vertex.VertexData;
import ice.node.Drawable;
import ice.node.mesh.Grid;
import ice.node.mesh.Mesh;
import ice.node.particle_system.TestParticleSystem;
import ice.node.widget.Button;
import ice.node.widget.TextureGrid;
import ice.res.Res;
import ice.util.ObjLoader;

import static javax.microedition.khronos.opengles.GL11.GL_ONE;
import static javax.microedition.khronos.opengles.GL11.GL_SRC_ALPHA;

/**
 * User: ice
 * Date: 12-1-6
 * Time: 下午5:07
 */
public class MainScene extends Scene {

    public MainScene() {

        int appWidth = EngineContext.getAppWidth();
        int appHeight = EngineContext.getAppHeight();

        Grid grid = alphaAnimationTest();

        TestParticleSystem testParticleSystem = particleTest();

        Drawable objMesh = objMeshTest();

        TextureGrid textureGrid = textureGridTest(appWidth, appHeight);

        Button btn = buttonTest();

        addChildren(grid, objMesh, textureGrid, testParticleSystem, btn);
    }

    private Button buttonTest() {
        final Button btn = new Button(R.drawable.image2, R.drawable.mask2);

        btn.enableBlend(GL_ONE, GL_ONE);

        btn.setPos(100, 256);

        btn.setOnTouchListener(new OnTouchListener() {
            private int lastX
                    ,
                    lastY;

            @Override
            public boolean onTouch(Drawable drawable, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                if (!btn.hitTest(x, y))
                    return false;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = x;
                        lastY = y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        btn.setPos(btn.getPosX() + x - lastX, btn.getPosY() + y - lastY);
                        lastX = x;
                        lastY = y;
                        return true;
                }
                return false;
            }
        });

        return btn;
    }

    private Grid alphaAnimationTest() {
        Grid grid = new Grid(50, 50);
        grid.setPos(0, 0, -200);
        grid.setCallFace(false);
        grid.setTexture(new Texture(R.drawable.star));
        grid.enableBlend(GL_SRC_ALPHA, GL_ONE);


        AlphaAnimation alphaAnimation = new AlphaAnimation(3000, 1, 0);
        alphaAnimation.setLoop(true);
        grid.startAnimation(alphaAnimation);
        return grid;
    }

    private TextureGrid textureGridTest(int appWidth, int appHeight) {
        TextureGrid textureGrid = new TextureGrid(R.drawable.mask2);
        textureGrid.setPos((appWidth - textureGrid.getWidth()) / 2, (appHeight + textureGrid.getHeight()) / 2);
        textureGrid.setCallFace(false);

        RotateAnimation translateRotate = new RotateAnimation(3000, 0, 360);
        translateRotate.setCenterOffset(textureGrid.getWidth() / 2, -textureGrid.getHeight() / 2, 0);
        translateRotate.setLoop(true);
        textureGrid.startAnimation(translateRotate);
        return textureGrid;
    }

    private TestParticleSystem particleTest() {

        TestParticleSystem testParticleSystem = new TestParticleSystem(
                50,
                new Texture(R.drawable.spark)
        );

        testParticleSystem.enableBlend(GL_ONE, GL_ONE);

        testParticleSystem.setPos(300, 400, -200);

        RotateAnimation rotateAnimation = new RotateAnimation(10000, 0, 360);
        rotateAnimation.setRotateVector(1, 1, 1);
        rotateAnimation.setLoop(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        testParticleSystem.startAnimation(rotateAnimation);
        return testParticleSystem;
    }

    private Drawable objMeshTest() {
        ObjLoader objLoader = new ObjLoader();
        objLoader.loadObj(Res.openAssets("teaport.obj"));

        VertexData vertexData = new VertexBufferObject(objLoader.getVertexNum(), objLoader.getAttributes());
        vertexData.setVertices(objLoader.getVertexData());

        Mesh objMesh = new Mesh(vertexData);

        objMesh.setPos(600, 400, -300);

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
}
