package server;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import server.engine.CycleRunner;
import server.engine.objects.Dude;
import server.events.game.NewPlayerEvent;
import server.events.user.AuthorizationEvent;
import server.events.user.ConnectEvent;
import server.events.user.SocketAuthorizeEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
class Application {
    @Autowired
    AuthorizationEvent authorizationEvent;
    @Autowired
    ConnectEvent connectEvent;
    @Autowired
    NewPlayerEvent newPlayerEvent;
    @Autowired
    SocketAuthorizeEvent socketAuthorizeEvent;

    @Bean
    Configuration configuration(){
        Configuration configuration = new Configuration();
        configuration.setHostname("localhost");
        configuration.setPort(8888);
        configuration.setAuthorizationListener(authorizationEvent);
        return configuration;
    }

    @Bean
    SocketIOServer server(){
        SocketIOServer server = new SocketIOServer(configuration());
        return server;
    }

    @Bean
    CycleRunner cycleRunner(){
        return new CycleRunner();
    }

    @Bean
    @Lazy
    ScheduledFuture mainCycle(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        return executorService.scheduleAtFixedRate(cycleRunner(), 0, 40, TimeUnit.MILLISECONDS);
    }

    @Bean
    Boolean listeners(){
        SocketIOServer server = server();
        server.addConnectListener(connectEvent);
        server.addEventListener("new_player", Dude.class, newPlayerEvent);
        server.addEventListener("authorize", String.class, socketAuthorizeEvent);
        return true;
    }

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        SocketIOServer server = context.getBean("server", SocketIOServer.class);
        while (true);
    }
}
