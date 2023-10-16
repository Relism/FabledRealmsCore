package net.fabledrealms.character;

import java.util.Date;

public class Character {

    private final String uuid;
    private final String className;
    private final int characterID;
    private final int levelMain;
    private final int levelWoodcutting;
    private final int levelFishing;
    private final int levelMining;
    private final int levelSmithing;
    private final int levelCrafting;
    private final int levelGathering;
    private final int expMain;
    private final int expWoodcutting;
    private final int expFishing;
    private final int expMining;
    private final int expSmithing;
    private final int expCrafting;
    private final int expGathering;
    private int balance;
    private long lastJoined;

    public Character(String uuid, int characterID, String className, int levelMain, int levelWoodcutting, int levelFishing, int levelMining, int levelSmithing, int levelCrafting, int levelGathering, int expMain, int expWoodcutting, int expFishing, int expMining, int expSmithing, int expCrafting, int expGathering, int balance, long lastJoined) {
        this.uuid = uuid;
        this.className = className;
        this.characterID = characterID;
        this.levelMain = levelMain;
        this.levelWoodcutting = levelWoodcutting;
        this.levelFishing = levelFishing;
        this.levelMining = levelMining;
        this.levelSmithing = levelSmithing;
        this.levelCrafting = levelCrafting;
        this.levelGathering = levelGathering;
        this.expMain = expMain;
        this.expWoodcutting = expWoodcutting;
        this.expFishing = expFishing;
        this.expMining = expMining;
        this.expSmithing = expSmithing;
        this.expCrafting = expCrafting;
        this.expGathering = expGathering;
        this.balance = balance;
        this.lastJoined = lastJoined;
    }

    public String getUuid() {
        return uuid;
    }

    public String getClassName() {
        return className;
    }

    public int getCharacterID() {
        return characterID;
    }

    public int getLevelMain() {
        return levelMain;
    }

    public int getLevelWoodcutting() {
        return levelWoodcutting;
    }

    public int getLevelFishing() {
        return levelFishing;
    }

    public int getLevelMining() {
        return levelMining;
    }

    public int getLevelSmithing() {
        return levelSmithing;
    }

    public int getLevelCrafting() {
        return levelCrafting;
    }

    public int getLevelGathering() {
        return levelGathering;
    }

    public int getExpMain() {
        return expMain;
    }

    public int getExpWoodcutting() {
        return expWoodcutting;
    }

    public int getExpFishing() {
        return expFishing;
    }

    public int getExpMining() {
        return expMining;
    }

    public int getExpSmithing() {
        return expSmithing;
    }

    public int getExpCrafting() {
        return expCrafting;
    }

    public int getExpGathering() {
        return expGathering;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public long getLastJoined() {
        return lastJoined;
    }

    public void setLastJoined(long lastJoined) {
        this.lastJoined = lastJoined;
    }
}