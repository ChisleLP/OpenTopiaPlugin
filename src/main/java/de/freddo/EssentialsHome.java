package de.freddo;

import com.earth2me.essentials.Essentials;
import de.freddo.utiliy.ChatUtils;
import net.ess3.api.IUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class EssentialsHome implements Listener {

    private final Main plugin;

    public EssentialsHome(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().toLowerCase();

        final Essentials ess = plugin.getEss();

        if (command.startsWith("/sethome")) {
            String[] commandParts = command.split(" ");
            String homeName = (commandParts.length > 1) ? commandParts[1] : "";
            Player player = event.getPlayer();
            IUser iUser = ess.getUser(player);
            List<String> homes = iUser.getHomes();

            if (homes.contains(homeName)){
                event.setCancelled(true);
                String homeMessage = ChatUtils.colorize(plugin.getConfig().getString("home.moved"));
                player.sendMessage(homeMessage.replace("%home%", homeName));

                iUser.setHome(homeName, player.getLocation());
            }
        }
    }
}
