package com.assessment.featureflags.controller;


import com.assessment.featureflags.model.Feature;
import com.assessment.featureflags.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.assessment.featureflags.util.CommonUtil.*;
import static com.assessment.featureflags.util.CommonUtil.GLOBAL;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @Test
    @DisplayName("Return all available features")
    public void shouldReturnAllFeatures_ForValidUserId() throws Exception {
        //Given
        List<Feature> allFeatures = new ArrayList<>(); {
            allFeatures.add(new Feature(LIBRARY, EXPLICIT, false));
            allFeatures.add(new Feature(BANK, EXPLICIT,false));
            allFeatures.add(new Feature(TRANSPORT, EXPLICIT,false));
            allFeatures.add(new Feature(VOTING, EXPLICIT,false));
            allFeatures.add(new Feature(GLOBALFEATURE1, GLOBAL,false));
            allFeatures.add(new Feature(GLOBALFEATURE2, GLOBAL,false));
            allFeatures.add(new Feature(GLOBALFEATURE3, GLOBAL,false));
        }

        //When
        when(adminService.getAllAvailableFeatures()).thenReturn(allFeatures);

        //Then
        mockMvc.perform(get("/admin/allfeatures").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(7)))
            .andExpect(jsonPath("$[0].featureName", is("Library_Service")))
            .andExpect(jsonPath("$[1].featureName", is("Bank_Service")))
            .andExpect(jsonPath("$[2].featureName", is("Transport_Service")))
            .andExpect(jsonPath("$[3].featureName", is("Voting_Service")))
            .andExpect(jsonPath("$[4].featureName", is("Global_Feature_1")))
            .andExpect(jsonPath("$[5].featureName", is("Global_Feature_2")))
            .andExpect(jsonPath("$[6].featureName", is("Global_Feature_3")));
    }

    @Test
    @DisplayName("Add a new feature if feature does not exists")
    public void shouldAddNewFeature_IfFeatureNotFound() throws Exception {
        //Given
        Feature newFeature = new Feature();
        newFeature.setFeatureName("New_Feature");
        newFeature.setFeatureType("Explicit");

        List<Feature> allAvailableFeaturesAfterAddingNewFeature = new ArrayList<>(); {
            allFeatures.add(new Feature(LIBRARY, EXPLICIT, false));
            allFeatures.add(new Feature(BANK, EXPLICIT,false));
            allFeatures.add(new Feature(TRANSPORT, EXPLICIT,false));
            allFeatures.add(new Feature(VOTING, EXPLICIT,false));
            allFeatures.add(new Feature(GLOBALFEATURE1, GLOBAL,false));
            allFeatures.add(new Feature(GLOBALFEATURE2, GLOBAL,false));
            allFeatures.add(new Feature(GLOBALFEATURE3, GLOBAL,false));
            allFeatures.add(new Feature("New_Feature", EXPLICIT,false));
        }

        //When
        when(adminService.addNewFeature(newFeature)).thenReturn(allAvailableFeaturesAfterAddingNewFeature);

        //Then
        mockMvc.perform(post("/admin/addfeature").contentType(MediaType.APPLICATION_JSON)
            .content("{ \"featureName\": \"New_Feature\", \"featureType\": \"Explicit\" }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Do not add a new feature if feature exists")
    public void shouldNotAddNewFeature_IfFeatureIsFound() throws Exception {
        //Given
        Feature newFeature = new Feature();
        newFeature.setFeatureName("Library_Service");
        newFeature.setFeatureType("Explicit");

        List<Feature> allAvailableFeaturesAfterAddingNewFeature = new ArrayList<>(); {
            allFeatures.add(new Feature(LIBRARY, EXPLICIT, false));
            allFeatures.add(new Feature(BANK, EXPLICIT,false));
            allFeatures.add(new Feature(TRANSPORT, EXPLICIT,false));
            allFeatures.add(new Feature(VOTING, EXPLICIT,false));
            allFeatures.add(new Feature(GLOBALFEATURE1, GLOBAL,false));
            allFeatures.add(new Feature(GLOBALFEATURE2, GLOBAL,false));
            allFeatures.add(new Feature(GLOBALFEATURE3, GLOBAL,false));
        }

        //When
        when(adminService.addNewFeature(newFeature)).thenReturn(allAvailableFeaturesAfterAddingNewFeature);

        /** Reason behind checking the "forwardedURL"
         * The issue is that the absence of @ResponseBody on the controller method completely changes its meaning.
         * Instead of writing to the body of the response, Spring MVC tries to select a view.
         * By default it will try to forward to a JSP view and it's the forward that results in a 404 or other status codes.
         *
         * Spring MVC Test has no way of knowing that you intended to have @ResponseBody but didn't add it so no way to recognize it as an error.
         * The best I can say is test the body of the response for the expected content, or check that response was not forwarded.
         *
         * Reference: https://github.com/spring-projects/spring-framework/issues/15323
         */
        //Then
        mockMvc.perform(post("/admin/addfeature").contentType(MediaType.APPLICATION_JSON)
            .content("{ \"featureName\": \"Library_Service\", \"featureType\": \"Explicit\" }")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(forwardedUrl(null));
    }

    @Test
    @DisplayName("Enable a feature for given valid User Id and if Feature is false")
    public void shouldEnableFeature_IfFeatureExistsForGivenUserId() throws Exception {
        //Given
        String validUserId = "1";
        String featureName = "Transport_Service";

        List<Feature> user1Features = new ArrayList<>(); {
            user1Features.add(new Feature(LIBRARY, EXPLICIT, true));
            user1Features.add(new Feature(BANK, EXPLICIT, true));
            user1Features.add(new Feature(TRANSPORT, EXPLICIT, true));
            user1Features.add(new Feature(VOTING, EXPLICIT, false));
            user1Features.add(new Feature(GLOBALFEATURE1, GLOBAL, true));
        }

        //When
        when(adminService.enableFeature(featureName, validUserId)).thenReturn(user1Features);

        //Then
        mockMvc.perform(post("/admin/enablefeature/"+featureName+"/user/"+validUserId).contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(5)))
            .andExpect(jsonPath("$[0].featureName", is("Library_Service")))
            .andExpect(jsonPath("$[1].featureName", is("Bank_Service")))
            .andExpect(jsonPath("$[2].featureName", is("Transport_Service")))
            .andExpect(jsonPath("$[3].featureName", is("Voting_Service")))
            .andExpect(jsonPath("$[4].featureName", is("Global_Feature_1")));
    }
}
