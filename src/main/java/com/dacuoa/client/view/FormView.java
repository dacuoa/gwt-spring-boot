package com.dacuoa.client.view;

import com.google.gwt.user.client.ui.*;
import com.dacuoa.client.presenter.FormPresenter;

public class FormView extends Composite implements FormPresenter.Display {
    private FormPresenter presenter;
    private TextBox inspectorNameBox;
    private TextBox inspectionDateBox;
    private TextBox bikeSerialNumberBox;
    private ListBox frameConditionBox;
    private ListBox brakesBox;
    private ListBox tyresBox;
    private CheckBox lightsPresentBox;
    private TextArea notesBox;
    private Button submitButton;
    private Label statusLabel;

    public FormView() {
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setStyleName("form-panel");

        // Inspector Name
        HorizontalPanel inspectorPanel = new HorizontalPanel();
        inspectorPanel.add(new Label("Inspector Name:"));
        inspectorNameBox = new TextBox();
        inspectorNameBox.setWidth("300px");
        inspectorPanel.add(inspectorNameBox);
        mainPanel.add(inspectorPanel);

        // Inspection Date
        HorizontalPanel datePanel = new HorizontalPanel();
        datePanel.add(new Label("Inspection Date (YYYY-MM-DD):"));
        inspectionDateBox = new TextBox();
        inspectionDateBox.setWidth("300px");
        datePanel.add(inspectionDateBox);
        mainPanel.add(datePanel);

        // Bike Serial Number
        HorizontalPanel serialPanel = new HorizontalPanel();
        serialPanel.add(new Label("Bike Serial Number:"));
        bikeSerialNumberBox = new TextBox();
        bikeSerialNumberBox.setWidth("300px");
        serialPanel.add(bikeSerialNumberBox);
        mainPanel.add(serialPanel);

        // Frame Condition
        HorizontalPanel framePanel = new HorizontalPanel();
        framePanel.add(new Label("Frame Condition:"));
        frameConditionBox = new ListBox();
        frameConditionBox.addItem("Good");
        frameConditionBox.addItem("Fair");
        frameConditionBox.addItem("Poor");
        framePanel.add(frameConditionBox);
        mainPanel.add(framePanel);

        // Brakes
        HorizontalPanel brakesPanel = new HorizontalPanel();
        brakesPanel.add(new Label("Brakes:"));
        brakesBox = new ListBox();
        brakesBox.addItem("Good");
        brakesBox.addItem("Fair");
        brakesBox.addItem("Poor");
        brakesPanel.add(brakesBox);
        mainPanel.add(brakesPanel);

        // Tyres
        HorizontalPanel tyresPanel = new HorizontalPanel();
        tyresPanel.add(new Label("Tyres:"));
        tyresBox = new ListBox();
        tyresBox.addItem("Good");
        tyresBox.addItem("Fair");
        tyresBox.addItem("Poor");
        tyresPanel.add(tyresBox);
        mainPanel.add(tyresPanel);

        // Lights
        HorizontalPanel lightsPanel = new HorizontalPanel();
        lightsPresentBox = new CheckBox("Lights Present");
        lightsPanel.add(lightsPresentBox);
        mainPanel.add(lightsPanel);

        // Notes
        mainPanel.add(new Label("Notes:"));
        notesBox = new TextArea();
        notesBox.setWidth("400px");
        notesBox.setHeight("100px");
        mainPanel.add(notesBox);

        // Submit Button
        submitButton = new Button("Submit Inspection");
        mainPanel.add(submitButton);

        // Status Label
        statusLabel = new Label();
        statusLabel.setStyleName("status-label");
        mainPanel.add(statusLabel);

        // Wrap in ScrollPanel
        ScrollPanel scrollPanel = new ScrollPanel(mainPanel);
        initWidget(scrollPanel);
        setStyleName("form-view");

        presenter = new FormPresenter(this);
    }

    @Override
    public String getInspectorName() {
        return inspectorNameBox.getValue();
    }

    @Override
    public void setInspectorName(String name) {
        inspectorNameBox.setValue(name);
    }

    @Override
    public String getInspectionDate() {
        return inspectionDateBox.getValue();
    }

    @Override
    public void setInspectionDate(String date) {
        inspectionDateBox.setValue(date);
    }

    @Override
    public String getBikeSerialNumber() {
        return bikeSerialNumberBox.getValue();
    }

    @Override
    public void setBikeSerialNumber(String serial) {
        bikeSerialNumberBox.setValue(serial);
    }

    @Override
    public String getFrameCondition() {
        return frameConditionBox.getValue(frameConditionBox.getSelectedIndex());
    }

    @Override
    public void setFrameCondition(String condition) {
        for (int i = 0; i < frameConditionBox.getItemCount(); i++) {
            if (frameConditionBox.getValue(i).equals(condition)) {
                frameConditionBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public String getBrakes() {
        return brakesBox.getValue(brakesBox.getSelectedIndex());
    }

    @Override
    public void setBrakes(String brakes) {
        for (int i = 0; i < brakesBox.getItemCount(); i++) {
            if (brakesBox.getValue(i).equals(brakes)) {
                brakesBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public String getTyres() {
        return tyresBox.getValue(tyresBox.getSelectedIndex());
    }

    @Override
    public void setTyres(String tyres) {
        for (int i = 0; i < tyresBox.getItemCount(); i++) {
            if (tyresBox.getValue(i).equals(tyres)) {
                tyresBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public Boolean getLightsPresent() {
        return lightsPresentBox.getValue();
    }

    @Override
    public void setLightsPresent(Boolean present) {
        lightsPresentBox.setValue(present);
    }

    @Override
    public String getNotes() {
        return notesBox.getValue();
    }

    @Override
    public void setNotes(String notes) {
        notesBox.setValue(notes);
    }

    @Override
    public Button getSubmitButton() {
        return submitButton;
    }

    @Override
    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    @Override
    public void clearForm() {
        inspectorNameBox.clear();
        inspectionDateBox.clear();
        bikeSerialNumberBox.clear();
        frameConditionBox.setSelectedIndex(0);
        brakesBox.setSelectedIndex(0);
        tyresBox.setSelectedIndex(0);
        lightsPresentBox.setValue(false);
        notesBox.clear();
        statusLabel.setText("");
    }
}
