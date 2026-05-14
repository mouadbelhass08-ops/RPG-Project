package RPG.engine.combat;

public class Damage {
    private int amount;
    private String damageType;

    public Damage(int amount) {
        this.amount=amount;
    }
    public int getAmount() {return amount;}
    public String getDamageType() {return damageType;}
    void describe() {}
}