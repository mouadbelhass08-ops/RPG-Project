package RPG.engine.world;

import java.util.Objects;

public class Tile {
    private int[] coordinates;
    private boolean walkable;
    private TerrainType terrainType;
    private Location location;

    public Tile(int[] coordinates) {
        this.coordinates=coordinates;
    }
    public Tile(int i, int j) {
        coordinates = new int[]{i,j};
    }
    public TerrainType getTerrainType() {return terrainType;}
    public int getX() {return coordinates[0];}
    public int getY() {return coordinates[1];}
    public boolean isWalkable() {return walkable;}
    public Location getLocation() {return location;}
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null || getClass()!=obj.getClass()) return false;
        Tile tile=(Tile) obj;
        return this.getX()==tile.getX() && this.getY()==tile.getY();
    }
    public int hashCode() {
        return Objects.hash(getX(),getY());
    }
}