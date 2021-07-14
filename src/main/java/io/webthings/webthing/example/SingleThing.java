package io.webthings.webthing.example;


import io.webthings.webthing.affordances.ActionAffordance;
import io.webthings.webthing.affordances.PropertyAffordance;
import io.webthings.webthing.common.ThingData;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Form;
import io.webthings.webthing.server.Action;
import io.webthings.webthing.server.ActionHandler;
import io.webthings.webthing.server.ThingObject;
import io.webthings.webthing.server.ThingServer;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SingleThing {
    public static class ToggleHandler extends ActionHandler {
        private boolean  __state = false;
        public void run() {
            System.out.println("Current state : " + __state);
            __state = !__state;
            System.out.println("New  state : " + __state);
        }
    }
    public static List<ThingObject> makeThing() throws URISyntaxException,WoTException{
        final List<ThingObject> ret = new ArrayList<>();
        final ThingData   td = new ThingData();
        final PropertyAffordance pa_name = new PropertyAffordance();
        pa_name.setDefaultTitle("Name");
        pa_name.setDefaultDescription("The real name");
        pa_name.addForm(new Form("/single/name"));
        td.addProperty("name", pa_name);
        
        final ActionAffordance aa_toggle = new ActionAffordance();
        aa_toggle.setDefaultTitle("Toggle");
        aa_toggle.setDefaultDescription("Toggle Action");
        aa_toggle.addForm(new Form("/single/toggle"));
        td.addAction("toggle", aa_toggle);
        
        
        
        final ThingObject to = new ThingObject(td);
        to.addAction(new Action("toggle",aa_toggle,ToggleHandler.class));
        
        ret.add(to);
        return ret;
    }

    public static void main(String[] args) {
        
        

        try {
            // If adding more than one thing, use MultipleThings() with a name.
            // In the single thing case, the thing's name will be broadcast.
            final List<ThingObject> thing = makeThing();
            final ThingServer server = new ThingServer(thing, 8888);

            Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

            server.start(false);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.exit(1);
        }
    }
/*
    public static class OverheatedEvent extends Event {
        public OverheatedEvent(Thing thing, int data) {
            super(thing, "overheated", data);
        }
    }

    public static class FadeAction extends Action {
        public FadeAction(Thing thing, JSONObject input) {
            super(UUID.randomUUID().toString(), thing, "fade", input);
        }

        @Override
        public void performAction() {
            Thing thing = this.getThing();
            JSONObject input = this.getInput();
            try {
                Thread.sleep(input.getInt("duration"));
            } catch (InterruptedException e) {
                // pass
            }

            try {
                thing.setProperty("brightness", input.getInt("brightness"));
                thing.addEvent(new OverheatedEvent(thing, 102));
            } catch (PropertyError e) {
                // pass
            }
        }
    }
*/
}
