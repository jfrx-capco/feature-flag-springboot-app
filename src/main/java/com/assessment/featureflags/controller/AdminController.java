package com.assessment.featureflags.controller;

import com.assessment.featureflags.model.Feature;
import com.assessment.featureflags.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping(value = "/allfeatures")
    List<Feature> getAllAvailableFeatures() {
        List<Feature> features = adminService.getAllAvailableFeatures();
        LOGGER.info("All available features (global + explicit) found successfully. Feature details: "+features);
        return features;
    }

    @PostMapping(value = "/addfeature")
    List<Feature> addNewFeature(@RequestBody Feature feature) {
        List<Feature> features = adminService.addNewFeature(feature);
        LOGGER.info("New feature added successfully. All available features(global + explicit) details: "+features);
        return features;
    }

    @PostMapping("/enablefeature/{featureName}/user/{userId}")
    List<Feature> enableFeature(@PathVariable String featureName, @PathVariable String userId) {
        List<Feature> enabledFeature = adminService.enableFeature(featureName, userId);
        LOGGER.info("Feature: "+featureName+ " is successfully enabled. All user feature details: "+enabledFeature);
        return enabledFeature;
    }
}