Jexer - Java Text User Interface library
========================================

WARNING: THIS IS ALPHA CODE!

This library is intended to implement a text-based windowing system
loosely reminiscient of Borland's [Turbo
Vision](http://en.wikipedia.org/wiki/Turbo_Vision) library.  For those
wishing to use the actual C++ Turbo Vision library, see [Sergio
Sigala's updated version](http://tvision.sourceforge.net/) that runs
on many more platforms.

Two backends are available:

* System.in/out to a command-line ECMA-48 / ANSI X3.64 type terminal
  (tested on Linux + xterm).  I/O is handled through terminal escape
  sequences generated by the library itself: ncurses is not required
  or linked to.  xterm mouse tracking using UTF8 coordinates is
  supported.  This is the default backend on non-Windows platforms.

* Java AWT UI.  This backend can be selected by setting
  jexer.AWT=true.  This is the default backend on Windows platforms.
  AWT is experimental, please consider filing bugs when you encounter
  them.  The default window size for AWT is 132x40, which is set in
  jexer.session.AWTSession.

A demo application showing the existing UI controls is available via
'java -jar jexer.jar' or 'java -Djexer.AWT=true -jar jexer.jar' .



License
-------

This project is licensed LGPL ("GNU Lesser General Public License")
version 3 or greater.  See the file LICENSE for the full license text,
which includes both the GPL v3 and the LGPL supplemental terms.



Acknowledgements
----------------

Jexer makes use of the Terminus TrueType font [made available
here](http://files.ax86.net/terminus-ttf/) .



Usage
-----

Usage patterns are still being worked on, but in general the goal will
be to build applications somewhat as follows:

```Java
import jexer.*;

public class MyApplication extends TApplication {

    public MyApplication() {
        super();

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

See the file demos/Demo1.java for detailed examples.



Known Issues / Arbitrary Decisions
----------------------------------

Some arbitrary design decisions had to be made when either the
obviously expected behavior did not happen or when a specification was
ambiguous.  This section describes such issues.

  TTerminalWindow
  ---------------

  - TTerminalWindow will hang on input from the remote if the
    TApplication is exited before closing the TTerminalWindow.  This
    is due to a Java limitation/interaction between blocking reads
    (necessary to get UTF8 translation correct) and file streams.

  - See jexer.tterminal.ECMA48 for more specifics of terminal
    emulation limitations.


Roadmap
-------

Many tasks remain before calling this version 1.0:

0.0.2:

- AWT:
  - Blinking cursor
  - Handle kbTab (disable focus traversal BS)
- ECMA48Backend running on socket
- TTreeView
- TDirectoryList
- TFileOpen
- Decide on naming convention: getText, getValue, getLabel: one or all
  of them?

0.0.3:

- TEditor

0.0.4:

- Bugs
  - TSubMenu keyboard mnemonic not working
  - Making TMenu keyboard accelerators active/inactive
  - TDirectoryList cannot be navigated only with keyboard
  - TTreeView cannot be navigated only with keyboard
  - RangeViolation after dragging scrollbar up/down

0.1.0:

- TWindow
  - "Smart placement" for new windows
- ECMATerminal
  - Mouse 1006 mode parsing

Wishlist features (2.0):

- TTerminal
  - Handle resize events (pass to child process)
  - xterm mouse handling
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


Screenshots
-----------

![Several Windows Open Including A Terminal](/screenshots/screenshot1.png?raw=true "Several Windows Open Including A Terminal")

