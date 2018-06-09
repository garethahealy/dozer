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

/**
 * Resolves types
 */
public interface ClassTypeResolver {

    /**
     * dd
     *
     * @param clazz        d
     * @param method       dd
     * @param isReadMethod dd
     * @return dd
     */
    Class<?> determineGenericsType(Class<?> clazz, Method method, boolean isReadMethod);

    /**
     * dd
     *
     * @param targetClass dd
     * @param readMethod  dd
     * @param writeMethod dd
     * @return dd
     */
    Class<?> resolvePropertyType(Class<?> targetClass, Method readMethod, Method writeMethod);

    /**
     * dsd
     *
     * @param parentClazz    dd
     * @param propDescriptor dd
     * @return d
     */
    Class<?> determineGenericsType(Class<?> parentClazz, PropertyDescriptor propDescriptor);

    /**
     * dff
     *
     * @param type ff
     * @return ff
     */
    Class<?> determineGenericsType(Type type);

    /**
     * dd
     *
     * @param clazz     dd
     * @param fieldName dd
     * @return dd
     */
    Field getFieldFromBean(Class<?> clazz, String fieldName);
}
