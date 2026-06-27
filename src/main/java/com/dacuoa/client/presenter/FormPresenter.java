package com.dacuoa.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasText;
import com.dacuoa.client.service.InspectionService;

public class FormPresenter {
    public interface Display {
        String getInspectorName();
        void setInspectorName(String name);
        String getInspectionDate();
        void setInspectionDate(String date);
        String getBikeSerialNumber();
        void setBikeSerialNumber(String serial);
        String getFrameCondition();
        void setFrameCondition(String condition);
        String getBrakes();
        void setBrakes(String brakes);
        String getTyres();
        void setTyres(String tyres);
        Boolean getLightsPresent();
        void setLightsPresent(Boolean present);
        String getNotes();
        void setNotes(String notes);
        Button getSubmitButton();
        void setStatusMessage(String message);
        void clearForm();
    }

    private Display display;
    private InspectionService service;

    public FormPresenter(Display display) {
        this.display = display;
        this.service = new InspectionService();
        bindEvents();
    }

    private void bindEvents() {
        display.getSubmitButton().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                handleSubmit();
            }
        });
    }

    private void handleSubmit() {
        String inspectorName = display.getInspectorName();
        String inspectionDate = display.getInspectionDate();
        String bikeSerialNumber = display.getBikeSerialNumber();
        String frameCondition = display.getFrameCondition();
        String brakes = display.getBrakes();
        String tyres = display.getTyres();
        Boolean lightsPresent = display.getLightsPresent();
        String notes = display.getNotes();

        if (inspectorName.isEmpty() || inspectionDate.isEmpty() || bikeSerialNumber.isEmpty()) {
            display.setStatusMessage("Please fill in all required fields");
            return;
        }

        service.submitInspection(inspectorName, inspectionDate, bikeSerialNumber, frameCondition,
                brakes, tyres, lightsPresent, notes, new InspectionService.InspectionCallback() {
                    @Override
                    public void onSuccess(String result) {
                        display.setStatusMessage("Inspection submitted successfully!");
                        display.clearForm();
                    }

                    @Override
                    public void onError(String error) {
                        display.setStatusMessage("Error: " + error);
                    }
                });
    }
}
