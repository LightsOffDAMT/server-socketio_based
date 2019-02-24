package server.events.user;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.ConnectListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.security.ConnectedUsers;

@Component
public class ConnectEvent implements ConnectListener {
    @Autowired
    ConnectedUsers connectedUsers;

    @Override
    public void onConnect(SocketIOClient socketIOClient) {
        socketIOClient.sendEvent("connection_success", socketIOClient.getSessionId());
        connectedUsers.connect( socketIOClient.getSessionId().toString() );
    }

}
