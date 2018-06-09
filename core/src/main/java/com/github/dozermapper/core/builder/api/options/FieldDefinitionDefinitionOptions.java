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

import com.github.dozermapper.core.builder.model.jaxb.FieldDefinitionDefinition;
import com.github.dozermapper.core.builder.model.jaxb.FieldType;

public final class FieldDefinitionDefinitionOptions {

    private FieldDefinitionDefinitionOptions() {
    }

    public static DefinitionOption<FieldDefinitionDefinition> dateFormat(final String value) {
        return definition -> definition.withDateFormat(value);
    }

    public static DefinitionOption<FieldDefinitionDefinition> iterate() {
        return type(FieldType.ITERATE);
    }

    public static DefinitionOption<FieldDefinitionDefinition> type(final FieldType value) {
        return definition -> definition.withType(value);
    }

    public static DefinitionOption<FieldDefinitionDefinition> getMethod(final String getMethod) {
        return definition -> definition.withGetMethod(getMethod);
    }

    public static DefinitionOption<FieldDefinitionDefinition> setMethod(final String setMethod) {
        return definition -> definition.withSetMethod(setMethod);
    }

    public static DefinitionOption<FieldDefinitionDefinition> mapKey(final String value) {
        return definition -> definition.withKey(value);
    }

    public static DefinitionOption<FieldDefinitionDefinition> mapMethods(final String getMethod, final String setMethod) {
        return definition -> definition.withMapGetMethod(getMethod).withMapSetMethod(setMethod);
    }

    public static DefinitionOption<FieldDefinitionDefinition> accessible() {
        return accessible(Boolean.TRUE);
    }

    public static DefinitionOption<FieldDefinitionDefinition> accessible(final Boolean isAccessible) {
        return definition -> definition.withAccessible(isAccessible);
    }

    public static DefinitionOption<FieldDefinitionDefinition> createMethod(final String method) {
        return definition -> definition.withCreateMethod(method);
    }
}
