package net.fabledrealms.environmentalEffects;

import net.fabledrealms.Core;
import net.fabledrealms.events.custom.playerChunkUpdateEvent;
import net.fabledrealms.util.msg;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ambientPopulator implements Listener {
    public Core FabledCore;
    public static final Map<String, List<ChunkArea>> populatedEnvironment = new HashMap<>();
    public static final int RENDER_DISTANCE = 10; // Adjust this to your render distance

    public ambientPopulator(Core core) {
        this.FabledCore = core;
        FabledCore.getServer().getPluginManager().registerEvents(this, FabledCore);
    }

    // Custom class to represent a loaded chunk area
    public static final class ChunkArea {
        int centerX;
        int centerZ;

        public ChunkArea(int centerX, int centerZ) {
            this.centerX = centerX;
            this.centerZ = centerZ;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            ChunkArea chunkArea = (ChunkArea) obj;
            return centerX == chunkArea.centerX && centerZ == chunkArea.centerZ;
        }

        @Override
        public int hashCode() {
            return 31 * centerX + centerZ;
        }
    }

    void handleUpdate(playerChunkUpdateEvent event) {
        World world = event.getPlayer().getWorld();
        String worldName = world.getName();

        int newChunkX = event.getNewChunk().getX();
        int newChunkZ = event.getNewChunk().getZ();

        List<ChunkArea> loadedChunks = populatedEnvironment.computeIfAbsent(worldName, w -> new ArrayList<>());

        // Calculate the currently loaded chunks based on the newChunk and render distance
        List<ChunkArea> newLoadedChunks = new ArrayList<>();
        for (int x = newChunkX - RENDER_DISTANCE; x <= newChunkX + RENDER_DISTANCE; x++) {
            for (int z = newChunkZ - RENDER_DISTANCE; z <= newChunkZ + RENDER_DISTANCE; z++) {
                ChunkArea chunk = new ChunkArea(x, z);
                if (!loadedChunks.contains(chunk)) {
                    newLoadedChunks.add(chunk);
                }
            }
        }

        if (event.getOldChunk() != null) {
            int oldChunkX = event.getOldChunk().getX();
            int oldChunkZ = event.getOldChunk().getZ();

            // Calculate the previously loaded chunks based on the oldChunk and render distance
            List<ChunkArea> oldLoadedChunks = new ArrayList<>();
            for (int x = oldChunkX - RENDER_DISTANCE; x <= oldChunkX + RENDER_DISTANCE; x++) {
                for (int z = oldChunkZ - RENDER_DISTANCE; z <= oldChunkZ + RENDER_DISTANCE; z++) {
                    ChunkArea chunk = new ChunkArea(x, z);
                    if (loadedChunks.contains(chunk)) {
                        oldLoadedChunks.add(chunk);
                    }
                }
            }

            // Calculate chunks to be removed
            /*List<ChunkArea> chunksToRemove = loadedChunks.stream()
                    .filter(chunk -> !newLoadedChunks.contains(chunk))
                    .toList();*/

            loadedChunks.removeAll(oldLoadedChunks);
            msg.log("removing : " + oldLoadedChunks.size());
        }

        loadedChunks.addAll(newLoadedChunks);

        msg.log("adding : " + newLoadedChunks.size());
        msg.log("loadedChunks size : " + loadedChunks.size());

        logData(event.getPlayer());
    }

    @EventHandler
    void onPlayerChunkUpdateEvent(playerChunkUpdateEvent event) {
        handleUpdate(event);
    }

    void logData(Player player) {
        msg.send(player, "New populatedEnvironment: ");
        for (Map.Entry<String, List<ChunkArea>> entry : populatedEnvironment.entrySet()) {
            String worldName = entry.getKey();
            List<ChunkArea> loadedChunks = entry.getValue();

            msg.send(player, "World: " + worldName);
            msg.send(player, "Loaded Chunks: " + loadedChunks.size());
        }
    }
}
