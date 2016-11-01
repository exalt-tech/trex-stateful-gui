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
        "key",
        "val"
})
public class HistogramItem {

    @JsonProperty("key")
    private Long key;
    @JsonProperty("val")
    private Long val;

    /**
     * No args constructor for use in serialization
     */
    public HistogramItem() {
        //constructor
    }

    /**
     * @param val
     * @param key
     */
    public HistogramItem(Long key, Long val) {
        this.key = key;
        this.val = val;
    }

    /**
     * @return The key
     */
    @JsonProperty("key")
    public Long getKey() {
        return key;
    }

    /**
     * @param key The key
     */
    @JsonProperty("key")
    public void setKey(Long key) {
        this.key = key;
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

}