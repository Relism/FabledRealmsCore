package net.fabledrealms.bossbar;

import net.fabledrealms.bossbar.bossbars.compassBossbar;

public enum BossbarType {
    COMPASS(compassBossbar.class);
    // Add more boss bar types and their associated classes here

    private final Class<? extends Bossbar> bossbarClass;

    BossbarType(Class<? extends Bossbar> bossbarClass) {
        this.bossbarClass = bossbarClass;
    }

    public Class<? extends Bossbar> getBossbarClass() {
        return bossbarClass;
    }
}

