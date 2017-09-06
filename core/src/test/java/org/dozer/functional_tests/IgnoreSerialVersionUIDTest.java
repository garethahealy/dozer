/*
 * Copyright 2005-2017 Dozer Project
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
package org.dozer.functional_tests;

import org.dozer.DozerBeanMapperBuilder;
import org.dozer.Mapper;
import org.dozer.vo.SerializableAImpl;
import org.dozer.vo.SourceSerializableA;
import org.dozer.vo.SourceSerializableB;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IgnoreSerialVersionUIDTest {

    @Test
    public void canMapBetween() {
        Mapper mapper = DozerBeanMapperBuilder.create()
                .withMappingFiles("mappings/ignoreSerialVersionUIDMapping.xml")
                .build();

        SourceSerializableA a = new SourceSerializableA();
        a.setValue(new SerializableAImpl(Integer.valueOf(9)));

        SourceSerializableB b = mapper.map(a, SourceSerializableB.class);

        assertEquals(a.getValue().getId(), b.getValue().getId());
    }
}
