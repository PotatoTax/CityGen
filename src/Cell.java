import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    Cell[] neighbors;
    boolean visited;
    boolean[] walls;
    PVector pos;

    public Cell(int x, int y) {
        visited = false;
        pos = new PVector(x, y);
        walls = new boolean[]{true, true, true, true};
    }

    public void addNeighbors(Cell[] neighbors) {
        this.neighbors = neighbors;
    }

    public List<Cell> getUnvisited() {
        List<Cell> unvisited = new ArrayList<>();
        for (Cell cell : neighbors) {
            if (cell != null) {
                if (!cell.visited) {
                    unvisited.add(cell);
                }
            }
        }
        return unvisited;
    }

    public void removeWall(Cell cell) {
        for (int i = 0; i < 4; i++) {
            if (neighbors[i] == cell) {
                walls[i] = false;
                break;
            }
        }
    }
}