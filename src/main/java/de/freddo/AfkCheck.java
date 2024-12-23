package de.freddo;

import com.earth2me.essentials.Essentials;
import net.ess3.api.IUser;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AfkCheck implements Listener {

    private Main plugin;

    private final Map<UUID, Long> lastActivity = new HashMap<>();
    private final long afkTimeout;

    public AfkCheck(Main plugin) {
        this.plugin = plugin;

        afkTimeout = (int) plugin.getEss().getSettings().getAutoAfk() * 1000L;

        startAfkCheckTask();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!(boolean) plugin.getConfig().get("afk.events.onPlayerMove")) {
            return;
        }
        if (!event.getFrom().toVector().equals(event.getTo().toVector())) {
            updateLastActivity(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (!(boolean) plugin.getConfig().get("afk.events.onPlayerChat")) {
            return;
        }
        updateLastActivity(event.getPlayer());
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!(boolean) plugin.getConfig().get("afk.events.onPlayerInteract")) {
            return;
        }
        updateLastActivity(event.getPlayer());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (!(boolean) plugin.getConfig().get("afk.events.onPlayerBreak")) {
            return;
        }
        updateLastActivity(event.getPlayer());
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!(boolean) plugin.getConfig().get("afk.events.onPlayerPlace")) {
            return;
        }
        updateLastActivity(event.getPlayer());
    }

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (!(boolean) plugin.getConfig().get("afk.events.onPlayerCommand")) {
            return;
        }
        updateLastActivity(event.getPlayer());
    }

    private void updateLastActivity(Player player) {
        lastActivity.put(player.getUniqueId(), System.currentTimeMillis());
    }

    private void startAfkCheckTask() {
        new BukkitRunnable() {
            final Essentials ess = plugin.getEss();
            @Override
            public void run() {
                long currenTime = System.currentTimeMillis();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    IUser iUser = ess.getUser(player.getUniqueId());
                    if (!iUser.isAfk()) {;
                        lastActivity.putIfAbsent(player.getUniqueId(), currenTime);
                        final long lastTime = lastActivity.get(player.getUniqueId());
                        if ((currenTime - lastTime) >= afkTimeout) {
                            player.performCommand("afk");
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20*60);
    }
}
