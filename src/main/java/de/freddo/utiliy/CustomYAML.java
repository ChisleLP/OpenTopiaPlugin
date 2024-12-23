package de.freddo.utiliy;

import de.freddo.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class CustomYAML {

    private final Main plugin;

    private final String fileName;
    private File file;
    private FileConfiguration config;


    public CustomYAML(Main plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        createFile();
    }

    private void createFile() {
        file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.getFanyLogger().log("Creating new File "+fileName);
            try {
                file.getParentFile().mkdirs();
                plugin.saveResource(fileName, false);
                if (!file.exists()) file.createNewFile(); // Otherwise, create a blank file
            } catch (IOException e) {
                plugin.getFanyLogger().log("Could not create " + fileName);
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save " + fileName);
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
