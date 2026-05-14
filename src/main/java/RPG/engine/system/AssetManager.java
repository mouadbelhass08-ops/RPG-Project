package RPG.engine.system;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import RPG.engine.abilities.Spell;
import RPG.engine.characters.Enemy;
import RPG.engine.items.Armor;
import RPG.engine.items.Potion;
import RPG.engine.items.Weapon;

public class AssetManager {
    private static final Map<String,Weapon> weapons=new HashMap<>();
    private static final Map<String,Spell> spells=new HashMap<>();
    private static final Map<String,Enemy> enemies=new HashMap<>();
    private static final Map<String,Potion> potions=new HashMap<>(); 
    private static final Map<String,Armor> armors=new HashMap<>();
    private static final Map<String,Image> images=new HashMap<>();
    private static final Map<String,Media> ost=new HashMap<>();
    private static final Map<String,AudioClip> cues=new HashMap<>();

    public static void loadAssets() throws Exception {
        // Weapons
        //weapons.putAll(FileManager.load("assets/items/weapons.dat"));
        // Spells
        //spells.putAll(FileManager.load("assets/spells/spells.dat"));
        // Potions
        //potions.putAll(FileManager.load("assets/items/potions.dat"));
        // Armors
        //armors.putAll(FileManager.load("assets/items/armors.dat"));
        // Enemies
        //enemies.putAll(FileManager.load("assets/characters/enemies.dat"));
        // Images
        Path folder=Paths.get("assets/images");
        try (Stream<Path> files=Files.list(folder)) {
            files.filter(p -> {
                String name=p.getFileName().toString().toLowerCase();
                return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg");
            }).forEach(p -> {
                String name=p.getFileName().toString().replaceFirst("[.][^.]+$", "");
                Image img=new Image(p.toUri().toString());
                images.put(name,img);
            });
        }
        // Audio
        Path audio = Paths.get("assets/audio");
        if (Files.exists(audio) && Files.isDirectory(audio)) {
            try (Stream<Path> files=Files.list(audio)) {
                files.forEach(p -> {
                    String name=p.getFileName().toString().toLowerCase();
                    String key=p.getFileName().toString().replaceFirst("[.][^.]+$", "");
                    if (name.endsWith(".mp3")) {
                        Media media=new Media(p.toUri().toString());
                        ost.put(key,media);
                    }
                    if (name.endsWith(".wav")) {
                        AudioClip cue=new AudioClip(p.toUri().toString());
                        cues.put(key,cue);
                    }
                });
            }
        }
    }
    public static Map<String,Weapon> getWeapons() {return weapons;}
    public static Map<String,Enemy> getEnemies() {return enemies;}
    public static Map<String,Potion> getPotions() {return potions;}
    public static Map<String,Armor> getArmors() {return armors;}
    public static Map<String,Spell> getSpells() {return spells;}
    public static Map<String,Image> getImages() {return images;}
    public static Map<String,Media> getOST() {return ost;}
    public static Map<String,AudioClip> getCues() {return cues;}
}