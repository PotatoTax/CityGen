import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Maze {
    Cell[][] cells;

    public Maze(int mazeSize) {
        cells = new Cell[mazeSize][mazeSize];
        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                cells[y][x] = new Cell(x, y);
            }
        }

        for (int y = 0; y < mazeSize; y++) {
            for (int x = 0; x < mazeSize; x++) {
                Cell[] neighbors = new Cell[] {null, null, null, null};
                if (y > 0) {
                    neighbors[0] = cells[y-1][x];
                }
                if (x < mazeSize - 1) {
                    neighbors[1] = cells[y][x+1];
                }
                if (y < mazeSize - 1) {
                    neighbors[2] = cells[y+1][x];
                }
                if (x > 0) {
                    neighbors[3] = cells[y][x-1];
                }
                cells[y][x].addNeighbors(neighbors);
            }
        }

        List<Cell> stack = new ArrayList<>();
        Cell currentCell = cells[0][0];
        currentCell.visited = true;
        while (hasUnvisited()) {
            List<Cell> unvisited = currentCell.getUnvisited();
            if (unvisited.size() > 0) {
                int randChoice = new Random().nextInt(unvisited.size());
                if (unvisited.size() > 1) {
                    stack.add(currentCell);
                }
                currentCell.removeWall(unvisited.get(randChoice));
                unvisited.get(randChoice).removeWall(currentCell);
                currentCell = unvisited.get(randChoice);
                currentCell.visited = true;
            }
            else if (stack.size() != 0) {
                Cell fromStack = stack.get(stack.size() - 1);
                stack.remove(stack.size() - 1);
                while (fromStack.getUnvisited().size() == 0) {
                    fromStack = stack.get(stack.size() - 1);
                    stack.remove(stack.size() - 1);
                }
                currentCell = fromStack;
            }
            if (currentCell.pos.x == 0 && currentCell.pos.y == 0) {
                break;
            }
        }
    }

    public boolean hasUnvisited() {
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (!cell.visited) {
                    return true;
                }
            }
        }
        return false;
    }
}