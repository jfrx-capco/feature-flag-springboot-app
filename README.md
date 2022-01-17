# Feature Flag REST service

### Overview
This is a Spring boot/Java 8 based RESTful service with gradle build engine. This service focuses on below scenarios:
We’d like to create a feature flag service using Spring in Java. Here are the requirements for this service:
* As an admin user, I want to be able to create a feature which defaults to disabled 
* As an admin user, I want to be able to switch on a feature for a user
* As a user, I want to be able to get all the enabled features (a mix of all the globally enabled ones and the ones enabled just for my user)
•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

### How To build the app
Follow the steps below to build this spring boot application.
* Clone the project from here: https://github.com/jfrx-capco/feature-flag-springboot-app
* Import the project in you preferred IDE (IntelliJ IDE recommended)
* Build the project via the IDE or through CLI using the command "gradle build"
* Once the project is built successfully, right-click on the file "FeatureFlagsApplication.java", and click on "Run ....". This will start the application and you will see below messages in your console:
  ```2022-01-17 11:07:21.559  INFO 13280 --- [           main] c.a.f.FeatureFlagsApplication            : Starting FeatureFlagsApplication using Java 1.8.0_312 on C02G21CCMD6P with PID 13280 (/Users/jfrx/Desktop/feature-flags/build/classes/java/main started by jfrx in /Users/jfrx/Desktop/feature-flags)
  2022-01-17 11:07:21.562  INFO 13280 --- [           main] c.a.f.FeatureFlagsApplication            : No active profile set, falling back to default profiles: default
  2022-01-17 11:07:22.441  INFO 13280 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
  2022-01-17 11:07:22.454  INFO 13280 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
  2022-01-17 11:07:22.454  INFO 13280 --- [           main] org.apache.catalina.core.StandardEngine  : Starting Servlet engine: [Apache Tomcat/9.0.56]
  2022-01-17 11:07:22.557  INFO 13280 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
  2022-01-17 11:07:22.557  INFO 13280 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 947 ms
  2022-01-17 11:07:22.925  INFO 13280 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
  2022-01-17 11:07:22.934  INFO 13280 --- [           main] c.a.f.FeatureFlagsApplication            : Started FeatureFlagsApplication in 1.832 seconds (JVM running for 2.657)```
* This above console logs confirm that the app has started successfully. You can also start the app in debug mode as well by following the above step, and clicking on "Debug..." option rather than the "Run..." option.
* To test the below offered APIs, you can make use of any REST clients, example: Insomnia, POSTMAN, etc.
•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

## REST APIs in detail
### ADMIN APIs
####1. As an Admin, get all available Features
####URL: http://localhost:8080/admin/allfeatures
####Method: GET
####Output JSON: All pre hardcoded, available feature list
[
   {
   "featureName": "Library_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Bank_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Transport_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Voting_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Global_Feature_1",
   "featureType": "Global",
   "enabled": false
   },
   {
   "featureName": "Global_Feature_2",
   "featureType": "Global",
   "enabled": false
   },
   {
   "featureName": "Global_Feature_3",
   "featureType": "Global",
   "enabled": false
   }
   ]
•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

####2. As an Admin, add a new feature
####URL: http://localhost:8080/admin/addfeature
####Method: POST
####Input JSON: 
  {
   "featureName": "Private_Service",
   "featureType": "Explicit"
   } 
####Output JSON: All feature list, with the newly added feature in it
   [
   {
   "featureName": "Library_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Bank_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Transport_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Voting_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Global_Feature_1",
   "featureType": "Global",
   "enabled": false
   },
   {
   "featureName": "Global_Feature_2",
   "featureType": "Global",
   "enabled": false
   },
   {
   "featureName": "Global_Feature_3",
   "featureType": "Global",
   "enabled": false
   },
   {
   "featureName": "Private_Service",
   "featureType": "Explicit",
   "enabled": false
   }
   ]
•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

####3. As an Admin, enable a feature for a user
####URL: http://localhost:8080/admin/enablefeature/<FEATURE_NAME>/user/<USER_ID>
####Method: POST
####Pre-available hardcoded FEATURE_NAMES = [Library_Service, Bank_Service, Transport_Service, Voting_Service, Global_Feature_1, Global_Feature_2, Global_Feature_3, <ANY_NEW_SERVICE_ADDED_BY_ADMIN>]
####Pre-available hardcoded USER_ID = [1, 2, 3]
####Output JSON: All Feature list, with that one feature being enabled
   [
   {
   "featureName": "Library_Service",
   "featureType": "Explicit",
   "enabled": true
   },
   {
   "featureName": "Bank_Service",
   "featureType": "Explicit",
   "enabled": true
   },
   {
   "featureName": "Transport_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Voting_Service",
   "featureType": "Explicit",
   "enabled": true
   },
   {
   "featureName": "Global_Feature_1",
   "featureType": "Global",
   "enabled": true
   } 
####NOTE: If FEATURE_NAME is already enabled, it throws "FeatureAlreadyEnabledException" exception and returns a 400 HTTP STATUS code.
•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

####4. As a User, get all features available for the user
####URL: http://localhost:8080/user/<USER_ID>
####Method: GET
####Pre-available hardcoded USER_ID = [1, 2, 3]
####Output JSON: All feature list for the user (mixed - enabled and disabled, global and explicit)
   [
   {
   "featureName": "Library_Service",
   "featureType": "Explicit",
   "enabled": true
   },
   {
   "featureName": "Bank_Service",
   "featureType": "Explicit",
   "enabled": true
   },
   {
   "featureName": "Transport_Service",
   "featureType": "Explicit",
   "enabled": false
   },
   {
   "featureName": "Voting_Service",
   "featureType": "Explicit",
   "enabled": true
   },
   {
   "featureName": "Global_Feature_1",
   "featureType": "Global",
   "enabled": true
   }
   ]
####NOTE: If USER_ID is invalid(other than 1, 2 or 3), it throws "UserNotFoundException" exception and returns a 404 HTTP STATUS code.
•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

####5. As a User, get all enabled-only (global as well as explicit) features available for the user
####URL: http://localhost:8080/user/<USER_ID>/enabledFeatures
####Method: GET
####Pre-available hardcoded USER_ID = [1, 2, 3]
####Output JSON: All enabled features only for the user (mixed - global and explicit)
   [
   {
   "featureName": "Library_Service",
   "featureType": "Explicit",
   "enabled": true
   },
   {
   "featureName": "Global_Feature_1",
   "featureType": "Global",
   "enabled": true
   },
   {
   "featureName": "Global_Feature_2",
   "featureType": "Global",
   "enabled": true
   }
   ]
####NOTE: If USER_ID is invalid(other than 1, 2 or 3), it throws "UserNotFoundException" exception and returns a 404 HTTP STATUS code.   
•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••

## REFER "CommonUtil.java" file for hardcoded static data. No DB configuration is in place for this app yet.
## Test files are present in the package: "test/java/com.assessment.featureflags/".
## No security has been added to this app yet.