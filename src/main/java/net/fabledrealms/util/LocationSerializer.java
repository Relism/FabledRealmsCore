package net.fabledrealms.util;

import org.bukkit.Location;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class LocationSerializer {

    public static String serialize(Location location) {
        String serialize;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(byteArrayOutputStream);
            bukkitObjectOutputStream.writeObject(location);
            bukkitObjectOutputStream.flush();
            byte[] serializedObject = byteArrayOutputStream.toByteArray();
            serialize = new String(Base64.getEncoder().encode(serializedObject));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return serialize;
    }

    public static Location deSerialize(String stringLocation) {
        byte[] deSerializedObject = Base64.getDecoder().decode(stringLocation);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(deSerializedObject);
        try {
            BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(byteArrayInputStream);
            return (Location) bukkitObjectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}