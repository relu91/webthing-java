/**
 * High-level Action base class implementation.
 */
package io.webthings.webthing;

import io.webthings.webthing.affordances.ActionAffordance;
import io.webthings.webthing.exceptions.WoTException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An Action represents an individual action on a thing.
 */
public abstract class Action extends ActionAffordance{
    private final String        id;
    private final Thing         thing;
    private String              hrefPrefix;
    
    private String              status;
    private final String        timeRequested;
    private String              timeCompleted;

    /**
     * Initialize the object.
     *
     * @param id    ID of this action
     * @param thing Thing this action belongs to
     */
    public Action(String id, Thing thing) throws WoTException{
        this(id, thing, null);
    }

    /**
     * Initialize the object.
     *
     * @param id    ID of this action
     * @param thing Thing this action belongs to
     * @param name  Name of the action
     * @param input Any action inputs
     */
    public Action(String id, Thing thing, JSONObject input) throws WoTException{
        this.id = id;
        this.thing = thing;

        this.hrefPrefix = "";
        //this.href = String.format("/actions/%s/%s", this.name, this.id);
        this.status = "created";
        this.timeRequested = Utils.timestamp();
        //loads json config
        super.fromJSON(input);
    }

    /**
     * Get the action description.
     *
     * @return Description of the action as a JSONObject.
     */

    /**
     * Set the prefix of any hrefs associated with this action.
     *
     * @param prefix The prefix
     */
    public void setHrefPrefix(String prefix) {
        this.hrefPrefix = prefix;
    }

    /**
     * Get this action's ID.
     *
     * @return The ID.
     */
    public String getId() {
        return this.id;
    }



    /**
     * Get this action's href.
     *
     * @return The href.
     */
/*    
    public List<String> getHrefs() {
        final List<String> ret = new ArrayList<>();
        for(final Form f : this.getForms()) {
            final String href = this.hrefPrefix
        }
        return this.hrefPrefix + this.href;
    }
*/
    /**
     * Get this action's status.
     *
     * @return The status.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Get the thing associated with this action.
     *
     * @return The thing.
     */
    public Thing getThing() {
        return this.thing;
    }

    /**
     * Get the time the action was requested.
     *
     * @return The time.
     */
    public String getTimeRequested() {
        return this.timeRequested;
    }

    /**
     * Get the time the action was completed.
     *
     * @return The time.
     */
    public String getTimeCompleted() {
        return this.timeCompleted;
    }

 

    /**
     * Start performing the action.
     */
    public void start() {
        this.status = "pending";
        this.thing.actionNotify(this);
        this.performAction();
        this.finish();
    }

    /**
     * Override this with the code necessary to perform the action.
     */
    public abstract void performAction();
    

    /**
     * Override this with the code necessary to cancel the action.
     */
    public abstract  void cancel();
    

    /**
     * Finish performing the action.
     */
    public void finish() {
        this.status = "completed";
        this.timeCompleted = Utils.timestamp();
        this.thing.actionNotify(this);
    }
}
