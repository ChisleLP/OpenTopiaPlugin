package de.freddo.utiliy;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;

import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class LocalizationManager {
    private final Map<String, Material> germanToMaterial = new HashMap<>();

    public LocalizationManager() {
        loadGermanLocalization();
    }

    private void loadGermanLocalization() {
        try (InputStreamReader reader = new InputStreamReader(
                getClass().getResourceAsStream("/de_de.json"), "UTF-8")) {
            Type mapType = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> translations = new Gson().fromJson(reader, mapType);
            for (Map.Entry<String, String> entry : translations.entrySet()) {
                String key = entry.getKey();
                String germanName = entry.getValue();
                if (key.startsWith("item.minecraft.") || key.startsWith("block.minecraft.")) {
                    String minecraftId = key.replace("item.minecraft.", "")
                            .replace("block.minecraft.", "");
                    Material material = Material.matchMaterial(minecraftId);
                    if (material != null) {
                        germanToMaterial.put(germanName.toLowerCase(), material);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Material getMaterialByGermanName(String germanName) {
        return germanToMaterial.get(germanName.toLowerCase());
    }
}
