package com.jason.scenes;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import com.jason.Demo;
import com.jason.R;
import ice.engine.App;
import ice.engine.EngineContext;
import ice.engine.Scene;
import ice.graphic.Primitives;
import ice.node.Drawable;
import ice.node.mesh.Grid;
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
        Bitmap bitmap = Res.getBitmap(R.drawable.poker_back_small);

        Drawable background = new TextureTile(bitmap);

        App app = EngineContext.getInstance().getApp();

        final int width = app.getWidth();
        final int height = app.getHeight();

        Drawable primitiveTest = new Drawable() {

            @Override
            protected void onDraw(GL11 gl) {
                gl.glEnable(GL_POINT_SMOOTH);

                Primitives.drawLine(gl, new PointF(100, 100), new PointF(400, 400));
                Primitives.drawRect(gl, new RectF(400, 100, 500, 200));
                Primitives.drawCircle(gl, width >> 1, height >> 1, 100, 0, 50, true);

                gl.glPointSize(10);
                Primitives.drawPoint(gl, 100, 100);
                gl.glPointSize(1);
            }
        };

        Grid grid = new Grid(50, 50);
        grid.setPos(400, 100, 0);
        grid.setCallFace(false);

        addChildren(background, primitiveTest, grid);
    }

    @Override
    protected void onDraw(GL11 gl) {

        gl.glDisable(GL_DEPTH_TEST);

        super.onDraw(gl);
    }
}
