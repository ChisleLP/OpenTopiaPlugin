package de.freddo.utiliy;

import de.freddo.Main;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class CraftbookVariables {

    public CraftbookVariables() {
    }

    private FileConfiguration loadCraftBookVariables() {
        File file = new File("plugins/CraftBook/variables.yml");
        if (!file.exists()) {
            System.out.println("variables.yml does not exist!");
            return null;
        }
        System.out.println("Exists");
        return YamlConfiguration.loadConfiguration(file);
    }

    public String getVariableValue(String namespace, String variableName) {
        File file = new File("plugins/CraftBook/variables.yml");
        if (!file.exists()) {
            System.out.println("variables.yml does not exist!");
            return null;
        }
        System.out.println("Exists");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config == null) return null;

        String key = namespace.equalsIgnoreCase("global")
                ? variableName
                : namespace + "|" + variableName;

        return config.getString(key, "Variable not found!");
    }

    public String findVariableByValue(String targetValue) {
        File file = new File("plugins/CraftBook/variables.yml");
        if (!file.exists()) {
            System.out.println("variables.yml does not exist!");
            return null;
        }

        System.out.println("File exists");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config == null) {
            System.out.println("Failed to load configuration!");
            return null;
        }

        // Start from the "variables" section
        ConfigurationSection variablesSection = config.getConfigurationSection("variables");
        if (variablesSection == null) {
            System.out.println("No 'variables' section found!");
            return null;
        }
        System.out.println(targetValue);

        // Iterate through keys in the "variables" section
        for (String key : variablesSection.getKeys(false)) {
            System.out.println("Checking key: " + key);
            String value = variablesSection.getString(key);
            System.out.println("Value: "+value);


            if (value != null && value.equalsIgnoreCase(targetValue)) {
                // Return the part after "|" if it exists, otherwise return the key
                return key.contains("|") ? key.split("\\|")[1] : key;
            }
        }

        return "0";
    }

}
