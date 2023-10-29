package net.fabledrealms.events.custom;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class playerChunkUpdateEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final Chunk oldChunk;
    private final Chunk newChunk;

    public playerChunkUpdateEvent(Player player, Chunk oldChunk, Chunk newChunk){
        this.player = player;
        this.oldChunk = oldChunk;
        this.newChunk = newChunk;
    }

    public Player getPlayer(){
        return this.player;
    }

    public Chunk getNewChunk(){
        return this.newChunk;
    }

    public Chunk getOldChunk(){
        return this.oldChunk;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
