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
        for (String key : graveyardFileWrapper.getFile().getKeys(false)){
            String graveyardID = key;
            String worldName = graveyardFileWrapper.getFile().getString(key+".world");
            int x = graveyardFileWrapper.getFile().getInt(key+".x");
            int y = graveyardFileWrapper.getFile().getInt(key+"y");
            int z = graveyardFileWrapper.getFile().getInt(key+"z");
            createGraveyard(graveyardID,worldName,x,y,z);
            Bukkit.getLogger().info("Graveyard:" + key + " has been generated.");
        }
    }

    public void createGraveyard(String graveyardName, String worldName, int x, int y, int z){
        Graveyard graveyard = new Graveyard(main, graveyardName,worldName,x,y,z);
        Location graveyardLocation = new Location(Bukkit.getWorld(worldName), x,y,z);
        graveyardMap.put(graveyard,graveyardLocation);
    }

    public Graveyard getGraveyardByID(String id){
        Graveyard returned = null;
        for (Graveyard graveyard : graveyardMap.keySet()){
            if (graveyard.getGraveyardName().equalsIgnoreCase(id)){
                returned = graveyard;
            }
        }
        return returned;
    }

    public Graveyard getGraveyardByLocation(Location location){
        for (Graveyard graveyard : graveyardMap.keySet()){
            Location location1 = graveyardMap.get(graveyard);
            if (location1.equals(location)){
                return graveyard;
            }
        }
        return null;
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

    public void saveToFile(){
        for (Location location : graveyardMap.values()){
            Graveyard graveyard = getGraveyardByLocation(location);
            String graveyardName = graveyard.getGraveyardName();
            String worldName = graveyard.getWorldName();
            int x = graveyard.getX();
            int y = graveyard.getY();
            int z = graveyard.getZ();
            if (!graveyardFileWrapper.getFile().getKeys(false).contains(graveyardName)){
                graveyardFileWrapper.getFile().createSection(graveyardName);
            }
            main.getGraveyardManager().getGraveyardFileWrapper().getFile().set(graveyardName + ".world", worldName);
            main.getGraveyardManager().getGraveyardFileWrapper().getFile().set(graveyardName + ".x", x);
            main.getGraveyardManager().getGraveyardFileWrapper().getFile().set(graveyardName + ".y", y);
            main.getGraveyardManager().getGraveyardFileWrapper().getFile().set(graveyardName + ".z", z);
            main.getGraveyardManager().getGraveyardFileWrapper().saveFile();
        }
    }

    public FileWrapper getGraveyardFileWrapper(){return graveyardFileWrapper;}
    public HashMap<Graveyard, Location> getGraveyardMap(){return graveyardMap;}



}
