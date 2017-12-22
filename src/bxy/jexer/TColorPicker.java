package bxy.jexer;

import bxy.jexer.Cellgrid;
import jexer.TWidget;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;

public class TColorPicker extends TWidget {

    /**
     * selected foreground and background color
     */
    private CellAttributes cellAttributes;
    /**
     * color grid to be drawn
     */
    private Cellgrid cellgrid;

    /**
     * width of one colored cell
     */
    private int colorWidth;
    /**
     * height of one colored cell
     */
    private int colorHeight;
    /**
     * color grid flags
     */
    private int flags;

    /**
     * default colors
     */
    private static final Color[] colors = { Color.BLACK, Color.BLUE, Color.GREEN, Color.CYAN, Color.RED, Color.MAGENTA, Color.YELLOW, Color.WHITE};

    /**
     * horizontal number of colored cells, used internally best values 16, 8, 4, 2, 1
     */
    private static int horizontalColors = 0;
    /**
     * vertical count of colored cells, used internally best values 1, 2, 4, 8, 16
     */
    private static int verticalColors = 0;
    /**
     * should bold colors be drawn first
     */
    private static boolean boldFirst;
    /**
     * should colored grid be populated from top to down, left to right or left to right, top to down
     */
    private static boolean topDown;
    /**
     * should colored grid be populated with colors in reverse order
     */
    private static boolean reverse;

    /**
     * grid 4x4, default no box
     */
    public static final int BOX = 0x01;
    /**
     * if no box, one line or two lines of colored cells, default two lines 8x2 (if flag horizontal) or 2x8 (if flag vertical)
     */
    public static final int ONE_LINE = 0x02;
    /**
     * if no box, vertical orientation, default horizontal
     */
    public static final int VERTICAL = 0x04;
    /**
     * draw bold colors first, default draw last
     */
    public static final int BOLD_FIRST = 0x08;
    /**
     * fill color grid from top to bottom, left to right, default fill left to right, top to botom
     */
    public static final int TOP_DOWN = 0x10;
    /**
     * fill color grid with colors from last to first, default first to last
     */
    public static final int REVERSE = 0x20;


    /**
     * parse flags, calculate horizontal colors, vertical colors, boldFirst, topDown and reverse
     * @param flags
     */
    private static void parseFlags(int flags){
        // if grid bit is set ignore one_line and vertical bit flags
        if ((flags & BOX) != 0) {
            horizontalColors = 4;
        } else if ((flags & ONE_LINE) != 0 && (flags & VERTICAL) != 0) {
            horizontalColors = 1;
        } else if ((flags & ONE_LINE) != 0 && (flags & VERTICAL) == 0) {
            horizontalColors = 16;
        } else if ((flags & VERTICAL) != 0) {
            horizontalColors = 2;
        } else {
            horizontalColors = 8;
        }

        verticalColors = colors.length * 2 / horizontalColors;

        boldFirst = (flags & BOLD_FIRST) != 0;
        topDown = (flags & TOP_DOWN) != 0;
        reverse = (flags & REVERSE) != 0;
    }

    /**
     * calculate horizontal colors if needed than return
     * @param flags
     * @return
     */
    private static int getHorizontalColors(final int flags) {
        if (horizontalColors == 0) parseFlags(flags);
        return horizontalColors;
    }

    /**
     * calculate vertical colors if needed than return
     * @param flags
     * @return
     */
    private static int getVerticalColors(final int flags) {
        if (verticalColors == 0) parseFlags(flags);
        return verticalColors;
    }

    /**
     * Public constructor
     * @param parent
     * @param x column relative to parent
     * @param y row relative to parent
     */
    public TColorPicker(final TWidget parent, final int x, final int y) {
        this(parent, x, y, 2, 1,0);
    }

    /**
     * Public constructor
     * @param parent
     * @param x column relative to parent
     * @param y row relative to parent
     * @param colorWidth width of one colored cell
     * @param colorHeight height of one colored cell
     */
    public TColorPicker(final TWidget parent, final int x, final int y, final int colorWidth, final int colorHeight) {
        this(parent, x, y, colorWidth, colorHeight,0);
    }

    /**
     * Public constructor
     * @param parent
     * @param x column relative to parent
     * @param y row relative to parent
     * @param flags creation flags
     */
    public TColorPicker(final TWidget parent, final int x, final int y, final int flags) {
        this(parent, x, y, 2, 1, flags);
    }

    /**
     * Public constructor
     * @param parent
     * @param x colum relative to parent
     * @param y row relative to parent
     * @param colorWidth width of one colored cell
     * @param colorHeight height of one colored cell
     * @param flags creation flags
     */
    public TColorPicker(final TWidget parent, final int x, final int y, final int colorWidth, final int colorHeight, final int flags) {
        super(parent, x, y, getHorizontalColors(flags) * colorWidth, getVerticalColors(flags) * colorHeight);

        this.flags = flags;
        this.colorWidth = colorWidth;
        this.colorHeight = colorHeight;

        cellgrid = new Cellgrid(getWidth(),getHeight(), new char[]{'\u2588'});

        if (topDown) {
            // fill from top to bottom
            for (int j = 0; j < cellgrid.getHeight(); j++) {
                for (int i = 0; i < cellgrid.getWidth(); i++) {
                    int index = (i / colorWidth * verticalColors + j / colorHeight);
                    int colorIndex = reverse ? colors.length - 1 - index % colors.length : index % colors.length;
                    cellgrid.getCell(i, j).setForeColor(colors[colorIndex]);
                    cellgrid.getCell(i, j).setBold(index / colors.length == (boldFirst ? 0 : 1));
                    cellgrid.getCell(i, j).setBackColor(colors[colorIndex]);
                }
            }
        } else {
            // fill from left to right
            for (int i = 0; i < cellgrid.getWidth(); i++) {
                for (int j = 0; j < cellgrid.getHeight(); j++) {
                    int index = (j / colorHeight * horizontalColors + i / colorWidth);
                    int colorIndex = reverse ? colors.length - 1 - index % colors.length : index % colors.length;
                    cellgrid.getCell(i, j).setForeColor(colors[colorIndex]);
                    cellgrid.getCell(i, j).setBold(index / colors.length == (boldFirst ? 0 : 1));
                    cellgrid.getCell(i, j).setBackColor(colors[colorIndex]);
                }
            }
        }

    }

    /**
     * Draw widget
     */
    @Override
    public void draw() {
        Cell cell = cellgrid.getCell(0,0);

        cell.setChar(GraphicsChars.DOT);
        cell.setForeColor(Color.WHITE);

        cell = cellgrid.getCell(1,0);
        cell.setChar(GraphicsChars.CIRCLE);
        cell.setForeColor(Color.WHITE);

        cell = cellgrid.getCell(2, 0);

        cell.setChar(GraphicsChars.DOT);
        cell.setForeColor(Color.BLACK);
//        cell.setBold(true);

        cell = cellgrid.getCell(3,0);
        cell.setChar(GraphicsChars.CIRCLE);
        cell.setForeColor(Color.BLACK);
//        cell.setBold(true);


        cell = cellgrid.getCell(0,1);

        cell.setChar(GraphicsChars.DOT_INVERTED);
//        cell.setForeColor(Color.WHITE);
//        cell.setBold(true);

        cell = cellgrid.getCell(1,1);
        cell.setChar(GraphicsChars.CIRCLE_INVERTED);
//        cell.setForeColor(Color.WHITE);
//        cell.setBold(true);

        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                    getScreen().putCharXY(i, j, cellgrid.getCell(i, j));
            }
        }
    }
}

