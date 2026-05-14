package RPG.engine.system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import RPG.engine.characters.Character;
import RPG.engine.characters.Player;
import RPG.engine.combat.CombatSystem;
import RPG.engine.world.Chart;
import RPG.engine.world.Shop;

/**
 * Session state and micro-phase routing (exploration, combat, shop, …).
 * UI layers listen via {@link #setOnPhaseChanged(Consumer)}; this class must not depend on JavaFX.
 */
public class Game {
    private Chart map;
    private GameState macroState = GameState.MAINMENU;
    private GamePhase phase = GamePhase.NARRATIVE;
    private final List<Player> players;
    private final Shop shop;
    private Consumer<GamePhase> onPhaseChanged;
    private CombatSystem activeCombat;

    public Game(Player hero) {
        this.players = new ArrayList<>(List.of(hero));
        this.shop = new Shop(hero);
    }

    /** Optional world chart for map-based features; may be null. */
    public Game(Chart map, List<Player> players) {
        this.map = map;
        this.players = new ArrayList<>(players);
        this.shop = new Shop(players.get(0));
    }

    public void setOnPhaseChanged(Consumer<GamePhase> listener) {
        this.onPhaseChanged = listener;
    }

    public GameState getMacroState() {
        return macroState;
    }

    public void setMacroState(GameState macroState) {
        this.macroState = macroState;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
        if (onPhaseChanged != null) {
            onPhaseChanged.accept(phase);
        }
    }

    public Player getPrimaryPlayer() {
        return players.get(0);
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public Shop getShop() {
        return shop;
    }

    public Chart getMap() {
        return map;
    }

    public void setMap(Chart map) {
        this.map = map;
    }

    public CombatSystem getActiveCombat() {
        return activeCombat;
    }

    public void startBattle(List<Character> participants) {
        setPhase(GamePhase.COMBAT);
        this.activeCombat = new CombatSystem(participants);
        activeCombat.startBattle();
    }

    public void update() {}

    public void save() {}
}
