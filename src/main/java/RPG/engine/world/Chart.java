package RPG.engine.world;

import java.util.*;

public class Chart {
    private final Tile[][] grid;
    private final Map<Location,List<Location>> locations=new HashMap<>();

    public Chart(String map,int tilesize) throws Exception {
        grid=MapTiler.subdivise(map,tilesize,tilesize);
    }

    public Chart(String map,int tileWidth,int tileHeight) throws Exception {
        grid=MapTiler.subdivise(map,tileWidth,tileHeight);
    }

    public Tile[][] getGrid() {return grid;}
    public Set<Location> getLocations() {return locations.keySet();}
    public List<Tile> getPaths() {
        List<Tile> paths=new ArrayList<>();
        for (Tile[] row : grid) {
            for (Tile tile : row) {
                if (tile.isWalkable()) paths.add(tile);
            }
        }
        return paths;
    }
    public void addConnection(Location from,Location to) {
        locations.computeIfAbsent(from,k -> new ArrayList<>()).add(to);
    }
    public void addLocation(Location location) {this.locations.put(location,null);}
    public Location getLocation(String name) {
        for (Location loc : locations.keySet()) {
            if (loc.getName().equals(name)) {
                return loc;
            }
        }
        return null;
    }
    public List<Location> getConnectedLocations(Location from) {
        return locations.getOrDefault(from,new ArrayList<>());
    }
    public List<Tile> getNeighbors(Tile tile) {
        List<Tile> neighbors=new ArrayList<>();
        int row=tile.getX();
        int col=tile.getY();
        if (row>0) neighbors.add(grid[row-1][col]);
        if (row<grid.length-1) neighbors.add(grid[row+1][col]);
        if (col>0) neighbors.add(grid[row][col-1]);
        if (col<grid[0].length-1) neighbors.add(grid[row][col+1]);
        return neighbors;
    }
    public List<Tile> getPath(Tile from,Tile to) {
        List<Tile> path=new LinkedList<>();
        Queue<Tile> Q=new LinkedList<>();
        boolean[][] visited=new boolean[grid.length][grid[0].length];
        Map<Tile,Tile> parent=new HashMap<>();
        Q.add(from);
        visited[from.getX()][from.getY()]=true;
        parent.put(from,null);
        while (!Q.isEmpty()) {
            Tile currT=Q.poll();
            if (currT.equals(to)) break;
            List<Tile> neighbors=this.getNeighbors(currT);
            for (Tile tile : neighbors) {
                int x=tile.getX();
                int y=tile.getY();
                if (!visited[x][y] && tile.isWalkable()) {
                    visited[x][y]=true;
                    parent.put(tile,currT);
                    Q.add(tile);
                }
            }
        }
        if (!parent.containsKey(to)) {
            return path;
        }
        Tile step=to;
        while (step!=null) {
            path.add(0,step);
            step=parent.get(step);
        }
        return path;
    }

    public int getWidth() {return grid[0].length;}

    public int getHeight() {return grid.length;}
}