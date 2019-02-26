package server.engine;

import com.corundumstudio.socketio.SocketIOServer;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import server.engine.objects.Bullet;
import server.engine.objects.Dude;
import server.engine.objects.actions.FireBulletAction;
import server.engine.objects.actions.UpdateDudeAction;
import server.storage.DudesStorage;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CycleRunner implements Runnable{
    @Autowired
    private SocketIOServer server;

    @Autowired
    DudesStorage dudesStorage;
    private ConcurrentLinkedQueue<UpdateDudeAction> updateDudeActions = new ConcurrentLinkedQueue<>();

    private ConcurrentHashMap<String, Bullet> bullets = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<FireBulletAction> fireBulletActions = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Bullet> releaseBulletActions = new ConcurrentLinkedQueue<>();
    private ArrayList<Bullet> currentBullets = new ArrayList<>();

    @Override
    public void run() {
        boolean dudesUpdated = !updateDudeActions.isEmpty();
        while(!updateDudeActions.isEmpty()){
            UpdateDudeAction updateDudeAction = updateDudeActions.poll();
            dudesStorage.putDude(updateDudeAction.target, updateDudeAction.dude);
        }

        currentBullets.clear();
        while (!fireBulletActions.isEmpty()){
            FireBulletAction fireBulletAction = fireBulletActions.poll();
            //Here we can check some conditions;
            System.out.println(new Gson().toJson(fireBulletAction));
            Dude dude = dudesStorage.getDudeWithId(fireBulletAction.getBullet().getId());
            if(dude != null){
                currentBullets.add(fireBulletAction.getBullet());
                bullets.put(fireBulletAction.getDude().getId(), fireBulletAction.getBullet());
            }
        }

        if(currentBullets.size() > 0)
            server.getBroadcastOperations().sendEvent("update_bullets", currentBullets);

        if(dudesUpdated)
            server.getBroadcastOperations().sendEvent("dudes_update", dudesStorage.values() );
    }

    public void submitDudeUpdate(UpdateDudeAction action){
        updateDudeActions.offer(action);
    }

    public void removeDude(String token){
        dudesStorage.removeDude(token);
    }

    public void submitFireEvent(FireBulletAction action){
        fireBulletActions.offer(action);
    }

    public void submitFireReleaseEvent(Bullet toRelease){
        releaseBulletActions.offer(toRelease);
    }

    @Autowired
    public void setServer(SocketIOServer server) {
        this.server = server;
    }
}
