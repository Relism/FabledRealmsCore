package net.fabledrealms.quests.base;

public class QuestObjective {

    private final String objective;
    private String[] commands;

    public QuestObjective(String objective, String[] commands) {
        this.objective = objective;
        this.commands = commands;
    }

    public String getObjective() {
        return objective;
    }

    public String[] getCommands() {
        return commands;
    }

    public void setCommands(String[] commands) {
        this.commands = commands;
    }
}
