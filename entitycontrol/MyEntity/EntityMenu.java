package me.air_bottle.entitycontrol.MyEntity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface EntityMenu {
    void spawningEntity(Location location);
    void moveEntity(LivingEntity entity, Location targetLocation);
    void attackEntity(LivingEntity spawnedEntity, LivingEntity target);
    void surroundEntity(LivingEntity spawnedEntity, LivingEntity target);
    void cancelDoingTask(LivingEntity entity);
}
