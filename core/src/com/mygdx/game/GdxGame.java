package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public class GdxGame extends Game {

    private AssetManager manager;
    public BaseScreen loadingScreen, gameScreen;

    public GdxGame() {
    }

    @Override
    public void create() {
        manager = new AssetManager();
        manager.load("skelleton.png", Texture.class);
        manager.load("block.png", Texture.class);
        loadingScreen = new LoadingScreen(this);
        setScreen(loadingScreen);
    }

    public void finishLoading() {
        gameScreen = new GameplayScreen(this);
        setScreen(gameScreen);
    }

    public AssetManager getManager() {
        return manager;
    }

}


