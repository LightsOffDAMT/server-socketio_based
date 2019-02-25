package server.engine.objects.actions;

import server.engine.objects.Bullet;
import server.engine.objects.Dude;

public class FireBulletAction {
    private Dude dude;
    private Bullet bullet;

    public FireBulletAction(){}

    public FireBulletAction(Dude dude, Bullet bullet){
        this.dude = dude;
        this.bullet = bullet;
    }

    public FireBulletAction setDude(Dude dude) {
        this.dude = dude;
        return this;
    }

    public FireBulletAction setBullet(Bullet bullet) {
        this.bullet = bullet;
        return this;
    }

    public Dude getDude() {
        return dude;
    }

    public Bullet getBullet() {
        return bullet;
    }
}
