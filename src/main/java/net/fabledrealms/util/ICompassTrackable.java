package net.fabledrealms.util;

import org.bukkit.util.Vector;

public interface ICompassTrackable {

    /**
     *
     * @param vector The origin location
     * @return String based on distance
     */
    String getTrackerIcon(Vector vector);

    /**
     *
     * @param vector The origin location
     * @return The squared distance between the origin and this tracker
     */
    double getDistanceSquared(Vector vector);

    /**
     *
     * @param vector The origin location
     * @return Angle in degrees between the origin and this tracker
     */
    float getAngle(Vector vector);

}
