# Read Me First
The following was discovered as part of building this project:

* The original package name 'com.assessment.feature-flags' is invalid and this project uses 'com.assessment.featureflags' instead.

# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.6.2/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.6.2/gradle-plugin/reference/html/#build-image)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#boot-features-developing-web-applications)
* [Testcontainers](https://www.testcontainers.org/)
* [Spring Security](https://docs.spring.io/spring-boot/docs/2.6.2/reference/htmlsingle/#boot-features-security)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

##############################################################################################################################################

# Feature Flag REST service

### Overview
This is a Spring boot/Java 8 based RESTful service with gradle build engine. This service focuses on below scenarios:
We’d like to create a feature flag service using Spring in Java. Here are the requirements for this service:
* As an admin user, I want to be able to create a feature which defaults to disabled 
* As an admin user, I want to be able to switch on a feature for a user
* As a user, I want to be able to get all the enabled features (a mix of all the globally enabled ones and the ones enabled just for my user)
•••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••••


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

## REFER "CommonUtil.java" file for hardcoded data. No DB configuration is in place for this app.
## Test files are present in the package: "test/java/com.assessment.featureflags/".
## No security has been added to this app.