package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameplayScreen extends BaseScreen {

    private SpriteBatch batch;
    private Player player;
    private Block block;
    private World world;
    private Stage stage;
    private Vector3 position;

    GameplayScreen(GdxGame game) {
        super(game);
        Gdx.graphics.setContinuousRendering(true);
        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), true);
        initCamera();
    }

    private void initCamera() {
        stage = new Stage(new FitViewport(640, 360));
        stage.setDebugAll(true);
        position = new Vector3(stage.getCamera().position);
    }

    @Override
    public void show() {
        player = new Player(world, game.getManager().get("skelleton.png"), new Vector2(1.5f, 1.5f));
        block = new Block(world, game.getManager().get("block.png"), new Vector2(6.5f, 1f));
        stage.addActor(player);
        stage.addActor(block);
        stage.getCamera().position.set(position);
        stage.getCamera().update();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        world.dispose();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0f, 0.201f, 0.253f, 1f);
        stage.act();
        world.step(delta, 6, 2);
        stage.draw();
    }

    @Override
    public void hide() {
        stage.clear();
        player.detach();
        block.detach();
    }

}