package me.air_bottle.entitycontrol.Commands;

import me.air_bottle.entitycontrol.Item.ItemsController;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandsController extends BukkitCommand {
    ItemsController itemscontroller = new ItemsController();
    public CommandsController(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("플레이어만 명령어 사용 가능");
            return true;
        }
        if(strings[0].equals("item")) {
            player.getInventory().addItem(itemscontroller.getWand());
        } else if (strings[0].equals("circle")) {
        }

        return false;
    }
}
