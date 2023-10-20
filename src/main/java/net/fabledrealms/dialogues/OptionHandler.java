package net.fabledrealms.dialogues;

import net.fabledrealms.Core;
import net.fabledrealms.events.dialogueEvent;
import net.fabledrealms.util.msg;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class OptionHandler implements Listener {
    private final Core FabledCore;
    private int options;
    private Player player;
    private int currentPosition;

    private boolean playerMadeSelection = false;

    public OptionHandler(Core FabledCore, String[] options, Player player) {
        this.FabledCore = FabledCore;
        this.options = options.length;
        this.player = player;
        this.currentPosition = 0;
    }

    public void handleOptions() {
        // Register this class as a listener
        FabledCore.getServer().getPluginManager().registerEvents(this, FabledCore);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (player != null && event.getPlayer() == player) {
            // Player left-clicked, stop listening
            playerMadeSelection = true;
            msg.log(player.getName() + " has interacted, with selected option : " + currentPosition);
            dialogueEvent dialogueEvent = new dialogueEvent(player, currentPosition);
            Bukkit.getPluginManager().callEvent(dialogueEvent);

            // Unregister this class as a listener
            PlayerInteractEvent.getHandlerList().unregister(this);
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        if (player != null && event.getPlayer() == player) {
            int previousSlot = event.getPreviousSlot();
            int newSlot = event.getNewSlot();

            int tolerance = 1;
            int displace = Math.abs(previousSlot - newSlot);

            if(displace != tolerance){
                return;
            }

            // Handle scroll events
            if (previousSlot == 0 && newSlot == 8) {
                currentPosition--;
            } else if (previousSlot == 8 && newSlot == 0) {
                currentPosition++;
            } else {
                if (newSlot > previousSlot) {
                    currentPosition++;
                } else {
                    currentPosition--;
                }
            }

            // Ensure currentPosition is within bounds (took me a while to figure out)
            if (currentPosition < 0) {
                currentPosition = options - 1;
            } else if (currentPosition >= options) {
                currentPosition = 0;
            }
            //msg.send(player, String.valueOf(currentPosition));
        }
    }

    public boolean hasPlayerMadeSelection() {
        return playerMadeSelection;
    }
}
