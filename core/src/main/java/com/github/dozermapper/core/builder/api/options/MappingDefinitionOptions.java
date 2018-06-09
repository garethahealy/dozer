/*
 * Copyright 2005-2018 Dozer Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.dozermapper.core.builder.api.options;

import com.github.dozermapper.core.builder.model.jaxb.MappingDefinition;
import com.github.dozermapper.core.builder.model.jaxb.Relationship;
import com.github.dozermapper.core.builder.model.jaxb.Type;

public final class MappingDefinitionOptions {

    private MappingDefinitionOptions() {
    }

    public static DefinitionOption<MappingDefinition> dateFormat(final String value) {
        return definition -> definition.withDateFormat(value);
    }

    public static DefinitionOption<MappingDefinition> stopOnErrors() {
        return stopOnErrors(Boolean.TRUE);
    }

    public static DefinitionOption<MappingDefinition> stopOnErrors(final Boolean value) {
        return definition -> definition.withStopOnErrors(value);
    }

    public static DefinitionOption<MappingDefinition> wildcard(final Boolean value) {
        return definition -> definition.withWildcard(value);
    }

    public static DefinitionOption<MappingDefinition> wildcardCaseInsensitive(final Boolean value) {
        return definition -> definition.withWildcardCaseInsensitive(value);
    }

    public static DefinitionOption<MappingDefinition> trimStrings() {
        return trimStrings(Boolean.TRUE);
    }

    public static DefinitionOption<MappingDefinition> trimStrings(final Boolean value) {
        return definition -> definition.withTrimStrings(value);
    }

    public static DefinitionOption<MappingDefinition> mapNull() {
        return mapNull(Boolean.TRUE);
    }

    public static DefinitionOption<MappingDefinition> mapNull(final Boolean value) {
        return definition -> definition.withMapNull(value);
    }

    public static DefinitionOption<MappingDefinition> mapEmptyString() {
        return mapEmptyString(Boolean.TRUE);
    }

    public static DefinitionOption<MappingDefinition> mapEmptyString(final Boolean value) {
        return definition -> definition.withMapEmptyString(value);
    }

    public static DefinitionOption<MappingDefinition> beanFactory(final String value) {
        return definition -> definition.withBeanFactory(value);
    }

    public static DefinitionOption<MappingDefinition> oneWay() {
        return type(Type.ONE_WAY);
    }

    public static DefinitionOption<MappingDefinition> type(final Type type) {
        return definition -> definition.withType(type);
    }

    public static DefinitionOption<MappingDefinition> relationshipType(final Relationship value) {
        return definition -> definition.withRelationshipType(value);
    }

    public static DefinitionOption<MappingDefinition> mapId(final String mapId) {
        return definition -> definition.withMapId(mapId);
    }
}
