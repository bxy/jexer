package bxy.jexer;

import jexer.TApplication;
import jexer.TEditColorThemeWindow;
import jexer.backend.SwingTerminal;
import jexer.event.TMenuEvent;
import jexer.menu.TMenu;

public class PlaygroundApp extends TApplication {
    public PlaygroundApp(final BackendType backendType) throws Exception {
        super(backendType);
        getBackend().setTitle("Playground Application");

        addFileMenu();
        addWindowMenu();

        if (getScreen() instanceof SwingTerminal) {
            TMenu swingMenu = addMenu("Swin&g");
            swingMenu.addItem(3000, "&Bigger +2");
            swingMenu.addItem(3001, "&Smaller -2");
        }

        new PlaygroundWindow(this, 60, 16);
        new TAsciiChartWindow(this);
        new ColorPickerWindow(this, 60, 16);
//        new TEditColorThemeWindow(this);

    }

    @Override
    protected boolean onMenu(TMenuEvent menu) {
        if (menu.getId() == 3000) {
            // Bigger +2
            assert (getScreen() instanceof SwingTerminal);
            SwingTerminal terminal = (SwingTerminal) getScreen();
            terminal.setFontSize(terminal.getFontSize() + 2);
            return true;
        }
        if (menu.getId() == 3001) {
            // Smaller -2
            assert (getScreen() instanceof SwingTerminal);
            SwingTerminal terminal = (SwingTerminal) getScreen();
            terminal.setFontSize(terminal.getFontSize() - 2);
            return true;
        }
        return super.onMenu(menu);
    }
}
