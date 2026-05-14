package RPG.engine.system;

import java.util.List;

import RPG.engine.characters.Player;
import RPG.engine.combat.CombatSystem;
import RPG.engine.world.Chart;
import RPG.gui.GameScene;
import RPG.gui.SceneManager;
import RPG.engine.characters.Character;

public class Game {
    private final Chart map;
    private GameState state;
    private GamePhase phase;
    private final List<Player> players;
    private final GameScene gamescene;

    public Game(Chart map,List<Player> players) {
        this.players=players;
        this.map=map;
        state=GameState.MAINMENU;
        gamescene=new GameScene();
        phase=null;
    }
    public void start() throws Exception {
        AssetManager.loadAssets();
        SceneManager.switchTo(state);
    }
    public void startBattle(List<Character> participants) {
        phase=GamePhase.COMBAT;
        gamescene.switchPhase(phase);
        CombatSystem combatsystem=new CombatSystem(participants);
        combatsystem.startBattle();
    }
    public void update() {}
    public void save() {}
    public Chart getMap() {return map;}
    public List<Player> getPlayers() {return players;}
}