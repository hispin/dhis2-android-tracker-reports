package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;

import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhancao on 1/27/16.
 */
public class HIResStock {
    public String title;

    public List<HIStockRow> rows;

    public HIResStock(JsonNode response) {
        rows = new ArrayList<>();
        try {
            this.title = response.get("title").asText();
            this.title = response.get("title").asText();
            final JsonNode arrNode = response.get("rows");
            if (arrNode.isArray()) {
                for (int i = 0; i < arrNode.size(); i++) {
                    Log.e("HIREStock","arrSize"+  arrNode.size());

                    final JsonNode objNode = arrNode.get(i);
//                    Log.e("HIREStock","objNode.get(2).asText()"+ objNode.get(3).asText());
//                    Log.e("HIREStock","arrnode"+ arrNode.get(i));
                    HIStockRow row = new HIStockRow(i, objNode.get(1).asText(), objNode.get(2).asText(),objNode.get(2).asText());
                    rows.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
