package net.fabledrealms.animations;

import net.fabledrealms.Core;
import net.fabledrealms.util.msg;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnAnimation {

    private final Core main;

    public RespawnAnimation(Core main, Player player){
        this.main = main;
        respawn(player);
    }

    public void playBeamAnimation(Player player) {
        new BukkitRunnable() {
            private int step = 0;
            Location playerLocation = player.getLocation();

            @Override
            public void run() {
                if (step >= 20) { // Adjust the number of steps as needed
                    cancel();
                    return;
                }

                double yOffset = 0.0;

                // Create the beam particles above the player's head
                playerLocation.getWorld().spawnParticle(
                        Particle.END_ROD, // Particle type
                        playerLocation.clone().add(0, yOffset, 0), // Location
                        1, // Count
                        0.0, // Offset X
                        0.0, // Offset Y
                        0.0, // Offset Z
                        0.1 // Extra (size or speed, adjust as needed)
                );

                // Adjust the yOffset to simulate the beam moving downwards
                yOffset -= 0.2;
                step++;
            }
        }.runTaskTimer(main, 0L, 10L); // 10L is the delay between each step (adjust as needed)
    }
    private void respawn(Player player) {
        String deathMessage = main.getLangFile().getFile().getString("player.death-message");
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_DEATH,1,1);
        playBeamAnimation(player);
        msg.send(player, deathMessage);
    }

}
