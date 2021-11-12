package world;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class MapHandler {
    private TiledMap map;
    private MapLayers mapLayers;

    public MapHandler() {
        map = new TiledMap();
        mapLayers = map.getLayers();
    }

    public void addLayer(TiledMapTileLayer layer) {
        this.mapLayers.add(layer);
    }

    public MapLayer getLayer(int index) {
        return this.mapLayers.get(index);
    }

    public TiledMap getMap() {
        return this.map;
    }
}
