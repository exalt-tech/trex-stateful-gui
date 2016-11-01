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

package com.exalttech.trex.stateful.models.txgenresponse;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author BassamJ
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "realtime-hist",
        "unknown"
})

public class Data {

    @JsonProperty("realtime-hist")
    private RealtimeHist realtimeHist;
    @JsonProperty("unknown")
    private Long unknown;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Data() {
        //constructor
    }

    /**
     * @param unknown
     * @param realtimeHist
     */
    public Data(RealtimeHist realtimeHist, Long unknown) {
        this.realtimeHist = realtimeHist;
        this.unknown = unknown;
    }

    /**
     * @return The realtimeHist
     */
    @JsonProperty("realtime-hist")
    public RealtimeHist getRealtimeHist() {
        return realtimeHist;
    }

    /**
     * @param realtimeHist The realtime-hist
     */
    @JsonProperty("realtime-hist")
    public void setRealtimeHist(RealtimeHist realtimeHist) {
        this.realtimeHist = realtimeHist;
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

    /**
     * @return
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * @param name
     * @param value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
