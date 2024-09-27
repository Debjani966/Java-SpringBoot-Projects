package com.example.IncidentManagementSystem.Project.Util;

public interface ApiPaths {
    String BASE_PATH = "/api";

    interface User {
        String PATH = BASE_PATH + "/users";
        String CREATE= "/create";
        String BY_ID = "/{id}";
        String LOGIN = "/login";
        String CHANGE_PASSWORD = "/changePassword";
        String FORGOT_PASSWORD = "/forgotPassword";
        String RESET_PASSWORD = "/resetPassword";
        String Enable = "/{id}/enable";
        String UPDATE="/update";
    }

    interface Incident{
        String PATH = BASE_PATH + "/incidents";
        String SEARCH_BY_INCIDENT_ID = "/{incidentId}";
        String BY_INCIDENT_ID = "/{incidentId}";
        String USER_ID="/user/{userId}";
        String SEARCH="/search";
        String MY_INCIDENTS="/myIncidents";

    }
}
