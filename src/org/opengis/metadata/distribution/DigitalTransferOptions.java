/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.distribution;

// J2SE direct dependencies
import java.util.Set;

// OpenGIS direct dependencies
import org.opengis.util.InternationalString;
import org.opengis.metadata.citation.OnLineResource;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * Technical means and media by which a resource is obtained from the distributor.
 *
 * @author ISO 19115
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 */
///@UML (identifier="MD_DigitalTransferOptions")
public interface DigitalTransferOptions {
    /**
     * Tiles, layers, geographic areas, etc., in which data is available.
     */
/// @UML (identifier="unitsOfDistribution", obligation=OPTIONAL)
    InternationalString getUnitsOfDistribution();

    /**
     * Estimated size of a unit in the specified transfer format, expressed in megabytes.
     * The transfer size is &gt; 0.0.
     * Returns <code>null</code> if the transfer size is unknown.
     */
/// @UML (identifier="transferSize", obligation=OPTIONAL)
    Number getTransferSize();

    /**
     * Information about online sources from which the resource can be obtained.
     */
/// @UML (identifier="onLine", obligation=OPTIONAL)
    Set/*<OnLineResource>*/ getOnLines();

    /**
     * Information about offline media on which the resource can be obtained.
     */
/// @UML (identifier="offLine", obligation=OPTIONAL)
    Medium getOffLines();
}
