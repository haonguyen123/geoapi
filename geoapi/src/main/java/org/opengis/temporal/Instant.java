/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.temporal;

//J2SE direct dependencies:
import static org.opengis.annotation.Obligation.MANDATORY;
import static org.opengis.annotation.Obligation.OPTIONAL;
import static org.opengis.annotation.Specification.ISO_19108;

import java.util.Collection;

import org.opengis.annotation.UML;


/**
 * A zero-dimensional geometric primitive that represents position in time, equivalent to a point
 * in space.
 *
 * @author Stephane Fellah (Image Matters)
 * @author Alexander Petkov
 * TODO There is a bit of a conflict in the spec document as to what should be returned
 * for "position." The diagram shows that Position should be returned, while the text in the document 
 * demands that TemporalPosition should represent position in time.  
 */
@UML(identifier="TM_Instant", specification=ISO_19108)
public interface Instant extends TemporalGeometricPrimitive {
    /**
     * Get the position of this instant.
     * 
     */
    @UML(identifier="position", obligation=MANDATORY, specification=ISO_19108)
    Position getPosition();
    
    /**
     * Get the Collection of temporal {@link Period}s,
     * for which this Instant is the beginning. The collection may be empty.
     * @see Period#begin
     */
    @UML(identifier="begunBy", obligation=OPTIONAL, specification=ISO_19108)
    Collection<Period> getBegunBy();

    /**
     * Get the Collection of temporal {@link Period}s,
     * for which this Instant is the end. The collection may be empty.
     * @see Period#end
     */
    @UML(identifier="endedBy", obligation=OPTIONAL, specification=ISO_19108)
    Collection<Period> getEndedBy();
}
