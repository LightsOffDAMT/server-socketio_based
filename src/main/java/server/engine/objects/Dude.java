package server.engine.objects;

import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import server.engine.objects.skills.Collidable;
import server.engine.objects.skills.Equipable;
import server.engine.objects.skills.Movable;
import server.engine.objects.skills.SimpleMovableAdapter;

public class Dude extends SimpleMovableAdapter
        implements Movable, Collidable, Equipable {
    private String id;
    private long hp;
    private long mana;
    private float angle;

    public Dude withId(String id){
        this.id = id;
        return this;
    }

    public Dude withPoint(float x, float y){
        moveToPoint(Geometries.point(x, y));
        return this;
    }

    public Point point(){
        return super.point;
    }

    public boolean hit(){
        return --hp > 0;
    }

    public boolean cast(){
        return --mana > 0;
    }

    public String id() {
        return id;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
