/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.schoolruns.web.common;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Michael
 */
public final class ThemeUtil {

    private static Map<String, String> themeMap;

    static {
        themeMap = new TreeMap<String, String>();
        
        themeMap.put("afterdark", "afterdark");
        themeMap.put("afternoon", "afternoon");
        themeMap.put("afterwork", "afterwork");
        themeMap.put("aristo", "aristo");
        themeMap.put("black-tie", "black-tie");
        themeMap.put("blitzer", "blitzer");
        themeMap.put("bluesky", "bluesky");
        themeMap.put("bootstrap", "bootstrap");
        themeMap.put("casablanca", "casablanca");
        themeMap.put("cruze", "cruze");
        themeMap.put("cupertino", "cupertino");
        themeMap.put("dark-hive", "dark-hive");
        themeMap.put("dot-luv", "dot-luv");
        themeMap.put("eggplant", "eggplant");
        themeMap.put("Excite-Bike", "excite-bike");
        themeMap.put("flick", "flick");
        themeMap.put("glassx", "glassx");
        themeMap.put("home", "home");
        themeMap.put("Hot-Sneaks", "hot-sneaks");
        themeMap.put("humanity", "humanity");
        themeMap.put("Le-Frog", "le-frog");
        themeMap.put("midnight", "midnight");
        themeMap.put("Mint-Choc", "mint-choc");
        themeMap.put("overcast", "overcast");
        themeMap.put("Pepper-Grinder", "pepper-grinder");
        themeMap.put("redmond", "redmond");
        themeMap.put("rocket", "rocket");
        themeMap.put("sam", "sam");
        themeMap.put("smoothness", "smoothness");
        themeMap.put("South-Street", "south-street");
        themeMap.put("start", "start");
        themeMap.put("sunny", "sunny");
        themeMap.put("Swanky-Purse", "swanky-purse");
        themeMap.put("Trontastic", "trontastic");
        themeMap.put("ui-darkness", "ui-darkness");
        themeMap.put("ui-lightness", "ui-lightness");
        themeMap.put("vader", "vader");
    }

    public static Map<String, String> getThemeMap() {
        return themeMap;
    }
}
