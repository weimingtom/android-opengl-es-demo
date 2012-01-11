package com.jason.graphic;

import android.content.Context;
import android.opengl.GLU;
import com.jason.Demo;
import ice.engine.EngineContext;
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
//                gl.glEnable(GL_BLEND);
//                gl.glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_ALPHA);

                setupLight(gl);
                //setupMaterial(gl);
            }

        };

    }


    private static void setupLight(GL11 gl) {
        float[] lightAmbient = {0.5f, 0.5f, 0.5f, 1.0f}; // ['æmbiənt] 环境光
        float[] lightDiffuse = {1.0f, 1.0f, 1.0f, 1.0f};  //[di'fju:s] 漫射光
        float[] specularParams = {0.4f, 0.4f, 0.4f, 1.0f}; //镜面光设置

        int height = EngineContext.getInstance().getApp().getHeight();
        float[] lightPosition = {0, height >> 1, -100f, 1.0f};


        gl.glLightfv(GL_LIGHT1, GL_AMBIENT, lightAmbient, 0);
        gl.glLightfv(GL_LIGHT1, GL_DIFFUSE, lightDiffuse, 0);
        gl.glLightfv(GL_LIGHT1, GL_SPECULAR, specularParams, 0);
        gl.glLightfv(GL_LIGHT1, GL_POSITION, lightPosition, 0);
        gl.glEnable(GL_LIGHT1);
        gl.glEnable(GL_LIGHTING);

        gl.glEnable(GL_COLOR_MATERIAL);
        //gl.glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
    }


    private static void setupMaterial(GL11 gl) {
        float ambientMaterial[] = {0.6f, 0.6f, 0.6f, 1.0f};  //环境光为白色材质
        float diffuseMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};   //散射光为白色材质
        float specularMaterial[] = {1.0f, 1.0f, 1.0f, 1.0f};  //高光材质为白色
        float[] emission = {0.2f, 0.2f, 0.2f, 1.0f};  //自发光

        gl.glMaterialfv(GL_FRONT, GL_AMBIENT, ambientMaterial, 0);
        gl.glMaterialfv(GL_FRONT, GL_DIFFUSE, diffuseMaterial, 0);
        gl.glMaterialfv(GL_FRONT, GL_SPECULAR, specularMaterial, 0);
        //gl.glMaterialfv(GL_FRONT, GL_EMISSION, emission, 0);
    }

}
