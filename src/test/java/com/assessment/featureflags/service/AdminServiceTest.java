package com.assessment.featureflags.service;

import com.assessment.featureflags.exception.DuplicateFeatureException;
import com.assessment.featureflags.model.Feature;
import com.assessment.featureflags.util.CommonUtil;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.assessment.featureflags.util.CommonUtil.*;
import static com.assessment.featureflags.util.CommonUtil.GLOBAL;
import static org.junit.jupiter.api.Assertions.*;

public class AdminServiceTest {
    AdminService adminService = new AdminService();

    @Test
    void testAddNewFeature() {
        //Given
        Feature newFeature = new Feature();
        newFeature.setFeatureName("New_Feature");
        newFeature.setFeatureType("Explicit");

        List<Feature> allExpectedFeaturesList = new ArrayList<>();
        allExpectedFeaturesList.add(new Feature("New_Feature1", "Explicit", false)); //due to static fields and data being captured in the next test
        allExpectedFeaturesList.add(newFeature);

        //When
        List<Feature> allFeaturesList = adminService.addNewFeature(newFeature);

        //Then
        assertEquals(allExpectedFeaturesList, allFeaturesList);
    }

    @Test
    void testAddNewFeature_DuplicateFeature() {
        //Given
        Feature newFeature1 = new Feature();
        newFeature1.setFeatureName("New_Feature1");
        newFeature1.setFeatureType("Explicit");

        Feature newFeature2 = new Feature();
        newFeature2.setFeatureName("New_Feature1");
        newFeature2.setFeatureType("Explicit");

        List<Feature> allExpectedFeaturesList = new ArrayList<>();
        allExpectedFeaturesList.add(newFeature1);

        //When & Then
        List<Feature> allFeaturesList = adminService.addNewFeature(newFeature1);
        assertEquals(allExpectedFeaturesList, allFeaturesList);

        //When & Then
        Throwable exception = assertThrows(DuplicateFeatureException.class,
                ()->{adminService.addNewFeature(newFeature2);} );
    }
}
