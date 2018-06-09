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
package com.github.dozermapper.core.builder.api;

import com.github.dozermapper.core.builder.api.options.DefinitionOption;
import com.github.dozermapper.core.builder.model.jaxb.FieldDefinition;
import com.github.dozermapper.core.builder.model.jaxb.FieldDefinitionDefinition;
import com.github.dozermapper.core.builder.model.jaxb.MappingDefinition;

public class MappingNameNotSure {

    private final MappingDefinition mapping;

    public MappingNameNotSure(MappingDefinition mapping) {
        this.mapping = mapping;
    }

    public MappingNameNotSure fields(String a, String b, DefinitionOption<FieldDefinition>... options) {
        return fields(new FieldDefinitionDefinition().withName(a), new FieldDefinitionDefinition().withName(b), options);
    }

    public MappingNameNotSure fields(FieldDefinitionDefinition a, String b, DefinitionOption<FieldDefinition>... options) {
        return fields(a, new FieldDefinitionDefinition().withName(b), options);
    }

    public MappingNameNotSure fields(String a, FieldDefinitionDefinition b, DefinitionOption<FieldDefinition>... options) {
        return fields(new FieldDefinitionDefinition().withName(a), b, options);
    }

    public MappingNameNotSure fields(FieldDefinitionDefinition a, FieldDefinitionDefinition b, DefinitionOption<FieldDefinition>... options) {
        FieldDefinition field = mapping.withField();
        field.withA(a).withB(b);

        for (DefinitionOption<FieldDefinition> option : options) {
            option.apply(field);
        }

        return this;
    }

    public MappingNameNotSure exclude(String field) {
        return exclude(new FieldDefinitionDefinition().withName(field));
    }

    public MappingNameNotSure exclude(String field, DefinitionOption<FieldDefinitionDefinition>... options) {
        return exclude(new FieldDefinitionDefinition().withName(field), options);
    }

    public MappingNameNotSure exclude(FieldDefinitionDefinition field, DefinitionOption<FieldDefinitionDefinition>... options) {
        for (DefinitionOption<FieldDefinitionDefinition> option : options) {
            option.apply(field);
        }

        mapping.withFieldExclude()
                .withA(field)
                .withB(field);

        return this;
    }
}
