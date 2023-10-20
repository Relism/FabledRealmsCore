package net.fabledrealms.combat.base;

import org.bukkit.potion.PotionEffect;

import java.util.List;

public class Skills {

    private String name;
    private float cooldown;
    private double minDamage;
    private double maxDamage;
    private List<PotionEffect> playerEffects;
    private List<PotionEffect> enemyEffects;
    private boolean throwLightning;
    private boolean punchToAir;
    private boolean switchLocations;
    private boolean smashGround;
    //* add more functionalities if u want

    public Skills(String name, float cooldown, double minDamage, double maxDamage,
                  List<PotionEffect> playerEffects, List<PotionEffect> enemyEffects,
                  boolean throwLightning, boolean punchToAir, boolean switchLocations, boolean smashGround) {
        this.name = name;
        this.cooldown = cooldown;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
        this.playerEffects = playerEffects;
        this.enemyEffects = enemyEffects;
        this.throwLightning = throwLightning;
        this.punchToAir = punchToAir;
        this.switchLocations = switchLocations;
        this.smashGround = smashGround;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public double getMinDamage() {
        return minDamage;
    }

    public void setMinDamage(double minDamage) {
        this.minDamage = minDamage;
    }

    public double getMaxDamage() {
        return maxDamage;
    }

    public void setMaxDamage(double maxDamage) {
        this.maxDamage = maxDamage;
    }

    public List<PotionEffect> getPlayerEffects() {
        return playerEffects;
    }

    public void setPlayerEffects(List<PotionEffect> playerEffects) {
        this.playerEffects = playerEffects;
    }

    public List<PotionEffect> getEnemyEffects() {
        return enemyEffects;
    }

    public void setEnemyEffects(List<PotionEffect> enemyEffects) {
        this.enemyEffects = enemyEffects;
    }

    public boolean isThrowLightning() {
        return throwLightning;
    }

    public void setThrowLightning(boolean throwLightning) {
        this.throwLightning = throwLightning;
    }

    public boolean isPunchToAir() {
        return punchToAir;
    }

    public void setPunchToAir(boolean punchToAir) {
        this.punchToAir = punchToAir;
    }

    public boolean isSwitchLocations() {
        return switchLocations;
    }

    public void setSwitchLocations(boolean switchLocations) {
        this.switchLocations = switchLocations;
    }

    public boolean isSmashGround() {
        return smashGround;
    }

    public void setSmashGround(boolean smashGround) {
        this.smashGround = smashGround;
    }

}
