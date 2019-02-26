package server.events.game;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.engine.CycleRunner;
import server.engine.objects.Bullet;
import server.engine.objects.Dude;
import server.engine.objects.actions.FireBulletAction;
import server.storage.DudesStorage;

@Component
public class FireBulletEvent implements DataListener<Bullet> {
    @Autowired
    CycleRunner cycleRunner;
    @Autowired
    DudesStorage dudesStorage;

    @Override
    public void onData(SocketIOClient socketIOClient, Bullet bullet, AckRequest ackRequest) throws Exception {
        String bulletId = bullet.getId();
        String socketId = socketIOClient.getSessionId().toString();
        Dude dude = dudesStorage.getDude(socketId);
        System.out.println("recieved bullet from: " + bullet.getId());
        if(bulletId.equals( dude.getId() ))
            cycleRunner.submitFireEvent(new FireBulletAction()
                .setBullet( bullet )
                .setDude( dude ));
    }
}
