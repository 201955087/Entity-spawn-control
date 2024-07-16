package me.air_bottle.entitycontrol.Sound;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundCreator {
    private final ProtocolManager protocolmanager;

    public SoundCreator() {
        this.protocolmanager = ProtocolLibrary.getProtocolManager();
    }
    public void spawnSound(Player player,Location location) {
        PacketContainer packet = protocolmanager.
                createPacket(PacketType.Play.Server.NAMED_SOUND_EFFECT);

        packet.getSoundCategories().write(0, EnumWrappers.SoundCategory.MASTER);
        packet.getSoundEffects().write(0, Sound.BLOCK_PORTAL_TRAVEL);
        packet.getIntegers().write(0, (int) (location.getX() * 8.0))
                .write(1, (int) (location.getY() * 8.0))
                .write(2, (int) (location.getZ() * 8.0));
        packet.getFloat().write(0, 0.2f).write(1, 1.0f);
        packet.getLongs().write(0, 0L);

        protocolmanager.sendServerPacket(player, packet);
    }
}
