package server.engine.objects;

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
        moveToPoint(new Point(x, y));
        return this;
    }

    public Point getPoint(){
        return super.point;
    }

    public boolean hit(){
        return --hp > 0;
    }

    public boolean cast(){
        return --mana > 0;
    }

    public String getId() {
        return id;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public long getHp() {
        return hp;
    }

    public long getMana() {
        return mana;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHp(long hp) {
        this.hp = hp;
    }

    public void setMana(long mana) {
        this.mana = mana;
    }
}
