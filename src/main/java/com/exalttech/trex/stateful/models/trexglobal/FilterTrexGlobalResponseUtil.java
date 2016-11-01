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

package com.exalttech.trex.stateful.models.trexglobal;

import com.exalttech.trex.stateful.utilities.Constants;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class is responsible to parse TrexGlobal response , and create Java Object from it
 *
 * @author BassamJ
 */
public class FilterTrexGlobalResponseUtil {


    private FilterTrexGlobalResponseUtil() {
        //constructor
    }

    /**
     * Filter Response to create PortProperty
     *
     * @return
     */
    public static List<PortProperties> createPortPropertiesArray(String response) {
        JSONObject jObject = new JSONObject(response);
        JSONObject data = jObject.getJSONObject("data");
        int numOfPorts = countNumberOfPorts(response);

        List<PortProperties> portProperties = new ArrayList<PortProperties>();
        for (int i = 0; i < numOfPorts; i++) {
            portProperties.add(new PortProperties(
                    Long.parseLong(data.get(Constants.PORT_OPACKETS_KEY + "-" + i).toString()),
                    Long.parseLong(data.get(Constants.PORT_OBYTES_KEY + "-" + i).toString()),
                    Long.parseLong(data.get(Constants.PORT_IPACKETS_KEY + "-" + i).toString()),
                    Long.parseLong(data.get(Constants.PORT_IBYTES_KEY + "-" + i).toString()),
                    Long.parseLong(data.get(Constants.PORT_IERROR_KEY + "-" + i).toString()),
                    Long.parseLong(data.get(Constants.PORT_OERROR_KEY + "-" + i).toString()),
                    Double.parseDouble(data.get(Constants.PORT_M_TOTAL_TX_BPS_KEY + "-" + i).toString()),
                    Double.parseDouble(data.get(Constants.PORT_M_TOTAL_TX_PPS_KEY + "-" + i).toString()),
                    Double.parseDouble(data.get(Constants.PORT_M_TOTAL_RX_BPS_KEY + "-" + i).toString()),
                    Double.parseDouble(data.get(Constants.PORT_M_TOTAL_RX_PPS_KEY + "-" + i).toString())
            ));
        }
        return portProperties;
    }

    /**
     * Count the number of ports depending on the number of special attributes for PORT
     */
    private static int countNumberOfPorts(String response) {
        return response.split(Constants.PORT_OPACKETS_KEY, -1).length - 1;
    }
}


