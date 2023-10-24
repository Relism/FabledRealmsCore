package net.fabledrealms.story;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chapter {

    private final String name;
    private final int id;
    private List<StoryLine> storyLines;

    public Chapter(String name, int id) {
        this.name = name;
        this.id = id;
        this.storyLines = new ArrayList<>();
    }

    public Chapter(String name, int id, List<StoryLine> storyLines) {
        this.name = name;
        this.id = id;
        this.storyLines = storyLines;
    }

    public Chapter addStoryLine(StoryLine... stories) {
        Collections.addAll(storyLines, stories);
        return this;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<StoryLine> getStoryLines() {
        return storyLines;
    }

    public void setStoryLines(List<StoryLine> storyLines) {
        this.storyLines = storyLines;
    }
}
