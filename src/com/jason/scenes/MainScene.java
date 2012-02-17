package com.jason.scenes;

import android.graphics.Color;
import com.jason.R;
import ice.animation.*;
import ice.animation.Interpolator.LinearInterpolator;
import ice.engine.EngineContext;
import ice.engine.Scene;
import ice.graphic.texture.FlowLighting;
import ice.graphic.texture.Texture;
import ice.model.vertex.VertexBufferObject;
import ice.model.vertex.VertexData;
import ice.node.Overlay;
import ice.node.OverlayParent;
import ice.node.mesh.Grid;
import ice.node.mesh.Mesh;
import ice.node.widget.BitmapOverlay;
import ice.node.widget.ButtonOverlay;
import ice.node.widget.TextOverlay;
import ice.practical.ComesMoreText;
import ice.practical.ComesMoreTextBox;
import ice.practical.GoAfterTouchListener;
import ice.practical.TestParticleSystem;
import ice.res.Res;
import ice.util.ObjLoader;

import static javax.microedition.khronos.opengles.GL10.GL_ZERO;
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

        TestParticleSystem testParticleSystem = particleTest(appWidth, appHeight);

        Overlay objMesh = objMeshTest(appWidth, appHeight);

        BitmapOverlay bitmapOverlay = textureGridTest(appWidth, appHeight);

        buttonTest();

        TextOverlay textOverlay = new ComesMoreText(200, 30, 1000);
        textOverlay.setText("Hello Demo !", Color.RED, 30);
        textOverlay.setPos(300, appHeight - textOverlay.getHeight());
        FlowLighting modifier = new FlowLighting(3000);
        textOverlay.getTexture().setModifier(modifier);

        ComesMoreTextBox comesMoreTextBox = new ComesMoreTextBox(500, 30, 1000);
        comesMoreTextBox.setTexts(new String[]{"ajkfjdsakfjaskfjka", "你好，呵呵"});
        comesMoreTextBox.setPos(appWidth / 2, appHeight - 60);
        comesMoreTextBox.enableBlend(GL_ONE, GL_ZERO);

        addChildren(grid, objMesh, bitmapOverlay, testParticleSystem, textOverlay, comesMoreTextBox);
    }

    private void buttonTest() {
        final ButtonOverlay btn = new ButtonOverlay(R.drawable.image2, R.drawable.mask2);

        btn.enableBlend(GL_ONE, GL_ONE);

        //btn.setPos(0, btn.getHeight() + 50);

        RotateAnimation rotate = new RotateAnimation(2000, 0, 360);
        rotate.setRotateVector(1, 0, 0);
        rotate.setLoop(true);
        btn.startAnimation(rotate);
        btn.setCallFace(false);


        OverlayParent overlayParent = new OverlayParent();

        overlayParent.addChild(btn);

        overlayParent.setPos(btn.getWidth() / 2, btn.getHeight());

        addChild(overlayParent);

    }

    private Grid alphaAnimationTest() {
        Grid grid = new Grid(50, 50);
        grid.setPos(25, 50, -50);
        grid.setCallFace(false);
        grid.setTexture(new Texture(R.drawable.star));
        grid.enableBlend(GL_SRC_ALPHA, GL_ONE);


        ColorAnimation colorAnimation = new AlphaAnimation(3000, 1, 0);
        colorAnimation.setLoop(true);
        grid.startAnimation(colorAnimation);
        return grid;
    }

    private BitmapOverlay textureGridTest(int appWidth, int appHeight) {
        BitmapOverlay bitmapOverlay = new BitmapOverlay(R.drawable.image2);
        bitmapOverlay.setPos(bitmapOverlay.getWidth() / 2, (EngineContext.getAppHeight() - bitmapOverlay.getHeight()) / 2);
        bitmapOverlay.enableBlend(GL_ONE, GL_ONE);
        bitmapOverlay.setOnTouchListener(new GoAfterTouchListener());

        return bitmapOverlay;
    }

    private TestParticleSystem particleTest(int appWidth, int appHeight) {

        TestParticleSystem testParticleSystem = new TestParticleSystem(
                50,
                new Texture(R.drawable.spark)
        );

        testParticleSystem.enableBlend(GL_ONE, GL_ONE);

        testParticleSystem.setPos(appWidth / 2, appHeight / 2, -100);

        RotateAnimation rotateAnimation = new RotateAnimation(10000, 0, 360);
        rotateAnimation.setRotateVector(1, 1, 1);
        rotateAnimation.setLoop(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        testParticleSystem.startAnimation(rotateAnimation);
        return testParticleSystem;
    }

    private Overlay objMeshTest(int appWidth, int appHeight) {
        ObjLoader objLoader = new ObjLoader();
        objLoader.loadObj(Res.openAssets("teaport.obj"));

        VertexData vertexData = new VertexBufferObject(objLoader.getVertexNum(), objLoader.getAttributes());
        vertexData.setVertices(objLoader.getVertexData());

        Mesh objMesh = new Mesh(vertexData);
        objMesh.setCallFace(false);

        objMesh.setPos(0.85f * appWidth, appHeight / 2, 0);

        objMesh.setCallFace(false);

        Texture texture = new Texture(R.drawable.mask1);
        texture.setParams(Texture.Params.LINEAR_REPEAT);
        objMesh.bindTexture(texture);

        TranslateAnimation translateAnimation = TranslateAnimation.createMoveBy(1000, -200, -50);

        ScaleAnimation scaleAnimation = ScaleAnimation.createScaleTo(3000, 2, 2);

        RotateAnimation rotateAnimation = new RotateAnimation(1000, 0, 180);
        rotateAnimation.setRotateVector(1, 1, 1);
        rotateAnimation.setFillAfter(true);

        objMesh.startAnimation(scaleAnimation);

        return objMesh;
    }
}
