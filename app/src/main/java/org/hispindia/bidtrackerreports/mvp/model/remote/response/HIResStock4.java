package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import com.fasterxml.jackson.databind.JsonNode;

import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow4;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourabh on 1/27/16.
 */
public class HIResStock4 {
    public String title;
    public List<HIStockRow4> rows4;

    public HIResStock4(JsonNode response) {
        rows4 = new ArrayList<>();
        try {
            this.title = response.get("title").asText();
            final JsonNode arrNode = response.get("rows");
            if (arrNode.isArray()) {
                for (int i = 0; i < arrNode.size(); i++) {
                    final JsonNode objNode = arrNode.get(i);
                    HIStockRow4 row = new HIStockRow4(i, objNode.get(0).asText(), objNode.get(2).asText());

                    rows4.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
