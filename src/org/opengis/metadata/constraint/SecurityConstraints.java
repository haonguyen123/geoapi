/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.constraint;

// OpenGIS direct dependencies
import org.opengis.util.InternationalString;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * Handling restrictions imposed on the resource for national security or similar security concerns.
 *
 * @author ISO 19115
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 */
///@UML (identifier="MD_SecurityConstraints")
public interface SecurityConstraints extends Constraints {
    /**
     * Name of the handling restrictions on the resource.
     */
/// @UML (identifier="classification", obligation=MANDATORY)
    Classification getClassification();

    /**
     * Explanation of the application of the legal constraints or other restrictions and legal
     * prerequisites for obtaining and using the resource.
     */
/// @UML (identifier="userNote", obligation=OPTIONAL)
    InternationalString getUserNote();

    /**
     * Name of the classification system.
     */
/// @UML (identifier="classificationSystem", obligation=OPTIONAL)
    InternationalString getClassificationSystem();

    /**
     * Additional information about the restrictions on handling the resource.
     */
/// @UML (identifier="handlingDescription", obligation=OPTIONAL)
    InternationalString getHandlingDescription();
}
