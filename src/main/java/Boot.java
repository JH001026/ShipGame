import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import world.LayerBuilder;
import world.MapHandler;
import world.NoiseGen;

public class Boot extends Game {
    MapHandler mapHandler;
    OrthogonalTiledMapRenderer otmr;
    OrthographicCamera cam = new OrthographicCamera();

    // new
    SpriteBatch batch;
    Sprite sprite;


    @Override
    public void create() {
        createMapLayers();

        otmr = new OrthogonalTiledMapRenderer(mapHandler.getMap());
        cam.setToOrtho(false, 960,640);
        batch  = new SpriteBatch(1000);
        // Create sprites outside of render method
        sprite = new Sprite(new Texture(Gdx.files.internal("water.png")));

    }

    @Override
    public void render() {
        otmr.setView(cam);
        otmr.render();

//        batch.begin();
//        for (int i = 0; i < 15; i++) {
//            for (int j = 0; j < 9; j++) {
//                batch.draw(sprite, 128*i, 128*j, 128, 128);
//            }
//        }
//        batch.end();

    }

    public void createMapLayers() {
        mapHandler = new MapHandler();

        int[][] waterMatrix = NoiseGen.generateFlatMap(100000,1000);
        int[][] landMatrix = new NoiseGen(1000,1000).getMatrix();

        mapHandler.addLayer(LayerBuilder.generateLayer(waterMatrix, 1000, 600, 2,2));
        mapHandler.addLayer(LayerBuilder.generateLayer(landMatrix,1000,600,2,2));

    }

}
