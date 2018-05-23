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
package com.github.dozermapper.protobuf.propertydescriptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.github.dozermapper.core.BeanBuilder;
import com.github.dozermapper.core.MappingException;
import com.github.dozermapper.core.config.BeanContainer;
import com.github.dozermapper.core.factory.DestBeanCreator;
import com.github.dozermapper.core.fieldmap.FieldMap;
import com.github.dozermapper.core.fieldmap.HintContainer;
import com.github.dozermapper.core.propertydescriptor.DozerPropertyDescriptor;
import com.github.dozermapper.core.propertydescriptor.PropertyDescriptorFactory;
import com.github.dozermapper.core.util.MappingUtils;
import com.github.dozermapper.protobuf.builder.ProtoBeanBuilder;
import com.github.dozermapper.protobuf.propertydescriptor.utils.DeepHierarchyUtils;
import com.github.dozermapper.protobuf.util.ProtoUtils;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link com.github.dozermapper.core.propertydescriptor.DozerPropertyDescriptor} which resolves Protobuf fields
 */
class ProtoFieldPropertyDescriptor implements DozerPropertyDescriptor {

    private static final Logger LOG = LoggerFactory.getLogger(ProtoFieldPropertyDescriptor.class);

    private final Class<?> clazz;
    private final String fieldName;
    private final boolean isIndexed;
    private final int index;
    private final HintContainer srcDeepIndexHintContainer;
    private final HintContainer destDeepIndexHintContainer;
    private final BeanContainer beanContainer;
    private final DestBeanCreator destBeanCreator;
    private final PropertyDescriptorFactory propertyDescriptorFactory;

    private Class<?> propertyType;
    private Class<?> genericType;
    private Descriptors.FieldDescriptor fieldDescriptor;

