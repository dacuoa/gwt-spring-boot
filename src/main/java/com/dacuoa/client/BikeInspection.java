package com.dacuoa.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.dacuoa.client.view.MainView;

public class BikeInspection implements EntryPoint {
    @Override
    public void onModuleLoad() {
        MainView mainView = new MainView();
        RootPanel.get("app").add(mainView);
    }
}
