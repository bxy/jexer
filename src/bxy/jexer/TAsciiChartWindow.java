package bxy.jexer;

import jexer.TAction;
import jexer.TApplication;
import jexer.TLabel;
import jexer.TWindow;
import jexer.bits.GraphicsChars;

import java.util.Arrays;

/**
 * Non resizable, non modal window with TAsciiChart
 * Grid view of Code Page 437 characters with additional char info in the bottom
 * Chars can be selected with a mouse or keyboard
 */
public class TAsciiChartWindow extends TWindow {

    /**
     * info labels
     */
    private TLabel[] labels = new TLabel[4];
    /**
     * ascii chart widget
     */
    private TAsciiChart asciiChart;

    /**
     * Public constructor
     * @param application parent application
     */
    public TAsciiChartWindow(final TApplication application) {
        super(application, "ASCII Chart", 0, 0, 34, 12, NOZOOMBOX);

        // add ascii chart widget
        asciiChart = new TAsciiChart(this, 0, 0,
                new TAction() {
                    // When the user presses Enter
                    public void DO() {
                        refreshLabels();
                    }
                },
                new TAction() {
                    // When the user presses Enter
                    public void DO() {
                        refreshLabels();
                    }
                }
        );

        // add horizontal separator
        char[] line = new char[asciiChart.getWidth()];
        Arrays.fill(line, GraphicsChars.SINGLE_BAR);
        addLabel(new String(line), 0, 8);

        // add info labels
        addLabel("Char:", 0, 9, "ttext");
        labels[0] = new TLabel(this, "", 5, 9);

        addLabel("Dec:", 7, 9, "ttext");
        labels[1] = new TLabel(this, "---", 11, 9);

        addLabel("Hex:", 15, 9, "ttext");
        labels[2] = new TLabel(this, "---", 19, 9);

        addLabel("Utf:", 24, 9, "ttext");
        labels[3] = new TLabel(this, "---", 28, 9);

        refreshLabels();

    }

    private void refreshLabels() {
        // get selected cell from asciiChart and update info
        int index = asciiChart.getSelectedIndex();
        char ch = asciiChart.getSelectedChar();
        labels[0].setLabel(String.valueOf(ch));
        labels[1].setLabel(String.valueOf(index));
        labels[2].setLabel("0x" + Integer.toHexString(index).toUpperCase());
        labels[3].setLabel(Integer.toHexString((int) ch).toUpperCase());
    }

}
