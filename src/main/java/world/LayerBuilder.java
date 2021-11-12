package world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;

public class LayerBuilder {
    private static TiledMapTileLayer layer;
    private static int[][] sourceMatrix;
    private static final int TILE_WIDTH = 128;
    private static final int TILE_HEIGHT = 128;

    private static Cell sand;
    private static Cell grass;
    private static Cell grass1;
    private static Cell rock;
    private static Cell water;

    public static TiledMapTileLayer generateLayer(int[][] sourceMatrix, int width, int height, int tile_width, int tile_height) {
        LayerBuilder.sourceMatrix = sourceMatrix;
        //ToDo actually generate layer
        layer = new TiledMapTileLayer(width, height, tile_width, tile_height);
        sand = buildCell(buildTile(new Texture(Gdx.files.internal("sand.png")), tile_width, tile_height));
        grass = buildCell(buildTile(new Texture(Gdx.files.internal("grass.png")), tile_width, tile_height));
        grass1 = buildCell(buildTile(new Texture(Gdx.files.internal("grass1.png")), tile_width, tile_height));
        rock = buildCell(buildTile(new Texture(Gdx.files.internal("rock.png")), tile_width, tile_height));
        water = buildCell(buildAnimatedTile(new Texture(Gdx.files.internal("water_sheet.png")), tile_width, tile_height, 5));


        buildLayer(width, height);

        return layer;
    }

    private static void buildLayer(int width, int height) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                layer.setCell(i, j, getCellForDepth(sourceMatrix[i][j]));
            }
        }
    }

    private static Cell getCellForDepth(int depthValue) {
        if (depthValue == -1) {
            return water;
        } else if (depthValue > 120 && depthValue < 140) {
            return sand;
        } else if (depthValue > 120 && depthValue < 200) {
            return grass;
        } else if (depthValue > 120 && depthValue < 250) {
            return grass1;
        } else if (depthValue > 120){
            return rock;
        }
        return null;
    }

    private static Cell buildAnimatedCell(String filename, int tile_width, int tile_height) {
        Cell cell = new TiledMapTileLayer.Cell();
        Texture texture = new Texture(Gdx.files.internal(filename));
        cell.setTile(buildAnimatedTile(texture, tile_width, tile_height, 3));
        return cell;

    }

    private static Cell buildCell(TiledMapTile tile) {
        return new Cell().setTile(tile);
    }

//    private static Cell buildCell(String filename, int tile_width, int tile_height) {
//
//        Cell cell = new TiledMapTileLayer.Cell();
//
//        Texture texture = new Texture(Gdx.files.internal(filename));
//        Sprite sprite = new Sprite(texture, 0, 0, tile_width, tile_height);
//
//        //ToDo check for animate property
//
////        cell.setTile(buildAnimatedTile(texture, TILE_WIDTH,TILE_HEIGHT, 3));
//
//        cell.setTile(new StaticTiledMapTile(sprite));
//
//        return cell;
//
//    }

    private static AnimatedTiledMapTile buildAnimatedTile(Texture img, int t_width, int t_height, int framerate) {
        int frames_count = (img.getHeight()*img.getWidth())/(t_width*t_height);

        Array<StaticTiledMapTile> frames = new Array<>(frames_count);

        TextureRegion[][] tmpFrames = TextureRegion.split(img, t_width, t_height);

        for (int i = 0; i < tmpFrames.length; i++) {
            for (int j = 0; j < tmpFrames[0].length; j++) { // ToDo make sure never indexOutOfBounds
                frames.add(new StaticTiledMapTile(new Sprite(tmpFrames[i][j])));
            }
        }

        return new AnimatedTiledMapTile(1f/framerate, frames);
    }

    private static TiledMapTile buildTile(Texture img, int t_width, int t_height) {
        Sprite sprite = new Sprite(img, 0, 0, t_width, t_height);
        return new StaticTiledMapTile(sprite);

    }

}
