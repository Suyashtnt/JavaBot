package utils;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class Utils {
    public JSONObject getObject(String URL) {
        return Unirest.get(URL)
                .asJson()
                .getBody()
                .getObject();
    }
}
