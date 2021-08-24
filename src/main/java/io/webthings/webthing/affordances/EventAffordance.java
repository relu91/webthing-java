package io.webthings.webthing.affordances;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Operation;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class EventAffordance extends InteractionAffordance {
    private DataSchema subscription;
    private DataSchema data;
    private DataSchema cancellation;
    private final static Set<Operation.id> ALLOWED_OPS =
            new TreeSet<>(Arrays.asList(Operation.id.subscribeevent,
                                        Operation.id.unsubscribeevent));

    public EventAffordance() {
        super(ALLOWED_OPS);
    }

    public EventAffordance(DataSchema s, DataSchema d, DataSchema c) {
        super(ALLOWED_OPS);
        data = d;
        subscription = s;
        cancellation = c;
    }

    public void setSubscription(DataSchema d) {
        subscription = d;
    }

    public DataSchema getSubscription() {
        return subscription;
    }

    public void setData(DataSchema d) {
        data = d;
    }

    public DataSchema getData() {
        return data;
    }

    public void setCancellation(DataSchema d) {
        cancellation = d;
    }

    public DataSchema getCancellation() {
        return cancellation;
    }

    @Override
    public JSONObject asJSON() {
        final JSONObject ret = super.asJSON();
        JSONEntityHelpers.addJSONEntity("data", data, ret);
        JSONEntityHelpers.addJSONEntity("cancellation", cancellation, ret);
        JSONEntityHelpers.addJSONEntity("subscription", subscription, ret);
        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        data = JSONEntityHelpers.readEntity(o, "data", DataSchema.class);
        cancellation = JSONEntityHelpers.readEntity(o,
                                                    "cancellation",
                                                    DataSchema.class);
        subscription = JSONEntityHelpers.readEntity(o,
                                                    "subscription",
                                                    DataSchema.class);
        super.fromJSON(o);

        return this;
    }
}
