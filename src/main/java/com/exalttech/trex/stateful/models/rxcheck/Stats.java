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
        "m_total_rx_bytes",
        "m_total_rx",
        "m_lookup",
        "m_found",
        "m_fif",
        "m_add",
        "m_remove",
        "m_active",
        "m_err_no_magic",
        "m_err_drop",
        "m_err_aged",
        "m_err_wrong_pkt_id",
        "m_err_fif_seen_twice",
        "m_err_open_with_no_fif_pkt",
        "m_err_oo_dup",
        "m_err_oo_early",
        "m_err_oo_late",
        "m_err_flow_length_changed"
})
public class Stats {

    @JsonProperty("m_total_rx_bytes")
    private Long mTotalRxBytes;
    @JsonProperty("m_total_rx")
    private Long mTotalRx;
    @JsonProperty("m_lookup")
    private Long mLookup;
    @JsonProperty("m_found")
    private Long mFound;
    @JsonProperty("m_fif")
    private Long mFif;
    @JsonProperty("m_add")
    private Long mAdd;
    @JsonProperty("m_remove")
    private Long mRemove;
    @JsonProperty("m_active")
    private Long mActive;
    @JsonProperty("m_err_no_magic")
    private Long mErrNoMagic;
    @JsonProperty("m_err_drop")
    private Long mErrDrop;
    @JsonProperty("m_err_aged")
    private Long mErrAged;
    @JsonProperty("m_err_wrong_pkt_id")
    private Long mErrWrongPktId;
    @JsonProperty("m_err_fif_seen_twice")
    private Long mErrFifSeenTwice;
    @JsonProperty("m_err_open_with_no_fif_pkt")
    private Long mErrOpenWithNoFifPkt;
    @JsonProperty("m_err_oo_dup")
    private Long mErrOoDup;
    @JsonProperty("m_err_oo_early")
    private Long mErrOoEarly;
    @JsonProperty("m_err_oo_late")
    private Long mErrOoLate;
    @JsonProperty("m_err_flow_length_changed")
    private Long mErrFlowLengthChanged;

    /**
     * No args constructor for use in serialization
     */
    public Stats() {
        //constructor
    }

    /**
     * @param mErrAged
     * @param mErrWrongPktId
     * @param mErrOoDup
     * @param mRemove
     * @param mTotalRx
     * @param mErrOoEarly
     * @param mFound
     * @param mFif
     * @param mErrDrop
     * @param mTotalRxBytes
     * @param mErrFifSeenTwice
     * @param mLookup
     * @param mErrNoMagic
     * @param mErrFlowLengthChanged
     * @param mActive
     * @param mErrOoLate
     * @param mAdd
     * @param mErrOpenWithNoFifPkt
     */
    public Stats(Long mTotalRxBytes, Long mTotalRx, Long mLookup, Long mFound, Long mFif, Long mAdd, Long mRemove, Long mActive, Long mErrNoMagic, Long mErrDrop, Long mErrAged, Long mErrWrongPktId, Long mErrFifSeenTwice, Long mErrOpenWithNoFifPkt, Long mErrOoDup, Long mErrOoEarly, Long mErrOoLate, Long mErrFlowLengthChanged) {
        this.mTotalRxBytes = mTotalRxBytes;
        this.mTotalRx = mTotalRx;
        this.mLookup = mLookup;
        this.mFound = mFound;
        this.mFif = mFif;
        this.mAdd = mAdd;
        this.mRemove = mRemove;
        this.mActive = mActive;
        this.mErrNoMagic = mErrNoMagic;
        this.mErrDrop = mErrDrop;
        this.mErrAged = mErrAged;
        this.mErrWrongPktId = mErrWrongPktId;
        this.mErrFifSeenTwice = mErrFifSeenTwice;
        this.mErrOpenWithNoFifPkt = mErrOpenWithNoFifPkt;
        this.mErrOoDup = mErrOoDup;
        this.mErrOoEarly = mErrOoEarly;
        this.mErrOoLate = mErrOoLate;
        this.mErrFlowLengthChanged = mErrFlowLengthChanged;
    }

    /**
     * @return The mTotalRxBytes
     */
    @JsonProperty("m_total_rx_bytes")
    public Long getMTotalRxBytes() {
        return mTotalRxBytes;
    }

    /**
     * @param mTotalRxBytes The m_total_rx_bytes
     */
    @JsonProperty("m_total_rx_bytes")
    public void setMTotalRxBytes(Long mTotalRxBytes) {
        this.mTotalRxBytes = mTotalRxBytes;
    }

    /**
     * @return The mTotalRx
     */
    @JsonProperty("m_total_rx")
    public Long getMTotalRx() {
        return mTotalRx;
    }

    /**
     * @param mTotalRx The m_total_rx
     */
    @JsonProperty("m_total_rx")
    public void setMTotalRx(Long mTotalRx) {
        this.mTotalRx = mTotalRx;
    }

    /**
     * @return The mLookup
     */
    @JsonProperty("m_lookup")
    public Long getMLookup() {
        return mLookup;
    }

    /**
     * @param mLookup The m_lookup
     */
    @JsonProperty("m_lookup")
    public void setMLookup(Long mLookup) {
        this.mLookup = mLookup;
    }

    /**
     * @return The mFound
     */
    @JsonProperty("m_found")
    public Long getMFound() {
        return mFound;
    }

    /**
     * @param mFound The m_found
     */
    @JsonProperty("m_found")
    public void setMFound(Long mFound) {
        this.mFound = mFound;
    }

