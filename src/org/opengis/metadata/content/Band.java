/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.content;

// J2SE extensions
import javax.units.Unit;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * Range of wavelengths in the electromagnetic spectrum.
 *
 * @author ISO 19115
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 */
///@UML (identifier="MD_Band")
public interface Band extends RangeDimension {
    /**
     * Longest wavelength that the sensor is capable of collecting within a designated band.
     * Returns <code>null</code> if unspecified.
     */
/// @UML (identifier="maxValue", obligation=OPTIONAL)
    Number getMaxValue();

    /**
     * Shortest wavelength that the sensor is capable of collecting within a designated band.
     * Returns <code>null</code> if unspecified.
     */
/// @UML (identifier="minValue", obligation=OPTIONAL)
    Number getMinValue();

    /**
     * Units in which sensor wavelengths are expressed. Should be non-null if
     * {@linkplain #getMinValue min value} or {@linkplain #getMaxValue max value}
     * are provided.
     */
/// @UML (identifier="units", obligation=CONDITIONAL)
    Unit getUnits();

    /**
     * Wavelength at which the response is the highest.
     * Returns <code>null</code> if unspecified.
     */
/// @UML (identifier="peakResponse", obligation=OPTIONAL)
    Number getPeakResponse();

    /**
     * Maximum number of significant bits in the uncompressed representation for the value
     * in each band of each pixel.
     * Returns <code>null</code> if unspecified.
     */
/// @UML (identifier="bitsPerValue", obligation=OPTIONAL)
    Integer getBitsPerValue();

    /**
     * Number of discrete numerical values in the grid data.
     * Returns <code>null</code> if unspecified.
     */
/// @UML (identifier="toneGradation", obligation=OPTIONAL)
    Integer getToneGradation();

    /**
     * Scale factor which has been applied to the cell value.
     * Returns <code>null</code> if unspecified.
     */
/// @UML (identifier="scaleFactor", obligation=OPTIONAL)
    Number getScaleFactor();

    /**
     * The physical value corresponding to a cell value of zero.
     * Returns <code>null</code> if unspecified.
     */
/// @UML (identifier="offset", obligation=OPTIONAL)
    Number getOffset();
}
