package com.assessment.featureflags.service;

import com.assessment.featureflags.exception.UserNotFoundException;
import com.assessment.featureflags.model.Feature;
import com.assessment.featureflags.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    public List<Feature> getAllFeaturesForUserId(String userId) {
        //get all existing features for the given user ID and return them
        List<Feature> userFeatures = CommonUtil.getAllFeaturesForUserId(userId);
        if(userFeatures == null){
            throw new UserNotFoundException("The user with the given ID: "+userId+" does not exists. Please try again.");
        }
        return userFeatures;
    }

    public List<Feature> getAllEnabledFeaturesForUserId(String userId) {
        //get all existing features for the given user ID
        List<Feature> allUserFeatures = CommonUtil.getAllFeaturesForUserId(userId);
        List<Feature> enabledUserFeatures = new ArrayList<>();

        if(allUserFeatures == null){
            throw new UserNotFoundException("The user with the given ID: "+userId+" does not exists. Please try again.");
        } else {
            //filter out the features that are enabled and return them
            for(Feature feature : allUserFeatures) {
                if(feature.isEnabled() == true) {
                    enabledUserFeatures.add(feature);
                }
            }
        }
        return  enabledUserFeatures;
    }
}
