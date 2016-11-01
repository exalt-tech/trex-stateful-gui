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

/**
 * @author BassamJ
 */
public class PortProperty {

    private Double avg;
    private Double max;
    private Double cMax;
    private Double error;
    private Double jitter;

    /**
     * No args constructor for use in serialization
     */
    public PortProperty() {
        //constructor
    }

    /**
     * @param max
     * @param error
     * @param cMax
     * @param jitter
     * @param avg
     */
    public PortProperty(Double avg, Double max, Double cMax, Double error, Double jitter) {
        this.avg = avg;
        this.max = max;
        this.cMax = cMax;
        this.error = error;
        this.jitter = jitter;
    }

    /**
     * @return The avg
     */
    public Double getAvg() {
        return avg;
    }

    /**
     * @param avg The avg
     */
    public void setAvg(Double avg) {
        this.avg = avg;
    }

    /**
     * @return The max
     */
    public Double getMax() {
        return max;
    }

    /**
     * @param max The max
     */
    public void setMax(Double max) {
        this.max = max;
    }

    /**
     * @return The cMax
     */
    public Double getCMax() {
        return cMax;
    }

    /**
     * @param cMax The c-max
     */
    public void setCMax(Double cMax) {
        this.cMax = cMax;
    }

    /**
     * @return The error
     */
    public Double getError() {
        return error;
    }

    /**
     * @param error The error
     */
    public void setError(Double error) {
        this.error = error;
    }

    /**
     * @return The jitter
     */
    public Double getJitter() {
        return jitter;
    }

    /**
     * @param jitter The jitter
     */
    public void setJitter(Double jitter) {
        this.jitter = jitter;
    }

    @Override
    public String toString() {
        return "PortProperty{" +
                "avg=" + avg +
                ", max=" + max +
                ", cMax=" + cMax +
                ", error=" + error +
                ", jitter=" + jitter +
                '}';
    }
}
