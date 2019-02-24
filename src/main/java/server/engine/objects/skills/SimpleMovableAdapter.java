package server.engine.objects.skills;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class SimpleMovableAdapter implements Movable {
    @JsonIgnore
    ConcurrentHashMap<String, Function<Point, Boolean>> constraints = new ConcurrentHashMap<>();
    protected Point point;

    @Override
    public boolean addConstraint(String constraintName, Function<Point, Boolean> constraint) {
        constraints.put(constraintName, constraint);
        return true;
    }

    @Override
    public boolean deleteConstraint(String constraintName) {
        return constraints.remove(constraintName) != null;
    }

    @Override
    public boolean moveToPoint(Point point) {
        if(!checkConstraints(constraints, point))
            return false;
        this.point = point;
        return true;
    }

    @Override
    public boolean moveByDiff(float dx, float dy) {
        if(point == null)
            throw new NullPointerException("Point undefined");
        Point newPoint = Geometries.point(point.x() + dx, point.y() + dy);
        return moveToPoint( newPoint );
    }

    @Override
    public boolean moveBySpeed(float speed, float alpha) {
        if(point == null)
            throw new NullPointerException("Point undefined");
        float dx = (float)(speed*Math.cos(alpha));
        float dy = (float)(speed*Math.sin(alpha));
        return moveByDiff(dx, dy);
    }
}
