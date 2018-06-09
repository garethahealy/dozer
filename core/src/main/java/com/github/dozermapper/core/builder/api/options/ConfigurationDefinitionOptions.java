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

import com.github.dozermapper.core.builder.model.jaxb.ConfigurationDefinition;
import com.github.dozermapper.core.builder.model.jaxb.Relationship;

public final class ConfigurationDefinitionOptions {

    private ConfigurationDefinitionOptions() {
    }

    public static DefinitionOption<ConfigurationDefinition> stopOnErrors() {
        return stopOnErrors(Boolean.TRUE);
    }

    public static DefinitionOption<ConfigurationDefinition> stopOnErrors(final Boolean value) {
        return definition -> definition.withStopOnErrors(value);
    }

    public static DefinitionOption<ConfigurationDefinition> dateFormat(final String value) {
        return definition -> definition.withDateFormat(value);
    }

    public static DefinitionOption<ConfigurationDefinition> wildcard(final Boolean value) {
        return definition -> definition.withWildcard(value);
    }

    public static DefinitionOption<ConfigurationDefinition> wildcardCaseInsensitive(final Boolean value) {
        return definition -> definition.withWildcardCaseInsensitive(value);
    }

    public static DefinitionOption<ConfigurationDefinition> trimStrings() {
        return trimStrings(Boolean.TRUE);
    }

    public static DefinitionOption<ConfigurationDefinition> trimStrings(final Boolean value) {
        return definition -> definition.withTrimStrings(value);
    }

    public static DefinitionOption<ConfigurationDefinition> mapNull() {
        return mapNull(Boolean.TRUE);
    }

    public static DefinitionOption<ConfigurationDefinition> mapNull(final Boolean value) {
        return definition -> definition.withMapNull(value);
    }

    public static DefinitionOption<ConfigurationDefinition> mapEmptyString() {
        return mapEmptyString(Boolean.TRUE);
    }

    public static DefinitionOption<ConfigurationDefinition> mapEmptyString(final Boolean value) {
        return definition -> definition.withMapEmptyString(value);
    }

    public static DefinitionOption<ConfigurationDefinition> beanFactory(final String value) {
        return definition -> definition.withBeanFactory(value);
    }

    public static DefinitionOption<ConfigurationDefinition> relationshipType(final Relationship relationshipType) {
        return definition -> definition.withRelationshipType(relationshipType);
    }

    public static DefinitionOption<ConfigurationDefinition> customConverter(final String typeName, final String clazzA, final String clazzB) {
        // @formatter:off
        return definition -> definition.withCustomConverters()
                .withConverter()
                    .withType(typeName)
                    .withClassA()
                        .withClazz(clazzA)
                    .endType()
                    .withClassB()
                        .withClazz(clazzB)
                    .endType()
                .end();
        // @formatter:on
    }

    public static DefinitionOption<ConfigurationDefinition> copyByReference(String ref) {
        return definition -> definition.withCopyByReferences().addCopyByReference(ref);
    }

    public static DefinitionOption<ConfigurationDefinition> allowedException(String clazz) {
        return definition -> definition.withAllowedExceptions().addException(clazz);
    }

    public static DefinitionOption<ConfigurationDefinition> variables(String name, String clazz) {
        return definition -> definition.withVariables().withVariable()
                .withName(name)
                .withClazz(clazz);
    }
}
