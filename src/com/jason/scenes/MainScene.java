package com.jason.scenes;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import com.jason.Demo;
import com.jason.R;
import ice.animation.Interpolator.LinearInterpolator;
import ice.animation.RotateAnimation;
import ice.animation.TranslateAnimation;
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
import ice.node.widget.TextureTile;
import ice.res.Res;
import junit.framework.Test;

import javax.microedition.khronos.opengles.GL11;

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

        Drawable primitiveTest = primitiveTest(app);

        Grid grid = new Grid(200, 200);
        grid.setPos(app.getWidth() >> 1, app.getHeight() >> 1, 0.001f - app.getZFar());
        grid.setCallFace(false);

        Drawable objMesh = objMeshTest();

        addChildren(primitiveTest, objMesh);
    }

    private Drawable primitiveTest(App app) {

        final int width = app.getWidth();
        final int height = app.getHeight();

        Drawable drawable = new Drawable(0, 0, 0.01f - app.getZFar()) {

            @Override
            protected void onDraw(GL11 gl) {
                gl.glEnable(GL_POINT_SMOOTH);

                Primitives.drawLine(gl, new PointF(100, 100), new PointF(400, 400));
                Primitives.drawRect(gl, new RectF(400, 100, 500, 200));
                Primitives.drawCircle(gl, width >> 1, height >> 1, 100, 0, 50, true);

                gl.glPointSize(10);
                Primitives.drawPoint(gl, width >> 1, height >> 1);
                gl.glPointSize(1);
            }
        };

        return drawable;
    }

    private Drawable objMeshTest() {
        ObjLoader objLoader = new ObjLoader();
        objLoader.loadObj(Res.openAssets("test.obj"));

        VertexData vertexData = new VertexArray(objLoader.getVertexNum(), objLoader.getAttributes());
        vertexData.setVertices(objLoader.getVertexData());

        Mesh objMesh = new Mesh(vertexData);

        objMesh.setPos(400, 400, -500);

        //objMesh.bindTexture(new Texture(Res.getBitmap(R.drawable.mask1)));

        RotateAnimation rotateAnimation = new RotateAnimation(10000, 0, 360);
        rotateAnimation.setRotateVector(1, 1, 1);
        rotateAnimation.enableLoop(true);
        rotateAnimation.setInterpolator(new LinearInterpolator());

        // objMesh.startAnimation(rotateAnimation);
        objMesh.setCallFace(false);

        return objMesh;
    }
}
