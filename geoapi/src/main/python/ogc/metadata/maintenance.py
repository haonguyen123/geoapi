#
#    GeoAPI - Programming interfaces for OGC/ISO standards
#    http://www.geoapi.org
#
#    Copyright (C) 2018 Open Geospatial Consortium, Inc.
#    All Rights Reserved. http://www.opengeospatial.org/ogc/legal
#

from abc import ABC, abstractproperty
from enum import Enum

class MaintenanceFrequencyCode(Enum):
    CONTINUAL = "continual"
    DAILY = "daily"
    WEEKLY = "weekly"
    FORTNIGHTLY = "fortnightly"
    MONTHLY = "monthly"
    QUARTERLY = "quarterly"
    BIANNUALLY = "biannually"
    ANNUALLY = "annually"
    AS_NEEDED = "asNeeded"
    IRREGULAR = "irregular"
    NOT_PLANNED = "notPlanned"
    UNKNOWN = "unknown"
    PERIODIC = "periodic"
    SEMIMONTHLY = "semimonthly"
    BIENNIALLY = "biennially"

class ScopeCode(Enum):
    COLLECTION_HARDWARE = "collectionHardware"
    COLLECTION_SESSION = "collectionSession"
    SERIES = "series"
    DATASET = "dataset"
    NON_GEOGRAPHIC_DATASET = "nonGeographicDataset"
    DIMENSION_GROUP = "dimensionGroup"
    FEATURE_TYPE = "featureType"
    FEATURE = "feature"
    ATTRIBUTE_TYPE = "attributeType"
    ATTRIBUTE = "attribute"
    PROPERTY_TYPE = "propertyType"
    FIELD_SESSION = "fieldSession"
    SOFTWARE = "software"
    SERVICE = "service"
    MODEL = "model"
    TILE = "tile"
    METADATA = "metadata"
    INITIATIVE = "initiative"
    SAMPLE = "sample"
    DOCUMENT = "document"
    REPOSITORY = "repository"
    AGGREGATE = "aggregate"
    PRODUCT = "product"
    COLLECTION = "collection"
    COVERAGE = "coverage"
    APPLICATION = "application"

class ScopeDescription(ABC):
    """Description of the class of information covered by the information."""

    @abstractproperty
    def attributes(self) -> str:
        """Instances of attribute types to which the information applies."""
        pass

    @abstractproperty
    def features(self) -> str:
        """Instances of feature types to which the information applies."""
        pass

    @abstractproperty
    def featureInstances(self) -> str:
        """Feature instances to which the information applies."""
        pass

    @abstractproperty
    def attributeInstances(self) -> str:
        """Attribute instances to which the information applies."""
        pass

    @abstractproperty
    def dataset(self) -> str:
        """Dataset to which the information applies."""
        pass

    @abstractproperty
    def other(self) -> str:
        """Class of information that does not fall into the other categories to which the information applies."""
        pass

class Scope(ABC):
    """New: information about the scope of the resource."""

    @abstractproperty
    def level(self) -> ScopeCode:
        """Description of the scope."""
        pass

    @abstractproperty
    def extent(self) -> Extent:
        pass

    @abstractproperty
    def levelDescription(self) -> ScopeDescription:
        pass

class MaintenanceInformation(ABC):
    """Information about the scope and frequency of updating."""

    @abstractproperty
    def maintenanceAndUpdateFrequency(self) -> MaintenanceFrequencyCode:
        """Frequency with which changes and additions are made to the resource after the initial resource is completed."""
        pass

    @abstractproperty
    def maintenanceDate(self) -> Date:
        """Date information associated with maintenance of resource."""
        pass

    @abstractproperty
    def userDefinedMaintenanceFrequency(self) -> PeriodDuration:
        """Maintenance period other than those defined."""
        pass

    @abstractproperty
    def maintenanceScope(self) -> Scope:
        """Information about the scope and extent of maintenance."""
        pass

    @abstractproperty
    def maintenanceNote(self) -> str:
        """Information regarding specific requirements for maintaining the resource."""
        pass

    @abstractproperty
    def contact(self) -> Responsibility:
        """Identification of, and means of communicating with, person(s) and organisation(s) with responsibility for maintaining the metadata."""
        pass
