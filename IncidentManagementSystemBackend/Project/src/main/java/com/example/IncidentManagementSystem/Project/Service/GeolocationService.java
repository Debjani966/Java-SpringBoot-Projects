package com.example.IncidentManagementSystem.Project.Service;

import com.example.IncidentManagementSystem.Project.Exception.ExternalServiceException;
import com.example.IncidentManagementSystem.Project.Exception.InvalidPinCodeException;
import com.example.IncidentManagementSystem.Project.DTO.LocationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

@Service
public class GeolocationService {

    private static final String GEO_API_URL = "https://api.zippopotam.us/in/{pinCode}";
    public LocationResponse getLocationByPinCode(String pinCode) {
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(GEO_API_URL, LocationResponse.class, pinCode);
        } catch (HttpClientErrorException e) {
            throw new InvalidPinCodeException("Invalid pin code: " + pinCode);
        } catch (ResourceAccessException e) {
            throw new ExternalServiceException("Failed to connect to the geolocation service.");
        } catch (Exception e) {
            throw new ExternalServiceException("An unexpected error occurred while fetching location.");
        }
    }
}
