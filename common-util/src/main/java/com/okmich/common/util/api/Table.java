/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.util.api;

import java.util.Collection;

/**
 *
 * @author Michael
 */
public interface Table<T, RowType, ColType> {

    /**
     *
     *
     * @param r
     */
    void addRow(RowType r);

    /**
     *
     *
     * @param collection - a collection of
     */
    void addRows(Collection<RowType> collection);

    /**
     *
     *
     * @param c
     */
    void addColumn(ColType c);

    /**
     *
     *
     * @param c
     * @return boolean
     */
    boolean containsColumn(ColType c);

    /**
     *
     *
     * @param r
     * @return
     */
    boolean containsRow(RowType r);

    /**
     * return a {@link Collection} that contains elements for all rows with the
     * column
     *
     * @param c - the column index values
     * @return Collection<C> - elements for all rows that belong with the column
     */
    Collection<T> getColumn(ColType c);

    /**
     *
     * @return Collection<C>
     */
    Collection<ColType> getColumnIndexes();

    /**
     *
     *
     * @param r
     * @return
     */
    Collection<T> getRow(RowType r);

    /**
     *
     *
     * @return Collection<C>
     */
    Collection<RowType> getRowIndexes();

    /**
     *
     *
     * @param r
     * @param c
     * @return T
     */
    T getValue(RowType r, ColType c);

    /**
     * return true if this container instance contains no element within. If the
     * container contains no element but still has a defined width (column), the
     * method would still return true
     *
     * @return returns true if there is not data element in this container
     * instance
     */
    boolean isEmpty();

    /**
     * return return if the implementation of this array automatically filled
     * the instance with a new instance of T on addition of new rows or column
     *
     * pre-filling means that once creation is complete, there will be no null
     * reference at any point of this container
     *
     * @return true is this container is pre-filled on creation
     */
    boolean isFill();

    /**
     *
     *
     * @param r
     * @return
     */
    boolean removeRow(RowType r);

    /**
     * remove the value in the table structure located on row by {@code r} and
     * at the column by {@code c} and sets the location to null
     *
     * @param r
     * @param c
     */
    void remove(RowType r, ColType c);

    /**
     *
     *
     */
    void reset();

    /**
     * resets this container column definition to container that define by the
     * parameterized Collection
     *
     * @param headers - the new Header or column labels for this container
     * instance
     */
    void setTableHeader(Collection<ColType> headers);

    /**
     * resets this container column definition to container that define by the
     * parameterized Collection
     *
     * @param c - the new Header or column labels for this container instance
     */
    void setTableHeader(ColType... c);

    /**
     * returns true if the object t is successfully added to the this container
     * otherwise returns false;
     *
     * @param r - the object index that represents the row to insert the data
     * @param c - the object index that represents the column to insert the data
     * @param t - data to be inserted
     * @return boolean if insertion is successful otherwise returns false
     */
    boolean setValue(RowType r, ColType c, T t);

    /**
     * return the number of row elements in this container. This could also be
     * regarded as the depth of this container
     *
     * @return the number of row elements in this container
     */
    int size();

    /**
     * return the number of column elements in this container. This can also be
     * called the breadth of the container
     *
     * @return the number of column elements in this container
     */
    int width();
}
