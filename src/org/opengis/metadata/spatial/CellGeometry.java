/*$************************************************************************************************
 **
 ** $Id$
 **
 ** $Source$
 **
 ** Copyright (C) 2003 Open GIS Consortium, Inc. All Rights Reserved. http://www.opengis.org/Legal/
 **
 *************************************************************************************************/
package org.opengis.metadata.spatial;

// J2SE directdependencies
import java.util.List;
import java.util.ArrayList;

// OpenGIS direct dependencies
import org.opengis.util.CodeList;

// Annotations
///import org.opengis.annotation.UML;
///import static org.opengis.annotation.Obligation.*;


/**
 * Code indicating whether grid data is point or area.
 *
 * @author ISO 19115
 * @author <A HREF="http://www.opengis.org">OpenGIS&reg; consortium</A>
 * @version <A HREF="http://www.opengis.org/docs/01-111.pdf">Abstract specification 5.0</A>
 */
///@UML (identifier="MD_CellGeometryCode")
public final class CellGeometry extends CodeList {
    /**
     * Serial number for compatibility with different versions.
     */
    private static final long serialVersionUID = -1901029875497457189L;

    /**
     * List of all enumerations of this type.
     * Must be declared before any enum declaration.
     */
    private static final List VALUES = new ArrayList(2);

    /**
     * Each cell represents a point.
     */
/// @UML (identifier="point", obligation=CONDITIONAL)
    public static final CellGeometry POINT = new CellGeometry("POINT");

    /**
     * Each cell represents an area.
     */
/// @UML (identifier="area", obligation=CONDITIONAL)
    public static final CellGeometry AREA = new CellGeometry("AREA");

    /**
     * Constructs an enum with the given name. The new enum is
     * automatically added to the list returned by {@link #values}.
     *
     * @param name The enum name. This name must not be in use by an other enum of this type.
     */
    public CellGeometry(final String name) {
        super(name, VALUES);
    }

    /**
     * Returns the list of <code>CellGeometry</code>s.
     */
    public static CellGeometry[] values() {
        synchronized (VALUES) {
            return (CellGeometry[]) VALUES.toArray(new CellGeometry[VALUES.size()]);
        }
    }

    /**
     * Returns the list of enumerations of the same kind than this enum.
     */
    public /*{CellGeometry}*/ CodeList[] family() {
        return values();
    }
}
