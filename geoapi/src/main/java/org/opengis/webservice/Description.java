package org.opengis.webservice;

// Annotations
import static org.opengis.annotation.Specification.UNSPECIFIED;

import org.opengis.annotation.UML;


/**
 * @author <a href="mailto:poth@lat-lon.de">Andreas Poth</a>
 */
public interface Description extends DescriptionBase {
    /**
     * Returns the label.
     */
    @UML(identifier="label", specification=UNSPECIFIED)
    String getLabel();
}
