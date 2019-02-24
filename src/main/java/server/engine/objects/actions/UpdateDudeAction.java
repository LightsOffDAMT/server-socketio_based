package server.engine.objects.actions;

import server.engine.objects.Dude;

public class UpdateDudeAction {
    public final String target;
    public final Dude dude;

    public UpdateDudeAction(String target, Dude dude) {
        this.target = target;
        this.dude = dude;
    }
}
