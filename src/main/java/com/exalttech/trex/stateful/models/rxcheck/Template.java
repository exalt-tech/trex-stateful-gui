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

package com.exalttech.trex.stateful.models.rxcheck;

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
        "id",
        "val",
        "rx_pkts",
        "jitter"
})
public class Template {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("val")
    private Long val;
    @JsonProperty("rx_pkts")
    private Long rxPkts;
    @JsonProperty("jitter")
    private Long jitter;

    /**
     * No args constructor for use in serialization
     */
    public Template() {
        //constructor
    }

    /**
     * @param val
     * @param id
     * @param rxPkts
     * @param jitter
     */
    public Template(Long id, Long val, Long rxPkts, Long jitter) {
        this.id = id;
        this.val = val;
        this.rxPkts = rxPkts;
        this.jitter = jitter;
    }

    /**
     * @return The id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * @param id The id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return The val
     */
    @JsonProperty("val")
    public Long getVal() {
        return val;
    }

    /**
     * @param val The val
     */
    @JsonProperty("val")
    public void setVal(Long val) {
        this.val = val;
    }

    /**
     * @return The rxPkts
     */
    @JsonProperty("rx_pkts")
    public Long getRxPkts() {
        return rxPkts;
    }

    /**
     * @param rxPkts The rx_pkts
     */
    @JsonProperty("rx_pkts")
    public void setRxPkts(Long rxPkts) {
        this.rxPkts = rxPkts;
    }

    /**
     * @return The jitter
     */
    @JsonProperty("jitter")
    public Long getJitter() {
        return jitter;
    }

    /**
     * @param jitter The jitter
     */
    @JsonProperty("jitter")
    public void setJitter(Long jitter) {
        this.jitter = jitter;
    }

}
