package RPG.engine.system;

import java.util.*;
import RPG.engine.characters.Character;

public class InitiativeSystem {
    private static Map<Character,Integer> initiativeMap=new HashMap<>();

    public static void rollInitiative(List<Character> participants) {
        for (Character c : participants) {
            int roll=DiceRoller.roll("1d20");
            initiativeMap.put(c,roll);
            System.out.println(c.getName()+" rolled initiative: "+roll);
        }
    }

    public static Queue<Character> getTurnOrder() {
        List<Map.Entry<Character,Integer>> entries=new ArrayList<>(initiativeMap.entrySet());
        entries.sort((a,b) -> b.getValue()-a.getValue());
        Queue<Character> order=new LinkedList<>();
        for (Map.Entry<Character,Integer> e : entries) {
            order.add(e.getKey());
        }
        return order;
    }
}