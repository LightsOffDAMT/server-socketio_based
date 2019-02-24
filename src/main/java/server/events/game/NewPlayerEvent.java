package server.events.game;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.engine.CycleRunner;
import server.engine.objects.Dude;
import server.engine.objects.actions.UpdateDudeAction;

@Component
public class NewPlayerEvent implements DataListener<Dude> {
    @Autowired
    CycleRunner cycleRunner;


    @Override
    public void onData(SocketIOClient socketIOClient, Dude s, AckRequest ackRequest) throws Exception {
        cycleRunner.submitDudeUpdate( new UpdateDudeAction(socketIOClient.getSessionId().toString(), s) );
    }
}
