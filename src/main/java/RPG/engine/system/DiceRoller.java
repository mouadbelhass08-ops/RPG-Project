package RPG.engine.system;

import java.util.Random;

public class DiceRoller {
    private static Random random=new Random();

    private DiceRoller() {}

    // Roll dice using notation like "2d6+3"
    public static int roll(String roll) {
        String[] parts=roll.split("d");
        int numDice=Integer.parseInt(parts[0]);
        int sides;
        int modifier=0;
        if (parts[1].contains("+") || parts[1].contains("-")) {
            String[] subParts=parts[1].split("+-");
            sides=Integer.parseInt(subParts[0]);
            modifier=Integer.parseInt(subParts[1]);
        } 
        else {
            sides=Integer.parseInt(parts[1]);
        }
        int total=0;
        for (int i=0;i<numDice;i++) {
            total+=random.nextInt(sides)+1;
        }
        return total+modifier;
    }
}