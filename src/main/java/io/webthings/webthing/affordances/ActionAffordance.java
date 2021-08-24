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
public class ActionAffordance extends InteractionAffordance {
    private DataSchema input;
    private DataSchema output;
    private Boolean safe;
    private Boolean idempotent;
    private final static Set<Operation.id> ALLOWED_OPS =
            new TreeSet<>(Arrays.asList(Operation.id.invokeaction));

    public ActionAffordance() {
        super(ALLOWED_OPS);
    }

    public DataSchema getInput() {
        return input;
    }

    public void setInput(DataSchema i) {
        input = i;
    }

    public DataSchema getOutput() {
        return output;
    }

    public void setOutput(DataSchema i) {
        output = i;
    }

    public void setSafe(boolean f) {
        safe = f;
    }

    public void setIdempotent(boolean f) {
        idempotent = f;
    }

    public Boolean getSafe() {
        return safe;
    }

    public Boolean getIdempotent() {
        return idempotent;
    }

    @Override
    public JSONObject asJSON() {
        final JSONObject ret = super.asJSON();

        JSONEntityHelpers.addJSONEntity("input", input, ret);
        JSONEntityHelpers.addJSONEntity("output", output, ret);
        JSONEntityHelpers.addObject("safe", safe, ret);
        JSONEntityHelpers.addObject("idempotent", idempotent, ret);

        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject root) throws WoTException {
        input = JSONEntityHelpers.readEntity(root, "input", DataSchema.class);
        output =
                JSONEntityHelpers.readEntity(root, "output", DataSchema.class);
        safe = JSONEntityHelpers.readObject(root, "safe", Boolean.class);
        idempotent =
                JSONEntityHelpers.readObject(root, "idempotent", Boolean.class);

        super.fromJSON(root);
        return this;
    }
}
