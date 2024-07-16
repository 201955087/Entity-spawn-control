package me.air_bottle.entitycontrol.MyEntity;

import me.air_bottle.entitycontrol.EntityControl;
import me.air_bottle.entitycontrol.Item.ItemsController;
import org.bukkit.*;
import org.bukkit.entity.*;

import java.util.HashMap;
import java.util.Map;

public class EntityController implements EntityMenu{
    public static Map<Vindicator, Integer> entityMap = new HashMap<>();
    public static Map<Vindicator, Integer> taskMap = new HashMap<>();
    private static int entityCount = 1;
    @Override
    public void spawningEntity(Location location) {
            ItemsController itemscontroller = new ItemsController();
            World world = location.getWorld();
            Bukkit.getScheduler().scheduleSyncDelayedTask(EntityControl.instance, () -> {
                Vindicator vindicator = world.spawn(location, Vindicator.class, entity -> {
                    entity.setCustomName(ChatColor.GOLD + "소환수" + entityCount);
                    entity.setAI(false);
/*            Bukkit.getScheduler().scheduleSyncRepeatingTask(EntityControl.instance, () -> {
                Block blockUnderWolf = entity.getLocation().subtract(0, 1, 0).getBlock();
                if(blockUnderWolf.getType() != Material.AIR) {
                    entity.setAI(false);
                    Bukkit.getScheduler().cancelTasks(EntityControl.instance);
                }
            },0, 1);*/
                });
                entityMap.put(vindicator, entityCount);
                entityCount++;
            }, 50);
    }

    @Override
    public void moveEntity(LivingEntity spawnedEntity, Location targetLocation) {
        cancelDoingTask(spawnedEntity);
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(EntityControl.instance, () -> {
            for(Vindicator vindicator : entityMap.keySet()) {
                vindicator.setAI(true);
                vindicator.getPathfinder().moveTo(targetLocation);
                if (vindicator.getLocation().distance(targetLocation) < 1) {
                    vindicator.setAI(false);
                    Bukkit.getScheduler().cancelTasks(EntityControl.instance);
                }
            }
            spawnedEntity.setAI(false);
        },0, 20);
        taskMap.put((Vindicator) spawnedEntity, taskId);
    }

    @Override
    public void attackEntity(LivingEntity spawnedEntity, LivingEntity target) {
        cancelDoingTask(spawnedEntity);
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(EntityControl.instance, () -> {
            for(Vindicator vindicator : entityMap.keySet()) {
                vindicator.setAI(true);
                vindicator.setTarget(target);
            }
            if(target.isDead()) {
                spawnedEntity.setAI(false);
            }
        }, 0, 20);
        taskMap.put((Vindicator) spawnedEntity, taskId);
    }

    @Override
    public void surroundEntity(LivingEntity spawnedEntity, LivingEntity target) {
        cancelDoingTask(spawnedEntity);
        int wolfCount = entityMap.size();
        double angleEach = 360.0 / wolfCount;
        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(EntityControl.instance, () -> {
            int i = 0;
            for(Vindicator vindicator : entityMap.keySet()) {
                vindicator.setAI(true);
                double angle = Math.toRadians(angleEach * i);
                double x = target.getLocation().getX() + 3 * Math.cos(angle);
                double z = target.getLocation().getZ() + 3 * Math.sin(angle);
                Location position = new Location(target.getWorld(), x, target.getLocation().getY(), z);
                vindicator.getPathfinder().moveTo(position);
                i++;
            }
        }, 0, 20);
        taskMap.put((Vindicator) spawnedEntity, taskId);
    }

    @Override
    public void cancelDoingTask(LivingEntity entity) {
        if(taskMap.containsKey((Vindicator) entity)) {
            int taskId = taskMap.get((Vindicator) entity);
            Bukkit.getScheduler().cancelTask(taskId);
        }
    }
}
