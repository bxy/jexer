package bxy.jexer;

import jexer.TApplication;
import jexer.TEditColorThemeWindow;

public class PlaygroundApp extends TApplication {
    public PlaygroundApp(final BackendType backendType) throws Exception {
        super(backendType);
        getBackend().setTitle("Playground Application");

        addFileMenu();
        addWindowMenu();

        new PlaygroundWindow(this, 40, 10);
        new TAsciiChartWindow(this);
        new TEditColorThemeWindow(this);




    }

}
