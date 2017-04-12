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
import org.dozer.Mapper;
import org.dozer.vo.GetWeatherByPlaceNameResponseDocument.GetWeatherByPlaceNameResponse;
import org.dozer.vo.WeatherForecasts;
import org.junit.Before;
import org.junit.Test;

public class XmlBeansFactoryTest extends AbstractDozerTest {

    private Mapper mapper;
    private ForecastClass forecastBean;
    private WeatherForecasts forecastXml;

    @Override
    @Before
    public void setUp() {
        mapper = new DozerBeanMapper(Arrays.asList("WeatherForecasts-mapping.xml"));
        forecastBean = new ForecastClass();
        forecastBean.setPlaceName(getRandomString());
        forecastXml = WeatherForecasts.Factory.newInstance();
    }

    @Test
    public void populateThenCreateDestination() {
        // Create a broken mapping
        mapper.map(forecastBean, forecastXml);
        assertEquals(forecastBean.getPlaceName(), forecastXml.getPlaceName());

        // Dozer now has to create the xml which it can't do because of
        // broken map.
        assertEquals(forecastBean.getPlaceName(), mapper.map(forecastBean, WeatherForecasts.class).getPlaceName());
    }

    @Test
    public void createDestinationFirst() {
        // This mapping won't be broken (java bean to xml bean interface)
        assertEquals(forecastBean.getPlaceName(), mapper.map(forecastBean, WeatherForecasts.class).getPlaceName());
        // This mapping will still work
        mapper.map(forecastBean, forecastXml);
        assertEquals(forecastBean.getPlaceName(), forecastXml.getPlaceName());
        // This mapping still won't be broken
        assertEquals(forecastBean.getPlaceName(), mapper.map(forecastBean, WeatherForecasts.class).getPlaceName());
    }

    @Test //todo
    public void populateInnerThenCreateOuter() {
        // Create a broken mapping
        mapper.map(forecastBean, forecastXml);
        assertEquals(forecastBean.getPlaceName(), forecastXml.getPlaceName());

        WeatherResponseClass responseBean = new WeatherResponseClass();
        responseBean.setGetWeatherByPlaceNameResult(forecastBean);

        // Dozer now has to create the xml which it can't do because of
        // broken map.
        GetWeatherByPlaceNameResponse responseXml = mapper.map(responseBean, GetWeatherByPlaceNameResponse.class);
        assertEquals(responseBean.getGetWeatherByPlaceNameResult().getPlaceName(), responseXml
            .getGetWeatherByPlaceNameResult().getPlaceName());
    }

    @Test
    public void createInnerThenCreateOuter() {
        // Create correct mapping
        assertEquals(forecastBean.getPlaceName(), mapper.map(forecastBean, WeatherForecasts.class).getPlaceName());

        // then do exactly the same thing as populateInnerThenCreateOuter
        mapper.map(forecastBean, forecastXml);
        assertEquals(forecastBean.getPlaceName(), forecastXml.getPlaceName());

        WeatherResponseClass responseBean = new WeatherResponseClass();
        responseBean.setGetWeatherByPlaceNameResult(forecastBean);

        GetWeatherByPlaceNameResponse responseXml = mapper.map(responseBean, GetWeatherByPlaceNameResponse.class);
        assertEquals(responseBean.getGetWeatherByPlaceNameResult().getPlaceName(), responseXml
            .getGetWeatherByPlaceNameResult().getPlaceName());
    }

    public static class WeatherResponseClass {
        private ForecastClass getWeatherByPlaceNameResult;

        public ForecastClass getGetWeatherByPlaceNameResult() {
            return getWeatherByPlaceNameResult;
        }

        public void setGetWeatherByPlaceNameResult(ForecastClass getWeatherByPlaceNameResult) {
            this.getWeatherByPlaceNameResult = getWeatherByPlaceNameResult;
        }
    }

    public static class ForecastClass {
        private String placeName;

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }
    }

}
