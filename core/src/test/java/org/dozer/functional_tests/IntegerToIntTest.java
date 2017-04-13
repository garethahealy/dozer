/**
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

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IntegerToIntTest extends AbstractFunctionalTest {

    @Before
    public void setUp() throws Exception {
        mapper = getMapper("integertoint-mapper.xml");
    }

    @Test
    public void testInteger() {
        Integer result = mapper.map(1, Integer.class);

        assertNotNull(result);
        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    public void testInt() {
        int result2 = mapper.map(Integer.valueOf(2), int.class);

        assertNotNull(result2);
        assertEquals(2, result2);
    }

    @Test
    public void testLong() {
        Long result3 = mapper.map(3, Long.class);

        assertNotNull(result3);
        assertEquals(Long.valueOf(3), result3);
    }
}
