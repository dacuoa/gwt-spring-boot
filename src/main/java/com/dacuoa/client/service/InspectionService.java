package com.dacuoa.client.service;

import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONBoolean;

public class InspectionService {
    private static final String BASE_URL = "http://localhost:8080/api/inspections";

    public interface InspectionCallback {
        void onSuccess(String result);
        void onError(String error);
    }

    public interface SearchCallback {
        void onSuccess(String result);
        void onError(String error);
    }

    public void submitInspection(String inspectorName, String inspectionDate, String bikeSerialNumber,
                                 String frameCondition, String brakes, String tyres, Boolean lightsPresent,
                                 String notes, InspectionCallback callback) {
        JSONObject json = new JSONObject();
        json.put("inspectorName", new JSONString(inspectorName));
        json.put("inspectionDate", new JSONString(inspectionDate));
        json.put("bikeSerialNumber", new JSONString(bikeSerialNumber));
        json.put("frameCondition", new JSONString(frameCondition));
        json.put("brakes", new JSONString(brakes));
        json.put("tyres", new JSONString(tyres));
        json.put("lightsPresent", JSONBoolean.getInstance(lightsPresent));
        json.put("notes", new JSONString(notes));

        RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, BASE_URL);
        builder.setHeader("Content-Type", "application/json");
        builder.setRequestData(json.toString());

        try {
            builder.sendRequest(json.toString(), new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == 200) {
                        callback.onSuccess("Inspection saved");
                    } else {
                        callback.onError("Status: " + response.getStatusCode());
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    callback.onError(exception.getMessage());
                }
            });
        } catch (RequestException e) {
            callback.onError(e.getMessage());
        }
    }

    public void search(String searchTerm, SearchCallback callback) {
        String url = BASE_URL + "/search?q=" + com.google.gwt.http.client.URL.encode(searchTerm);
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

        try {
            builder.sendRequest(null, new RequestCallback() {
                @Override
                public void onResponseReceived(Request request, Response response) {
                    if (response.getStatusCode() == 200) {
                        callback.onSuccess(response.getText());
                    } else {
                        callback.onError("Status: " + response.getStatusCode());
                    }
                }

                @Override
                public void onError(Request request, Throwable exception) {
                    callback.onError(exception.getMessage());
                }
            });
        } catch (RequestException e) {
            callback.onError(e.getMessage());
        }
    }
}
