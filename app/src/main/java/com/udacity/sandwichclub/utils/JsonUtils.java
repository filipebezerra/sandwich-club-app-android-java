package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameJsonObject = jsonObject.getJSONObject("name");
            sandwich = parseSandwichFromJsonObject(jsonObject, nameJsonObject);
        } catch (JSONException e) {
            Timber.e(e, "Fail attempting to parse the JSON");
        }
        return sandwich;
    }

    private static Sandwich parseSandwichFromJsonObject(
            JSONObject jsonObject,
            JSONObject nameJsonObject
    ) throws JSONException {
        return new Sandwich(
                parseStringAttribute(nameJsonObject, "mainName"),
                parseListAttribute(nameJsonObject, "alsoKnownAs"),
                parseStringAttribute(jsonObject, "placeOfOrigin"),
                parseStringAttribute(jsonObject, "description"),
                parseStringAttribute(jsonObject, "image"),
                parseListAttribute(jsonObject, "ingredients")
        );
    }

    @NotNull
    private static List<String> parseListAttribute(
            JSONObject jsonObject,
            String attribute
    ) throws JSONException {
        if (!jsonObject.has(attribute)) return Collections.emptyList();
        JSONArray jsonArray = jsonObject.getJSONArray(attribute);
        final List<String> stringList = new ArrayList<>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            stringList.add(i, jsonArray.getString(i));
        }
        return stringList;
    }

    private static String parseStringAttribute(
            JSONObject jsonObject,
            String attribute
    ) throws JSONException {
        return jsonObject.has(attribute) ? jsonObject.getString(attribute) : "";
    }
}
