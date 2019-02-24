package server.security;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConnectedUsers {
    private ConcurrentHashMap<String, Boolean> connectedUsers;

    public void addUser(String token){
        connectedUsers.put(token, false);
    }

    public void deleteUser(String token){
        connectedUsers.remove(token);
    }

    public boolean isConnected(String token){
        return connectedUsers.get(token);
    }

    public boolean contains(String token){
        return connectedUsers.containsKey(token);
    }

    public void connect(String token){
        connectedUsers.replace(token, true);
    }
}
