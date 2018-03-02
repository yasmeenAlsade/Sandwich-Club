package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        JSONObject sandwichJson = null, nameJson = null;
        JSONArray alsoKnownAsJsonArray = null, ingredientsJsonArray = null;

        String mainNameStr = "", placeOfOriginStr = "", descriptionStr = "", imageStr = "";
        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();


        try {
            sandwichJson = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (sandwichJson != null) {
            nameJson = sandwichJson.optJSONObject("name");
            placeOfOriginStr = sandwichJson.optString("placeOfOrigin");
            descriptionStr = sandwichJson.optString("description");
            imageStr = sandwichJson.optString("image");
            ingredientsJsonArray = sandwichJson.optJSONArray("ingredients");
        }

        if (nameJson != null) {
            mainNameStr = nameJson.optString("mainName");
            alsoKnownAsJsonArray = nameJson.optJSONArray("alsoKnownAs");
        }

        if (alsoKnownAsJsonArray != null) {
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                String listItem = null;
                try {
                    listItem = alsoKnownAsJsonArray.getString(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alsoKnownAsList.add(listItem);
            }
        }

        if (ingredientsJsonArray != null) {
            for (int i = 0; i < ingredientsJsonArray.length(); i++) {
                String listItem = null;
                try {
                    listItem = ingredientsJsonArray.getString(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ingredientsList.add(listItem);
            }
        }

        Sandwich sandwich = new Sandwich(mainNameStr, alsoKnownAsList, placeOfOriginStr, descriptionStr, imageStr, ingredientsList);

        return sandwich;
    }
}
