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

import java.util.Arrays;
import java.util.List;

import com.github.dozermapper.core.builder.BeanMappingsBuilder;
import com.github.dozermapper.core.builder.api.options.ClassDefinitionOptions;
import com.github.dozermapper.core.builder.api.options.DefinitionOption;
import com.github.dozermapper.core.builder.model.elengine.ELMappingsDefinition;
import com.github.dozermapper.core.builder.model.jaxb.ClassDefinition;
import com.github.dozermapper.core.builder.model.jaxb.ConfigurationDefinition;
import com.github.dozermapper.core.builder.model.jaxb.FieldDefinitionDefinition;
import com.github.dozermapper.core.builder.model.jaxb.MappingDefinition;
import com.github.dozermapper.core.builder.model.jaxb.MappingsDefinition;
import com.github.dozermapper.core.classmap.Configuration;
import com.github.dozermapper.core.classmap.MappingFileData;
import com.github.dozermapper.core.config.BeanContainer;
import com.github.dozermapper.core.el.ELEngine;
import com.github.dozermapper.core.factory.DestBeanCreator;
import com.github.dozermapper.core.propertydescriptor.PropertyDescriptorFactory;
import com.github.dozermapper.core.util.DozerConstants;

/**
 * Builds a mapping definition based on a Java API
 */
public abstract class BeanMappingAPIBuilder implements BeanMappingsBuilder {

    private final MappingsDefinition mappingsDefinition;

    public BeanMappingAPIBuilder(ELEngine elEngine) {
        this.mappingsDefinition = new ELMappingsDefinition(elEngine);

        configure();
    }

    /**
     * Configure the {@link MappingsDefinition}
     */
    protected abstract void configure();

    protected ConfigurationDefinition configuration(DefinitionOption<ConfigurationDefinition>... configurationDefinitionOptions) {
        ConfigurationDefinition config = mappingsDefinition.withConfiguration();
        for (DefinitionOption<ConfigurationDefinition> option : configurationDefinitionOptions) {
            option.apply(config);
        }

        return config;
    }

    protected MappingNameNotSure mapping(String typeA, String typeB, DefinitionOption<MappingDefinition>... mappingDefinitionOptions) {
        return mapping(ClassDefinitionOptions.type(typeA), ClassDefinitionOptions.type(typeB), mappingDefinitionOptions);
    }

    protected MappingNameNotSure mapping(DefinitionOption<ClassDefinition> typeA, String typeB, DefinitionOption<MappingDefinition>... mappingDefinitionOptions) {
        return mapping(typeA, ClassDefinitionOptions.type(typeB), mappingDefinitionOptions);
    }

    protected MappingNameNotSure mapping(String typeA, DefinitionOption<ClassDefinition> typeB, DefinitionOption<MappingDefinition>... mappingDefinitionOptions) {
        return mapping(ClassDefinitionOptions.type(typeA), typeB, mappingDefinitionOptions);
    }

    protected MappingNameNotSure mapping(Class<?> typeA, Class<?> typeB, DefinitionOption<MappingDefinition>... mappingDefinitionOptions) {
        return mapping(ClassDefinitionOptions.type(typeA), ClassDefinitionOptions.type(typeB), mappingDefinitionOptions);
    }

    protected MappingNameNotSure mapping(DefinitionOption<ClassDefinition> typeA, Class<?> typeB, DefinitionOption<MappingDefinition>... mappingDefinitionOptions) {
        return mapping(typeA, ClassDefinitionOptions.type(typeB), mappingDefinitionOptions);
    }

    protected MappingNameNotSure mapping(Class<?> typeA, DefinitionOption<ClassDefinition> typeB, DefinitionOption<MappingDefinition>... mappingDefinitionOptions) {
        return mapping(ClassDefinitionOptions.type(typeA), typeB, mappingDefinitionOptions);
    }

    protected MappingNameNotSure mapping(DefinitionOption<ClassDefinition> typeA, DefinitionOption<ClassDefinition> typeB,
                                         DefinitionOption<MappingDefinition>... mappingDefinitionOptions) {
        MappingDefinition mapping = mappingsDefinition.addMapping()
                .withClassA().end()
                .withClassB().end();

        typeA.apply(mapping.getClassA());
        typeB.apply(mapping.getClassB());

        for (DefinitionOption<MappingDefinition> option : mappingDefinitionOptions) {
            option.apply(mapping);
        }

        return new MappingNameNotSure(mapping);
    }

    protected FieldDefinitionDefinition field(String name) {
        return new FieldDefinitionDefinition().withName(name);
    }

    protected FieldDefinitionDefinition field(String name, DefinitionOption<FieldDefinitionDefinition>... options) {
        FieldDefinitionDefinition field = new FieldDefinitionDefinition().withName(name);

        for (DefinitionOption<FieldDefinitionDefinition> option : options) {
            option.apply(field);
        }

        return field;
    }

    /**
     * References current object in mapping process.
     *
     * @return field definition
     */
    protected FieldDefinitionDefinition this_() {
        return new FieldDefinitionDefinition().withName(DozerConstants.SELF_KEYWORD);
    }

    protected FieldDefinitionDefinition this_(DefinitionOption<FieldDefinitionDefinition>... options) {
        FieldDefinitionDefinition field = new FieldDefinitionDefinition().withName(DozerConstants.SELF_KEYWORD);

        for (DefinitionOption<FieldDefinitionDefinition> option : options) {
            option.apply(field);
        }

        return field;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MappingFileData> build(BeanContainer beanContainer, DestBeanCreator destBeanCreator, PropertyDescriptorFactory propertyDescriptorFactory) {
        Configuration configuration = null;
        if (mappingsDefinition.getConfiguration() != null) {
            configuration = mappingsDefinition.getConfiguration().build(beanContainer);
        }

        MappingFileData data = new MappingFileData();
        data.setConfiguration(configuration);
        data.getClassMaps().addAll(mappingsDefinition.build(configuration, beanContainer, destBeanCreator, propertyDescriptorFactory));

        return Arrays.asList(data);
    }
}
