package RPG.engine.world;

import java.util.List;

import RPG.engine.system.MenuOption;

public class MenuNavigator {
    private MenuOption pointer;
    private int selectedIndex=0;

    public MenuNavigator(MenuOption root) {
        this.pointer = root != null ? root : new MenuOption("(Empty)");
    }

    /** Jump to a new subtree (e.g. next narrative beat). Resets selection to the first choice. */
    public void resetTo(MenuOption root) {
        this.pointer = root != null ? root : new MenuOption("(Empty)");
        this.selectedIndex = 0;
    }

    public MenuOption getPointer() {return pointer;}
    public List<MenuOption> getCurrentOptions() {
        return pointer != null ? pointer.getChildren() : List.of();
    }

    public int getSelectedIndex() {return selectedIndex;}

    public MenuOption getSelectedOption() {
        List<MenuOption> opts=getCurrentOptions();
        if (opts.isEmpty()) return null;
        return opts.get(selectedIndex);
    }

    public void moveUp() {
        List<MenuOption> opts=getCurrentOptions();
        if (!opts.isEmpty()) selectedIndex=(selectedIndex-1+opts.size()) % opts.size();
    }

    public void moveDown() {
        List<MenuOption> opts=getCurrentOptions();
        if (!opts.isEmpty()) selectedIndex=(selectedIndex+1) % opts.size();
    }

    public void goBack() {
        if (pointer != null && pointer.getParent() != null) {
            pointer=pointer.getParent();
            selectedIndex=0;
        }
    }

    public void select() {
        MenuOption opt=getSelectedOption();
        if (opt==null) return;
        if (opt.hasChildren()) {
            pointer=opt;
            selectedIndex=0;
        } else {
            opt.execute();
        }
    }
}