package server.events.game;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import org.springframework.beans.factory.annotation.Autowired;
import server.engine.CycleRunner;
import server.engine.objects.actions.FireBulletAction;

public class FireBulletEvent implements DataListener<FireBulletAction> {
    @Autowired
    CycleRunner cycleRunner;

    @Override
    public void onData(SocketIOClient socketIOClient, FireBulletAction fireBulletAction, AckRequest ackRequest) throws Exception {
        cycleRunner.submitFireEvent(fireBulletAction);
    }
}
