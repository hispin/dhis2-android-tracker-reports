package org.hispindia.bidtrackerreports.ui.fragment.hibidreport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nhancao on 2/5/16.
 */
public class HIParamBIDHardcode {

    public static final Map<String, DataElement> DE = new HashMap<String, DataElement>() {{
        put("bpBUOvqy1Jn", new DataElement("bpBUOvqy1Jn", "BCG", "BCG"));
        put("EMcT5j5zR81", new DataElement("EMcT5j5zR81", "BCG", "BCG scar"));
        put("KRF40x6EILp", new DataElement("KRF40x6EILp", "BCG", "BCG repeat dose"));
        put("no7SkAxepi7", new DataElement("no7SkAxepi7", "OPV", "OPV 0"));
        put("CfPM8lsEMzH", new DataElement("CfPM8lsEMzH", "OPV", "OPV 1"));
        put("K3TcJM1ySQA", new DataElement("K3TcJM1ySQA", "DPT", "DPT-HepB-Hib1"));
        put("fmXCCPENnwR", new DataElement("fmXCCPENnwR", "PCV", "PCV 1"));
        put("nIqQYeSwU9E", new DataElement("nIqQYeSwU9E", "RV", "RV 1"));
        put("sDORmAKh32v", new DataElement("sDORmAKh32v", "OPV", "OPV 2"));
        put("PvHUllrtPiy", new DataElement("PvHUllrtPiy", "PCV", "PCV 2"));
        put("wYg2gOWSyJG", new DataElement("wYg2gOWSyJG", "RV", "RV 2"));
        put("nQeUfqPjK5o", new DataElement("nQeUfqPjK5o", "OPV", "OPV 3"));
        put("pxCZNoqDVJC", new DataElement("pxCZNoqDVJC", "DPT", "DPT-HepB-Hib3"));
        put("B4eJCy6LFLZ", new DataElement("B4eJCy6LFLZ", "PCV", "PCV 3"));
        put("cNA9EmFaiAa", new DataElement("cNA9EmFaiAa", "OPV", "OPV 4"));
        put("g8dMiUOTFla", new DataElement("g8dMiUOTFla", "Measles", "Measles 1"));
        put("Bxh1xgIY9nA", new DataElement("Bxh1xgIY9nA", "Measles", "Measles 2"));
    }};

//public static String ORGUNITID = new Select().from(OrganisationUnit.class).querySingle().getId();
    public static String ORGUNITID ="GUhbn1R8q6w";

    /*
    <organisationUnit id="DQjaNvP9ulw">
<displayName>Linda Clinic</displayName>
</organisationUnit>
<organisationUnit id="GUhbn1R8q6w">
<displayName>Livingstone District</displayName>
</organisationUnit>
<organisationUnit id="WxEt7wHRNeW">
<displayName>Maramba Clinic</displayName>
</organisationUnit>
<organisationUnit id="ozvn5V1CkYM">
<displayName>Simoonga Clinic</displayName>
</organisationUnit>
<organisationUnit id="MClooR8Tjs6">
<displayName>Southern Province</displayName>
</organisationUnit>
<organisationUnit id="LWjoKmGc00n">
<displayName>Victoria Falls Clinic</displayName>
</organisationUnit>
    */

    public static String ORGUNITID1 ="GUhbn1R8q6w";
    public static String ORGUNITID2 ="WxEt7wHRNeW";
    public static String ORGUNITID3 ="ozvn5V1CkYM";
    public static String ORGUNITID4 ="MClooR8Tjs6";
    public static String ORGUNITID5 ="DQjaNvP9ulw";

    public static String PROGRAMID = "SSLpOM0r1U7";
    public static String PROGRAMSTAGEID = "s53RFfXA75f";
    public static String OUMODE = "SELECTED";
    public static String OUMODEID = "lTqNF1rWha3";
    public static String FIRSTNAMEID = "sB1IHYu2xQT";
    public static String CHILDNAMEID = "wbtl3uN0spv";
    public static String DATEOFBIRTH = "rKtHjgcO2Bn";

    public static class DataElement {
        private String id;
        private String groupby;
        private String name;

        public DataElement(String id, String groupby, String name) {
            this.id = id;
            this.groupby = groupby;
            this.name = name;
        }

        public String getId() {

            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGroupby() {
            return groupby;
        }

        public void setGroupby(String groupby) {
            this.groupby = groupby;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
