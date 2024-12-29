package de.freddo.utiliy;

import de.freddo.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CraftbookVariables {

    public CraftbookVariables() {
    }

    public String findVariableByValue(String targetValue) {
        File file = new File("plugins/CraftBook/variables.yml");
        if (!file.exists()) {
            System.out.println("variables.yml does not exist!");
            return null;
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config == null) {
            System.out.println("Failed to load configuration!");
            return null;
        }

        ConfigurationSection variablesSection = config.getConfigurationSection("variables");
        if (variablesSection == null) {
            return null;
        }
        for (String key : variablesSection.getKeys(false)) {
            String value = variablesSection.getString(key);
            if (value != null && value.equalsIgnoreCase(targetValue)) {
                return key.contains("|") ? key.split("\\|")[1] : key;
            }
        }
        return "0";
    }

}
