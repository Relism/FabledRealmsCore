package net.fabledrealms.quests;

import net.fabledrealms.quests.base.QuestObjective;
import net.fabledrealms.quests.base.QuestPoint;

import java.util.Set;

public class Quest {

    private final String name;
    private Set<QuestPoint> questPoints;
    private QuestObjective questObjective;

    public Quest(String name) {
        this.name = name;
    }

    public Quest(String name, Set<QuestPoint> questPoints, QuestObjective questObjective) {
        this.name = name;
        this.questPoints = questPoints;
        this.questObjective = questObjective;
    }

    public String getName() {
        return name;
    }

    public Set<QuestPoint> getQuestPoints() {
        return questPoints;
    }

    public void setQuestPoints(Set<QuestPoint> questPoints) {
        this.questPoints = questPoints;
    }

    public QuestObjective getQuestObjective() {
        return questObjective;
    }

    public void save() {}
    public void delete() {}
}
