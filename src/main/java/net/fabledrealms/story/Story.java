package net.fabledrealms.story;

import org.bukkit.entity.Player;

public class Story {

    /*
     > Player Story Progression based on int. Each storyline --> id++ (no matter the chapter) chapter 1 - storyline 1 but chapter 2 != storyline 1 (correct: 2)
     */
    private final Player player;
    private int progress;
    private double points;

    public Story(Player player, int progress) {
        this.player = player;
        this.progress = progress;
    }

    public void nextChapter() {}
    public void nextStoryLine() {}
    public void resetStory() {}
    public void saveProgress(){} // * save int on db
    public String getProgressionBar() {return "::::::::::::";}

    public Player getPlayer() {
        return player;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }
}
