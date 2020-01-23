package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Level {

    private TiledMap map;
    private TiledMapRenderer renderer;
    private int[] decorationLayersIndices;
    private Viewport viewport;
    private World world;
    private Body player;
    private Box2DDebugRenderer b2dr;

    Level(Viewport viewport) {
        this.viewport = viewport;
        world = new World(new Vector2(0, 0), false);
        b2dr = new Box2DDebugRenderer();
        createMap();
        createPlayer();
        createBoxes();
    }

    private void createMap() {
        AssetManager manager = new AssetManager();
        manager.setLoader(TiledMap.class, new TmxMapLoader());
        manager.load("Mapa.tmx", TiledMap.class);
        manager.finishLoading();
        map = manager.get("Mapa.tmx", TiledMap.class);

        MapLayers mapLayers = map.getLayers();
        decorationLayersIndices = new int[]{
                mapLayers.getIndex("BackGround")
        };

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    private void createPlayer() {
        this.player = createBox(world, 1000, 1200, 32, 32,
                false, false);
        this.player.setLinearDamping(20f);
    }

    private void createBoxes() {
        createBox(world, 1500, 1200, 64, 192,
                true, true);
        createBox(world, 600, 1200, 64, 192,
                true, true);
        createBox(world, 1000, 1500, 192, 64,
                true, true);
        createBox(world, 1000, 900, 192, 64,
                true, true);
    }

    private Body createBox(World world, float x, float y, int width, int height, boolean isStatic,
                           boolean fixedRotation) {
        Body pBody;
        BodyDef def = new BodyDef();

        if (isStatic) {
            def.type = BodyDef.BodyType.StaticBody;
        } else {
            def.type = BodyDef.BodyType.DynamicBody;
        }

        def.position.set(x / 32, y / 32);
        def.fixedRotation = fixedRotation;
        pBody = world.createBody(def);
        pBody.setUserData("wall");

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2f / 32, height / 2f / 32);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.filter.categoryBits = 1;
        fd.filter.maskBits = 2 | 1 | 4;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd);
        shape.dispose();
        return pBody;
    }

    void render() {
        world.step(1 / 60f, 6, 2);
        viewport.apply();
        renderer.setView((OrthographicCamera) viewport.getCamera());
        renderer.render(decorationLayersIndices);
        b2dr.render(world, viewport.getCamera().combined.cpy().scl(32));
    }

    public void dispose() {
        map.dispose();
    }
}
