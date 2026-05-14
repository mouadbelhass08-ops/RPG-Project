package RPG.engine.world;

import java.util.Set;

import RPG.engine.characters.Player;

public class Location implements Interactable {
    private String name;
    private Set<Tile> tiles;
    private InteractibleType type;
    private Tile entrance;
    private boolean locked;

    public Location(String name,Tile entrance) {
        this.name=name;
        this.entrance=entrance;
    }
    
    public String getName() {return name;}

    public Set<Tile> getTiles() {return tiles;}
    public void setEntrance(Tile entrance) {this.entrance=entrance;}
    public Tile getPosition() {return entrance;}
    public String getPrompt() {return "Enter "+getName()+" ?";}
    public void interact(Player player) {}
    public boolean isAvailable() {return locked;}
    public InteractibleType getType() {return type;}
}