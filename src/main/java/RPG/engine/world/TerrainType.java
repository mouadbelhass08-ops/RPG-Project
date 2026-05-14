package RPG.engine.world;

public enum TerrainType {
    GRASS(true,1.0),
    WATER(false,0.0),
    MOUNTAIN(false,0.0),
    ROAD(true,0.5);

    private final boolean walkable;
    private final double movementCost;

    TerrainType(boolean walkable,double movementCost) {
        this.walkable=walkable;
        this.movementCost=movementCost;
    }

    public boolean isWalkable() {return walkable;}
    public double getMovementCost() {return movementCost;}
}