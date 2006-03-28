package org.opengis.feature.type;

import java.util.Collection;

import org.opengis.feature.Attribute;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * Type builder for creating FeatureTypes.
 * <p>
 * The following additional information is needed when creating a FeatureType:
 * <ul>
 * <li>coordinate reference system
 * <li>default geometry
 * </ul>
 * </p>
 * <p>
 * In the following class the following generics are used:
 * <ul>
 * <li>T indicates the FeatureType
 * <li>C indicates the collection type used to hold attributes
 * </ul>
 * </p>
 * @author Justin Deoliveira, The Open Planning Project, jdeolive@openplans.org
 * @author Jody Garnett, Refractions Research, jdeolive@openplans.org
 * @since GeoAPI 2.1
 * @version GeoAPI 2.1
 */

public interface FeatureTypeBuilder<C extends Collection<Attribute>, T extends FeatureType<C>>
	extends ComplexTypeBuilder<C,T> {
	
//	/**
//	 * Sets the coordinate reference system of the type to be created.
//	 */
//	void setCRS(CoordinateReferenceSystem crs);
//	
//	/**
//	 * Returns the coordinate reference system of the type to be created.
//	 */
//	CoordinateReferenceSystem getCRS();
	
	/**
	 * Sets the default geometry attribute of the type to be created.
	 */
	void setDefaultGeometry(GeometryType type);
	
	/**
	 * Returns the default geometry attribute of the type to be created.
	 */
	GeometryType getDefaultGeometry();
}