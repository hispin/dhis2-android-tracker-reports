package org.hispindia.bidtrackerreports.mvp.model.local;

/**
 * Created by Sourabh on 1/27/16.
 */
public class HIStockRow5 {
    private int order;
    private String name;
    private String value;

    public HIStockRow5(int order, String name, String value) {
        this.order = order;
        this.name = name;
        this.value = value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
