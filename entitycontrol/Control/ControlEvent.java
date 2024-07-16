package me.air_bottle.entitycontrol.Control;

import me.air_bottle.entitycontrol.Item.ItemsController;
import me.air_bottle.entitycontrol.MyEntity.EntityController;
import me.air_bottle.entitycontrol.Sound.SoundCreator;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ControlEvent implements ControlMenu, Listener {
    ItemsController itemscontroller = new ItemsController();
    EntityController entitycontroller = new EntityController();
    SoundCreator soundcreator = new SoundCreator();

    private static final double MAX_DISTANCE = 30.0;

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item != null && item.equals(itemscontroller.getWand())) {
            Action action = event.getAction();
            Block targetBlock = player.getTargetBlockExact(10);
            LivingEntity targetEntity = (LivingEntity) player.getTargetEntity((int)MAX_DISTANCE);
            Location targetLocation = targetBlock != null ? targetBlock.getLocation().add(0, 1, 0) : null;
            if(action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
                if(player.isSneaking()) {
                    player.sendMessage("shift + 좌클릭");
                    for(LivingEntity entity : EntityController.entityMap.keySet()) {
                        if(entity.getType() == EntityType.VINDICATOR) {
                            entitycontroller.surroundEntity(entity, targetEntity);
                        } else if(targetEntity == null) {
                            player.sendMessage("잘못된 대상 지정");
                        }
                    }
                } else {
                    player.sendMessage("그냥 좌클릭");
                    for(LivingEntity entity : EntityController.entityMap.keySet()) {
                        if(entity.getType() == EntityType.VINDICATOR) {
                            entitycontroller.attackEntity(entity,targetEntity);
                        } else if(targetEntity == null) {
                            player.sendMessage("잘못된 대상 지정");
                        }
                    }
                }
            }
            if(action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR) {
                if(player.isSneaking()) {
                    if(targetLocation == null) {
                        Location airLocation = player.getEyeLocation().add(player.getLocation().getDirection().multiply(5));
                            entitycontroller.spawningEntity(airLocation);
                            itemscontroller.createCircle(airLocation);
                            soundcreator.spawnSound(player, airLocation);
                    } else {
                            entitycontroller.spawningEntity(targetLocation);
                            itemscontroller.createCircle(targetLocation);
                            soundcreator.spawnSound(player, targetLocation);
                    }
                    player.sendMessage("shift + 우클릭");
                } else {
                    player.sendMessage("그냥 우클릭");
                    for(LivingEntity entity : EntityController.entityMap.keySet()) {
                        if(entity.getType() == EntityType.VINDICATOR) {
                            entitycontroller.moveEntity(entity, targetLocation);
                        } else if(targetLocation == null) {
                            player.sendMessage("잘못된 이동 위치 지정");
                        }
                    }
                }
            }
        }
    }
}
