package de.freddo;

import com.earth2me.essentials.Essentials;
import de.freddo.utiliy.CustomYAML;
import de.freddo.utiliy.FancyLogger;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private FancyLogger logger;
    private CustomYAML usersFile;
    Essentials ess;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        logger = new FancyLogger(this);
        ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");

        /*
        Wird evtl. nützlich für die zukunft.

        usersFile = new CustomYAML(this, "users.yml");
        if (!usersFile.getConfig().contains("players")) {
            usersFile.getConfig().set("players", new ArrayList<>());
            usersFile.save();
        }*/

        if (ess == null) {
            logger.log("§cEssentials plugin nicht gefunden, Plugin wird deaktiviert!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        logger.log("§aDies plugin wurde erfolgirech aktiviert.");

        getServer().getPluginManager().registerEvents(new EssentialsHome(this), this);
        getServer().getPluginManager().registerEvents(new AfkCheck(this), this);
    }

    public Essentials getEss() {
        return ess;
    }

    public FancyLogger getFanyLogger() {
        return logger;
    }

    public CustomYAML getUsersFile() {
        return usersFile;
    }


}