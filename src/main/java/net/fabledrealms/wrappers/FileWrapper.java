package net.fabledrealms.wrappers;

import net.fabledrealms.Core;
import net.fabledrealms.util.msg;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileWrapper {
    private final Core main;
    private final File directory;
    private final File file;
    private final String fileName;
    private final FileConfiguration config;

    public FileWrapper(Core main, String directoryPath, String fileName){
        this.main = main;
        this.fileName = fileName;
        this.directory = new File(directoryPath);
        this.file = new File(directory, fileName);
        this.config = YamlConfiguration.loadConfiguration(file);
        setupFile();
    }

    public void setupFile() {
        if (!directory.exists()) {
            directory.mkdirs();
            msg.log("&#eb8c34• &fDirectory &#a83232does not exist&f. Created directory: \"&#eb8c34" + directory.getAbsolutePath() + "&f\"");
            return; // directory doesn't exist, just return
        }

        if (!file.exists()) {
            try {
                loadFile();
                msg.log("&#eb8c34• &fFile \"&#eb8c34" + file.getName() + "&f\"&#a83232 not found");
            } catch (Error e) {
                msg.log("&#eb8c34• &fError encountered while loading file: &#a83232" + e.getMessage());
            }
        } else {
            msg.log("&#eb8c34• &fFile \"&#eb8c34" + file.getName() + "&f\" has been &#5ca832successfully &finitialized!");
        }
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
