/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.referencing;

// J2SE direct dependencies
import java.util.Locale;

// OpenGIS direct dependencies
import org.opengis.metadata.extent.Extent;
import org.opengis.util.InternationalString;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * Description of a spatial and temporal reference system used by a dataset.
 *  
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 *
 * @see org.opengis.referencing.crs.CoordinateReferenceSystem
 */
///@UML (identifier="RS_ReferenceSystem")
public interface ReferenceSystem extends IdentifiedObject {
    /**
     * Area for which the (coordinate) reference system is valid.
     *
     * @return Coordinate reference system valid area, or <code>null</code> if not available.
     */
/// @UML (identifier="validArea", obligation=OPTIONAL)
    Extent getValidArea();

    /**
     * Description of domain of usage, or limitations of usage, for which this
     * (coordinate) reference system object is valid.
     */
/// @UML (identifier="scope", obligation=OPTIONAL)
    InternationalString getScope();

    /**
     * Description of domain of usage, or limitations of usage, for which this
     * (coordinate) reference system object is valid.
     *
     * @param  locale The desired locale for the scope to be returned, or <code>null</code>
     *         for a scope in some default locale (may or may not be the
     *         {@linkplain Locale#getDefault() system default}).
     * @return The Coordinate reference system scope in the given locale, or <code>null</code> if
     *         none. If no scope is available in the given locale, then some default locale is used.
     *
     * @deprecated Replaced by {@link #getScope()}.
     */
/// @UML (identifier="scope", obligation=OPTIONAL)
    String getScope(Locale locale);
}
