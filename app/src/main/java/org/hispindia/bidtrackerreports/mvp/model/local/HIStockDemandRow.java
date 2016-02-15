package org.hispindia.bidtrackerreports.mvp.model.local;

/**
 * Created by nhancao on 2/15/16.
 */
public class HIStockDemandRow {
    private String name;
    private String inhand;
    private String demand;

    public HIStockDemandRow(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInhand() {
        return inhand;
    }

    public void setInhand(String inhand) {
        this.inhand = inhand;
    }

    public String getDemand() {
        return demand;
    }

    public void setDemand(String demand) {
        this.demand = demand;
    }
}
