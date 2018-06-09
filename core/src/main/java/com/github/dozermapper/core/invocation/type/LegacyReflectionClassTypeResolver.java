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
package com.github.dozermapper.core.invocation.type;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import com.github.dozermapper.core.util.ReflectionUtils;

/**
 * Wrapper resolver around {@link ReflectionUtils}
 */
public class LegacyReflectionClassTypeResolver implements ClassTypeResolver {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> determineGenericsType(Class<?> clazz, Method method, boolean isReadMethod) {
        return ReflectionUtils.determineGenericsType(clazz, method, isReadMethod);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> resolvePropertyType(Class<?> targetClass, Method readMethod, Method writeMethod) {
        return com.github.dozermapper.core.propertydescriptor.utils.TypeResolver.resolvePropertyType(targetClass, readMethod, writeMethod);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> determineGenericsType(Class<?> parentClazz, PropertyDescriptor propDescriptor) {
        return ReflectionUtils.determineGenericsType(parentClazz, propDescriptor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<?> determineGenericsType(Type type) {
        return ReflectionUtils.determineGenericsType(type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field getFieldFromBean(Class<?> clazz, String fieldName) {
        return ReflectionUtils.getFieldFromBean(clazz, fieldName);
    }
}
