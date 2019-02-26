package server.events.game;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.engine.CycleRunner;
import server.engine.objects.Bullet;

@Component
public class ReleaseBulletEvent implements DataListener<Bullet> {
    @Autowired
    CycleRunner cycleRunner;

    //TODO replace Bullet with BulletReleaseAction
    @Override
    public void onData(SocketIOClient socketIOClient, Bullet bullet, AckRequest ackRequest) throws Exception {
        cycleRunner.submitFireReleaseEvent(bullet);
    }
}
