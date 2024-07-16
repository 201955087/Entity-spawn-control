package me.air_bottle.entitycontrol;

import me.air_bottle.entitycontrol.Commands.CommandsController;
import me.air_bottle.entitycontrol.Control.ControlEvent;
import me.air_bottle.entitycontrol.Item.ItemsController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EntityControl extends JavaPlugin {
    public static EntityControl instance;
    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getCommandMap().register("magicwand", new CommandsController("magicwand"));
        Bukkit.getPluginManager().registerEvents(new ControlEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
