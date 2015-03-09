Jexer - Java Text User Interface library
========================================

This library is currently in design, but when finished it is intended
to implement a text-based windowing system loosely reminiscient of
Borland's [Turbo Vision](http://en.wikipedia.org/wiki/Turbo_Vision)
library.  For those wishing to use the actual C++ Turbo Vision
library, see [Sergio Sigala's updated
version](http://tvision.sourceforge.net/) that runs on many more
platforms.


License
-------

This library is licensed LGPL ("GNU Lesser General Public License")
version 3 or greater.  See the file LICENSE for the full license text,
which includes both the GPL v3 and the LGPL supplemental terms.


Usage
-----

The library is currently under initial development, usage patterns are
still being worked on.  Generally the goal will be to build
applications somewhat as follows:

```Java
import jexer.*;

public class MyApplication extends TApplication {

    public MyApplication() {
        super();

        // Create an editor window that has support for
        // copy/paste, search text, arrow keys, horizontal
        // and vertical scrollbar, etc.
        addEditor();

        // Create standard menus for File and Window
        addFileMenu();
        addWindowMenu();
    }

    public static void main(String [] args) {
        MyApplication app = new MyApplication();
        app.run();
    }
}
```


Roadmap
-------

This is a work in progress.  Many tasks remain before calling this
version 1.0:

0.0.1:

- Base classes:
  - Events
  - Backend
    - ECMABackend
  - TApplication loop

0.0.2:

- Get modal messagebox running without fibers
- Port remaining d-tui functionality over
  - All widgets

0.0.3:

- ECMATerminal
  - Mouse 1006 mode parsing
  - Win32 support (used for reading/writing sockets)
- Bugs
  - TDirectoryList cannot be navigated only with keyboard
  - TTreeView cannot be navigated only with keyboard
  - RangeViolation after dragging scrollbar up/down

Wishlist features (2.0):

- TTerminal
  - Handle resize events (pass to child process)
  - xterm mouse handling
- TWindow
  - "Smart placement" for new windows
- Screen
  - Allow complex characters in putCharXY() and detect them in putStrXY().
- TComboBox
- TListBox
- TSpinner
- TCalendar widget
- TColorPicker widget
- Drag and drop
  - TEditor
  - TField
  - TText
  - TTerminal
  - TComboBox
- AWTBackend
- ECMABackend
  - libgpm support