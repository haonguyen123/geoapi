/*
 *    GeoAPI - Java interfaces for OGC/ISO standards
 *    http://www.geoapi.org
 *
 *    Copyright (C) 2018 Open Geospatial Consortium, Inc.
 *    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
 *
 *    Permission to use, copy, and modify this software and its documentation, with
 *    or without modification, for any purpose and without fee or royalty is hereby
 *    granted, provided that you include the following on ALL copies of the software
 *    and documentation or portions thereof, including modifications, that you make:
 *
 *    1. The full text of this NOTICE in a location viewable to users of the
 *       redistributed or derivative work.
 *    2. Notice of any changes or modifications to the OGC files, including the
 *       date changes were made.
 *
 *    THIS SOFTWARE AND DOCUMENTATION IS PROVIDED "AS IS," AND COPYRIGHT HOLDERS MAKE
 *    NO REPRESENTATIONS OR WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 *    TO, WARRANTIES OF MERCHANTABILITY OR FITNESS FOR ANY PARTICULAR PURPOSE OR THAT
 *    THE USE OF THE SOFTWARE OR DOCUMENTATION WILL NOT INFRINGE ANY THIRD PARTY
 *    PATENTS, COPYRIGHTS, TRADEMARKS OR OTHER RIGHTS.
 *
 *    COPYRIGHT HOLDERS WILL NOT BE LIABLE FOR ANY DIRECT, INDIRECT, SPECIAL OR
 *    CONSEQUENTIAL DAMAGES ARISING OUT OF ANY USE OF THE SOFTWARE OR DOCUMENTATION.
 *
 *    The name and trademarks of copyright holders may NOT be used in advertising or
 *    publicity pertaining to the software without specific, written prior permission.
 *    Title to copyright in this software and any associated documentation will at all
 *    times remain with copyright holders.
 */
package org.opengis.tools.export;

import java.util.Arrays;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Date;
import java.util.GregorianCalendar;
import java.math.BigInteger;
import java.lang.reflect.Method;
import java.io.Writer;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.parsers.ParserConfigurationException;
import org.opengis.Content;
import org.opengis.SourceGenerator;
import org.opengis.annotation.UML;
import org.opengis.util.ControlledVocabulary;
import org.opengis.util.InternationalString;
import org.opengis.xml.NameSpaces;
import org.opengis.xml.Departures;
import org.opengis.xml.DocumentationStyle;
import org.opengis.xml.SchemaInformation;
import org.opengis.xml.SchemaException;
import org.xml.sax.SAXException;

import static java.util.Collections.singletonMap;
import static org.junit.Assert.*;


/**
 * Generates or verifies Python abstract classes from Java interfaces.
 * If Python files exist in the {@code geoapi/src/python/ogc} directory, they will be compared with expected content.
 * If those files do not exist, then they will be generated from {@link UML} annotations given by Java interfaces.
 * This should be used only as a starting point before review by Python developers.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @since   3.1
 * @version 3.1
 */
