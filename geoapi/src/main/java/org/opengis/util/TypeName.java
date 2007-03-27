/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2003-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.util;

// Annotations
import static org.opengis.annotation.Specification.ISO_19103;

import org.opengis.annotation.UML;


/**
 * The name of an {@linkplain MemberName attribute} type.
 *
 * @author Bryce Nordgren (USDA)
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.1
 */
@UML(identifier="TypeName", specification=ISO_19103)
public interface TypeName extends LocalName {
}
