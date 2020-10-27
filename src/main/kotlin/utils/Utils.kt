package utils

import kong.unirest.Unirest
import kong.unirest.json.JSONObject
import java.util.concurrent.ThreadLocalRandom

class Utils {
    fun getObject(URL: String?): JSONObject {
        val yes = Unirest.get(URL)
                .asJson()
        return if (yes.isSuccess) {
            yes
                    .body
                    .getObject()
        } else {
            throw NullPointerException()
        }
    }

    fun <T> getRandomListElement(items: List<T>): T {
        return items[ThreadLocalRandom.current().nextInt(items.size)]
    }
}