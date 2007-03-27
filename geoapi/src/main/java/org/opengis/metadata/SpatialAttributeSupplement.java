/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $URL$
 **
 ** Copyright (C) 2004-2005 Open GIS Consortium, Inc.
 ** All Rights Reserved. http://www.opengis.org/legal/
 **
 *************************************************************************************************/
package org.opengis.metadata;

// J2SE direct dependencies
import static org.opengis.annotation.Obligation.MANDATORY;
import static org.opengis.annotation.Specification.ISO_19115;

import java.util.Collection;

import org.opengis.annotation.UML;


/**
 * Spatial attributes in the application schema for the feature types.
 *
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 * @author Martin Desruisseaux (IRD)
 * @since GeoAPI 2.0
 */
@UML(identifier="MD_SpatialAttributeSupplement", specification=ISO_19115)
public interface SpatialAttributeSupplement {
    /**
     * Provides information about the list of feature types with the same spatial representation.
     */
    @UML(identifier="theFeatureTypeList", obligation=MANDATORY, specification=ISO_19115)
    Collection<FeatureTypeList> getFeatureTypeList();
}
