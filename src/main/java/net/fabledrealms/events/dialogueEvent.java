package net.fabledrealms.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class dialogueEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final String option;

    public dialogueEvent(Player player, String responseOption) {
        this.player = player;
        this.option = responseOption;
    }

    public Player getPlayer() {
        return player;
    }

    public String getResponseOption() {
        return option;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
