/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.web.util.staticlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 *
 * @author Michael
 */
public class SmallListManager implements SmallListTypes {

    /**
     * singleton instance
     */
    private static SmallListManager _instance;
    /**
     *
     */
    private ResourceBundle messageBundle;
    private Map<String, List<SmallListData>> smallList;
    private final static Object lock = new Object();

    /**
     * hidden constructor
     */
    private SmallListManager() {
        messageBundle = ResourceBundle.getBundle(
                this.getClass().getPackage().getName() + MEMBER_OPERATOR + SMALLLIST_PROPERTY_FILE,
                Locale.getDefault());

        smallList = new HashMap<>();
        for (String s : messageBundle.keySet()) {
            smallList.put(s, getListValue(messageBundle.getString(s)));
        }
    }

    /**
     *
     * @param listVal
     * @return
     */
    private List<SmallListData> getListValue(String listVal) {
        String[] strings = listVal.split(SMALLLIST_RECORD_DELIMITER);
        String[] fields;
        List<SmallListData> list = new ArrayList<>();
        for (String record : strings) {
            fields = record.split(SMALLLIST_FIELD_DELIMITER);
            list.add(new SmallListData(fields[0], fields[1]));
        }
        return list;
    }

    /**
     *
     * @return
     */
    public static Map<String, List<SmallListData>> getSmallListMap() {
        synchronized (lock) {
            if (_instance == null) {
                _instance = new SmallListManager();
            }
        }
        return _instance.smallList;
    }

//    public static void main(String[] args) {
//        System.out.println(SmallListManager.getSmallListMap());
//    }
}
