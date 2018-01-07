package bxy.jexer;

import jexer.TAction;
import jexer.TWidget;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;
import jexer.bits.Color;
import jexer.bits.GraphicsChars;
import jexer.event.TKeypressEvent;
import jexer.event.TMouseEvent;

import static jexer.TKeypress.*;
import static jexer.TKeypress.kbEnter;

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
     * default colors
     */
    private static final Color[] colors = { Color.BLACK, Color.BLUE, Color.GREEN, Color.CYAN, Color.RED, Color.MAGENTA, Color.YELLOW, Color.WHITE};

    /**
     * horizontal number of colored cells, used internally best values 16, 8, 4, 2, 1
     */
    private int horizontalColors = 0;
    /**
     * vertical count of colored cells, used internally best values 1, 2, 4, 8, 16
     */
    private int verticalColors = 0;
    /**
     * should bold colors be drawn first
     */
    private boolean boldFirst;
    /**
     * should colored grid be populated from top to down, left to right or left to right, top to down
     */
    private boolean topDown;
    /**
     * should colored grid be populated with colors in reverse order
     */
    private boolean reverse;

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
     * The action to perform when the user selects an item (clicks or enter).
     */
    private TAction enterAction = null;

    /**
     * The action to perform when the user navigates with keyboard.
     */
    private TAction moveAction = null;

    /**
     * selected color index
     */
    private int fgIdx, bgIdx;

    private boolean fgSelect = true;

    private Color fgColor = Color.WHITE;
    private boolean bold = false;
    private Color bgColor = Color.BLACK;


    /**
     * parse flags, calculate horizontal colors, vertical colors, boldFirst, topDown and reverse
     * @param flags
     */
    private void parseFlags(int flags){
        horizontalColors = calcHorizontalColors(flags);
        verticalColors = calcVerticalColors(flags);

        boldFirst = (flags & BOLD_FIRST) != 0;
        topDown = (flags & TOP_DOWN) != 0;
        reverse = (flags & REVERSE) != 0;
    }

    /**
     * calculate horizontal colors
     * @param flags
     * @return
     */
    private static int calcHorizontalColors(final int flags) {
        if ((flags & BOX) != 0) {
            return 4;
        } else if ((flags & ONE_LINE) != 0 && (flags & VERTICAL) != 0) {
            return 1;
        } else if ((flags & ONE_LINE) != 0 && (flags & VERTICAL) == 0) {
            return 16;
        } else if ((flags & VERTICAL) != 0) {
            return 2;
        } else {
            return 8;
        }
    }

    /**
     * calculate vertical colors
     * @param flags
     * @return
     */
    private static int calcVerticalColors(final int flags) {
        return colors.length * 2 / calcHorizontalColors(flags);
    }

    /**
     * Public constructor
     * @param parent
     * @param x column relative to parent
     * @param y row relative to parent
     */
    public TColorPicker(final TWidget parent, final int x, final int y) {
        this(parent, x, y, null, null);
    }

    public TColorPicker(final TWidget parent, final int x, final int y,
                        final TAction enterAction, final TAction moveAction) {
        this(parent, x, y, 2, 1,0, enterAction, moveAction);
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
        this(parent, x, y, colorWidth, colorHeight,null, null);
    }

    public TColorPicker(final TWidget parent, final int x, final int y, final int colorWidth, final int colorHeight,
                        final TAction enterAction, final TAction moveAction) {
        this(parent, x, y, colorWidth, colorHeight,0, enterAction, moveAction);
    }

    /**
     * Public constructor
     * @param parent
     * @param x column relative to parent
     * @param y row relative to parent
     * @param flags creation flags
     */
    public TColorPicker(final TWidget parent, final int x, final int y, final int flags) {
        this(parent, x, y, flags, null, null);
    }

    public TColorPicker(final TWidget parent, final int x, final int y, final int flags,
                        final TAction enterAction, final TAction moveAction) {
        this(parent, x, y, 2, 1, flags, enterAction, moveAction);
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
        this(parent, x, y, colorWidth, colorHeight, flags, null, null);
    }

    public TColorPicker(final TWidget parent, final int x, final int y, final int colorWidth, final int colorHeight, final int flags,
                        final TAction enterAction, final TAction moveAction) {
        super(parent, x, y, calcHorizontalColors(flags) * colorWidth, calcVerticalColors(flags) * colorHeight);

        this.moveAction = moveAction;
        this.enterAction = enterAction;

        parseFlags(flags);

        this.colorWidth = colorWidth;
        this.colorHeight = colorHeight;

        this.fgIdx = calcIndexFromColor(fgColor, bold);
        this.bgIdx = calcIndexFromColor(bgColor, false);

        cellgrid = new Cellgrid(getWidth(),getHeight(), new char[]{'\u2588'});

        if (topDown) {
            // fill from top to bottom
            for (int j = 0; j < cellgrid.getHeight(); j++) {
                for (int i = 0; i < cellgrid.getWidth(); i++) {
                    fillCell(i,j);
                }
            }
        } else {
            // fill from left to right
            for (int i = 0; i < cellgrid.getWidth(); i++) {
                for (int j = 0; j < cellgrid.getHeight(); j++) {
                    fillCell(i,j);
                }
            }
        }
    }

    private void fillCell(final int i, final int j) {
        int idx = calcIndexFromPosition(i,j);
        Color color = colors[calcColorIndexFromIndex(idx)];
        boolean bold = calcBoldFromIndex(idx);
        cellgrid.getCell(i, j).setForeColor(color);
        cellgrid.getCell(i, j).setBold(bold);
        if (Color.BLACK.equals(color) && !bold) {
            cellgrid.getCell(i, j).setBackColor(Color.WHITE);
        }
    }

    private int calcIndexFromPosition(final int x, final int y) {
        if(topDown) {
            return x / colorWidth * verticalColors + y / colorHeight;
        } else {
            return y / colorHeight * horizontalColors + x / colorWidth;
        }
    }

    private int calcXFromIndex(final int index) {
        if(topDown) {
            return index / verticalColors * colorWidth;
        } else {
            return index % horizontalColors * colorWidth;
        }
    }

    private int calcYFromIndex(final int index) {
        if(topDown) {
            return index % verticalColors * colorHeight;
        } else {
            return index / horizontalColors * colorHeight;
        }
    }

    private int calcColorIndexFromIndex(final int index) {
        return reverse ? colors.length - 1 - index % colors.length : index % colors.length;
    }

    private boolean calcBoldFromIndex(final int index) {
        return index / colors.length == (boldFirst ? 0 : 1);
    }

    private boolean calcBoldFromPosition(final int x, final int y) {
        return calcBoldFromIndex(calcIndexFromPosition(x,y));
    }

    private int calcIndexFromColor(final Color color, final boolean bold) {
        int idx = -1;
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(color)) {
                idx = i;
                break;
            }
        }

        if(bold) {
            idx = boldFirst ? (reverse ? colors.length - 1 - idx : idx) : colors.length + (reverse ? colors.length - 1 - idx : idx);
        } else {
            idx = boldFirst ? colors.length + (reverse ? colors.length - 1 - idx : idx) : (reverse ? colors.length - 1 - idx : idx);
        }

        return idx;
    }

    /**
     * Draw widget
     */
    @Override
    public void draw() {
        int fgX = calcXFromIndex(fgIdx);
        int fgY = calcYFromIndex(fgIdx);
        int bgX = calcXFromIndex(bgIdx) + colorWidth - 1;
        int bgY = calcYFromIndex(bgIdx) + colorHeight - 1;

        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeight(); j++) {
                Cell cell = cellgrid.getCellCopy(i,j);
                if (i == fgX && j == fgY) {
                    cell.setChar(GraphicsChars.DOT_INVERTED);
                    getScreen().putCharXY(i, j, cell);
                } else if (i == bgX && j == bgY) {
                    cell.setChar(GraphicsChars.CIRCLE_INVERTED);
                    getScreen().putCharXY(i, j, cell);
                } else {
                    getScreen().putCharXY(i, j, cellgrid.getCell(i, j));
                }
            }
        }
    }

    /**
     * Perform user selection action.
     */
    public void dispatchEnter() {
        if (enterAction != null) {
            enterAction.DO();
        }
    }

    /**
     * Perform list movement action.
     */
    public void dispatchMove() {
        if (moveAction != null) {
            moveAction.DO();
        }
    }

    /**
     * Handle mouse up events.
     *
     * @param mouse mouse button up event
     */
    @Override
    public void onMouseUp(final TMouseEvent mouse) {
        if(mouse.isMouse1()) {
            fgSelect = true;
            calcColors(mouse.getX(), mouse.getY());
            dispatchMove();
        } else if (mouse.isMouse3()) {
            fgSelect = false;
            calcColors(mouse.getX(), mouse.getY());
            dispatchMove();
        }
    }

    /**
     * Handle mouse double click.
     *
     * @param mouse mouse double click event
     */
    @Override
    public void onMouseDoubleClick(final TMouseEvent mouse) {
        dispatchEnter();
    }

    private void calcColors(final int x, final int y) {
        if (fgSelect) {
            fgIdx = calcIndexFromPosition(x, y);
            fgColor = colors[calcColorIndexFromIndex(fgIdx)];
            bold = calcBoldFromIndex(fgIdx);
        } else if (!calcBoldFromPosition(x, y)) {
            bgIdx = calcIndexFromPosition(x, y);
            bgColor = colors[calcColorIndexFromIndex(bgIdx)];
        }
    }

    /**
     * Handle keystrokes.
     *
     * @param keypress keystroke event
     */
    @Override
    public void onKeypress(final TKeypressEvent keypress) {
        if(keypress.equals(kbF)) {
            fgSelect = true;
        } else if (keypress.equals(kbB)) {
            fgSelect = false;
        } else if (keypress.equals(kbSpace)) {
            fgSelect = !fgSelect;
        } else if (keypress.equals(kbRight)) {
            int dotX = calcXFromIndex(fgSelect ? fgIdx : bgIdx);
            int dotY = calcYFromIndex(fgSelect ? fgIdx : bgIdx);
            if (dotX < getWidth() - colorWidth) {
                dotX += colorWidth;
            }
            calcColors(dotX, dotY);
            dispatchMove();
        } else if (keypress.equals(kbLeft)) {
            int dotX = calcXFromIndex(fgSelect ? fgIdx : bgIdx);
            int dotY = calcYFromIndex(fgSelect ? fgIdx : bgIdx);
            if (dotX >= colorWidth) {
                dotX -= colorWidth;
            }
            calcColors(dotX, dotY);
            dispatchMove();
        } else if (keypress.equals(kbUp)) {
            int dotX = calcXFromIndex(fgSelect ? fgIdx : bgIdx);
            int dotY = calcYFromIndex(fgSelect ? fgIdx : bgIdx);
            if (dotY >= colorHeight) {
                dotY -= colorHeight;
            }
            calcColors(dotX, dotY);
            dispatchMove();
        } else if (keypress.equals(kbDown)) {
            int dotX = calcXFromIndex(fgSelect ? fgIdx : bgIdx);
            int dotY = calcYFromIndex(fgSelect ? fgIdx : bgIdx);
            if (dotY < getHeight() - colorHeight) {
                dotY += colorHeight;
            }
            calcColors(dotX, dotY);
            dispatchMove();
        } else if (keypress.equals(kbEnter)) {
            dispatchEnter();
        } else {
            super.onKeypress(keypress);
        }
    }

    public Color getFgColor() {
        return fgColor;
    }

    public void setFgColor(Color fgColor) {
        fgIdx = calcIndexFromColor(fgColor, bold);
        this.fgColor = fgColor;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        fgIdx = calcIndexFromColor(fgColor, bold);
        this.bold = bold;
    }

    public Color getBgColor() {
        return bgColor;
    }

    public void setBgColor(Color bgColor) {
        bgIdx = calcIndexFromColor(bgColor, false);
        this.bgColor = bgColor;
    }
}

