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

import com.github.dozermapper.core.CustomConverter;
import com.github.dozermapper.core.builder.model.jaxb.FieldDefinition;
import com.github.dozermapper.core.builder.model.jaxb.Relationship;
import com.github.dozermapper.core.builder.model.jaxb.Type;

public final class FieldDefinitionOptions {

    private FieldDefinitionOptions() {
    }

    public static DefinitionOption<FieldDefinition> hintA(final Class<?>... type) {
        return definition -> definition.withAHints(type);
    }

    public static DefinitionOption<FieldDefinition> hintB(final Class<?>... type) {
        return definition -> definition.withBHints(type);
    }

    public static DefinitionOption<FieldDefinition> deepHintA(final Class<?>... type) {
        return definition -> definition.withADeepIndexHints(type);
    }

    public static DefinitionOption<FieldDefinition> deepHintB(final Class<?>... type) {
        return definition -> definition.withBDeepIndexHints(type);
    }

    public static DefinitionOption<FieldDefinition> relationshipType(final Relationship relationshipType) {
        return definition -> definition.withRelationshipType(relationshipType);
    }

    public static DefinitionOption<FieldDefinition> removeOrphans() {
        return removeOrphans(Boolean.TRUE);
    }

    public static DefinitionOption<FieldDefinition> removeOrphans(final Boolean removeOrphans) {
        return definition -> definition.withRemoveOrphans(removeOrphans);
    }

    public static DefinitionOption<FieldDefinition> oneWay() {
        return type(Type.ONE_WAY);
    }

    public static DefinitionOption<FieldDefinition> type(Type type) {
        return definition -> definition.withType(type);
    }

    public static DefinitionOption<FieldDefinition> useMapId(final String mapId) {
        return definition -> definition.withMapId(mapId);
    }

    public static DefinitionOption<FieldDefinition> copyByReference() {
        return definition -> definition.withCopyByReference(true);
    }

    public static DefinitionOption<FieldDefinition> customConverter(final Class<? extends CustomConverter> type) {
        return customConverter(type.getName(), null);
    }

    public static DefinitionOption<FieldDefinition> customConverter(final Class<? extends CustomConverter> type, final String parameter) {
        return customConverter(type.getName(), parameter);
    }

    public static DefinitionOption<FieldDefinition> customConverter(final String typeName) {
        return customConverter(typeName, null);
    }

    public static DefinitionOption<FieldDefinition> customConverter(final String typeName, final String parameter) {
        return definition -> {
            definition.withCustomConverter(typeName);
            definition.withCustomConverterParam(parameter);
        };
    }

    public static DefinitionOption<FieldDefinition> customConverterId(final String id) {
        return definition -> definition.withCustomConverterId(id);
    }

    public static DefinitionOption<FieldDefinition> customConverterId(final String id, final String parameter) {
        return definition -> {
            definition.withCustomConverterId(id);
            definition.withCustomConverterParam(parameter);
        };
    }

    public static DefinitionOption<FieldDefinition> collectionStrategy(final boolean removeOrphans, final Relationship relationshipType) {
        return definition -> {
            definition.withRemoveOrphans(removeOrphans);
            definition.withRelationshipType(relationshipType);
        };
    }
}
