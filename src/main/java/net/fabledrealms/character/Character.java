package net.fabledrealms.character;

public class Character {


    public Character(int characterID, String className, int characterLevel, int characterExp, int balance){
        this.characterID = characterID;
        this.className = className;
        this.characterLevel = characterLevel;
        this.characterExp = characterExp;
        this.balance = balance;
    }

    private int characterID;
    private int balance;
    private int characterLevel;
    private int characterExp;
    private String className;

    public int getCharacterID() {
        return characterID;
    }

    public void setCharacterID(int characterID) {
        this.characterID = characterID;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getCharacterLevel() {
        return characterLevel;
    }

    public void setCharacterLevel(int characterLevel) {
        this.characterLevel = characterLevel;
    }

    public float getCharacterExp() {
        return characterExp;
    }

    public void setCharacterExp(int characterExp) {
        this.characterExp = characterExp;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
