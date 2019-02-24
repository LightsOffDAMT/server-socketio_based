package server.events.user;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import server.security.ConnectedUsers;


/*Rework component, change connection check, now it uses wrong check class*/
@Component
public class AuthorizationEvent implements AuthorizationListener {
    private ConnectedUsers connectedUsers;

    @Autowired
    public void setConnectedUsers(ConnectedUsers connectedUsers) {
        this.connectedUsers = connectedUsers;
    }

    public boolean isAuthorized(HandshakeData handshakeData) {
        return connectedUsers.isConnected(
                handshakeData.getHttpHeaders().get("SESSION_ID")
        );
    }
}
