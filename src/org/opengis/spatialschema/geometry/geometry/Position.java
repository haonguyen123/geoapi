/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.spatialschema.geometry.geometry;

// OpenGIS direct dependencies
import org.opengis.spatialschema.geometry.DirectPosition;
import org.opengis.spatialschema.geometry.primitive.Point;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * A type consisting of either a {@linkplain DirectPosition direct position} or of a
 * {@linkplain Point point} from which a {@linkplain DirectPosition direct position}
 * shall be obtained. The use of this data type allows the identification of a position
 * either directly as a coordinate (variant direct) or indirectly as a {@linkplain Point point}
 * (variant indirect).
 * <ul>
 *   <li>In the variant direct case, uses {@link #getPosition} method.</li>
 *   <li>In the variant indirect case, cast this position to a {@link Point}.</li>
 * </ul>
 *  
 * @author ISO/DIS 19107
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version 2.0
 */
///@UML (identifier="GM_Position")
public interface Position {
    /**
     * Returns the direct position.
     *
     * @return The direct position.
     */
/// @UML (identifier="direct", obligation=CONDITIONAL)
    public DirectPosition getPosition();
}
