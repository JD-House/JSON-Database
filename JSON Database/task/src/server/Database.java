package server;

import java.util.HashMap;
import java.util.Map;

public abstract class Database {

    private static final Map<String, String> database = new HashMap<>();
    private static final String ERROR = "ERROR";
    private static final String OK = "OK";

    public static String addToDatabase(String key, String value) {
        database.put(key, value);
        return OK;
    }

    public static String getFromDatabase(String key) {
        if (!database.containsKey(key)) {
            return ERROR;
        }
        return database.get(key);
    }

    public static String deleteFromDatabase(String key) {
        database.remove(key);
        return OK;
    }
}
