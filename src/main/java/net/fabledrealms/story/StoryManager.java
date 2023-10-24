package net.fabledrealms.story;

import net.fabledrealms.Core;
import net.fabledrealms.dialogues.Dialogue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoryManager {

    private final Core core;
    private Set<Chapter> chapters = new HashSet<>();

    public StoryManager(Core core) {
        this.core = core;

        StoryLine initial = new StoryLine(1, new Dialogue(core, "initial", null), core.getQuestManager().getQuest("initial"));
        chapters.add(new Chapter("Beginning...", 1).addStoryLine(initial));

    }



    public List<StoryLine> getStoryLinesOfChapter(int id) {
        Chapter chapter = getChapter(id);
        if(chapter!=null) return chapter.getStoryLines();
        return null;
    }

    public List<StoryLine> getStoryLinesOfChapter(Chapter chapter) {
        return chapter.getStoryLines();
    }

    public Chapter getChapterByProgress(int progress) {
        for (Chapter chapter : getChapters()) {
            for (StoryLine stories : chapter.getStoryLines()) {
                if (stories.getId() == progress) {
                    return chapter;
                }
            }
        }return null;
    }

    public Chapter getChapter(int id) {
        for(Chapter chapters : getChapters()) {
            if (chapters.getId() == id) {
                return chapters;
            }
        } return null;
    }

    public Set<Chapter> getChapters() { return chapters; }

}
