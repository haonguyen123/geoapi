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

// J2SE direct dependencies
import static org.opengis.annotation.Obligation.MANDATORY;
import static org.opengis.annotation.Specification.ISO_19108;

import java.util.Collection;

import org.opengis.annotation.UML;


/**
 * Provides only the attributes inherited from temporal reference system.
 * 
 * @author Alexander Petkov
 *
 * @todo The javadoc doesn't seem accurate.
 * @todo Missing UML annotations.
 */
public interface OrdinalReferenceSystem extends TemporalReferenceSystem {
	/**
     * Get the set of ordinal eras of which this ordinal reference system consists of.
     *
     * @return A hierarchically-structured collection of ordinal eras.
     *
     * @todo What the structure is exactly?
     */
    @UML(identifier="structure", obligation=MANDATORY,specification=ISO_19108)
    Collection<OrdinalEra> getOrdinalEraSequence();
}
