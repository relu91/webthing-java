package io.webthings.webthing.affordances;

import io.webthings.webthing.JSONEntity;
import io.webthings.webthing.common.DataSchema;
import io.webthings.webthing.common.JSONEntityHelpers;

import static io.webthings.webthing.common.JSONEntityHelpers.addCollection;
import static io.webthings.webthing.common.JSONEntityHelpers.addJSONEntityCollection;
import static io.webthings.webthing.common.JSONEntityHelpers.addSingleItemOrList;
import static io.webthings.webthing.common.JSONEntityHelpers.addString;
import static io.webthings.webthing.common.JSONEntityHelpers.checkedInitList;

import io.webthings.webthing.exceptions.InvalidFieldException;
import io.webthings.webthing.exceptions.WoTException;
import io.webthings.webthing.forms.Form;
import io.webthings.webthing.forms.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.JSONObject;

/**
 * @author Lorenzo
 */
public class InteractionAffordance extends JSONEntity {
    private String title;
    private Map<String, String> titles;
    private String description;
    private Map<String, String> descriptions;
    private List<String> types;
    private List<Form> forms;
    private Map<String, DataSchema> uriVariables;
    private Set<Operation.id> allowedOps;

    protected InteractionAffordance(Set<Operation.id> allowedOps) {
        this.allowedOps = allowedOps;
    }

    public InteractionAffordance() {

    }

    public InteractionAffordance(Form f) {
        forms = new ArrayList<>();
        forms.add(f);
    }

    public InteractionAffordance(String type,
                                 String title,
                                 String desc,
                                 Form f) {
        this(f);
        this.title = title;
        description = desc;
        types = checkedInitList(type);
    }

    public void setType(String t) {
        types = new ArrayList<>();
        types.add(t);
    }

    public String getType() {
        String ret = null;
        if (types != null && types.size() >= 1) {
            ret = types.get(0);
        }
        return ret;
    }

    public List<String> getTypes() {
        return types;
    }

    public void addType(String t) {
        if (types == null) {
            setType(t);
        } else {
            types.add(t);
        }
    }

    public void setDefaultTitle(String t) {
        title = t;
    }

    public String getDefaultTitle() {
        return title;
    }

    public void setI18NTitle(String lang, String t) {
        if (titles == null) {
            titles = new TreeMap<>();
        }

        titles.put(lang, t);
    }

    public String getI18NTitle(String lang) {
        String ret = null;
        if (titles != null) {
            ret = titles.get(lang);
        }

        return ret;
    }

    public void removeI18NTitle(String lang) {
        if (titles != null) {
            titles.remove(lang);
        }
    }

    public void setDefaultDescription(String d) {
        description = d;
    }

    public String getDefaultDescription() {
        return description;
    }

    public void setI18NDescription(String lang, String d) {
        if (descriptions == null) {
            descriptions = new TreeMap<>();
        }
        descriptions.put(lang, d);
    }

    public String getI18NDescription(String lang) {
        String ret = null;
        if (descriptions != null) {
            ret = descriptions.get(lang);
        }

        return ret;
    }

    public void removeI18NDescription(String lang) {
        if (descriptions != null) {
            descriptions.remove(lang);
        }
    }

    public void addForm(Form f) throws WoTException {
        checkForm(f);
        if (forms == null) {
            forms = new ArrayList<>();
        }


        forms.add(f);
    }

    public List<Form> getForms() {
        return forms;
    }

    public Map<String, DataSchema> getUriVariables() {
        return uriVariables;
    }

    public DataSchema getUriVariable(String s) {
        final DataSchema ret =
                (uriVariables == null ? null : uriVariables.get(s));
        return ret;
    }

    public void putUriVariable(String s, DataSchema d) {
        if (uriVariables == null) {
            uriVariables = new TreeMap<>();
        }

        uriVariables.put(s, d);
    }

    @Override
    public JSONObject asJSON() {
        final JSONObject ret = new JSONObject();

        addSingleItemOrList("@type", types, ret);

        addString("title", title, ret);
        addCollection("titles", titles, ret);
        addString("description", description, ret);
        addCollection("descriptions", descriptions, ret);
        addJSONEntityCollection("forms", forms, ret);


        return ret;
    }

    @Override
    public JSONEntity fromJSON(JSONObject o) throws WoTException {
        title = JSONEntityHelpers.readObject(o, "title", String.class);
        titles = JSONEntityHelpers.readCollection(o,
                                                  "titles",
                                                  String.class,
                                                  TreeMap.class);
        description =
                JSONEntityHelpers.readObject(o, "description", String.class);
        descriptions = JSONEntityHelpers.readCollection(o,
                                                        "descriptions",
                                                        String.class,
                                                        TreeMap.class);
        types = JSONEntityHelpers.readObjectSingleOrList(o,
                                                         "@type",
                                                         String.class,
                                                         ArrayList.class);
        forms = JSONEntityHelpers.readEntityCollection(o,
                                                       "forms",
                                                       Form.class,
                                                       ArrayList.class);
        //checks all form operations
        for (final Form f : forms) {
            checkForm(f);
        }
        //uriVariables
        return this;
    }

    private void checkForm(Form f) throws WoTException {
        final List<Operation.id> opList = f.getOperationList();

        if (opList != null) {
            for (final Operation.id i : opList) {
                if (allowedOps.contains(i) == false) {
                    throw new InvalidFieldException("operation", i.toString());
                }
            }
        }
    }
}
