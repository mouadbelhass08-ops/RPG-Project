package RPG.engine.combat;

public class ActionResult {
    private String message;
    private BattleAction action;
    private boolean success;

    ActionResult(String message,BattleAction action) {
        this.message=message;
        this.action=action;
    }

    public String getMessage() {return message;}
    public BattleAction getAction() {return action;}
    public boolean isSuccess() {return success;}
    public void setSuccess() {this.success=true;}
}