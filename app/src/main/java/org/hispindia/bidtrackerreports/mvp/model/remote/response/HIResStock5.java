package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import com.fasterxml.jackson.databind.JsonNode;

import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow5;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourabh on 1/27/16.
 */
public class HIResStock5 {
    public String title;
    public List<HIStockRow5> rows5;

    public HIResStock5(JsonNode response) {
        rows5 = new ArrayList<>();
        try {
            this.title = response.get("title").asText();
            final JsonNode arrNode = response.get("rows");
            if (arrNode.isArray()) {
                for (int i = 0; i < arrNode.size(); i++) {
                    final JsonNode objNode = arrNode.get(i);
                    HIStockRow5 row5 = new HIStockRow5(i, objNode.get(0).asText(), objNode.get(2).asText());

                    rows5.add(row5);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
