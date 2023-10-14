package net.fabledrealms.quests.base;

import java.util.List;

public class QuestObjective {

    private final String objective;
    private List<String> commands;

    public QuestObjective(String objective, List<String> commands) {
        this.objective = objective;
        this.commands = commands;
    }

    public String getObjective() {
        return objective;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
