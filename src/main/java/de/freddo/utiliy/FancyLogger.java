package de.freddo.utiliy;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FancyLogger {

    public FancyLogger(JavaPlugin plugin) {
    }

    private String getPrefix() {
        return ChatColor.GRAY+ "["+ChatColor.GOLD+"OpenTopia] §r";
    }

    public void log (String message) {
        Bukkit.getConsoleSender().sendMessage(getPrefix()+" "+message);
    }
}
