/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.filter.expression;


/**
 * Abstract base class for the various filter expressions that compute some
 * value from two input values.
 *
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/02-059.pdf">Filter encoding implementation specification 1.0</A>
 * @since 1.1
 */
public interface BinaryExpression extends Expression {
    /**
     * Returns the expression that represents the first (left) value that will
     * be used in the computation of another value.
     */
    Expression getExpression1();

    /**
     * Returns the expression that represents the second (right) value that will
     * be used in the computation of another value.
     */
    Expression getExpression2();
}
