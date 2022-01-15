package com.assessment.featureflags.util;

import com.assessment.featureflags.model.Feature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonUtil {
    public static final String LIBRARY   = "Library_Service";
    public static final String BANK   = "Bank_Service";
    public static final String TRANSPORT   = "Transport_Service";
    public static final String VOTING   = "Voting_Service";

    public static final String GLOBALFEATURE1   = "Global_Feature_1";
    public static final String GLOBALFEATURE2   = "Global_Feature_2";
    public static final String GLOBALFEATURE3   = "Global_Feature_3";

    public static final String GLOBAL = "Global";
    public static final String EXPLICIT = "Explicit";

    public static Map<String, List<Feature>> allUserFeatures = new HashMap<>();
    public static List<Feature> allFeatures = new ArrayList<>();

    public static Map<String, List<Feature>> getAllUsersAndFeatures() {
        return allUserFeatures;
    }

    //Get and Put data from/in global "allUserFeatures" variable
    public static List<Feature> getAllFeaturesForUserId(String userId) {
        return allUserFeatures.get(userId);
    }
    public static void putAllUserFeatures(String key, List<Feature> value) {
        allUserFeatures.put(key, value);
    }

    //Get and Put data from/in global "allFeatures" variable
    public static List<Feature> getAllFeatures() {
        return allFeatures;
    }
    public static void putAllFeatures(Feature value) {
        allFeatures.add(value);
    }
}
