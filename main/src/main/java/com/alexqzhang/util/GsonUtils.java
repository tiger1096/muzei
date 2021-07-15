package com.alexqzhang.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public final class GsonUtils {

    private static final String TAG = GsonUtils.class.getName();

    private static Gson gson;

    static {
        gson = new GsonBuilder().create();
    }

    private GsonUtils() {
    }

    /**
     * json to object
     */
    public static <T> T json2Obj(Gson gson, String json, Type cls) {
        T obj = null;
        try {
            obj = gson.fromJson(json, cls);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return obj;
    }

    public static <T> T json2Obj(String json, Type cls) {
        T obj = null;
        try {
            obj = gson.fromJson(json, cls);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return obj;
    }

    public static <T> T json2Obj(Gson gson, String json, Class<T> clazz) {
        try {
            return gson.fromJson(json, clazz);
        } catch (Throwable e) {
            LogUtils.e(TAG, e);
        }
        return null;
    }

    public static <T> T json2Obj(String json, Class<T> clazz) {
        return json2Obj(gson, json, clazz);
    }

    public static JsonObject json2JsonObject(String json) {
        if (json == null) {
            return null;
        }

        try {
            JsonElement element = new JsonParser().parse(json);
            if (element instanceof JsonObject) {
                return (JsonObject) element;
            }
        } catch (JsonSyntaxException e) {
            LogUtils.e(TAG, e);
        }
        return null;
    }


    /**
     * object to json
     */
    public static <T> String obj2Json(Gson gson, T obj) {
        String json = null;
        try {
            json = gson.toJson(obj);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return json;
    }

    public static <T> String obj2Json(T obj) {
        return obj2Json(gson, obj);
    }

    public static <T> String obj2Json(Gson gson, T obj, Type tTYpe) {
        String json = null;
        try {
            json = gson.toJson(obj, tTYpe);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return json;
    }

    public static <T> String obj2Json(T obj, Type tTYpe) {
        return obj2Json(gson, obj, tTYpe);
    }

    /**
     * json to object list
     */
    public static <T> List<T> json2ObjList(Gson gson, String jsonString, Class<T> clazz) {
        List<T> list = null;
        try {
            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(jsonString).getAsJsonArray();
            list = new ArrayList<>();
            for (int i = 0, size = jsonArray.size(); i < size; i++) {
                list.add(gson.fromJson(jsonArray.get(i), clazz));
            }
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return list;
    }

    public static <T> List<T> json2ObjList(String jsonString, Class<T> clazz) {
        return json2ObjList(gson, jsonString, clazz);
    }

    /**
     * object list to json
     */
    public static <T> String objList2Json(Gson gson, List<T> list) {
        String json = null;
        try {
            json = gson.toJson(list);
        } catch (Exception e) {
            LogUtils.e(e);
        }
        return json;
    }

    public static <T> String objList2Json(List<T> list) {
        return objList2Json(gson, list);
    }

    // XML.toJSONObject转出来的JSON,如果一个父节点只有一个孩子节点的话,转出来的是Object,但有时期望的是array
    public static void confirmValueIsArray(JsonObject fatherNode, String key) {
        if (fatherNode == null || key == null) {
            return;
        }
        JsonElement element = fatherNode.get(key);
        if (element == null) {
            return;
        }
        if (element.isJsonArray()) {
            return;
        }
        JsonArray array = new JsonArray();
        array.add(element);
        fatherNode.remove(key);
        fatherNode.add(key, array);
    }

    public static String optString(JsonObject jsonObj, String key) {
        return optString(jsonObj, key, "");
    }

    public static String optString(JsonObject jsonObj, String key, String defaultValue) {
        try {
            JsonElement element = jsonObj.get(key);
            if (element instanceof JsonPrimitive) {
                return element.getAsString();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return defaultValue;
    }

    public static int optInt(JsonObject jsonObj, String key) {
        return optInt(jsonObj, key, 0);
    }

    public static int optInt(JsonObject jsonObj, String key, int defaultValue) {
        try {
            JsonElement element = jsonObj.get(key);
            if (element instanceof JsonPrimitive) {
                return element.getAsInt();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return defaultValue;
    }

    public static double optDouble(JsonObject jsonObj, String key) {
        return optDouble(jsonObj, key, Double.NaN);
    }

    public static double optDouble(JsonObject jsonObj, String key, double defaultValue) {
        try {
            JsonElement element = jsonObj.get(key);
            if (element instanceof JsonPrimitive) {
                return element.getAsDouble();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return defaultValue;
    }

    public static float optFloat(JsonObject jsonObj, String key) {
        return optFloat(jsonObj, key, Float.NaN);
    }

    public static float optFloat(JsonObject jsonObj, String key, float defaultValue) {
        try {
            JsonElement element = jsonObj.get(key);
            if (element instanceof JsonPrimitive) {
                return element.getAsFloat();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return defaultValue;
    }

    public static long optLong(JsonObject jsonObj, String key) {
        return optLong(jsonObj, key, 0L);
    }

    public static long optLong(JsonObject jsonObj, String key, long defaultValue) {
        try {
            JsonElement element = jsonObj.get(key);
            if (element instanceof JsonPrimitive) {
                return element.getAsLong();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return defaultValue;
    }

    public static boolean optBoolean(JsonObject jsonObj, String key) {
        return optBoolean(jsonObj, key, false);
    }

    public static boolean optBoolean(JsonObject jsonObj, String key, boolean defaultValue) {
        try {
            JsonElement element = jsonObj.get(key);
            if (element instanceof JsonPrimitive) {
                return element.getAsBoolean();
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return defaultValue;
    }

    public static JsonObject optJsonObject(JsonObject jsonObj, String key) {
        try {
            JsonElement element = jsonObj.get(key);
            if (element instanceof JsonObject) {
                return (JsonObject) element;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return null;
    }

    public static JsonArray optJsonArray(JsonObject jsonObj, String key) {
        try {
            JsonElement element = jsonObj.get(key);
            if (element instanceof JsonArray) {
                return (JsonArray) element;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return null;
    }

    public static String optString(JsonArray array, int index) {
        try {
            if (index >= 0 && index < array.size()) {
                JsonElement element = array.get(index);
                if (element instanceof JsonPrimitive) {
                    return element.getAsString();
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return "";
    }

    public static String getStringUnsafe(JsonArray array, int index) {
        return array.get(index).getAsString();
    }

    public static int optInt(JsonArray array, int index) {
        return optInt(array, index, 0);
    }

    public static int optInt(JsonArray array, int index, int defaultValue) {
        try {
            if (index >= 0 && index < array.size()) {
                JsonElement element = array.get(index);
                if (element instanceof JsonPrimitive) {
                    return element.getAsInt();
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return defaultValue;
    }

    public static int getIntUnsafe(JsonArray array, int index) {
        return array.get(index).getAsInt();
    }

    public static double optDouble(JsonArray array, int index) {
        return optDouble(array, index, Double.NaN);
    }

    public static double optDouble(JsonArray array, int index, double defaultValue) {
        try {
            if (index >= 0 && index < array.size()) {
                JsonElement element = array.get(index);
                if (element instanceof JsonPrimitive) {
                    return element.getAsDouble();
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return defaultValue;
    }

    public static double getDoubleUnsafe(JsonArray array, int index) {
        return array.get(index).getAsDouble();
    }

    public static float getFloatUnsafe(JsonArray array, int index) {
        return array.get(index).getAsFloat();
    }

    public static float getFloatSafe(JsonArray array, int index) {
        if (index >= array.size()) {
            return 0.0f;
        }
        return array.get(index).getAsFloat();
    }

    public static JsonObject optJsonObject(JsonArray array, int index) {
        try {
            if (index >= 0 && index < array.size()) {
                JsonElement element = array.get(index);
                if (element instanceof JsonObject) {
                    return (JsonObject) element;
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return null;
    }

    public static JsonArray optJsonArray(JsonArray array, int index) {
        try {
            if (index >= 0 && index < array.size()) {
                JsonElement element = array.get(index);
                if (element instanceof JsonArray) {
                    return (JsonArray) element;
                }
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e);
        }
        return null;
    }

    public static JsonObject getJsonObjectUnsafe(JsonArray array, int index) {
        return array.get(index).getAsJsonObject();
    }

    public static String getStringUnsafe(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsString();
    }

    public static int getIntUnsafe(JsonObject jsonObject, String key) {
        return jsonObject.get(key).getAsInt();
    }
}
