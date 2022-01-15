package com.assessment.featureflags.controller;

import com.assessment.featureflags.model.Feature;
import com.assessment.featureflags.service.UserService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Return all Features for valid user ID")
    public void shouldReturnAllFeatures_ForValidUserId() throws Exception {
        //Given
        String validUserId = "1";

        List<Feature> user1Features = new ArrayList<>(); {
            user1Features.add(new Feature(LIBRARY, EXPLICIT, true));
            user1Features.add(new Feature(BANK, EXPLICIT, true));
            user1Features.add(new Feature(TRANSPORT, EXPLICIT, false));
            user1Features.add(new Feature(VOTING, EXPLICIT, false));
            user1Features.add(new Feature(GLOBALFEATURE1, GLOBAL, true));
        }

        //When
        when(userService.getAllFeaturesForUserId(validUserId)).thenReturn(user1Features);

        //Then
        mockMvc.perform(get("/user/"+validUserId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].featureName", is("Library_Service")))
                .andExpect(jsonPath("$[1].featureName", is("Bank_Service")))
                .andExpect(jsonPath("$[2].featureName", is("Transport_Service")))
                .andExpect(jsonPath("$[3].featureName", is("Voting_Service")))
                .andExpect(jsonPath("$[4].featureName", is("Global_Feature_1")));
    }

    @Test
    @DisplayName("Do not return Features for Invalid user ID")
    public void shouldReturnAllFeatures_ForInvalidUserId() throws Exception {
        //Given
        String invalidUserId = "abc";

        //When
        when(userService.getAllFeaturesForUserId(invalidUserId)).thenReturn(null);

        /** Reason behind checking the "forwardedURL"
         * The issue is that the absence of @ResponseBody on the controller method completely changes its meaning.
         * Instead of writing to the body of the response, Spring MVC tries to select a view.
         * By default it will try to forward to a JSP view and it's the forward that results in a 404.
         *
         * Spring MVC Test has no way of knowing that you intended to have @ResponseBody but didn't add it so no way to recognize it as an error.
         * The best I can say is test the body of the response for the expected content, or check that response was not forwarded.
         *
         * Reference: https://github.com/spring-projects/spring-framework/issues/15323
         */
        //Then
        mockMvc.perform(get("/user/"+invalidUserId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(null));
    }

    @Test
    @DisplayName("Return all Enabled Features for valid user ID")
    public void shouldReturnAllEnabledFeatures_ForValidUserId() throws Exception {
        //Given
        String validUserId = "1";

        List<Feature> user1AllFeatures = new ArrayList<>(); {
            user1AllFeatures.add(new Feature(LIBRARY, EXPLICIT, true));
            user1AllFeatures.add(new Feature(BANK, EXPLICIT, true));
            user1AllFeatures.add(new Feature(TRANSPORT, EXPLICIT, false));
            user1AllFeatures.add(new Feature(VOTING, EXPLICIT, false));
            user1AllFeatures.add(new Feature(GLOBALFEATURE1, GLOBAL, true));
        }

        List<Feature> user1EnabledFeatures = new ArrayList<>();
        for(Feature feature: user1AllFeatures) {
            if(feature.isEnabled() == true) {
                user1EnabledFeatures.add(feature);
            }
        }

        //When
        when(userService.getAllEnabledFeaturesForUserId(validUserId)).thenReturn(user1EnabledFeatures);

        //Then
        mockMvc.perform(get("/user/"+validUserId+"/enabledFeatures").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].featureName", is("Library_Service")))
                .andExpect(jsonPath("$[1].featureName", is("Bank_Service")))
                .andExpect(jsonPath("$[2].featureName", is("Global_Feature_1")));
    }

    @Test
    @DisplayName("Do not return all Enabled Features for invalid user ID")
    public void shouldReturnAllEnabledFeatures_ForInvalidUserId() throws Exception {
        //Given
        String invalidUserId = "abc";

        //When
        when(userService.getAllFeaturesForUserId(invalidUserId)).thenReturn(null);

        /** Reason behind checking the "forwardedURL"
         * The issue is that the absence of @ResponseBody on the controller method completely changes its meaning.
         * Instead of writing to the body of the response, Spring MVC tries to select a view.
         * By default it will try to forward to a JSP view and it's the forward that results in a 404.
         *
         * Spring MVC Test has no way of knowing that you intended to have @ResponseBody but didn't add it so no way to recognize it as an error.
         * The best I can say is test the body of the response for the expected content, or check that response was not forwarded.
         *
         * Reference: https://github.com/spring-projects/spring-framework/issues/15323
         */
        //Then
        mockMvc.perform(get("/user/"+invalidUserId+"/enabledFeatures").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl(null));
    }
}
