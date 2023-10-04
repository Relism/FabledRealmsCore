package net.fabledrealms.graveyards;

import net.fabledrealms.Core;
import net.fabledrealms.wrappers.FileWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GraveyardManager {

    private final Core main;
    private final FileWrapper graveyardFileWrapper;
    private final HashMap<Graveyard, Location> graveyardMap;

    public GraveyardManager(Core main){
        this.main = main;
        this.graveyardFileWrapper = new FileWrapper(main, main.getDataFolder() + File.separator + "Graveyards", "graveyards.yml");
        this.graveyardMap = new HashMap<>();
        generateGraveyards();
    }

    private void generateGraveyards(){
        List<String> keyList = new ArrayList<>(graveyardFileWrapper.getFile().getKeys(false));
        for (int i = 0; i < graveyardFileWrapper.getFile().getKeys(false).size(); i++){
            String graveyardName = graveyardFileWrapper.getFile().getString(keyList.get(i));
            String worldName = graveyardFileWrapper.getFile().getString(keyList.get(i) + ".world");
            int x = graveyardFileWrapper.getFile().getInt(keyList.get(i) + ".x");
            int y = graveyardFileWrapper.getFile().getInt(keyList.get(i) + ".y");
            int z = graveyardFileWrapper.getFile().getInt(keyList.get(i) + ".z");
            createGraveyard(graveyardName, worldName, x, y, z);
        }
    }

    public void createGraveyard(String graveyardName, String worldName, int x, int y, int z){
        Graveyard graveyard = new Graveyard(main, graveyardName,worldName,x,y,z);
        Location graveyardLocation = new Location(Bukkit.getWorld(worldName), x,y,z);
        graveyardMap.put(graveyard,graveyardLocation);
    }

    public Location getClosestGraveyard(Player player){
        World world = player.getWorld();
        List<Location> graveyards = new ArrayList<>();

        //Gets graveyards in the world
        for (Graveyard graveyard : graveyardMap.keySet()){
            if (graveyard.getWorldName().equalsIgnoreCase(world.getName())){
                graveyards.add(new Location(Bukkit.getWorld(graveyard.getWorldName()),graveyard.getX(), graveyard.getY(), graveyard.getZ()));
            }
        }

        //Finds the closest Graveyard to the player
        Location closest = null;
        for(Location loc : graveyards) {
            if(closest == null)
                closest = loc;
            else if(loc.distanceSquared(player.getLocation()) < closest.distanceSquared(player.getLocation()))
                closest = loc;
        }

        return closest;
    }



}
