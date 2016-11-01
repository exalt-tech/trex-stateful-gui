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

/**
 * @author BassamJ
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "hist",
        "stats"
})
public class PortProperty {

    @JsonProperty("hist")
    private Hist hist;
    @JsonProperty("stats")
    private Stats stats;

    /**
     * No args constructor for use in serialization
     */
    public PortProperty() {
        //constructor
    }

    /**
     * @param stats
     * @param hist
     */
    public PortProperty(Hist hist, Stats stats) {
        this.hist = hist;
        this.stats = stats;
    }

    /**
     * @return The hist
     */
    @JsonProperty("hist")
    public Hist getHist() {
        return hist;
    }

    /**
     * @param hist The hist
     */
    @JsonProperty("hist")
    public void setHist(Hist hist) {
        this.hist = hist;
    }

    /**
     * @return The stats
     */
    @JsonProperty("stats")
    public Stats getStats() {
        return stats;
    }

    /**
     * @param stats The stats
     */
    @JsonProperty("stats")
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    @Override
    public String toString() {
        return "PortProperty{" +
                "hist=" + hist +
                ", stats=" + stats +
                '}';
    }
}
