package utils;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;

public class Utils {
    public JSONObject getObject(String URL) {
        HttpResponse<JsonNode> yes = Unirest.get(URL)
                .asJson();
        if (yes.isSuccess()) {
            return yes
                    .getBody()
                    .getObject();
        } else {
            throw new NullPointerException();
        }
    }
}