    /**
     * @return The mFif
     */
    @JsonProperty("m_fif")
    public Long getMFif() {
        return mFif;
    }

    /**
     * @param mFif The m_fif
     */
    @JsonProperty("m_fif")
    public void setMFif(Long mFif) {
        this.mFif = mFif;
    }

    /**
     * @return The mAdd
     */
    @JsonProperty("m_add")
    public Long getMAdd() {
        return mAdd;
    }

    /**
     * @param mAdd The m_add
     */
    @JsonProperty("m_add")
    public void setMAdd(Long mAdd) {
        this.mAdd = mAdd;
    }

    /**
     * @return The mRemove
     */
    @JsonProperty("m_remove")
    public Long getMRemove() {
        return mRemove;
    }

    /**
     * @param mRemove The m_remove
     */
    @JsonProperty("m_remove")
    public void setMRemove(Long mRemove) {
        this.mRemove = mRemove;
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

    /**
     * @return The mErrNoMagic
     */
    @JsonProperty("m_err_no_magic")
    public Long getMErrNoMagic() {
        return mErrNoMagic;
    }

    /**
     * @param mErrNoMagic The m_err_no_magic
     */
    @JsonProperty("m_err_no_magic")
    public void setMErrNoMagic(Long mErrNoMagic) {
        this.mErrNoMagic = mErrNoMagic;
    }

    /**
     * @return The mErrDrop
     */
    @JsonProperty("m_err_drop")
    public Long getMErrDrop() {
        return mErrDrop;
    }

    /**
     * @param mErrDrop The m_err_drop
     */
    @JsonProperty("m_err_drop")
    public void setMErrDrop(Long mErrDrop) {
        this.mErrDrop = mErrDrop;
    }

    /**
     * @return The mErrAged
     */
    @JsonProperty("m_err_aged")
    public Long getMErrAged() {
        return mErrAged;
    }

    /**
     * @param mErrAged The m_err_aged
     */
    @JsonProperty("m_err_aged")
    public void setMErrAged(Long mErrAged) {
        this.mErrAged = mErrAged;
    }

    /**
     * @return The mErrWrongPktId
     */
    @JsonProperty("m_err_wrong_pkt_id")
    public Long getMErrWrongPktId() {
        return mErrWrongPktId;
    }

    /**
     * @param mErrWrongPktId The m_err_wrong_pkt_id
     */
    @JsonProperty("m_err_wrong_pkt_id")
    public void setMErrWrongPktId(Long mErrWrongPktId) {
        this.mErrWrongPktId = mErrWrongPktId;
    }

    /**
     * @return The mErrFifSeenTwice
     */
    @JsonProperty("m_err_fif_seen_twice")
    public Long getMErrFifSeenTwice() {
        return mErrFifSeenTwice;
    }

    /**
     * @param mErrFifSeenTwice The m_err_fif_seen_twice
     */
    @JsonProperty("m_err_fif_seen_twice")
    public void setMErrFifSeenTwice(Long mErrFifSeenTwice) {
        this.mErrFifSeenTwice = mErrFifSeenTwice;
    }

    /**
     * @return The mErrOpenWithNoFifPkt
     */
    @JsonProperty("m_err_open_with_no_fif_pkt")
    public Long getMErrOpenWithNoFifPkt() {
        return mErrOpenWithNoFifPkt;
    }

    /**
     * @param mErrOpenWithNoFifPkt The m_err_open_with_no_fif_pkt
     */
    @JsonProperty("m_err_open_with_no_fif_pkt")
    public void setMErrOpenWithNoFifPkt(Long mErrOpenWithNoFifPkt) {
        this.mErrOpenWithNoFifPkt = mErrOpenWithNoFifPkt;
    }

    /**
     * @return The mErrOoDup
     */
    @JsonProperty("m_err_oo_dup")
    public Long getMErrOoDup() {
        return mErrOoDup;
    }

    /**
     * @param mErrOoDup The m_err_oo_dup
     */
    @JsonProperty("m_err_oo_dup")
    public void setMErrOoDup(Long mErrOoDup) {
        this.mErrOoDup = mErrOoDup;
    }

    /**
     * @return The mErrOoEarly
     */
    @JsonProperty("m_err_oo_early")
    public Long getMErrOoEarly() {
        return mErrOoEarly;
    }

    /**
     * @param mErrOoEarly The m_err_oo_early
     */
    @JsonProperty("m_err_oo_early")
    public void setMErrOoEarly(Long mErrOoEarly) {
        this.mErrOoEarly = mErrOoEarly;
    }

    /**
     * @return The mErrOoLate
     */
    @JsonProperty("m_err_oo_late")
    public Long getMErrOoLate() {
        return mErrOoLate;
    }

    /**
     * @param mErrOoLate The m_err_oo_late
     */
    @JsonProperty("m_err_oo_late")
    public void setMErrOoLate(Long mErrOoLate) {
        this.mErrOoLate = mErrOoLate;
    }

    /**
     * @return The mErrFlowLengthChanged
     */
    @JsonProperty("m_err_flow_length_changed")
    public Long getMErrFlowLengthChanged() {
        return mErrFlowLengthChanged;
    }

    /**
     * @param mErrFlowLengthChanged The m_err_flow_length_changed
     */
    @JsonProperty("m_err_flow_length_changed")
    public void setMErrFlowLengthChanged(Long mErrFlowLengthChanged) {
        this.mErrFlowLengthChanged = mErrFlowLengthChanged;
    }

}
