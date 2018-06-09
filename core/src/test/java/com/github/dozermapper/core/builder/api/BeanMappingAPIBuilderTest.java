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

import java.util.List;
import java.util.Map;

import com.github.dozermapper.core.builder.api.options.ConfigurationDefinitionOptions;
import com.github.dozermapper.core.builder.api.options.FieldDefinitionDefinitionOptions;
import com.github.dozermapper.core.builder.api.options.FieldDefinitionOptions;
import com.github.dozermapper.core.builder.api.options.MappingDefinitionOptions;
import org.dozer.builder.model.jaxb.Relationship;
import org.dozer.classmap.MappingFileData;
import org.dozer.config.BeanContainer;
import org.dozer.el.DefaultELEngine;
import org.dozer.el.ELEngine;
import org.dozer.el.ELExpressionFactory;
import org.dozer.factory.DestBeanCreator;
import org.dozer.factory.JAXBBeanFactory;
import org.dozer.functional_tests.builder.DozerBuilderTest;
import org.dozer.functional_tests.support.TestCustomConverter;
import org.dozer.propertydescriptor.PropertyDescriptorFactory;
import org.dozer.vo.CustomDoubleObjectIF;
import org.dozer.vo.SimpleEnum;
import org.junit.Test;

import static com.github.dozermapper.core.builder.api.options.ClassDefinitionOptions.type;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BeanMappingAPIBuilderTest {

    private class TestAPIBuilder extends BeanMappingAPIBuilder {

        TestAPIBuilder(ELEngine elEngine) {
            super(elEngine);
        }

        @Override
        protected void configure() {
            //NOTE: The below is not a valid mapping - it is simply testing all options dont fail

            // @formatter:off
            configuration(ConfigurationDefinitionOptions.stopOnErrors(),
                          ConfigurationDefinitionOptions.dateFormat("MM/dd/yyyy HH:mm"),
                          ConfigurationDefinitionOptions.wildcard(true),
                          ConfigurationDefinitionOptions.trimStrings(),
                          ConfigurationDefinitionOptions.mapNull(),
                          ConfigurationDefinitionOptions.mapEmptyString(),
                          ConfigurationDefinitionOptions.beanFactory(JAXBBeanFactory.class.getName()),
                          ConfigurationDefinitionOptions.relationshipType(Relationship.CUMULATIVE),
                          ConfigurationDefinitionOptions.customConverter(TestCustomConverter.class.getName(), CustomDoubleObjectIF.class.getName(), Double.class.getName()),
                          ConfigurationDefinitionOptions.copyByReference(SimpleEnum.class.getName()),
                          ConfigurationDefinitionOptions.allowedException(RuntimeException.class.getName()),
                          ConfigurationDefinitionOptions.variables("container", "org.dozer.functional_tests.VariablesTest$Container"));
            // @formatter:on

            // @formatter:off
            mapping(DozerBuilderTest.Bean.class, DozerBuilderTest.Bean.class,
                    MappingDefinitionOptions.mapNull(),
                    MappingDefinitionOptions.stopOnErrors(),
                    MappingDefinitionOptions.trimStrings(),
                    MappingDefinitionOptions.wildcard(true),
                    MappingDefinitionOptions.mapEmptyString(),
                    MappingDefinitionOptions.dateFormat("MM/dd/yyyy HH:mm"),
                    MappingDefinitionOptions.mapId("A"),
                    MappingDefinitionOptions.oneWay(),
                    MappingDefinitionOptions.beanFactory(JAXBBeanFactory.class.getName()),
                    MappingDefinitionOptions.relationshipType(Relationship.CUMULATIVE))
                    .exclude("excluded")
                    .fields("src", "dest",
                            FieldDefinitionOptions.hintA(String.class),
                            FieldDefinitionOptions.hintB(Integer.class),
                            FieldDefinitionOptions.deepHintA(String.class) ,
                            FieldDefinitionOptions.deepHintB(Integer.class),
                            FieldDefinitionOptions.relationshipType(  Relationship.NON_CUMULATIVE),
                            FieldDefinitionOptions.removeOrphans(),
                            FieldDefinitionOptions.oneWay(),
                            FieldDefinitionOptions.useMapId("A"),
                            FieldDefinitionOptions.copyByReference(),
                            FieldDefinitionOptions.customConverter(TestCustomConverter.class, "param"),
                            FieldDefinitionOptions.customConverterId("id"));
            // @formatter:on

            // @formatter:off
            mapping(type(DozerBuilderTest.Bean.class), type(Map.class.getName()))
                    .fields(field("src",
                                  FieldDefinitionDefinitionOptions.dateFormat("MM/dd/yyyy HH:mm"),
                                  FieldDefinitionDefinitionOptions.iterate(),
                                  FieldDefinitionDefinitionOptions.getMethod("get"),
                                  FieldDefinitionDefinitionOptions.setMethod("set"),
                                  FieldDefinitionDefinitionOptions.mapKey("id"),
                                  FieldDefinitionDefinitionOptions.mapMethods("get", "put"),
                                  FieldDefinitionDefinitionOptions.accessible(),
                                  FieldDefinitionDefinitionOptions.createMethod("create")),
                            this_(FieldDefinitionDefinitionOptions.dateFormat("MM/dd/yyyy HH:mm"),
                                  FieldDefinitionDefinitionOptions.iterate(),
                                  FieldDefinitionDefinitionOptions.getMethod("get"),
                                  FieldDefinitionDefinitionOptions.setMethod("set"),
                                  FieldDefinitionDefinitionOptions.mapKey("id"),
                                  FieldDefinitionDefinitionOptions.mapMethods("get", "put"),
                                  FieldDefinitionDefinitionOptions.accessible(),
                                  FieldDefinitionDefinitionOptions.createMethod("create")));
            // @formatter:on
        }
    }

    @Test
    public void canBuild() {
        ELEngine elEngine = new DefaultELEngine(ELExpressionFactory.newInstance());
        BeanMappingAPIBuilder builder = new TestAPIBuilder(elEngine);

        BeanContainer beanContainer = new BeanContainer();
        DestBeanCreator destBeanCreator = new DestBeanCreator(beanContainer);
        PropertyDescriptorFactory propertyDescriptorFactory = new PropertyDescriptorFactory();

        List<MappingFileData> answer = builder.build(beanContainer, destBeanCreator, propertyDescriptorFactory);

        assertNotNull(answer);
        assertTrue(answer.size() > 0);
    }
}
