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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BassamJ
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "cpu_util",
        "unknown"
})
public class Data {

    @JsonProperty("cpu_util")
    private Double cpuUtil;

    private List<PortProperty> portProperty = new ArrayList<PortProperty>();
    @JsonProperty("unknown")
    private Long unknown;

    /**
     * No args constructor for use in serialization
     */
    public Data() {
        //constructor
    }

    /**
     * @param cpuUtil
     * @param unknown
     * @param portProperty
     */
    public Data(Double cpuUtil, List<PortProperty> portProperty, Long unknown) {
        this.cpuUtil = cpuUtil;
        this.portProperty = portProperty;
        this.unknown = unknown;
    }

    /**
     * @return The cpuUtil
     */
    @JsonProperty("cpu_util")
    public Double getCpuUtil() {
        return cpuUtil;
    }

    /**
     * @param cpuUtil The cpu_util
     */
    @JsonProperty("cpu_util")
    public void setCpuUtil(Double cpuUtil) {
        this.cpuUtil = cpuUtil;
    }

    /**
     * @return The portProperty
     */
    public List<PortProperty> getPortProperty() {
        return portProperty;
    }

    /**
     * @param portProperty The portProperty
     */
    public void setPortProperty(List<PortProperty> portProperty) {
        this.portProperty = portProperty;
    }

    /**
     * @return The unknown
     */
    @JsonProperty("unknown")
    public Long getUnknown() {
        return unknown;
    }

    /**
     * @param unknown The unknown
     */
    @JsonProperty("unknown")
    public void setUnknown(Long unknown) {
        this.unknown = unknown;
    }

}
