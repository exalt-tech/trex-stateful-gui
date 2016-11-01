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
        "m_tx_pkt_ok",
        "m_tx_pkt_err",
        "m_pkt_ok",
        "m_unsup_prot",
        "m_no_magic",
        "m_no_id",
        "m_seq_error",
        "m_length_error",
        "m_no_ipv4_option",
        "m_jitter",
        "m_rx_check"
})
public class Stats {

    @JsonProperty("m_tx_pkt_ok")
    private Long mTxPktOk;
    @JsonProperty("m_tx_pkt_err")
    private Long mTxPktErr;
    @JsonProperty("m_pkt_ok")
    private Long mPktOk;
    @JsonProperty("m_unsup_prot")
    private Long mUnsupProt;
    @JsonProperty("m_no_magic")
    private Long mNoMagic;
    @JsonProperty("m_no_id")
    private Long mNoId;
    @JsonProperty("m_seq_error")
    private Long mSeqError;
    @JsonProperty("m_length_error")
    private Long mLengthError;
    @JsonProperty("m_no_ipv4_option")
    private Long mNoIpv4Option;
    @JsonProperty("m_jitter")
    private Long mJitter;
    @JsonProperty("m_rx_check")
    private Long mRxCheck;

    /**
     * No args constructor for use in serialization
     */
    public Stats() {
        //constructor
    }

    /**
     * @param mTxPktErr
     * @param mNoId
     * @param mLengthError
     * @param mPktOk
     * @param mNoIpv4Option
     * @param mUnsupProt
     * @param mTxPktOk
     * @param mNoMagic
     * @param mSeqError
     * @param mRxCheck
     * @param mJitter
     */
    public Stats(Long mTxPktOk, Long mTxPktErr, Long mPktOk, Long mUnsupProt, Long mNoMagic, Long mNoId, Long mSeqError, Long mLengthError, Long mNoIpv4Option, Long mJitter, Long mRxCheck) {
        this.mTxPktOk = mTxPktOk;
        this.mTxPktErr = mTxPktErr;
        this.mPktOk = mPktOk;
        this.mUnsupProt = mUnsupProt;
        this.mNoMagic = mNoMagic;
        this.mNoId = mNoId;
        this.mSeqError = mSeqError;
        this.mLengthError = mLengthError;
        this.mNoIpv4Option = mNoIpv4Option;
        this.mJitter = mJitter;
        this.mRxCheck = mRxCheck;
    }

    /**
     * @return The mTxPktOk
     */
    @JsonProperty("m_tx_pkt_ok")
    public Long getMTxPktOk() {
        return mTxPktOk;
    }

    /**
     * @param mTxPktOk The m_tx_pkt_ok
     */
    @JsonProperty("m_tx_pkt_ok")
    public void setMTxPktOk(Long mTxPktOk) {
        this.mTxPktOk = mTxPktOk;
    }

    /**
     * @return The mTxPktErr
     */
    @JsonProperty("m_tx_pkt_err")
    public Long getMTxPktErr() {
        return mTxPktErr;
    }

    /**
     * @param mTxPktErr The m_tx_pkt_err
     */
    @JsonProperty("m_tx_pkt_err")
    public void setMTxPktErr(Long mTxPktErr) {
        this.mTxPktErr = mTxPktErr;
    }

    /**
     * @return The mPktOk
     */
    @JsonProperty("m_pkt_ok")
    public Long getMPktOk() {
        return mPktOk;
    }

    /**
     * @param mPktOk The m_pkt_ok
     */
    @JsonProperty("m_pkt_ok")
    public void setMPktOk(Long mPktOk) {
        this.mPktOk = mPktOk;
    }

    /**
     * @return The mUnsupProt
     */
    @JsonProperty("m_unsup_prot")
    public Long getMUnsupProt() {
        return mUnsupProt;
    }

    /**
     * @param mUnsupProt The m_unsup_prot
     */
    @JsonProperty("m_unsup_prot")
    public void setMUnsupProt(Long mUnsupProt) {
        this.mUnsupProt = mUnsupProt;
    }

    /**
     * @return The mNoMagic
     */
    @JsonProperty("m_no_magic")
    public Long getMNoMagic() {
        return mNoMagic;
    }

    /**
     * @param mNoMagic The m_no_magic
     */
    @JsonProperty("m_no_magic")
    public void setMNoMagic(Long mNoMagic) {
        this.mNoMagic = mNoMagic;
    }

    /**
     * @return The mNoId
     */
    @JsonProperty("m_no_id")
    public Long getMNoId() {
        return mNoId;
    }

    /**
     * @param mNoId The m_no_id
     */
    @JsonProperty("m_no_id")
    public void setMNoId(Long mNoId) {
        this.mNoId = mNoId;
    }

    /**
     * @return The mSeqError
     */
    @JsonProperty("m_seq_error")
    public Long getMSeqError() {
        return mSeqError;
    }

    /**
     * @param mSeqError The m_seq_error
     */
    @JsonProperty("m_seq_error")
    public void setMSeqError(Long mSeqError) {
        this.mSeqError = mSeqError;
    }

    /**
     * @return The mLengthError
     */
    @JsonProperty("m_length_error")
    public Long getMLengthError() {
        return mLengthError;
    }

    /**
     * @param mLengthError The m_length_error
     */
    @JsonProperty("m_length_error")
    public void setMLengthError(Long mLengthError) {
        this.mLengthError = mLengthError;
    }

    /**
     * @return The mNoIpv4Option
     */
    @JsonProperty("m_no_ipv4_option")
    public Long getMNoIpv4Option() {
        return mNoIpv4Option;
    }

    /**
     * @param mNoIpv4Option The m_no_ipv4_option
     */
    @JsonProperty("m_no_ipv4_option")
    public void setMNoIpv4Option(Long mNoIpv4Option) {
        this.mNoIpv4Option = mNoIpv4Option;
    }

    /**
     * @return The mJitter
     */
    @JsonProperty("m_jitter")
    public Long getMJitter() {
        return mJitter;
    }

    /**
     * @param mJitter The m_jitter
     */
    @JsonProperty("m_jitter")
    public void setMJitter(Long mJitter) {
        this.mJitter = mJitter;
    }

    /**
     * @return The mRxCheck
     */
    @JsonProperty("m_rx_check")
    public Long getMRxCheck() {
        return mRxCheck;
    }

    /**
     * @param mRxCheck The m_rx_check
     */
    @JsonProperty("m_rx_check")
    public void setMRxCheck(Long mRxCheck) {
        this.mRxCheck = mRxCheck;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "mTxPktOk=" + mTxPktOk +
                ", mTxPktErr=" + mTxPktErr +
                ", mPktOk=" + mPktOk +
                ", mUnsupProt=" + mUnsupProt +
                ", mNoMagic=" + mNoMagic +
                ", mNoId=" + mNoId +
                ", mSeqError=" + mSeqError +
                ", mLengthError=" + mLengthError +
                ", mNoIpv4Option=" + mNoIpv4Option +
                ", mJitter=" + mJitter +
                ", mRxCheck=" + mRxCheck +
                '}';
    }
}
