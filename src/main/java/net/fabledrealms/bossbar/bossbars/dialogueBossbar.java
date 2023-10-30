package net.fabledrealms.bossbar.bossbars;

import net.fabledrealms.Core;
import net.fabledrealms.bossbar.Bossbar;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

class dialogueBossbarComposer implements Bossbar {
    static Core FabledCore;
    static Player player;
    private static BossBar dialogueBar;

    @Override
    public void init(Core core, Player p) {
        FabledCore = core;
        player = p;
    }

    @Override
    public void display() {
        dialogueBar.addPlayer(player);
    }

    @Override
    public void remove() {
        dialogueBar.removePlayer(player);
    }

    private void construct(){
        //
    }
}
