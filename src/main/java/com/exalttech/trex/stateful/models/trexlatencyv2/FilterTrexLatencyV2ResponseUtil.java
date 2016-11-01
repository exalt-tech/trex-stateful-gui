/*
 *
 *  * *****************************************************************************
 *  * Copyright (c) 2016
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  ******************************************************************************
 *
 */

package com.exalttech.trex.stateful.models.trexlatencyv2;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BassamJ
 */
public class FilterTrexLatencyV2ResponseUtil {
    private static final Logger LOG = Logger.getLogger(FilterTrexLatencyV2ResponseUtil.class.getName());


    private FilterTrexLatencyV2ResponseUtil() {
        //constructor
    }

    /**
     * Filter Response to create PortProperty
     *
     * @return
     */
    public static List<PortProperty> filterPortPropertiesArray(String response) {

        JSONObject jObject = new JSONObject(response);
        JSONObject data = jObject.getJSONObject("data");
        int numOfPorts = countNumberOfPorts(response);
        List<PortProperty> portProperty = new ArrayList<PortProperty>();
        ObjectMapper mapper = new ObjectMapper();

        for (int i = 0; i < numOfPorts; i++) {
            try {
                portProperty.add(
                        mapper.readValue(data.getJSONObject("port-" + i).toString(), PortProperty.class)
                );
            } catch (Exception ex) {
                LOG.error("Error: ", ex);
            }
        }
        return portProperty;
    }

    /**
     * Count the number of ports depending on the number of special attributes for PORT
     */
    private static int countNumberOfPorts(String response) {
        return response.split("port-", -1).length - 1;
    }


}
