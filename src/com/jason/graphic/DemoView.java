package com.jason.graphic;

import android.content.Context;
import android.opengl.GLU;
import com.jason.Demo;
import ice.engine.GameView;
import ice.engine.GlRenderer;
import ice.graphic.projection.PerspectiveProjection;

import javax.microedition.khronos.opengles.GL11;

import static javax.microedition.khronos.opengles.GL11.*;

/**
 * User: ice
 * Date: 12-1-6
 * Time: 下午4:59
 */
public class DemoView extends GameView {

    public DemoView(Context context) {
        super(context, createRender());
    }

    private static GlRenderer createRender() {

        PerspectiveProjection projection = new PerspectiveProjection(
                new GLU(),
                60,
                Demo.Z_NEAR,
                Demo.Z_FAR
        );

        return new GlRenderer(projection) {
            @Override
            protected void onInit(GL11 gl) {
                gl.glDisable(GL_DEPTH_TEST);

                gl.glEnable(GL_BLEND);
                gl.glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_ALPHA);
            }
        };
    }


}
