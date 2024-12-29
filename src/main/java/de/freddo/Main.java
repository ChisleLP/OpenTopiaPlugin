package de.freddo;

import com.earth2me.essentials.Essentials;
import de.freddo.commands.OpenTopia;
import de.freddo.commands.PipeID;
import de.freddo.utiliy.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private FancyLogger logger;
    private CustomYAML usersFile;
    private LocalizationManager germanLanguage;
    private CraftbookVariables craftbookVariables;
    private PlayerMessage playerMessage;
    Essentials ess;

    @Override
    public void onEnable() {
        super.onEnable();
        saveDefaultConfig();
        logger = new FancyLogger(this);
        ess = (Essentials) getServer().getPluginManager().getPlugin("Essentials");

        germanLanguage = new LocalizationManager();
        craftbookVariables = new CraftbookVariables();
        playerMessage = new PlayerMessage();



        if (ess == null) {
            logger.log("§cEssentials plugin nicht gefunden, Plugin wird deaktiviert!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        logger.log("§aDies plugin wurde erfolgirech aktiviert.");

        getServer().getPluginManager().registerEvents(new EssentialsHome(this), this);
        getServer().getPluginManager().registerEvents(new AfkCheck(this), this);

        OpenTopia OT = new OpenTopia(this);
        getServer().getPluginManager().registerEvents(OT, this);

        PipeID pipeID = new PipeID(this);


        getCommand("pipeid").setExecutor(pipeID);
        getCommand("otsign").setExecutor(OT);
        getCommand("OpenTopia").setExecutor(OT);

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

    public LocalizationManager getGermanLanguage() {
        return germanLanguage;
    }

    public CraftbookVariables getCraftbookVariables() {
        return craftbookVariables;
    }

    public PlayerMessage getPlayerMessage() {
        return playerMessage;
    }


}