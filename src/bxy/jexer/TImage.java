package bxy.jexer;

import jexer.TWidget;
import jexer.bits.Cell;
import jexer.bits.CellAttributes;
import jexer.event.TMouseEvent;

import java.util.Arrays;

public final class TImage extends TWidget {
    private int selectedX = -1;
    private int selectedY = -1;

    private Cell[] data;

    private int calcIndex(int x, int y) {
        return y * getWidth() + x;
    }

    public TImage(final TWidget parent, final int x, final int y, final int width, final int height) {
        this(parent, x, y, width, height, parent.getWindow().getBackground(), null);
    }

    public TImage(final TWidget parent, final int x, final int y, final int width, final int height, final CellAttributes cellAttributes) {
        this(parent, x, y, width, height, cellAttributes, null);
    }

    public TImage(final TWidget parent, final int x, final int y, final int width, final int height, final char[] chars) {
        this(parent, x, y, width, height, parent.getWindow().getBackground(), chars);
    }

    public TImage(TWidget parent, int x, int y, int width, int height, final CellAttributes cellAttributes, final char[] chars) {
        super(parent, x, y, width, height);
        this.data = new Cell[getWidth() * getHeight()];

        Cell cell = new Cell();
        cell.setAttr(cellAttributes);
        Arrays.fill(this.data, cell);

        if (chars == null) return;

        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++) {
                int index = calcIndex(j, i);

                if (index >= chars.length) return;

                cell = new Cell();
                cell.setAttr(cellAttributes);
                cell.setChar(chars[index]);
                this.data[index] = cell;
            }
    }

    public void setCell(int x, int y, Cell cell) {
        data[calcIndex(x, y)] = cell;
    }

    public Cell getSelectedCell() {
        if (selectedX == -1 || selectedY == -1)
            return null;
        return data[calcIndex(selectedX, selectedY)];
    }

    public int getSelectedIndex() {
        if (selectedX == -1 || selectedY == -1) return -1;
        return calcIndex(selectedX, selectedY);
    }

    @Override
    public void draw() {
        for (int i = 0; i < getWidth(); i++)
            for (int j = 0; j < getHeight(); j++)
                getScreen().putCharXY(i, j, data[calcIndex(i, j)]);
    }

    @Override
    public void onMouseUp(TMouseEvent mouse) {
        selectedX = mouse.getX();
        selectedY = mouse.getY();
    }
}
