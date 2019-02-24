package server.events.user;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.security.ConnectedUsers;

@Component
public class SocketAuthorizeEvent implements DataListener<String> {
    @Autowired
    ConnectedUsers connectedUsers;

    @Override
    public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
        if(connectedUsers.contains(s))
            socketIOClient.sendEvent("socket_authorized");
        else
            socketIOClient.disconnect();
    }
}
