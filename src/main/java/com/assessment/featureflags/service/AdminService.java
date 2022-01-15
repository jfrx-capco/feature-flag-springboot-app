package com.assessment.featureflags.service;

import com.assessment.featureflags.exception.DuplicateFeatureException;
import com.assessment.featureflags.exception.FeatureAlreadyEnabledException;
import com.assessment.featureflags.model.Feature;
import com.assessment.featureflags.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    public List<Feature> addNewFeature(Feature feature) {
        //get all existing features
        List<Feature> allFeatures = CommonUtil.getAllFeatures();

        //check if the new feature to be added already exists in the list
        if(allFeatures.stream().anyMatch(o -> o.getFeatureName().equals(feature.getFeatureName()))) {
            throw new DuplicateFeatureException("The Feature with the given name: "+feature.getFeatureName()+" already exists. Please try again.");
        } else {
            // if feature does not exists, add it to the list
            CommonUtil.putAllFeatures(new Feature(feature.getFeatureName(), feature.getFeatureType(), false));
        }
        return allFeatures;
    }

    public List<Feature> enableFeature(String featureName, String userId) {
        //get all existing features for the given user ID
        List<Feature> userFeatures = CommonUtil.getAllFeaturesForUserId(userId);

        //check if the feature exists for the user and is not already enabled
        for(Feature feature : userFeatures) {
            if(feature.getFeatureName().equals(featureName)) {
                if(feature.isEnabled() == false) {
                    //enable the feature
                    feature.setEnabled(true);
                } else {
                    throw new FeatureAlreadyEnabledException("The Feature with the given name: "+featureName+" is already enabled. Please try a different feature.");
                }
            }
        }
        CommonUtil.putAllUserFeatures(userId, userFeatures);
        return userFeatures;
    }

    public List<Feature> getAllAvailableFeatures() {
        //get all existing features and return them
        List<Feature> allFeatures = CommonUtil.getAllFeatures();
        return allFeatures;
    }
}
