package net.fabledrealms.managers;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {
    private final Core main;
    private final File directory;
    private final File file;
    private final String fileName;
    private final FileConfiguration config;

    public FileManager(Core main, String directoryPath, String fileName){
        this.main = main;
        this.fileName = fileName;
        this.directory = new File(directoryPath);
        this.file = new File(directory, fileName);
        this.config = YamlConfiguration.loadConfiguration(file);
        setupFile();
    }

    public void setupFile(){
        if (!directory.exists()){
            directory.mkdirs();
        }
        if (!file.exists()) {
            loadFile();
        }
        Bukkit.getLogger().info(file.getName() + " has been successfully initialized!");
    }

    // Load File

    public void loadFile(){
        try {
            InputStreamReader reader = new InputStreamReader(main.getResource(fileName));
            FileConfiguration fileConfig = YamlConfiguration.loadConfiguration(reader);
            config.setDefaults(fileConfig);
            config.options().copyDefaults(true);
            reader.close();
            saveFile();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //Save File

    public void saveFile(){
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean fileExists(){
        return file.exists();
    }

    public FileConfiguration getFile(){
        return config;
    }
}
