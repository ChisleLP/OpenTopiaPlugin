package de.freddo.utiliy;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class PlayerMessage {


    public void sendClickableMessage(Player player, String text, ClickEvent.Action clickEvent, String value) {
        // Create a TextComponent for the message
        TextComponent message = new TextComponent(text);

        // Set the click event to run a command when clicked (e.g., "/help")
        message.setClickEvent(new ClickEvent(clickEvent, value));

        // Optionally, you can also set hover text for when the player hovers over the text
        message.setHoverEvent(new net.md_5.bungee.api.chat.HoverEvent(
                net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT,
                new net.md_5.bungee.api.chat.ComponentBuilder("Drücke hier, um den Befehl zu kopieren").create()
        ));

        // Send the message to the player
        player.spigot().sendMessage(message);
    }

}
