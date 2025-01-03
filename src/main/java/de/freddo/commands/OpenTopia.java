package de.freddo.commands;

import de.freddo.Main;
import de.freddo.utiliy.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class OpenTopia implements CommandExecutor, Listener {

    private final Main main;
    private final HashMap<UUID, String> signCommand = new HashMap<>();

    public OpenTopia(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }
        Player player = (Player) commandSender;
        if (s.toLowerCase().equals("opentopia")) {
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("sign")) {
                    if (player.hasPermission(main.getConfig().getString("openid.permission"))) {
                        signCommand.put(player.getUniqueId(), String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
                        String signclick = ChatUtils.colorize(main.getConfig().getString("openid.messages.signclick"));
                        player.sendMessage(signclick);

                        Boolean enabled = main.getConfig().getBoolean("openid.sign.enabled");

                        if (enabled) {
                            Bukkit.getScheduler().runTaskLater(main, () -> {
                                if (signCommand.containsKey(player.getUniqueId())) {
                                    signCommand.remove(player.getUniqueId());
                                    String signlate = ChatUtils.colorize(main.getConfig().getString("openid.messages.signlate"));
                                    player.sendMessage(signlate);
                                }
                            }, main.getConfig().getInt("openid.sign.timeout", 5) * 20L);
                        }
                        return true;
                    } else {
                        String permission = ChatUtils.colorize(main.getConfig().getString("openid.messages.nopermission"));
                        player.sendMessage(permission);
                        return true;
                    }

                }
            }
        } else if (s.toLowerCase().equals("otsign")) {
            if (signCommand.containsKey(player.getUniqueId())) {
                signCommand.remove(player.getUniqueId());
                String otsignstop = ChatUtils.colorize(main.getConfig().getString("openid.messages.otsignstop"));
                player.sendMessage(otsignstop);
            }

        }
        return false;
    }

    @EventHandler
    public void OnSignInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK && block != null && block.getState() instanceof Sign) {
            if (signCommand.containsKey(player.getUniqueId())) {
                event.setCancelled(true);
                Sign sign = (Sign) block.getState();
                sign.setLine(1, "[Pipe]");
                sign.setLine(2, signCommand.get(player.getUniqueId()));
                sign.update();

                Boolean enabled = main.getConfig().getBoolean("openid.sign.enabled");
                if (enabled) {
                    signCommand.remove(player.getUniqueId());
                }

                String signregister = ChatUtils.colorize(main.getConfig().getString("openid.messages.signregister"));
                player.sendMessage(signregister);
            }
        }
    }
}
