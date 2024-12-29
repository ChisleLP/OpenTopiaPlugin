package de.freddo.commands;

import de.freddo.Main;
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

        if (args.length == 0) {
            ItemStack itemHand = player.getInventory().getItemInMainHand();
            if (itemHand == null || itemHand.getType() == Material.AIR) {
                player.sendMessage("Item in hand halten!");
                return true;
            }


            main.getPlayerMessage().sendClickableMessage(player, "Füge erst [Pipe] in 2.nd zeile", ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 2 [Pipe]");
            main.getPlayerMessage().sendClickableMessage(player, "Minecraft-ID: "+itemHand.getType().getKey(), ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 3 "+itemHand.getType().getKey());
            String result = main.getCraftbookVariables().findVariableByValue(itemHand.getType().getKey().toString());
            if (!result.contains("0")) {
                main.getPlayerMessage().sendClickableMessage(player, "ID: %"+result+"%", ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 3 %"+result+"%");
            }

            return true;

        } else {
            String searchItem = String.join(" ", args);
            Material material = Material.matchMaterial(searchItem);
            if (material == null) {
                player.sendMessage("Ain't english items, looking for german");
                Material material1 = main.getGermanLanguage().getMaterialByGermanName(searchItem);
                if (material1 == null) {
                    player.sendMessage("Sorry, no items found.");
                    return true;
                } else {
                    main.getPlayerMessage().sendClickableMessage(player, "Füge erst [Pipe] in 2.nd zeile", ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 2 [Pipe]");
                    main.getPlayerMessage().sendClickableMessage(player, "Minecraft-ID: "+material1.getKey(), ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 3 "+material1.getKey());
                    String result = main.getCraftbookVariables().findVariableByValue(material1.getKey().toString());
                    if (!result.contains("0")) {
                        main.getPlayerMessage().sendClickableMessage(player, "ID: %"+result+"%", ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 3 %"+result+"%");
                    }
                    return true;
                }
            } else {
                main.getPlayerMessage().sendClickableMessage(player, "Füge erst [Pipe] in 2.nd zeile", ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 2 [Pipe]");
                main.getPlayerMessage().sendClickableMessage(player, "Minecraft-ID: "+material.getKey(), ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 3 "+material.getKey());
                String result = main.getCraftbookVariables().findVariableByValue(material.getKey().toString());
                if (!result.contains("0")) {
                    main.getPlayerMessage().sendClickableMessage(player, "ID: %"+result+"%", ClickEvent.Action.COPY_TO_CLIPBOARD, "/sign edit 3 %"+result+"%");
                }
                return true;
            }
        }


    }
}
