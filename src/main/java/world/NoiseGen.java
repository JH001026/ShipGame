package world;

import world.MapCoordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class NoiseGen {
    private final int[][] MATRIX;

    private int WIDTH;
    private int HEIGHT;
    private final int MAX_VALUE = 255;
    private final int CHUNK_SIZE = 64;

    public NoiseGen(int w, int h) {
        this.WIDTH = w;
        this.HEIGHT = h;
        MATRIX = new int[w][h];
        this.WIDTH = w/2;
        MapCoordinate[] l1 = generatePeaks(w/2,h,0,0, 20); //20
        this.WIDTH = w;
        MapCoordinate[] l2 = generatePeaks(w/2,h,w/2,0,15); //15

        generateMap(l1, l2);
    }

    //ToDo remove
    public static int[][] generateFlatMap(int w, int h) {
        int[][] res = new int[w][h];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                res[i][j] = -1;
            }
        }
        return res;

    }

    private void generateMap(MapCoordinate[]... peaks) {
        iterate(Arrays.stream(peaks).flatMap(Arrays::stream).collect(Collectors.toList()));
    }

    private MapCoordinate[] generatePeaks(int w, int h, int w_off, int h_off, int numberOfPeaks) {
        List<MapCoordinate> res = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < numberOfPeaks; i++) {
            int x = rand.nextInt(w) + w_off;
            int y = rand.nextInt(h) + h_off;
            MATRIX[x][y] = MAX_VALUE;
            res.add(new MapCoordinate(x, y));
        }
        return res.toArray(new MapCoordinate[0]);
    }


    private void iterate(List<MapCoordinate> mapCoordinates) {
        List<MapCoordinate> next = new ArrayList<>();
        for (MapCoordinate mc : mapCoordinates) {
            next.addAll(getEmptyNeighbours(mc, 30, 49));
        }
        if (!next.isEmpty()) {
            iterate(next);
        }
    }

    public List<MapCoordinate> getEmptyNeighbours(MapCoordinate coord, int steep, int rise) {
        List<MapCoordinate> res = new ArrayList<>();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
//                if (i != j && i != -j && new Random().nextBoolean() || new Random().nextBoolean()) {
                if ((new Random().nextBoolean() && new Random().nextBoolean()) || MATRIX[coord.getX()][coord.getY()] == MAX_VALUE) {
                    if (changeEmptyAndExistingNeighbour(coord.getX(), coord.getY(), i, j, steep, rise)) {
                        res.add(new MapCoordinate(coord.getX() + i, coord.getY() + j));
                    }
                }
            }
        }
        return res;
    }


    private boolean changeEmptyAndExistingNeighbour(int x, int y, int x_off, int y_off, int steep, int rise) {
        int new_x = x + x_off;
        int new_y = y + y_off;
        if (new_x < 0 || new_x >= WIDTH|| new_y < 0 || new_y >= HEIGHT) {
            // Out of bounds
        } else {

            if (MATRIX[new_x][new_y] == 0) {
                // Change value
                int value = Math.min((MATRIX[x][y] - steep) + new Random().nextInt(rise), MAX_VALUE);
                if (value <= 0) value = 1;
                MATRIX[new_x][new_y] = value;
                return true;
            }
        }
        return false;
    }

    public int[][] getMatrix() {
        return this.MATRIX;
    }

    public int[][] getChunk(int x, int y) {
        return null;
    }


}
