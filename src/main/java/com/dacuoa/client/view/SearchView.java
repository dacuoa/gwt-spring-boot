package com.dacuoa.client.view;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.dacuoa.client.presenter.SearchPresenter;

public class SearchView extends Composite implements SearchPresenter.Display {
    private SearchPresenter presenter;
    private TextBox searchBox;
    private Button searchButton;
    private CellTable<InspectionRow> resultsTable;
    private Label statusLabel;

    public SearchView() {
        VerticalPanel mainPanel = new VerticalPanel();
        mainPanel.setStyleName("search-panel");

        // Search Input
        HorizontalPanel searchPanel = new HorizontalPanel();
        searchPanel.add(new Label("Search (by Serial Number or Inspector Name):"));
        searchBox = new TextBox();
        searchBox.setWidth("300px");
        searchPanel.add(searchBox);

        searchButton = new Button("Search");
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel);

        // Status Label
        statusLabel = new Label();
        statusLabel.setStyleName("status-label");
        mainPanel.add(statusLabel);

        // Results Table
        resultsTable = new CellTable<InspectionRow>();
        resultsTable.setWidth("100%", true);

        // Define columns
        TextColumn<InspectionRow> idColumn = new TextColumn<InspectionRow>() {
            @Override
            public String getValue(InspectionRow object) {
                return String.valueOf(object.getId());
            }
        };
        resultsTable.addColumn(idColumn, "ID");

        TextColumn<InspectionRow> inspectorColumn = new TextColumn<InspectionRow>() {
            @Override
            public String getValue(InspectionRow object) {
                return object.getInspectorName();
            }
        };
        resultsTable.addColumn(inspectorColumn, "Inspector");

        TextColumn<InspectionRow> serialColumn = new TextColumn<InspectionRow>() {
            @Override
            public String getValue(InspectionRow object) {
                return object.getBikeSerialNumber();
            }
        };
        resultsTable.addColumn(serialColumn, "Bike Serial");

        TextColumn<InspectionRow> dateColumn = new TextColumn<InspectionRow>() {
            @Override
            public String getValue(InspectionRow object) {
                return object.getInspectionDate();
            }
        };
        resultsTable.addColumn(dateColumn, "Date");

        TextColumn<InspectionRow> frameColumn = new TextColumn<InspectionRow>() {
            @Override
            public String getValue(InspectionRow object) {
                return object.getFrameCondition();
            }
        };
        resultsTable.addColumn(frameColumn, "Frame");

        TextColumn<InspectionRow> brakesColumn = new TextColumn<InspectionRow>() {
            @Override
            public String getValue(InspectionRow object) {
                return object.getBrakes();
            }
        };
        resultsTable.addColumn(brakesColumn, "Brakes");

        TextColumn<InspectionRow> tyresColumn = new TextColumn<InspectionRow>() {
            @Override
            public String getValue(InspectionRow object) {
                return object.getTyres();
            }
        };
        resultsTable.addColumn(tyresColumn, "Tyres");

        TextColumn<InspectionRow> lightsColumn = new TextColumn<InspectionRow>() {
            @Override
            public String getValue(InspectionRow object) {
                return object.getLightsPresent() ? "Yes" : "No";
            }
        };
        resultsTable.addColumn(lightsColumn, "Lights");

        ScrollPanel scrollPanel = new ScrollPanel(resultsTable);
        scrollPanel.setHeight("400px");
        mainPanel.add(scrollPanel);

        initWidget(mainPanel);
        setStyleName("search-view");

        presenter = new SearchPresenter(this);
    }

    @Override
    public String getSearchTerm() {
        return searchBox.getValue();
    }

    @Override
    public Button getSearchButton() {
        return searchButton;
    }

    @Override
    public void setStatusMessage(String message) {
        statusLabel.setText(message);
    }

    @Override
    public void displayResults(java.util.List<InspectionRow> results) {
        resultsTable.setRowData(results);
    }

    public static class InspectionRow {
        private Long id;
        private String inspectorName;
        private String inspectionDate;
        private String bikeSerialNumber;
        private String frameCondition;
        private String brakes;
        private String tyres;
        private Boolean lightsPresent;
        private String notes;

        public InspectionRow(Long id, String inspectorName, String inspectionDate, String bikeSerialNumber,
                             String frameCondition, String brakes, String tyres, Boolean lightsPresent, String notes) {
            this.id = id;
            this.inspectorName = inspectorName;
            this.inspectionDate = inspectionDate;
            this.bikeSerialNumber = bikeSerialNumber;
            this.frameCondition = frameCondition;
            this.brakes = brakes;
            this.tyres = tyres;
            this.lightsPresent = lightsPresent;
            this.notes = notes;
        }

        public Long getId() { return id; }
        public String getInspectorName() { return inspectorName; }
        public String getInspectionDate() { return inspectionDate; }
        public String getBikeSerialNumber() { return bikeSerialNumber; }
        public String getFrameCondition() { return frameCondition; }
        public String getBrakes() { return brakes; }
        public String getTyres() { return tyres; }
        public Boolean getLightsPresent() { return lightsPresent; }
        public String getNotes() { return notes; }
    }
}
