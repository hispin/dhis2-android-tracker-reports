package org.hispindia.bidtrackerreports.mvp.model.remote.response;

import com.fasterxml.jackson.databind.JsonNode;

import org.hispindia.bidtrackerreports.mvp.model.local.HIStockRow3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sourabh on 1/27/16.
 */
public class HIResStock3 {
    public String title;
    public List<HIStockRow3> rows3;

    public HIResStock3(JsonNode response) {
        rows3 = new ArrayList<>();
        try {
            this.title = response.get("title").asText();
            final JsonNode arrNode = response.get("rows");
            if (arrNode.isArray()) {
                for (int i = 0; i < arrNode.size(); i++) {
                    final JsonNode objNode = arrNode.get(i);
                    HIStockRow3 row = new HIStockRow3(i, objNode.get(0).asText(), objNode.get(1).asText());

                    rows3.add(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

}
