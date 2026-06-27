package com.dacuoa.client.view;

import com.google.gwt.user.client.ui.*;

public class MainView extends Composite {
    private StackPanel stackPanel;
    private FormView formView;
    private SearchView searchView;

    public MainView() {
        stackPanel = new StackPanel();
        formView = new FormView();
        searchView = new SearchView();

        stackPanel.add(formView, "Inspection Form");
        stackPanel.add(searchView, "Search Results");
        stackPanel.showStack(0);

        initWidget(stackPanel);
        setStyleName("main-view");
    }
}
