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

// Annotations
import org.opengis.annotation.XmlSchema;


/**
 * Encodes the operation of multiplication.
 * Instances of this interface implement their {@link #evaluate evaluate} method by
 * computing the numeric product of their {@linkplain #getExpression1 first} and
 * {@linkplain #getExpression2 second} operand.
 *
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/02-059.pdf">Filter encoding implementation specification 1.0</A>
 * @since 1.1
 */
@XmlSchema(URL="filter.xsd", element="Mul")
public interface Multiply extends BinaryExpression {
}
