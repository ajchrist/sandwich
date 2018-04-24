package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        // used https://www.tutorialspoint.com/android/android_json_parser.htm
        // as a reminder on how to do json parsing
        JSONObject sJson;
        try {
            sJson = new JSONObject(json);
        } catch (JSONException e) {
            return null;
        }
        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;
        try {
            mainName = sJson.getJSONObject("name").getString("mainName");
            alsoKnownAs = fromJsonArray(sJson.getJSONObject("name").getJSONArray("alsoKnownAs"));
            placeOfOrigin = sJson.getString("placeOfOrigin");
            description = sJson.getString("description");
            image = sJson.getString("image");
            ingredients = fromJsonArray(sJson.getJSONArray("ingredients"));
        } catch (JSONException e) {
            return null;
        }
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }

    private static List<String> fromJsonArray(JSONArray array) throws JSONException {
        ArrayList<String> tempArr = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            tempArr.add((String) array.get(i));
        }
        return tempArr;
    }
}
