/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.util.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Michael
 */
public class ArrayTable<T, RowType, ColType> implements Table<T, RowType, ColType> {

    private Map<RowType, Integer> rowIndex;
    private Map<ColType, Integer> colIndex;
    private T[][] data;
    private static final int INITIAL_ROW_CAPACITY = 20;
    private static final int INITIAL_COL_CAPACITY = 1;
    private int lastRowPointer;
    private boolean fill;

    /**
     *
     *
     */
    public ArrayTable() {
        this(false);
    }

    /**
     *
     *
     */
    public ArrayTable(boolean prefill) {
        this(prefill, INITIAL_ROW_CAPACITY, INITIAL_COL_CAPACITY);
    }

    /**
     * default constructor that creates a new TableCollection object with a
     * dimension of {@code rowCapacity} x {@code columnCapacity}. The backing
     * matrix of {@link T} is also created
     *
     * @param rowCapacity - the depth of this container
     * @param columnCapacity - the breadth of this container
     */
    public ArrayTable(boolean prefill, int rowCapacity, int columnCapacity) {
        rowIndex = new HashMap<>(rowCapacity);
        colIndex = new HashMap<>(columnCapacity);
        data = createStorage(rowCapacity, columnCapacity, prefill);
        lastRowPointer = 0;
    }

