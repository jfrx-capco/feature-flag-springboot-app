package com.assessment.featureflags.service;

import com.assessment.featureflags.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {
    UserService userService = new UserService();

    @Test
    void testGetAllFeaturesForUserId_InvalidUserId() {
        //Given
        String invalidUserId = "5";

        //When & Then
        Throwable exception = assertThrows(UserNotFoundException.class,
                ()->{userService.getAllFeaturesForUserId(invalidUserId);} );
    }

    @Test
    void testGetAllEnabledFeaturesForUserId_InvalidUserId() {
        //Given
        String invalidUserId = "5";

        //When & Then
        Throwable exception = assertThrows(UserNotFoundException.class,
                ()->{userService.getAllEnabledFeaturesForUserId(invalidUserId);} );
    }
}
