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

import java.util.Arrays;

import org.dozer.AbstractDozerTest;
import org.dozer.DozerBeanMapper;
import org.dozer.vo.GetWeatherByZipCodeDocument.GetWeatherByZipCode;
import org.junit.Assert;
import org.junit.Test;

public class XmlBeanToXmlBeanTest extends AbstractDozerTest {

    @Test
    public void objectToObject() {
        GetWeatherByZipCode source = GetWeatherByZipCode.Factory.newInstance();
        source.setZipCode("90210");
        GetWeatherByZipCode destination = GetWeatherByZipCode.Factory.newInstance();

        new DozerBeanMapper(Arrays.asList("GetWeatherByZipCode-mapping.xml")).map(source, destination);
        Assert.assertEquals(source.getZipCode(), destination.getZipCode());
    }

}
