package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameplayScreen extends ScreenAdapter {

    private Level level;
    private SpriteBatch batch;
    private Viewport viewport;

    GameplayScreen() {
        Gdx.graphics.setContinuousRendering(true);
        batch = new SpriteBatch();
        initCamera();
        level = new Level(viewport);
    }

    private void initCamera() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.zoom = 1;
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
        Gdx.input.setInputProcessor(new InputControl(camera));
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        level.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0.201f, 0.253f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        level.render();
        batch.end();
    }

    private static class InputControl extends InputAdapter {
        private OrthographicCamera camera;

        InputControl(OrthographicCamera camera) {
            this.camera = camera;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            float factorMovimientoGesture = 5.5f;
            float x = (Gdx.input.getDeltaX() / factorMovimientoGesture) * 2;
            float y = (Gdx.input.getDeltaY() / factorMovimientoGesture) * 2;

            camera.translate(-x, y);
            camera.update();
            return true;
        }
    }
}