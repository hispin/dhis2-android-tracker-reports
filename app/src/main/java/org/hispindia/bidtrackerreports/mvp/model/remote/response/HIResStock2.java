package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import com.fasterxml.jackson.databind.JsonNode;

import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourabh on 1/27/16.
 */
public class HIResStock2 {
    public String title;
    public List<HIStockRow2> rows2;

    public HIResStock2(JsonNode response) {
        rows2 = new ArrayList<>();
        try {
            this.title = response.get("title").asText();
            final JsonNode arrNode = response.get("rows");
            if (arrNode.isArray()) {
                for (int i = 0; i < arrNode.size(); i++) {
                    final JsonNode objNode = arrNode.get(i);
                    HIStockRow2 row = new HIStockRow2(i, objNode.get(0).asText(), objNode.get(1).asText());

                    rows2.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
