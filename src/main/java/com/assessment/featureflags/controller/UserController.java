package com.assessment.featureflags.controller;

import com.assessment.featureflags.model.Feature;
import com.assessment.featureflags.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/{userId}")
public class UserController {
    private UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    List<Feature> getAllFeaturesForUserId(@PathVariable String userId) {
        List<Feature> features = userService.getAllFeaturesForUserId(userId);
        LOGGER.info("All available features (global + explicit) for the user: "+userId+" found successfully. Feature details: "+features);
        return features;
    }

    @GetMapping(value = "/enabledFeatures")
    List<Feature> getAllEnabledFeaturesForUserId(@PathVariable String userId) {
        List<Feature> features = userService.getAllEnabledFeaturesForUserId(userId);
        LOGGER.info("All enabled features (global + explicit) for the user: "+userId+" found successfully. Feature details: "+features);
        return features;
    }
}
