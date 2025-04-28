package org.example.constant;

import java.util.HashMap;
import java.util.Map;

public class TokenMap {
    private static final Map<String, Integer> map=new HashMap<>();

    public static Integer get(String token) {
        return map.get(token);
    }
    public static void put(String token, Integer userId) {
        map.put(token, userId);
    }
}
