package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import com.fasterxml.jackson.databind.JsonNode;

import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nhancao on 1/27/16.
 */
public class HIResStock1 {
    public String title;
    public List<HIStockRow1> rows1;

    public HIResStock1(JsonNode response) {
        rows1 = new ArrayList<>();
        try {
            this.title = response.get("title").asText();
            final JsonNode arrNode = response.get("rows");
            if (arrNode.isArray()) {
                for (int i = 0; i < arrNode.size(); i++) {
                    final JsonNode objNode = arrNode.get(i);
                    HIStockRow1 row1 = new HIStockRow1(i, objNode.get(0).asText(), objNode.get(2).asText());

                    rows1.add(row1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
