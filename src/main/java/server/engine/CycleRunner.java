package server.engine;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import server.engine.objects.Dude;
import server.engine.objects.actions.UpdateDudeAction;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CycleRunner implements Runnable{
    private SocketIOServer server;
    private ConcurrentHashMap<String, Dude> dudes = new ConcurrentHashMap<>();
    private ConcurrentLinkedQueue<UpdateDudeAction> updateDudeActions = new ConcurrentLinkedQueue<>();

    @Override
    public void run() {
        boolean dudesUpdated = !updateDudeActions.isEmpty();
        while(!updateDudeActions.isEmpty()){
            UpdateDudeAction updateDudeAction = updateDudeActions.poll();
            dudes.replace(updateDudeAction.target, updateDudeAction.dude);
        }

        if(dudesUpdated)
            server.getBroadcastOperations().sendEvent("dudes_update", dudes.values() );
    }

    public void submitDudeUpdate(UpdateDudeAction action){
        updateDudeActions.offer(action);
    }

    @Autowired
    public void setServer(SocketIOServer server) {
        this.server = server;
    }
}
