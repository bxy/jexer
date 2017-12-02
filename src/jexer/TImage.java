package jexer;

import jexer.bits.Cell;

import java.util.Arrays;

public final class TImage extends TWidget {
    private Cell[] data;

    private int calcIndex(int x, int y) {
        return y * getWidth() + x;
    }

    public TImage(TWidget parent, int x, int y, int width, int height) {
        super(parent, x, y, width, height);
        data = new Cell[getWidth() * getHeight()];
        Arrays.fill(data, new Cell());
    }

    public TImage(TWidget parent, int x, int y, int width, char[] data) {
        super(parent, x, y, width, data.length / width);
        this.data = new Cell[getWidth() * getHeight()];
        for (int i = 0 ; i < getWidth(); i++)
            for(int j = 0; j < getHeight(); j++)
                this.data[calcIndex(i, j)] = new Cell(data[calcIndex(i, j)]);
    }

    public void setCell(int x, int y, Cell cell) {
        data[calcIndex(x, y)] = cell;
    }

    @Override
    public void draw() {
        for (int i = 0 ; i < getWidth(); i++)
            for(int j = 0; j < getHeight(); j++)
                getScreen().putCharXY(i, j, data[calcIndex(i, j)]);
    }
}
