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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author BassamJ
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "m_st_alloc",
        "m_st_free",
        "m_st_start",
        "m_st_stop",
        "m_st_handle",
        "m_active"
})
public class TimerW {

    @JsonProperty("m_st_alloc")
    private Long mStAlloc;
    @JsonProperty("m_st_free")
    private Long mStFree;
    @JsonProperty("m_st_start")
    private Long mStStart;
    @JsonProperty("m_st_stop")
    private Long mStStop;
    @JsonProperty("m_st_handle")
    private Long mStHandle;
    @JsonProperty("m_active")
    private Long mActive;

    /**
     * No args constructor for use in serialization
     */
    public TimerW() {
        //constructor
    }

    /**
     * @param mStHandle
     * @param mStStop
     * @param mActive
     * @param mStAlloc
     * @param mStStart
     * @param mStFree
     */
    public TimerW(Long mStAlloc, Long mStFree, Long mStStart, Long mStStop, Long mStHandle, Long mActive) {
        this.mStAlloc = mStAlloc;
        this.mStFree = mStFree;
        this.mStStart = mStStart;
        this.mStStop = mStStop;
        this.mStHandle = mStHandle;
        this.mActive = mActive;
    }

    /**
     * @return The mStAlloc
     */
    @JsonProperty("m_st_alloc")
    public Long getMStAlloc() {
        return mStAlloc;
    }

    /**
     * @param mStAlloc The m_st_alloc
     */
    @JsonProperty("m_st_alloc")
    public void setMStAlloc(Long mStAlloc) {
        this.mStAlloc = mStAlloc;
    }

    /**
     * @return The mStFree
     */
    @JsonProperty("m_st_free")
    public Long getMStFree() {
        return mStFree;
    }

    /**
     * @param mStFree The m_st_free
     */
    @JsonProperty("m_st_free")
    public void setMStFree(Long mStFree) {
        this.mStFree = mStFree;
    }

    /**
     * @return The mStStart
     */
    @JsonProperty("m_st_start")
    public Long getMStStart() {
        return mStStart;
    }

    /**
     * @param mStStart The m_st_start
     */
    @JsonProperty("m_st_start")
    public void setMStStart(Long mStStart) {
        this.mStStart = mStStart;
    }

    /**
     * @return The mStStop
     */
    @JsonProperty("m_st_stop")
    public Long getMStStop() {
        return mStStop;
    }

    /**
     * @param mStStop The m_st_stop
     */
    @JsonProperty("m_st_stop")
    public void setMStStop(Long mStStop) {
        this.mStStop = mStStop;
    }

    /**
     * @return The mStHandle
     */
    @JsonProperty("m_st_handle")
    public Long getMStHandle() {
        return mStHandle;
    }

    /**
     * @param mStHandle The m_st_handle
     */
    @JsonProperty("m_st_handle")
    public void setMStHandle(Long mStHandle) {
        this.mStHandle = mStHandle;
    }

    /**
     * @return The mActive
     */
    @JsonProperty("m_active")
    public Long getMActive() {
        return mActive;
    }

    /**
     * @param mActive The m_active
     */
    @JsonProperty("m_active")
    public void setMActive(Long mActive) {
        this.mActive = mActive;
    }

}
