package server.engine;

import com.corundumstudio.socketio.SocketIOServer;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import server.engine.objects.Bullet;
import server.engine.objects.Dude;
import server.engine.objects.actions.FireBulletAction;
import server.engine.objects.actions.UpdateDudeAction;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CycleRunner implements Runnable{
    @Autowired
    private SocketIOServer server;

    private ConcurrentHashMap<String, Dude> dudes = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<UpdateDudeAction> updateDudeActions = new ConcurrentLinkedQueue<>();

    private ConcurrentHashMap<String, Bullet> bullets = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<FireBulletAction> fireBulletActions = new ConcurrentLinkedQueue<>();
    private ArrayList< Pair<String, Bullet> > currentBullets = new ArrayList<>();

    @Override
    public void run() {
        boolean dudesUpdated = !updateDudeActions.isEmpty();
        while(!updateDudeActions.isEmpty()){
            UpdateDudeAction updateDudeAction = updateDudeActions.poll();
            dudes.put(updateDudeAction.target, updateDudeAction.dude);
        }

        currentBullets.clear();
        while (!fireBulletActions.isEmpty()){
            FireBulletAction fireBulletAction = fireBulletActions.poll();
            //Here we can check some conditions;
            currentBullets.add(new Pair<>(fireBulletAction.getDude().getId(), fireBulletAction.getBullet()));
        }

        if(currentBullets.size() > 0)
            server.getBroadcastOperations().sendEvent("update_bullets", currentBullets);

        if(dudesUpdated)
            server.getBroadcastOperations().sendEvent("dudes_update", dudes.values() );
    }

    public void submitDudeUpdate(UpdateDudeAction action){
        updateDudeActions.offer(action);
    }

    public void removeDude(String token){
        dudes.remove(token);
    }

    public void submitFireEvent(FireBulletAction action){
        fireBulletActions.offer(action);
    }

    @Autowired
    public void setServer(SocketIOServer server) {
        this.server = server;
    }
}