    /**
     *
     *
     * @param r
     */
    @Override
    public void addRow(RowType r) {
        if (rowIndex.containsKey(r)) {
            throw new IllegalArgumentException("key already exists");
        }
        synchronized (this) {
            if (lastRowPointer == data.length) {
                //increase the size of the array by a quarter of its current size
                increaseCapacity(1);
            }
            rowIndex.put(r, lastRowPointer);
            ++lastRowPointer;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void addRows(Collection<RowType> collection) {
        for (RowType t : collection) {
            addRow(t);
        }
    }

    /**
     *
     *
     * @param r
     */
    @Override
    public void addColumn(ColType c) {
        if (containsColumn(c)) {
            throw new IllegalArgumentException("column already exist");
        }
        T[][] temp = createStorage(data.length, colIndex.size() + 1, isFill());
        synchronized (this) {
            for (int i = 0; i < lastRowPointer; i++) {
                //copy array from source to destination
                System.arraycopy(data[i], 0, temp[i], 0, data[i].length);
            }
            //add column to colIndex
            colIndex.put(c, colIndex.size());
            //swap data
            data = temp;
        }
    }

    /**
     * removes all the element from this container but retains its width and
     * label
     */
    public void clear() {
        data = null;
        synchronized (this) {
            //clear the row index
            rowIndex.clear();
            //recreate another data reference
            data = createStorage(INITIAL_ROW_CAPACITY, colIndex.size(), isFill());
            lastRowPointer = 0;
        }
    }
//
//    /**
//     * Trims the capacity of this <tt>ArrayBasedTable</tt> instance to be
//     * the list's current size. An application can use this operation to
//     * minimize the storage of an <tt>ArrayBasedTable</tt> instance.
//     */
//    public void trimToSize() {
//        if (size < oldCapacity) {
//            elementData = Arrays.copyOf(elementData, size);
//        }
//    }

    /**
     *
     *
     * @param r
     * @return
     */
    @Override
    public boolean removeRow(RowType r) {
        if (!containsRow(r)) {
            throw new IllegalArgumentException("does not contain row key " + r);
        }
        int i = rowIndex.get(r); //the index of the row deleted
        synchronized (this) { //the object guides this by itself
            //remove from map
            rowIndex.remove(r);
            //remove the row from the array
            data[i] = null;
            int row = i; //initialize j
            //from i to rowPointer, promote all rows one step
            for (; row < lastRowPointer - 1; row++) {
                data[row] = data[row + 1];
            }
            //data[row] = null; //set the last row to null
            --lastRowPointer; //reduce the row pointer
            for (RowType _r : rowIndex.keySet()) {
                if (rowIndex.get(_r) > i) {
                    rowIndex.put(_r, rowIndex.get(_r) - 1);
                }
            }
        }
        return true;
    }

    /**
     * returns true if the object t is successfully added to the this container
     * otherwise returns false;
     *
     * @param r - the object index that represents the row to insert the data
     * @param c - the object index that represents the column to insert the data
     * @param t - data to be inserted
     * @return boolean if insertion is successful otherwise returns false
     */
    @Override
    public boolean setValue(RowType r, ColType c, T t) {
        if (containsRow(r) && containsColumn(c)) {
            data[rowIndex.get(r)][colIndex.get(c)] = t;
            return true;
        }
        return false;
    }

    /**
     *
     *
     * @param r
     * @param c
     * @return T
     */
    @Override
    public T getValue(RowType r, ColType c) {
        if (containsRow(r) && containsColumn(c)) {
            return data[rowIndex.get(r)][colIndex.get(c)];
        }
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void remove(RowType r, ColType c) {
        if (containsRow(r) && containsColumn(c)) {
            data[rowIndex.get(r)][colIndex.get(c)] = null;
        }
    }

    /**
     * return a {@link Collection} that contains elements for all rows with the
     * column
     *
     * @param c - the column index values
     * @return Collection<C> - elements for all rows that belong with the column
     */
    @Override
    public Collection<T> getColumn(ColType c) {
        List<T> tList = new ArrayList<>();
        synchronized (this) {
            //return the empty list if the column doesn't contains such value
            if (!containsColumn(c)) {
                return tList;
            }
            int j = colIndex.get(c); //get the column index
            for (int i = 0; i < rowIndex.size(); i++) {
                tList.add(data[i][j]);
            }
        }
        return tList;
    }

    /**
     *
     *
     * @param r
     * @return
     */
    @Override
    public Collection<T> getRow(RowType r) {
        List<T> tList = null;
        synchronized (this) {
            //return the empty list if the row doesn't contains such value
            if (!containsRow(r)) {
                return tList;
            }
            int i = rowIndex.get(r); //get the row index
            tList = new ArrayList<T>();
            for (int j = 0; j < colIndex.size(); j++) {
                tList.add(data[i][j]);
            }
        }
        return tList;
    }

    /**
     * return true if this container instance contains no element within. If the
     * container contains no element but still has a defined width (column), the
     * method would still return true
     *
     * @return returns true if there is not data element in this container
     * instance
     */
    @Override
    public boolean isEmpty() {
        return lastRowPointer == 0;
    }

    /**
     * resets this container column definition to container that define by the
     * parameterized Collection
     *
     * @param headers - the new Header or column labels for this container
     * instance
     */
    @Override
    public void setTableHeader(Collection<ColType> headers) {
        //call the setTableHeader(C...) variant of this class
        setTableHeader(headers.toArray((ColType[]) new Object[headers.size()]));
    }

    /**
     * resets this container column definition to container that define by the
     * parameterized Collection
     *
     * @param c - the new Header or column labels for this container instance
     */
    @Override
    public void setTableHeader(ColType... c) {
        if (lastRowPointer > 0) {
            throw new IllegalArgumentException("collection not empty");
        }
        //clear the column index and repopulates
        synchronized (this) {
            colIndex.clear();
            for (int i = 0; i < c.length; i++) {
                colIndex.put(c[i], i);
            }

            data = createStorage(INITIAL_ROW_CAPACITY, c.length, isFill());
        }
    }

    /**
     *
     *
     * @param r
     * @return
     */
    @Override
    public boolean containsRow(RowType r) {
        return rowIndex.containsKey(r);
    }

    /**
     *
     *
     * @param c
     * @return
     */
    @Override
    public boolean containsColumn(ColType c) {
        return colIndex.containsKey(c);
    }

    /**
     *
     *
     * @return Collection<C>
     */
    @Override
    public Collection<RowType> getRowIndexes() {
        return rowIndex.keySet();
    }

    /**
     *
     * @return Collection<C>
     */
    @Override
    public Collection<ColType> getColumnIndexes() {
        return colIndex.keySet();
    }

    /**
     * @return the fill
     */
    @Override
    public boolean isFill() {
        return fill;
    }

    /**
     *
     *
     */
    @Override
    public void reset() {
        synchronized (this) {
            //clear the row index
            rowIndex.clear();
            //clear the column index
            colIndex.clear();
            //destroy the data reference
            data = null;
            lastRowPointer = 0;
        }
    }

    /**
     * return the number of row elements in this container. This could also be
     * regarded as the depth of this container
     *
     * @return the number of row elements in this container
     */
    @Override
    public synchronized int size() {
        return lastRowPointer;
    }

    /**
     * return the number of column elements in this container. This can also be
     * called the breadth of the container
     *
     * @return the number of column elements in this container
     */
    @Override
    public int width() {
        return colIndex.size();
    }

    @Override
    public String toString() {
        return new StringBuilder("TableCollectionImpl::: [").append(lastRowPointer).
                append(" rows, ").append(colIndex.size()).append(" columns]").
                toString();
    }

    /**
     *
     *
     * @param step
     */
    private void increaseCapacity(int step) {
        T[][] temp = createStorage(lastRowPointer + step, colIndex.size(), isFill());
        System.arraycopy(data, 0, temp, 0, lastRowPointer);
        data = temp;
    }

    /**
     *
     *
     * @param rowCapacity
     * @param colCapacity
     * @param prefill
     * @return T[][]
     */
    private T[][] createStorage(int rowCapacity, int colCapacity, boolean prefill) {
        T[][] _dataStore = (T[][]) new Object[rowCapacity][colCapacity];
        if (prefill) {
            for (int i = 0; i < rowIndex.size(); i++) {
                Arrays.fill(data[i], (T) new Object());
            }
        }
        return _dataStore;
    }
}
