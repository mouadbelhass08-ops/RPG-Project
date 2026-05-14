package RPG.engine.resources;

public interface Resource {
    void consume(int amount);
    void restore(int amount);
    boolean isFull();
    boolean isAvailable();
    boolean isDepleted();
}