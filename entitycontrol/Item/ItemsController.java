package me.air_bottle.entitycontrol.Item;

import com.google.common.util.concurrent.AtomicDouble;
import dev.lone.itemsadder.api.CustomStack;
import me.air_bottle.entitycontrol.EntityControl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Transformation;

import java.util.HashMap;
import java.util.Map;


public class ItemsController {
    public ItemStack getWand() {
        CustomStack wand = CustomStack.getInstance("wand");
        ItemStack wandStack;
        if (wand != null) {
            wandStack = wand.getItemStack();
        } else {
            return null;
        }
        return wandStack;
    }
    public ItemStack getCircle() {
        CustomStack circle = CustomStack.getInstance("magic");
        ItemStack circleStack;
        if (circle != null) {
            circleStack = circle.getItemStack();
        } else {
            Bukkit.getLogger().warning("아이템이 존재하지 않음");
            return null;
        }
        return circleStack;
    }
    public void createCircle(Location location) {
        World world = location.getWorld();
        ItemDisplay itemdisplay = world.spawn(location, ItemDisplay.class, display -> {
            display.setItemStack(getCircle());
            Transformation transformation = display.getTransformation();
            transformation.getScale().mul(4.f);
            display.setTransformation(transformation);
            Bukkit.getLogger().info("아이템 소환됨?");
        });
        AtomicDouble ticks = new AtomicDouble();
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(EntityControl.instance, () -> {
            if (ticks.get() >= 60) {
                itemdisplay.remove();
            } else {
                Location itemLocation = itemdisplay.getLocation();
                itemLocation.setYaw(itemLocation.getYaw() + 12);
                itemdisplay.teleport(itemLocation);
                ticks.getAndAdd(1.0);
            }
        }, 0, 1);
    }
}