public final strictfp class JavaToPython extends SourceGenerator {
    /**
     * Suffix of Python files.
     */
    private static final String FILE_SUFFIX = ".py";

    /**
     * The character encoding to use for reading and writing Python files.
     */
    private static final String ENCODING = "UTF-8";

    /**
     * The current year, for formatting the Copyright header.
     */
    private final int currentYear;

    /**
     * Content of python files to write. Keys are module paths
     * (e.g. {@code "metadata/citation"}) and values are file contents.
     */
    private final Map<String,StringBuilder> contents;

    /**
     * The Python modules to create for each package prefixes. The keys are the same than in the {@link #contents} map.
     * Values are Python filenames without {@value #FILE_SUFFIX} suffix.
     */
    private final NameSpaces namespaces;

    /**
     * Python primitive types for given Java types.
     */
    private final Map<Class<?>, String> primitiveTypes;

    /**
     * Python keywords associated to replacements.
     * Keys are enclosing interfaces and values are map of properties to rename in that particular interface.
     */
    private final Map<Class<?>, Map<String,String>> keywords;

    /**
     * Information read from the XML schema. This is used for determining property order.
     */
    private final SchemaInformation schema;

    /**
     * A temporary map used for sorting properties in declaration order.
     * Keys are the UML identifiers.
     */
    private final Map<String,Property> properties;

    /**
     * Information about a Python property to declare in a class.
     */
    private static final class Property implements Comparable<Property> {
        /** The Python name (can not be null).  */ final String name;
        /** The python type, or null if none.   */ final String type;
        /** Declaration order, to be set later. */ int position;

        Property(final String name, final String type) {
            this.name = name;
            this.type = type;
            position  = Integer.MAX_VALUE / 2;
        }

        /** For sorting properties in declaration order. */
        @Override public int compareTo(final Property o) {
            return position - o.position;
        }

        /** Returns a string representation for error reporting only. */
        @Override public String toString() {
            return name;
        }
    }

    /**
     * Creates a new Python class writer. If the computer contains a local copy of ISO schemas, then
     * the {@code schemaRootDirectory} argument can be set to that directory for faster schema loadings.
     * If non-null, that directory should contain the same files than
     * <a href="http://standards.iso.org/iso/">http://standards.iso.org/iso/</a> (not necessarily with
     * all sub-directories). In particular, than directory should contain a {@code 19115} sub-directory.
     *
     * @param  schemaRootDirectory  path to local copy of ISO schemas, or {@code null} if none.
     * @throws ParserConfigurationException if the XML parser can not be created.
     * @throws IOException     if an I/O error occurred while reading a file.
     * @throws SAXException    if a file can not be parsed as a XML document.
     * @throws SchemaException if a XML document can not be interpreted as an OGC/ISO schema.
     */
    public JavaToPython(final Path schemaRootDirectory)
            throws ParserConfigurationException, IOException, SAXException, SchemaException
    {
        currentYear    = new GregorianCalendar().get(GregorianCalendar.YEAR);
        contents       = new LinkedHashMap<>();
        properties     = new HashMap<>();
        primitiveTypes = new HashMap<>(20);
        primitiveTypes.put(CharSequence        .class, "str");
        primitiveTypes.put(String              .class, "str");
        primitiveTypes.put(InternationalString .class, "str");
        primitiveTypes.put(Byte                .class, "int");
        primitiveTypes.put(Byte                .TYPE,  "int");
        primitiveTypes.put(Short               .class, "int");
        primitiveTypes.put(Short               .TYPE,  "int");
        primitiveTypes.put(Integer             .class, "int");
        primitiveTypes.put(Integer             .TYPE,  "int");
        primitiveTypes.put(Long                .class, "int");
        primitiveTypes.put(Long                .TYPE,  "int");
        primitiveTypes.put(BigInteger          .class, "int");
        primitiveTypes.put(Float               .class, "float");
        primitiveTypes.put(Float               .TYPE,  "float");
        primitiveTypes.put(Double              .class, "float");
        primitiveTypes.put(Double              .TYPE,  "float");
        primitiveTypes.put(Date                .class, "datetime");

        keywords = new HashMap<>(4);
        keywords.put(org.opengis.metadata.acquisition.Objective.class,     singletonMap("pass", "platformPass"));
        keywords.put(org.opengis.metadata.quality.ConformanceResult.class, singletonMap("pass", "isConform"));

        namespaces = new NameSpaces();
        schema = new SchemaInformation(schemaRootDirectory, new Departures(), DocumentationStyle.SENTENCE);
        schema.loadDefaultSchemas();
    }

    /**
     * Returns the Python name of the given Java type, or {@code null} if unknown.
     * First, this method check if the given type can be mapped to a Python primitive.
     * If not, then the UML identifier is returned with the prefix omitted.
     */
    private String nameOf(final Class<?> type) {
        String id = primitiveTypes.get(type);
        if (id == null) {
            final UML uml = type.getAnnotation(UML.class);
            if (uml != null) {
                id = uml.identifier();
                id = id.substring(id.indexOf('_') + 1);
                if (id.isEmpty()) id = null;
            }
        }
        return id;
    }

    /**
     * Creates Python files for all supported types, in approximative dependency order.
     * The contents are stored in the {@link #contents} map.
     */
    private void createContent() {
        createContent(Content.CONTROLLED_VOCABULARY);
        createContent(Content.INTERFACES);
    }

    /**
     * Creates Python files for all types in the given category.
     * The contents are stored in the {@link #contents} map.
     */
    private void createContent(final Content category) {
        final String lineSeparator = System.lineSeparator();
        for (final Class<?> type : category.types()) {
            /*
             * Skip deprecated types (e.g. types from legacy ISO 19115:2003 specification)
             * or type without UML annotations (e.g. extensions specific to Java platform).
             */
            if (type.isSynthetic() || type.isAnnotationPresent(Deprecated.class)) {
                continue;
            }
            final UML uml = type.getAnnotation(UML.class);
            if (uml == null) {
                continue;
            }
            /*
             * Get the OGC/ISO type name (NOT the Java type name) without its prefix. The prefix will determine
             * the Python module where the class will be declared.   Note that there is not always a one-to-one
             * relationship between the prefix in class name (e.g. "CI" in "CI_Citation") and the XML namespace
             * (e.g. "…/cit/…"). The Java package may also differ for historical reasons. For Python we use the
             * the XML prefixes because they provide a finer modularisation, at least with ISO 19115-3 schemas.
             */
            String prefix = null;
            final Map<String, SchemaInformation.Element> definition = schema.getTypeDefinition(type);
            if (definition != null) {
                SchemaInformation.Element def = definition.get(null);
                if (def != null && def.namespace.startsWith(SchemaInformation.ROOT_NAMESPACE)) {
                    final int end   = def.namespace.lastIndexOf('/', def.namespace.length() - 1);
                    final int start = def.namespace.lastIndexOf('/', end - 1);
                    prefix = def.namespace.substring(start + 1, end);
                }
            }
            String typeName = uml.identifier();
            final int splitAt = typeName.indexOf('_');
            if (prefix == null) {
                if (splitAt >= 0) {
                    prefix = typeName.substring(0, splitAt);
                } else {
                    switch (type.getPackage().getName()) {
                        case "org.opengis.util":    prefix = "gco"; break;
                        case "org.opengis.feature": prefix = "GF";  break;
                        default: {
                            switch (typeName) {
                                case "DirectPosition": prefix = "GM"; break;
                                default: throw new CanNotExportException("Can not choose a module for " + typeName);
                            }
                        }
                    }
                }
            }
            typeName = typeName.substring(splitAt + 1);
            if (typeName.isEmpty()) {
                continue;                                       // Paranoiac check (should never happen).
            }
            /*
             * For Python language, we create one file per module. Note that OGC/ISO/Java packages
             * map to Python "modules"; we do not use the "package" word in the Python sense here.
             * More than one OGC/ISO packages may map to the same Python module since we may merge
             * some small modules together.
             */
            String module = namespaces.toPackage(prefix, type);
            if (module == null) module = prefix;
            StringBuilder content = contents.get(module);
            if (content == null) {
                content = new StringBuilder(2048)
                       .append('#').append(lineSeparator)
                       .append("#    GeoAPI - Programming interfaces for OGC/ISO standards").append(lineSeparator)
                       .append("#    http://www.geoapi.org").append(lineSeparator)
                       .append('#').append(lineSeparator)
                       .append("#    Copyright (C) ").append(currentYear).append(" Open Geospatial Consortium, Inc.").append(lineSeparator)
                       .append("#    All Rights Reserved. http://www.opengeospatial.org/ogc/legal").append(lineSeparator)
                       .append('#').append(lineSeparator)
                       .append(lineSeparator)
                       .append("from abc import ABC, abstractproperty").append(lineSeparator);
                if (category.isControlledVocabulary()) {
                    content.append("from enum import Enum").append(lineSeparator);
                }
                contents.put(module, content);
            }
            content.append(lineSeparator);
            switch (category) {
                /*
                 * Creates a Python enumeration.
                 */
                case CODE_LISTS:
                case ENUMERATIONS:
                case CONTROLLED_VOCABULARY: {
                    Object[] values = type.getEnumConstants();
                    if (values == null) try {
                        values = (Object[]) type.getMethod("values").invoke(null);
                    } catch (ReflectiveOperationException | ClassCastException e) {
                        throw new CanNotExportException(type + " is not a valid controlled vocabulary.", e);
                    }
                    content.append("class ").append(typeName).append("(Enum):").append(lineSeparator);
                    for (int i=0; i<values.length; i++) {
                        final ControlledVocabulary item = (ControlledVocabulary) values[i];
                        indent(content, 1).append(item.name()).append(" = \"")
                                          .append(item.identifier()).append('"').append(lineSeparator);
                    }
                    break;
                }
                /*
                 * Create a Python class with all properties defined in the Java interface. We use UML annotations
                 * in Java interfaces instead than elements in XSD file because the later contains a few mispellings
                 * (e.g. "satisifiedPlan" instead of "satisfiedPlan") or extra properties not in abstract specification.
                 */
                case INTERFACES: {
                    for (final Method property : type.getDeclaredMethods()) {
                        if (property.isSynthetic() || property.isAnnotationPresent(Deprecated.class)) {
                            continue;
                        }
                        final UML def = property.getAnnotation(UML.class);
                        if (def == null) {
                            continue;
                        }
                        final String name = def.identifier();
                        if (name.indexOf('.') >= 0) {
                            continue;                               // TODO
                        }
                        if (property.getParameterTypes().length == 0) {
                            final Property p = new Property(name, nameOf(Content.typeOf(property)));
                            if (properties.put(name, p) != null) switch (name) {
                                /*
                                 * If the same property appears twice, this is theoretically an error.
                                 * But we make an exception for properties that have been splitted in
                                 * two Java methods because Java can return only one value.
                                 */
                                case "cardinality":     // [minOccurs … maxOccurs]
                                case "signature":       // [parameters : result]
                                {
                                    properties.remove(name);        // TODO
                                    break;
                                }
                                default: {
                                    throw new CanNotExportException("The same " + p +
                                            " property is defined twice in " + type);
                                }
                            }
                        }
                    }
                    /*
                     * At this point we got the list of properties to write in Python interfaces.
                     * But Java methods are listed in no particular order. Before to write them,
                     * we should sort them in the same order than in the XSD file.
                     */
                    if (definition != null) {
                        int position = 0;
                        for (final String name : definition.keySet()) {
                            final Property p = properties.get(name);
                            if (p != null) {
                                p.position = position++;
                            }
                        }
                    }
                    final Property[] props = properties.values().toArray(new Property[properties.size()]);
                    properties.clear();
                    Arrays.sort(props);
                    /*
                     * At this point we have all properties in XSD declaration order.
                     * We can now write the Python class.
                     */
                    content.append("class ").append(typeName).append('(');
                    String parent = "ABC";
                    for (final Class<?> p : type.getInterfaces()) {
                        final String pi = nameOf(p);
                        if (pi != null) {
                            parent = pi;
                            break;              // No multi-inheritence expected. The first type should be the main one.
                        }
                    }
                    content.append(parent).append("):").append(lineSeparator);
                    if (definition != null) {
                        appendDocumentation(definition.get(null), content, 1, lineSeparator);
                    }
                    final Map<String,String> replacements = keywords.remove(type);
                    for (final Property property : props) {
                        String name = property.name;
                        if (replacements != null) {
                            final String r = replacements.get(name);
                            if (r != null) name = r;
                        }
                        content.append(lineSeparator);
                        indent(content, 1).append("@abstractproperty").append(lineSeparator);
                        indent(content, 1).append("def ").append(name).append("(self)");
                        if (property.type != null) {
                            content.append(" -> ").append(property.type);
                        }
                        content.append(':').append(lineSeparator);
                        if (definition != null) {
                            appendDocumentation(definition.get(property.name), content, 2, lineSeparator);
                        }
                        indent(content, 2).append("pass").append(lineSeparator);
                    }
                    break;
                }
            }
        }
    }

    /**
     * Adds spaces for the given indentation level.
     */
    private static StringBuilder indent(final StringBuilder content, int n) {
        while (--n >= 0) {
            content.append("    ");
        }
        return content;
    }

    /**
     * Adds documentation for the given element if non-null and if documentation exists.
     *
     * @param  element  the element for which to add documentation, or {@code null}.
     * @param  content  where to add documentation if it exists.
     * @param  level    the indentation level to use (1 or 2).
     */
    private static void appendDocumentation(final SchemaInformation.Element element,
            final StringBuilder content, final int level, final String lineSeparator)
    {
        if (element != null) {
            final String doc = element.documentation;
            if (doc != null) {
                indent(content, level).append("\"\"\"").append(doc).append("\"\"\"").append(lineSeparator);
            }
        }
    }

    /**
     * Verifies existing source Python files against expected content, or writes missing source files.
     *
     * @throws IOException if an error occurred while reading or writing the files.
     */
    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public void verifyOrCreateSourceFiles() throws IOException {
        createContent();
        final Path dir = sourceDirectory("python").resolve("ogc");
        final Set<String> modules = namespaces.packages();
        assertNotNull(modules.remove("metadata/language"));   // Omitted because it defines only "FreeText", which is not used here.
        for (final String path : modules) {
            final StringBuilder content = contents.remove(path);
            if (content == null) {
                fail("No content found for \"" + path + "\" prefix.");
                continue;
            }
            final Path file = dir.resolve(path + FILE_SUFFIX);
            if (Files.exists(file)) {
                // TODO: verify file content.
            }
            // We do not use Files.newBufferedWriter(Path) because buffering is useless in this case.
            try (Writer out = new OutputStreamWriter(new FileOutputStream(file.toFile()), ENCODING)) {
                out.append(content);
            }
        }
        if (!keywords.isEmpty()) {
            fail("Expected types not found: " + keywords.keySet());
        }
        if (!contents.isEmpty()) {
            System.err.printf("WARNING: no Python modules created for %s.%n", contents.keySet());
        }
    }

    /**
     * For testing purpose only.
     */
    public static void main(String[] args) throws Exception {
        JavaToPython generator = new JavaToPython(null);
        generator.verifyOrCreateSourceFiles();
    }
}
