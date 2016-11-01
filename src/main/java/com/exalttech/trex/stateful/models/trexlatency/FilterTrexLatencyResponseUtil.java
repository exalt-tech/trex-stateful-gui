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

package com.exalttech.trex.stateful.models.trexlatency;

import com.exalttech.trex.stateful.utilities.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BassamJ
 */
public class FilterTrexLatencyResponseUtil {


    private FilterTrexLatencyResponseUtil() {
        //constructor
    }

    /**
     * Filter Response to create PortProperty
     *
     * @return
     */
    public static List<PortProperty> filterPortPropertiesArray(String response) {
        Map<String, String> map = new HashMap<String, String>();
        String[] parts = response.replaceAll("^\\{|\\}$", "").split("\"?(:|,)(?![^\\{]*\\})\"?");
        for (int i = 0; i < parts.length - 1; i += 2) {
            map.put(parts[i], parts[i + 1]);
        }
        String data = map.get("data");

        Map<String, String> mapData = new HashMap<String, String>();
        String[] dataParts = data.replaceAll("^\\{|\\}$", "").split("\"?(:|,)(?![^\\{]*\\})\"?");
        for (int i = 0; i < dataParts.length - 1; i += 2) {
            mapData.put(dataParts[i].replaceAll("\"", ""), dataParts[i + 1]);
        }
        //Data Map should contain all Key: value pairs

        List<PortProperty> portProperties = new ArrayList<PortProperty>();
        int numOfPorts = countNumberOfPorts(response);
        for (int i = 0; i < numOfPorts; i++) {
            portProperties.add(new PortProperty(
                    Double.parseDouble(mapData.get(Constants.TREX_LATENCY_AVG_KEY + "-" + i)),
                    Double.parseDouble(mapData.get(Constants.TREX_LATENCY_MAX_KEY + "-" + i)),
                    Double.parseDouble(mapData.get(Constants.TREX_LATENCY_C_MAX_KEY + "-" + i)),
                    Double.parseDouble(mapData.get(Constants.TREX_LATENCY_ERROR_KEY + "-" + i)),
                    Double.parseDouble(mapData.get(Constants.TREX_LATENCY_JITTER_KEY + "-" + i))
            ));
        }
        return portProperties;
    }

    /**
     * Count the number of ports depending on the number of special attributes for PORT
     */
    private static int countNumberOfPorts(String response) {
        return response.split(Constants.TREX_LATENCY_AVG_KEY, -1).length - 1;
    }
}
