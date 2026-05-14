package RPG.engine.combat;

import java.util.ArrayList;
import java.util.List;

public class BattleLog {
    private List<String> entries=new ArrayList<String>();

    public void addEntry(String entry) {
        entries.add(entry);
        System.out.println(entry);
    }
    public List<String> getEntries() {
        return entries;
    }
    public void showLog() {
        System.out.println("\n--- Battle Log ---");
        for (String entry : entries) {
            System.out.println(entry);
        }
    }
}