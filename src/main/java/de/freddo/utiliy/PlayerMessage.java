package de.freddo.utiliy;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class PlayerMessage {


    public void sendClickableMessage(Player player, String text, ClickEvent.Action clickEvent, String value, String hoverText) {
        TextComponent message = new TextComponent(text);
        message.setClickEvent(new ClickEvent(clickEvent, value));
        message.setHoverEvent(new net.md_5.bungee.api.chat.HoverEvent(
                HoverEvent.Action.SHOW_TEXT,
                new net.md_5.bungee.api.chat.ComponentBuilder(hoverText).create()
        ));
        player.spigot().sendMessage(message);
    }

}
