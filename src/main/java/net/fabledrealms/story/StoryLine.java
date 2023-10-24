package net.fabledrealms.story;

import net.fabledrealms.dialogues.Dialogue;
import net.fabledrealms.quests.Quest;

public class StoryLine {

    private int id;
    private final Dialogue dialogue;
    private final Quest quest;
    private StoryLine nextStoryLine; // # Based on options


    public StoryLine(int id, Dialogue dialogue, Quest quest) {
        this.id = id;
        this.dialogue = dialogue;
        this.quest = quest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Quest getQuest() {
        return quest;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public StoryLine getNextStoryLine() {
        return nextStoryLine;
    }

    public void setNextStoryLine(StoryLine nextStoryLine) {
        this.nextStoryLine = nextStoryLine;
    }
}
