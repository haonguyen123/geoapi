/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.filter.expression;

// Annotations
import org.opengis.annotation.XmlElement;


/**
 * Encodes the operation of addition.
 * <p>
 * Instances of this interface implement their {@link #evaluate evaluate} method by
 * computing the numeric addition of their {@linkplain #getExpression1 first} and
 * {@linkplain #getExpression2 second} operand.
 * </p>
 * 
 * @version <A HREF="http://www.opengis.org/docs/02-059.pdf">Implementation specification 1.0</A>
 * @author Chris Dillard (SYS Technologies)
 * @since GeoAPI 2.0
 */
@XmlElement("Add")
public interface Add extends BinaryExpression {
}