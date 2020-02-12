public class MazeToGraph {

    public static void GenerateGraph(Graph graph, int mazeSize) {
        Maze maze = new Maze(mazeSize);

        for (Cell[] cells : maze.cells) {
            for (Cell cell : cells) {
                cell.visited = false;
            }
        }

        Vertex[][] vArray = new Vertex[mazeSize][mazeSize];

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                Vertex v = new Vertex(graph.sketch, j*20, i*20);
                vArray[i][j] = v;
                graph.addVertex(v);
            }
        }

        float fill = .3f;

        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                Cell cCell = maze.cells[i][j];
                if (!cCell.walls[0]) {
                    graph.makeNeighbors(vArray[i][j], vArray[i-1][j]);
                } else if (cCell.neighbors[0] != null && graph.sketch.random(1) < fill) {
                    graph.makeNeighbors(vArray[i][j], vArray[i-1][j]);
                }
                if (!cCell.walls[1] && cCell.neighbors[1] != null) {
                    graph.makeNeighbors(vArray[i][j], vArray[i][j+1]);
                } else if (cCell.neighbors[1] != null && graph.sketch.random(1) < fill) {
                    graph.makeNeighbors(vArray[i][j], vArray[i][j+1]);
                }
                if (!cCell.walls[2] && cCell.neighbors[2] != null) {
                    graph.makeNeighbors(vArray[i][j], vArray[i+1][j]);
                } else if (cCell.neighbors[2] != null && graph.sketch.random(1) < fill) {
                    graph.makeNeighbors(vArray[i][j], vArray[i+1][j]);
                }
                if (!cCell.walls[3] && cCell.neighbors[3] != null) {
                    graph.makeNeighbors(vArray[i][j], vArray[i][j-1]);
                } else if (cCell.neighbors[3] != null && graph.sketch.random(1) < fill) {
                    graph.makeNeighbors(vArray[i][j], vArray[i][j-1]);
                }
            }
        }
    }
}
