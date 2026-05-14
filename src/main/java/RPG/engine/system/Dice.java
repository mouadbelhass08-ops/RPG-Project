package RPG.engine.system;

import java.util.Random;

public class Dice {
    private int sides;
    private Random rd=new Random();

    public Dice(int sd) {
        this.sides=sd;
        this.rd=new Random();
    }

    public int roll() {return rd.nextInt(sides)+1;}
    public String toString() {return "d"+sides;}
}