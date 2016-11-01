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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BassamJ
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "min_usec",
        "max_usec",
        "high_cnt",
        "cnt",
        "s_avg",
        "histogram"
})
public class RealtimeHist {

    @JsonProperty("min_usec")
    private Long minUsec;
    @JsonProperty("max_usec")
    private Long maxUsec;
    @JsonProperty("high_cnt")
    private Long highCnt;
    @JsonProperty("cnt")
    private Long cnt;
    @JsonProperty("s_avg")
    private Double sAvg;
    @JsonProperty("histogram")
    private List<Histogram> histogram = new ArrayList<Histogram>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public RealtimeHist() {
        //constructor
    }

    /**
     * @param highCnt
     * @param maxUsec
     * @param cnt
     * @param minUsec
     * @param histogram
     * @param sAvg
     */
    public RealtimeHist(Long minUsec, Long maxUsec, Long highCnt, Long cnt, Double sAvg, List<Histogram> histogram) {
        this.minUsec = minUsec;
        this.maxUsec = maxUsec;
        this.highCnt = highCnt;
        this.cnt = cnt;
        this.sAvg = sAvg;
        this.histogram = histogram;
    }

    /**
     * @return The minUsec
     */
    @JsonProperty("min_usec")
    public Long getMinUsec() {
        return minUsec;
    }

    /**
     * @param minUsec The min_usec
     */
    @JsonProperty("min_usec")
    public void setMinUsec(Long minUsec) {
        this.minUsec = minUsec;
    }

    /**
     * @return The maxUsec
     */
    @JsonProperty("max_usec")
    public Long getMaxUsec() {
        return maxUsec;
    }

    /**
     * @param maxUsec The max_usec
     */
    @JsonProperty("max_usec")
    public void setMaxUsec(Long maxUsec) {
        this.maxUsec = maxUsec;
    }

    /**
     * @return The highCnt
     */
    @JsonProperty("high_cnt")
    public Long getHighCnt() {
        return highCnt;
    }

    /**
     * @param highCnt The high_cnt
     */
    @JsonProperty("high_cnt")
    public void setHighCnt(Long highCnt) {
        this.highCnt = highCnt;
    }

    /**
     * @return The cnt
     */
    @JsonProperty("cnt")
    public Long getCnt() {
        return cnt;
    }

    /**
     * @param cnt The cnt
     */
    @JsonProperty("cnt")
    public void setCnt(Long cnt) {
        this.cnt = cnt;
    }

    /**
     * @return The sAvg
     */
    @JsonProperty("s_avg")
    public Double getSAvg() {
        return sAvg;
    }

    /**
     * @param sAvg The s_avg
     */
    @JsonProperty("s_avg")
    public void setSAvg(Double sAvg) {
        this.sAvg = sAvg;
    }

    /**
     * @return The histogram
     */
    @JsonProperty("histogram")
    public List<Histogram> getHistogram() {
        return histogram;
    }

    /**
     * @param histogram The histogram
     */
    @JsonProperty("histogram")
    public void setHistogram(List<Histogram> histogram) {
        this.histogram = histogram;
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
