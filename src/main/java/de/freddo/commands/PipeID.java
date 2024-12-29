package de.freddo.commands;

import de.freddo.Main;
import de.freddo.utiliy.ChatUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PipeID implements CommandExecutor {

    private final Main main;

    public PipeID(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;
        Material material = args.length == 0 ? getMaterialFromHand(player) : getMaterialFromArgs(player, args);

        if (material == null) {
            String nofound = ChatUtils.colorize(main.getConfig().getString("openid.messages.noitems"));
            player.sendMessage(nofound);
            return true;
        }

        sendClickableMessages(player, material);

        return true;
    }

    private Material getMaterialFromHand(Player player) {
        ItemStack itemHand = player.getInventory().getItemInMainHand();
        if (itemHand == null || itemHand.getType() == Material.AIR) {
            String noItemInHand = ChatUtils.colorize(main.getConfig().getString("openid.messages.noItemInHand"));
            player.sendMessage(noItemInHand);
            return null;
        }
        return itemHand.getType();
    }

    private Material getMaterialFromArgs(Player player, String[] args) {
        String searchItem = String.join(" ", args);
        Material material = Material.matchMaterial(searchItem);

        if (material == null) {
            material = main.getGermanLanguage().getMaterialByGermanName(searchItem);
        }

        return material;
    }

    private void sendClickableMessages(Player player, Material material) {
        String materialKey = material.getKey().toString();
        String header = ChatUtils.colorize(main.getConfig().getString("openid.messages.header"));
        String minecraftIdMessage = ChatUtils.colorize(main.getConfig().getString("openid.messages.minecraft-id"));
        String idMessage = ChatUtils.colorize(main.getConfig().getString("openid.messages.id"));
        String notify = ChatUtils.colorize(main.getConfig().getString("openid.messages.notify"));
        String footer = ChatUtils.colorize(main.getConfig().getString("openid.messages.footer"));
        String hover = ChatUtils.colorize(main.getConfig().getString("openid.messages.hover"));

        player.sendMessage(header);

        main.getPlayerMessage().sendClickableMessage(
                player,
                minecraftIdMessage.replace("%id%", materialKey),
                ClickEvent.Action.RUN_COMMAND,
                "/opentopia sign " + materialKey,
                hover
        );

        String result = main.getCraftbookVariables().findVariableByValue(materialKey);
        if (!result.contains("0")) {
            main.getPlayerMessage().sendClickableMessage(
                    player,
                    idMessage.replace("%id%", "%"+result+"%"),
                    ClickEvent.Action.RUN_COMMAND,
                    "/opentopia sign %" + result + "%",
                    hover
            );
        }
        player.sendMessage(notify);
        player.sendMessage(footer);
    }


}
