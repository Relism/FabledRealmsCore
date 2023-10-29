package net.fabledrealms.dialogues;

import net.fabledrealms.Core;
import net.fabledrealms.events.custom.dialogueEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

public class OptionHandler implements Listener {
    private final Core FabledCore;
    private String[] options;
    private Player player;
    private int currentPosition;
    private Dialogue dialogue;

    private boolean playerMadeSelection = false;

    public OptionHandler(Core FabledCore, Player player, Dialogue dialogue) {
        this.FabledCore = FabledCore;
        this.player = player;
        this.currentPosition = 0;
        this.dialogue = dialogue;
    }

    public void handleOptions(String[] options) {
        if(!dialogue.isEnded() && options != null){
            // Register this class as a listener
            this.options = options;
            //msg.broadcast("Handling the following options : " + Arrays.toString(options));
            FabledCore.getServer().getPluginManager().registerEvents(this, FabledCore);
        } else {
            PlayerInteractEvent.getHandlerList().unregister(this);
            PlayerItemHeldEvent.getHandlerList().unregister(this);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!dialogue.isEnded()){
            if (player != null && event.getPlayer() == player) {
                // Player left-clicked, stop listening
                playerMadeSelection = true;
                //msg.log(player.getName() + " has interacted, with selected option : " + currentPosition);
                dialogueEvent dEvent = new dialogueEvent(player, options[currentPosition]);
                Bukkit.getPluginManager().callEvent(dEvent);
                event.setCancelled(true);
            }
        } else {
            PlayerInteractEvent.getHandlerList().unregister(this);
            PlayerItemHeldEvent.getHandlerList().unregister(this);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        if(!dialogue.isEnded()){
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
                    currentPosition = options.length - 1;
                } else if (currentPosition >= options.length) {
                    currentPosition = 0;
                }
                event.setCancelled(true);
                //msg.send(player, String.valueOf(currentPosition) + " " + options[currentPosition]);
            }
        } else {
            PlayerInteractEvent.getHandlerList().unregister(this);
            PlayerItemHeldEvent.getHandlerList().unregister(this);
            event.setCancelled(true);
        }
    }

    public boolean hasPlayerMadeSelection() {
        return playerMadeSelection;
    }
}
