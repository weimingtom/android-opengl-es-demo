package com.jason;

import android.os.Bundle;
import android.view.Display;
import com.jason.graphic.DemoView;
import com.jason.sceneProviders.Main;
import ice.engine.Game;
import ice.engine.GameView;
import ice.engine.SceneProvider;

public class Demo extends Game {
    public static final float Z_NEAR = 200, Z_FAR = 650;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        super.onCreate(savedInstanceState);
    }

    @Override
    protected Class<? extends SceneProvider> getEntry() {
        return Main.class;
    }

    @Override
    protected GameView createRenderer() {
        return new DemoView(this);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private int width, height;
}
