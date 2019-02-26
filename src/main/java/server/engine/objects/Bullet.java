package server.engine.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import server.engine.objects.skills.Movable;
import server.engine.objects.skills.SimpleMovableAdapter;

public class Bullet extends SimpleMovableAdapter
        implements Movable {
    private String id;
    @JsonIgnore
    private long bufferLifetime;
    private float angle;

    public Bullet(){}

    public Bullet withId(String id){
        this.id = id;
        return this;
    }

    public Point getPoint(){
        return super.point;
    }

    public String getId() {
        return id;
    }

    public Bullet setId(String id) {
        this.id = id;
        return this;
    }

    public long getBufferLifetime() {
        return bufferLifetime;
    }

    public Bullet setBufferLifetime(long bufferLifetime) {
        this.bufferLifetime = bufferLifetime;
        return this;
    }

    public float getAngle() {
        return angle;
    }

    public Bullet setAngle(float angle) {
        this.angle = angle;
        return this;
    }
}
