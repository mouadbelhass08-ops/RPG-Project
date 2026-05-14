package RPG.engine.skills;

import RPG.engine.system.Dice;

public class Skill {
    private String name;
    private int level;
    private int experience;

    public Skill(String name) {
        this.name=name;
        this.level=1;
        this.experience=0;
    }

    public String getName() {return name;}
    public int getLevel() {return level;}
    public void gainExperience(int amount) {
        this.experience+=amount;
        if (this.experience>=level*100) {
            this.level++;
            this.experience=0;
            System.out.println(this.name+" leveled up to "+this.level+"!");
        }
    }
    public boolean checkSuccess(int difficulty) {
        int roll=new Dice(6).roll()+this.level;
        return roll>=difficulty;
    }
}