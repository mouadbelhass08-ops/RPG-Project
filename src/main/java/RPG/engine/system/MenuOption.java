package RPG.engine.system;

import java.util.ArrayList;
import java.util.List;

public class MenuOption {
    private final String text;
    private Runnable action;
    private final List<MenuOption> children=new ArrayList<>();
    private MenuOption parent;

    public MenuOption(String text) {this.text=text;}

    public MenuOption(String text,Runnable action) {
        this.text=text;
        this.action=action;
    }

    public String getText() { return text; }
    public Runnable getAction() { return action; }
    public void setAction(Runnable action) { this.action = action; }

    public MenuOption getParent() { return parent; }
    public List<MenuOption> getChildren() { return children; }
    public boolean hasChildren() { return !children.isEmpty(); }

    public void addChild(MenuOption child) {
        if (child == null) return;
        child.parent = this;
        children.add(child);
    }

    public void execute() {
        if (action != null) action.run();
    }
}