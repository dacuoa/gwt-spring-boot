package com.dacuoa.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.json.client.*;
import com.dacuoa.client.service.InspectionService;
import com.dacuoa.client.view.SearchView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenter {
    public interface Display {
        String getSearchTerm();
        Button getSearchButton();
        void setStatusMessage(String message);
        void displayResults(List<SearchView.InspectionRow> results);
    }

    private Display display;
    private InspectionService service;

    public SearchPresenter(Display display) {
        this.display = display;
        this.service = new InspectionService();
        bindEvents();
    }

    private void bindEvents() {
        display.getSearchButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleSearch();
            }
        });
    }

    private void handleSearch() {
        String searchTerm = display.getSearchTerm();
        if (searchTerm.isEmpty()) {
            display.setStatusMessage("Please enter a search term");
            return;
        }

        display.setStatusMessage("Searching...");
        service.search(searchTerm, new InspectionService.SearchCallback() {
            @Override
            public void onSuccess(String result) {
                parseAndDisplayResults(result);
            }

            @Override
            public void onError(String error) {
                display.setStatusMessage("Error: " + error);
            }
        });
    }

    private void parseAndDisplayResults(String jsonText) {
        List<SearchView.InspectionRow> rows = new ArrayList<SearchView.InspectionRow>();
        try {
            JSONArray array = JSONParser.parseStrict(jsonText).isArray();
            if (array != null) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject obj = array.get(i).isObject();
                    if (obj != null) {
                        Long id = (long) obj.get("id").isNumber().doubleValue();
                        String inspectorName = obj.get("inspectorName").isString().stringValue();
                        String inspectionDate = obj.get("inspectionDate").isString().stringValue();
                        String bikeSerialNumber = obj.get("bikeSerialNumber").isString().stringValue();
                        String frameCondition = obj.get("frameCondition").isString().stringValue();
                        String brakes = obj.get("brakes").isString().stringValue();
                        String tyres = obj.get("tyres").isString().stringValue();
                        Boolean lightsPresent = obj.get("lightsPresent").isBoolean().booleanValue();
                        String notes = obj.get("notes").isString().stringValue();

                        rows.add(new SearchView.InspectionRow(id, inspectorName, inspectionDate, bikeSerialNumber,
                                frameCondition, brakes, tyres, lightsPresent, notes));
                    }
                }
                display.displayResults(rows);
                display.setStatusMessage("Found " + rows.size() + " results");
            }
        } catch (Exception e) {
            display.setStatusMessage("Error parsing results: " + e.getMessage());
        }
    }
}
