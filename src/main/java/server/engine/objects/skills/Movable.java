package server.engine.objects.skills;


import server.engine.objects.Point;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public interface Movable {
    /**
     *
     * @param constraintName - constraint name
     * @param constraint - constraint check function
     * @return - true if constraint was successfully added
     */
    boolean addConstraint(String constraintName, Function<Point, Boolean> constraint);

    /**
     *
     * @param constraintName - @see {@link Movable#addConstraint(String, Function)}
     * @return - @see {@link Movable#addConstraint(String, Function)}
     */
    boolean deleteConstraint(String constraintName);

    /**
     *
     * @param point - destination getPoint
     * @return - true if {@link Movable} object has rights to make this move
     */
    boolean moveToPoint(Point point);

    /**
     *
     * @param dx - difference in x
     * @param dy - difference in y
     * @return - same as {@link Movable#moveToPoint(Point)} (uses it's internally)
     */
    boolean moveByDiff(float dx, float dy);

    /**
     * @param alpha - is polar angle
     * @param speed - is value of speed vector
     *
     * @return - same as {@link Movable#moveByDiff(float, float)} (uses it's internally)
     **/
    boolean moveBySpeed(float speed, float alpha);

    default boolean checkConstraints(ConcurrentHashMap<String, Function<Point, Boolean>> constraints, Point point){
        for (Function<Point, Boolean> constraint : constraints.values())
            if(!constraint.apply(point))
                return false;

        return true;
    }
}
