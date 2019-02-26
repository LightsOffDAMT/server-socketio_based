package server.storage;

import org.springframework.stereotype.Component;
import server.engine.objects.Dude;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DudesStorage {
    private ConcurrentHashMap<String, Dude> dudes = new ConcurrentHashMap<>();

    public Dude getDude(String id){
        return dudes.get(id);
    }

    public void putDude(String id, Dude dude){
        dudes.put(id, dude);
    }

    public boolean haveDude(String id){
        return dudes.containsKey(id);
    }

    public Dude getDudeWithId(String id){
        return dudes.values().stream()
                .filter(dude -> dude.getId().equals(id)).findFirst().get();
    }

    public void removeDude(String id){
        dudes.remove(id);
    }

    public Collection<Dude> values(){
        return dudes.values();
    }
}
