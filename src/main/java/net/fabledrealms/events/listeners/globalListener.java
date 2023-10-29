package net.fabledrealms.events.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.events.custom.playerChunkUpdateEvent;
import net.fabledrealms.util.msg;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

public class globalListener implements Listener {

    public globalListener(Core FabledCore){
        FabledCore.getServer().getPluginManager().registerEvents(this, FabledCore);
        msg.log("GlobalListener succesfully enabled");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        // Check if the player has moved to a different chunk
        if(!event.getFrom().getChunk().equals(Objects.requireNonNull(event.getTo()).getChunk())) {
            Player player = event.getPlayer();
            Chunk oldChunk = event.getFrom().getChunk();
            Chunk newChunk = event.getTo().getChunk();
            playerChunkUpdateEvent chunkUpdateEvent = new playerChunkUpdateEvent(player, oldChunk, newChunk);
            Bukkit.getPluginManager().callEvent(chunkUpdateEvent);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();
        playerChunkUpdateEvent chunkUpdateEvent = new playerChunkUpdateEvent(player, null, chunk);
        Bukkit.getPluginManager().callEvent(chunkUpdateEvent);
        //other stuff onPlayerJoin
    }
}