    /**
     * {@link com.github.dozermapper.core.propertydescriptor.DozerPropertyDescriptor} which resolves Protobuf fields
     *
     * @param clazz                      clazz to work on
     * @param fieldName                  field name to resolve
     * @param isIndexed                  whether the mapping is indexed
     * @param index                      current index
     * @param srcDeepIndexHintContainer  source hint
     * @param destDeepIndexHintContainer destination hint
     * @param beanContainer              {@link BeanContainer} instance
     * @param destBeanCreator            {@link DestBeanCreator} instance
     * @param propertyDescriptorFactory  {@link PropertyDescriptorFactory} instance
     */
    ProtoFieldPropertyDescriptor(Class<?> clazz, String fieldName, boolean isIndexed, int index, HintContainer srcDeepIndexHintContainer,
                                        HintContainer destDeepIndexHintContainer, BeanContainer beanContainer, DestBeanCreator destBeanCreator,
                                        PropertyDescriptorFactory propertyDescriptorFactory) {
        this.clazz = clazz;
        this.fieldName = fieldName;
        this.isIndexed = isIndexed;
        this.index = index;
        this.srcDeepIndexHintContainer = srcDeepIndexHintContainer;
        this.destDeepIndexHintContainer = destDeepIndexHintContainer;
        this.beanContainer = beanContainer;
        this.destBeanCreator = destBeanCreator;
        this.propertyDescriptorFactory = propertyDescriptorFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> getPropertyType() {
        if (this.propertyType == null) {

            Class<?> result;
            if (MappingUtils.isDeepMapping(fieldName)) {
                try {
                    result = DeepHierarchyUtils.getDeepFieldType(clazz, fieldName, srcDeepIndexHintContainer, beanContainer, destBeanCreator, propertyDescriptorFactory);
                } catch (Exception ignore) {
                    LOG.info("Determine field type by srcDeepIndexHintContainer failed");

                    try {
                        result = DeepHierarchyUtils.getDeepFieldType(clazz, fieldName, destDeepIndexHintContainer, beanContainer, destBeanCreator, propertyDescriptorFactory);
                    } catch (Exception secondIgnore) {
                        LOG.info("Determine field type by destDeepIndexHintContainer failed");
                        result = null;
                    }
                }
            } else {
                result = ProtoUtils.getJavaClass(getFieldDescriptor(), beanContainer);
            }

            this.propertyType = result;
        }

        return this.propertyType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getPropertyValue(Object bean) {
        Object result;
        if (MappingUtils.isDeepMapping(fieldName)) {
            result = DeepHierarchyUtils.getDeepFieldValue(bean, fieldName, isIndexed, index, srcDeepIndexHintContainer, beanContainer, destBeanCreator, propertyDescriptorFactory);
        } else {
            result = getSimplePropertyValue(bean);
            if (isIndexed) {
                result = MappingUtils.getIndexedValue(result, index);
            }
        }

        return result;
    }

    private Object getSimplePropertyValue(Object bean) {
        // Proto builder can't contains already created object and even if contain - it's fields can't be changed
        if (bean instanceof BeanBuilder) {
            return null;
        }

        if (!(bean instanceof Message)) {
            throw new MappingException("Expected a Message instance, but got "
                                       + bean.getClass().getCanonicalName());
        }

        Object value = ProtoUtils.getFieldValue(bean, fieldName);

        return ProtoUtils.unwrapEnums(value, beanContainer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPropertyValue(Object bean, Object value, FieldMap fieldMap) {
        if (!(bean instanceof ProtoBeanBuilder)) {
            throw new MappingException("Expected a ProtoBeanBuilder instance, but got "
                                       + bean.getClass().getCanonicalName());
        }

        ProtoBeanBuilder builder = (ProtoBeanBuilder)bean;

        value = ProtoUtils.wrapEnums(value);
        if (value != null) {
            if (getFieldDescriptor().isMapField()) {
                // Capitalize the first letter of the string;
                String propertyName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                String methodName = String.format("putAll%s", propertyName);

                try {
                    Method mapSetterMethod = builder.internalProtoBuilder().getClass().getMethod(methodName, Map.class);
                    mapSetterMethod.invoke(builder.internalProtoBuilder(), value);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                    throw new MappingException("Could not call map setter method " + methodName, ex);
                }
            } else {
                builder.internalProtoBuilder().setField(getFieldDescriptor(), value);
            }
        } else {
            builder.internalProtoBuilder().clearField(getFieldDescriptor());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> genericType() {
        if (this.genericType == null) {

            Class<?> result;
            if (MappingUtils.isDeepMapping(fieldName)) {
                try {
                    result = DeepHierarchyUtils.getDeepGenericType(clazz, fieldName, srcDeepIndexHintContainer, beanContainer, destBeanCreator, propertyDescriptorFactory);
                } catch (Exception ignore) {
                    LOG.info("Determine field generic type by srcDeepIndexHintContainer failed");

                    try {
                        result = DeepHierarchyUtils.getDeepGenericType(clazz, fieldName, destDeepIndexHintContainer, beanContainer, destBeanCreator, propertyDescriptorFactory);
                    } catch (Exception secondIgnore) {
                        LOG.info("Determine field generic type by destDeepIndexHintContainer failed");
                        result = null;
                    }
                }
            } else {
                result = ProtoUtils.getJavaGenericClassForCollection(getFieldDescriptor(), beanContainer);
            }

            this.genericType = result;
        }

        return this.genericType;
    }

    @SuppressWarnings("unchecked")
    private Descriptors.FieldDescriptor getFieldDescriptor() {
        if (this.fieldDescriptor == null && Message.class.isAssignableFrom(clazz)) {
            this.fieldDescriptor = ProtoUtils.getFieldDescriptor((Class<? extends Message>)clazz, fieldName);
            if (this.fieldDescriptor == null && !MappingUtils.isDeepMapping(fieldName)) {
                throw new MappingException("No field descriptor for field with name: " + fieldName);
            }
        }

        return this.fieldDescriptor;
    }
}
