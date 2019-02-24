package server.engine.objects;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import java.util.function.Function;

public class Bullet {
    private String id;
    private long bufferLifetime;
    private long lifetime;
    private Point point;
    private float dx;
    private float dy;
    private Function<Point, Boolean> constraint = (point1 -> lifetime != 0);


    public Bullet(long lifetime, float dx, float dy){
        this.dx = dx;
        this.dy = dy;
        bufferLifetime = lifetime;
        this.lifetime = lifetime;
    }

    public Bullet refresh(){
        this.lifetime = this.bufferLifetime;
        return withPoint(0, 0);
    }

    public Bullet refresh(long lifetime){
        bufferLifetime = lifetime;
        this.lifetime = lifetime;
        return withPoint(0, 0);
    }

    public Bullet withId(String id){
        this.id = id;
        return this;
    }

    public Bullet withPoint(float x, float y){
        this.point = Geometries.point(x, y);
        return this;
    }

    public Point point(){
        return point;
    }

    public String id(){
        return id;
    }

    public boolean move(){
        point = Geometries.point(point.x() + dx, point.y() + dy);
        lifetime--;
        return (constraint.apply(point));
    }

    public void setMoveWithAlpha(float speed, float alpha){
        dx = (float)(speed*Math.cos(alpha));
        dy = (float)(speed*Math.sin(alpha));
    }
}
