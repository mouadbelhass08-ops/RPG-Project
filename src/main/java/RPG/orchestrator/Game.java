package RPG.orchestrator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import RPG.engine.characters.Character;
import RPG.engine.characters.Player;
import RPG.engine.combat.CombatSystem;
import RPG.engine.narrative.ChapterDirector;
import RPG.engine.narrative.StoryProgress;
import RPG.engine.world.Shop;

/**
 * Session state and micro-phase routing (narrative, combat, shop, …).
 * UI layers listen via {@link #setOnPhaseChanged(Consumer)}; this class must not depend on JavaFX.
 */
public class Game {
    private GameState macroState = GameState.MAINMENU;
    private GamePhase phase = GamePhase.NARRATIVE;
    private final List<Player> players;
    private final Shop shop;
    private final StoryProgress storyProgress = new StoryProgress();
    private Consumer<GamePhase> onPhaseChanged;
    private CombatSystem activeCombat;
    private Runnable narrativeContinuation;

    public Game(Player hero) {
        this.players = new ArrayList<>(List.of(hero));
        this.shop = new Shop(hero);
    }

    /** Optional world chart for map-based features; may be null. */
    public Game(List<Player> players) {
        this.players = new ArrayList<>(players);
        this.shop = new Shop(players.get(0));
    }

    public StoryProgress getStoryProgress() {
        return storyProgress;
    }

    /** Called when returning to narrative so the active chapter can refresh beats (e.g. post-battle). */
    public void setNarrativeContinuation(Runnable narrativeContinuation) {
        this.narrativeContinuation = narrativeContinuation;
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

    public CombatSystem getActiveCombat() {
        return activeCombat;
    }

    /**
     * Starts a story-driven fight: switches to {@link GamePhase#COMBAT}, runs initiative, then invokes
     * {@link #setNarrativeContinuation} after the battle ends so chapter scripts can resume.
     */
    public void startChapterCombat(List<Character> participants) {
        this.activeCombat = new CombatSystem(participants);
        this.activeCombat.setOnBattleEnd(outcome -> {
            ChapterDirector.onBattleEnded(this, outcome);
            this.activeCombat = null;
            setPhase(GamePhase.NARRATIVE);
            if (narrativeContinuation != null) {
                narrativeContinuation.run();
            }
        });
        setPhase(GamePhase.COMBAT);
        this.activeCombat.startBattle();
    }

    public void update() {}

    public void save() {}
}
