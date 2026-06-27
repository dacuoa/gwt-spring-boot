package com.dacuoa.client.view;

import com.google.gwt.user.client.ui.*;

public class MainView extends Composite implements SearchView.SelectionListener {
    private VerticalPanel mainPanel;
    private FormView formView;
    private SearchView searchView;

    public MainView() {
        mainPanel = new VerticalPanel();
        mainPanel.setWidth("100%");
        formView = new FormView();
        searchView = new SearchView();

        searchView.setSelectionListener(this);

        // Section 1
        VerticalPanel searchSection = new VerticalPanel();
        searchSection.setWidth("100%");
        searchSection.setStyleName("gwt-StackPanelItem");
        Label searchHeader = new Label("Find Inspections");
        searchHeader.setStyleName("gwt-StackPanelItem-header");
        searchSection.add(searchHeader);
        searchSection.add(searchView);
        mainPanel.add(searchSection);

        // Section 2
        VerticalPanel formSection = new VerticalPanel();
        formSection.setWidth("100%");
        formSection.setStyleName("gwt-StackPanelItem");
        Label formHeader = new Label("Add/Edit Inspections");
        formHeader.setStyleName("gwt-StackPanelItem-header");
        formSection.add(formHeader);
        formSection.add(formView);
        mainPanel.add(formSection);

        initWidget(mainPanel);
        setStyleName("main-view");
    }

    @Override
    public void onInspectionSelected(SearchView.InspectionRow row) {
        formView.setCurrentInspectionId(row.getId());
        formView.setInspectorName(row.getInspectorName());
        formView.setInspectionDate(row.getInspectionDate());
        formView.setBikeSerialNumber(row.getBikeSerialNumber());
        formView.setFrameCondition(row.getFrameCondition());
        formView.setBrakes(row.getBrakes());
        formView.setTyres(row.getTyres());
        formView.setLightsPresent(row.getLightsPresent());
        formView.setNotes(row.getNotes());
    }
}
