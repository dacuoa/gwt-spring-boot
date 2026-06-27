package com.dacuoa.client.view;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.dacuoa.client.presenter.FormPresenter;

public class FormView extends Composite implements FormPresenter.Display {
    private FormPresenter presenter;
    private ListBox inspectorNameBox;
    private DateBox inspectionDateBox;
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
        mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
        mainPanel.setStyleName("form-panel");

        // Inspector Name
        FlowPanel inspectorPanel = new FlowPanel();
        inspectorPanel.setStyleName("field-container");
        InlineLabel inspectorLabel = new InlineLabel("Inspector Name:");
        inspectorLabel.setStyleName("field-label");
        inspectorPanel.add(inspectorLabel);
        inspectorNameBox = new ListBox();
        inspectorNameBox.addItem("Alice");
        inspectorNameBox.addItem("Bob");
        inspectorNameBox.addItem("Charlie");
        inspectorNameBox.setWidth("300px");
        inspectorPanel.add(inspectorNameBox);
        mainPanel.add(inspectorPanel);

        // Inspection Date
        FlowPanel datePanel = new FlowPanel();
        datePanel.setStyleName("field-container");
        InlineLabel dateLabel = new InlineLabel("Inspection Date (YYYY-MM-DD):");
        dateLabel.setStyleName("field-label");
        datePanel.add(dateLabel);
        DateTimeFormat dateFormat = DateTimeFormat.getFormat("yyyy-MM-dd");
        inspectionDateBox = new DateBox();
        inspectionDateBox.setFormat(new DateBox.DefaultFormat(dateFormat));
        inspectionDateBox.setWidth("300px");
        datePanel.add(inspectionDateBox);
        mainPanel.add(datePanel);

        // Bike Serial Number
        FlowPanel serialPanel = new FlowPanel();
        serialPanel.setStyleName("field-container");
        InlineLabel serialLabel = new InlineLabel("Bike Serial Number:");
        serialLabel.setStyleName("field-label");
        serialPanel.add(serialLabel);
        bikeSerialNumberBox = new TextBox();
        bikeSerialNumberBox.setWidth("300px");
        serialPanel.add(bikeSerialNumberBox);
        mainPanel.add(serialPanel);

        // Frame Condition
        FlowPanel framePanel = new FlowPanel();
        framePanel.setStyleName("field-container");
        InlineLabel frameLabel = new InlineLabel("Frame Condition:");
        frameLabel.setStyleName("field-label");
        framePanel.add(frameLabel);
        frameConditionBox = new ListBox();
        frameConditionBox.addItem("Good");
        frameConditionBox.addItem("Fair");
        frameConditionBox.addItem("Poor");
        framePanel.add(frameConditionBox);
        mainPanel.add(framePanel);

        // Brakes
        FlowPanel brakesPanel = new FlowPanel();
        brakesPanel.setStyleName("field-container");
        InlineLabel brakesLabel = new InlineLabel("Brakes:");
        brakesLabel.setStyleName("field-label");
        brakesPanel.add(brakesLabel);
        brakesBox = new ListBox();
        brakesBox.addItem("Good");
        brakesBox.addItem("Fair");
        brakesBox.addItem("Poor");
        brakesPanel.add(brakesBox);
        mainPanel.add(brakesPanel);

        // Tyres
        FlowPanel tyresPanel = new FlowPanel();
        tyresPanel.setStyleName("field-container");
        InlineLabel tyresLabel = new InlineLabel("Tyres:");
        tyresLabel.setStyleName("field-label");
        tyresPanel.add(tyresLabel);
        tyresBox = new ListBox();
        tyresBox.addItem("Good");
        tyresBox.addItem("Fair");
        tyresBox.addItem("Poor");
        tyresPanel.add(tyresBox);
        mainPanel.add(tyresPanel);

        // Lights
        FlowPanel lightsPanel = new FlowPanel();
        lightsPanel.setStyleName("field-container");
        InlineLabel lightsLabel = new InlineLabel("Lights Present:");
        lightsLabel.setStyleName("field-label");
        lightsLabel.addStyleName("lights-label");
        lightsPanel.add(lightsLabel);
        lightsPresentBox = new CheckBox();
        lightsPanel.add(lightsPresentBox);
        mainPanel.add(lightsPanel);

        // Notes
        FlowPanel notesPanel = new FlowPanel();
        notesPanel.setStyleName("field-container");
        InlineLabel notesLabel = new InlineLabel("Notes:");
        notesLabel.setStyleName("field-label");
        notesPanel.add(notesLabel);
        notesBox = new TextArea();
        notesBox.setWidth("400px");
        notesBox.setHeight("100px");
        notesPanel.add(notesBox);
        mainPanel.add(notesPanel);

        // Submit Button
        submitButton = new Button("Submit Inspection");
        submitButton.setStyleName("submit-button");
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
        return inspectorNameBox.getValue(inspectorNameBox.getSelectedIndex());
    }

    @Override
    public void setInspectorName(String name) {
        for (int i = 0; i < inspectorNameBox.getItemCount(); i++) {
            if (inspectorNameBox.getValue(i).equals(name)) {
                inspectorNameBox.setSelectedIndex(i);
                break;
            }
        }
    }

    @Override
    public String getInspectionDate() {
        java.util.Date date = inspectionDateBox.getValue();
        return (date == null) ? "" : DateTimeFormat.getFormat("yyyy-MM-dd").format(date);
    }

    @Override
    public void setInspectionDate(String date) {
        if (date == null || date.isEmpty()) {
            inspectionDateBox.setValue(null);
        } else {
            try {
                java.util.Date d = DateTimeFormat.getFormat("yyyy-MM-dd").parse(date);
                inspectionDateBox.setValue(d);
            } catch (Exception e) {
                inspectionDateBox.setValue(null);
            }
        }
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
        inspectorNameBox.setSelectedIndex(0);
        inspectionDateBox.setValue(null);
        bikeSerialNumberBox.setValue("");
        frameConditionBox.setSelectedIndex(0);
        brakesBox.setSelectedIndex(0);
        tyresBox.setSelectedIndex(0);
        lightsPresentBox.setValue(false);
        notesBox.setValue("");
        statusLabel.setText("");
    }
}
