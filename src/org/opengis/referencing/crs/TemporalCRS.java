/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.referencing.crs;

// OpenGIS direct dependencies
import org.opengis.referencing.cs.TemporalCS;
import org.opengis.referencing.datum.TemporalDatum;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * A 1D coordinate reference system used for the recording of time.
 *
 * <TABLE CELLPADDING='6' BORDER='1'>
 * <TR BGCOLOR="#EEEEFF"><TH NOWRAP>Used with CS type(s)</TH></TR>
 * <TR><TD>
 *   {@link org.opengis.referencing.cs.TemporalCS Temporal}
 * </TD></TR></TABLE>
 *
 * @author ISO 19111
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/03-073r1.zip">Abstract specification 2.0</A>
 */
///@UML (identifier="SC_TemporalCRS")
public interface TemporalCRS extends SingleCRS {
    /** 	 
     * Returns the coordinate system, which must be temporal. 	 
     */ 	 
/// @UML (identifier="usesCS", obligation=MANDATORY)
/// TemporalCS getCoordinateSystem(); 	 

    /** 	 
     * Returns the datum, which must be temporal. 	 
     */ 	 
/// @UML (identifier="usesDatum", obligation=MANDATORY)
/// TemporalDatum getDatum();
}
