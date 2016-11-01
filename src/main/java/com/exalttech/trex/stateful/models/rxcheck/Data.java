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

import java.util.ArrayList;
import java.util.List;

/**
 * @author BassamJ
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "stats",
        "latency_hist",
        "template",
        "timer_w",
        "unknown"
})
public class Data {

    @JsonProperty("stats")
    private Stats stats;
    @JsonProperty("latency_hist")
    private LatencyHist latencyHist;
    @JsonProperty("template")
    private List<Template> template = new ArrayList<Template>();
    @JsonProperty("timer_w")
    private TimerW timerW;
    @JsonProperty("unknown")
    private Integer unknown;

    /**
     * No args constructor for use in serialization
     */
    public Data() {
        //constructor
    }

    /**
     * @param template
     * @param latencyHist
     * @param stats
     * @param timerW
     * @param unknown
     */
    public Data(Stats stats, LatencyHist latencyHist, List<Template> template, TimerW timerW, Integer unknown) {
        this.stats = stats;
        this.latencyHist = latencyHist;
        this.template = template;
        this.timerW = timerW;
        this.unknown = unknown;
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

    /**
     * @return The latencyHist
     */
    @JsonProperty("latency_hist")
    public LatencyHist getLatencyHist() {
        return latencyHist;
    }

    /**
     * @param latencyHist The latency_hist
     */
    @JsonProperty("latency_hist")
    public void setLatencyHist(LatencyHist latencyHist) {
        this.latencyHist = latencyHist;
    }

    /**
     * @return The template
     */
    @JsonProperty("template")
    public List<Template> getTemplate() {
        return template;
    }

    /**
     * @param template The template
     */
    @JsonProperty("template")
    public void setTemplate(List<Template> template) {
        this.template = template;
    }

    /**
     * @return The timerW
     */
    @JsonProperty("timer_w")
    public TimerW getTimerW() {
        return timerW;
    }

    /**
     * @param timerW The timer_w
     */
    @JsonProperty("timer_w")
    public void setTimerW(TimerW timerW) {
        this.timerW = timerW;
    }

    /**
     * @return The unknown
     */
    @JsonProperty("unknown")
    public Integer getUnknown() {
        return unknown;
    }

    /**
     * @param unknown The unknown
     */
    @JsonProperty("unknown")
    public void setUnknown(Integer unknown) {
        this.unknown = unknown;
    }

}
