package RPG.engine.narrative;

import RPG.engine.system.MenuOption;
import RPG.engine.world.MenuNavigator;

/**
 * Sample branching script: same mechanics you would later load from serialized data or a DSL.
 * <p>
 * Pattern: each "beat" is a {@link MenuOption} whose {@link MenuOption#getText()} is shown as the
 * dialogue header (speaker + body, separated by blank lines). Its <em>children</em> are player
 * choices. Choosing a leaf runs its {@link Runnable}, which typically calls {@link MenuNavigator#resetTo}
 * for the next beat and then {@code refresh.run()} so the GUI redraws.
 * <p>
 * To add story later: extract beats into data files, deserialize to {@code MenuOption} trees, or
 * generate trees from tables (id, text, nextId, conditions).
 */
public final class PhilosophyIntro {

    private PhilosophyIntro() {}

    public static void start(MenuNavigator nav, Runnable refresh) {
        beatOpening(nav, refresh);
    }

    private static String block(String speaker, String body) {
        return speaker + "\n\n" + body;
    }

    private static MenuOption choice(String label, Runnable onSelect) {
        return new MenuOption(label, onSelect);
    }

    private static void beatOpening(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "You arrive before a world that does not yet know your name. There is no map here—only this voice, "
                        + "and the quiet suspicion that every game is a mirror.\n\n"
                        + "What draws you forward when the path is not drawn?"));
        root.addChild(choice("I seek something I think I lost", () -> beatLoss(nav, refresh)));
        root.addChild(choice("I want to see how worlds are stitched together", () -> beatMaking(nav, refresh)));
        root.addChild(choice("I want room to breathe outside my own story", () -> beatOtherStory(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatLoss(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Loss in fiction is strange: it can be rehearsed without being recovered. "
                        + "Perhaps you chase a feeling that once sat between two save files, "
                        + "or a person who was never more than ink and intent.\n\n"
                        + "If you found it again, would it still belong to you?"));
        root.addChild(choice("Yes—memory would make it mine again", () -> beatBelonging(nav, refresh)));
        root.addChild(choice("Maybe not—and that is why I keep walking", () -> beatWandering(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatMaking(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "To study the stitching is to admit you are both audience and apprentice. "
                        + "Rules, scenes, assets—they are small mercies: they let us pretend chaos has corners.\n\n"
                        + "Do you build so you can control, or so you can be surprised?"));
        root.addChild(choice("Control steadies me", () -> beatControl(nav, refresh)));
        root.addChild(choice("Surprise is the only honest reward", () -> beatSurprise(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatOtherStory(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Another story is a borrowed room: you rearrange the furniture, but the walls remember other tenants. "
                        + "Still, borrowed rooms can teach you how light falls on your own windows.\n\n"
                        + "Is escape a kindness, or a postponement?"));
        root.addChild(choice("Kindness—fiction widens me", () -> beatKindness(nav, refresh)));
        root.addChild(choice("Postponement—but a gentle one", () -> beatPostponement(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatBelonging(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Then you treat the world as an archive of feelings you are entitled to reopen. "
                        + "Be careful: archives demand curators. What will you refuse to catalogue?"));
        root.addChild(choice("Whatever would make me smaller if I kept it", () -> beatConvergence(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatWandering(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Walking without arrival is not failure; it is a vow against false endings. "
                        + "The horizon you never touch still bends the compass."));
        root.addChild(choice("Then let the compass spin", () -> beatConvergence(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatControl(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Control is a small lamp. It keeps the table visible, but it shrinks the stars you will notice. "
                        + "Leave one drawer unlocked in your design—let the night air in."));
        root.addChild(choice("I will leave a drawer open", () -> beatConvergence(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatSurprise(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Surprise is faith in the unseen rule—the one you did not write. "
                        + "It is how players remind authors that authorship is a conversation, not a lock."));
        root.addChild(choice("I am listening for the answer back", () -> beatConvergence(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatKindness(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Kindness through fiction is real enough to change breath and posture. "
                        + "Do not apologize for needing a gentler physics than the one outside."));
        root.addChild(choice("I will not apologize", () -> beatConvergence(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatPostponement(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Postponement is not cowardice if it buys you time to become someone who can return without collapsing. "
                        + "The pause between notes is still music."));
        root.addChild(choice("Then I will honor the pause", () -> beatConvergence(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatConvergence(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Here is the hinge: the world you explore is also exploring you—measuring what you notice, "
                        + "what you skip, what you return to when no quest marker tells you to.\n\n"
                        + "When the narrative ends, what do you hope remains?"));
        root.addChild(choice("A habit of asking better questions", () -> beatClosing(nav, refresh)));
        root.addChild(choice("A softness I can carry into ordinary days", () -> beatClosing(nav, refresh)));
        nav.resetTo(root);
        refresh.run();
    }

    private static void beatClosing(MenuNavigator nav, Runnable refresh) {
        MenuOption root = new MenuOption(block("The Watcher",
                "Good. Maps are conveniences; attention is the true terrain. "
                        + "What you cultivate here—curiosity, restraint, courage—will leak through the screen "
                        + "in ways no log file captures.\n\n"
                        + "When you are ready, the world will remember how you listened."));
        root.addChild(choice("Speak again from the threshold", () -> beatOpening(nav, refresh)));
        root.addChild(choice("Stay in this silence a while", () -> {
            MenuOption still = new MenuOption(block("Silence",
                    "Nothing is demanded of you now. The next line will arrive when you move—not before."));
            still.addChild(choice("Return to the threshold", () -> beatOpening(nav, refresh)));
            nav.resetTo(still);
            refresh.run();
        }));
        nav.resetTo(root);
        refresh.run();
    }
}
