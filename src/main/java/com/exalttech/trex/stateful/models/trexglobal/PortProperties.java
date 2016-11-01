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

package com.exalttech.trex.stateful.models.trexglobal;

/**
 * @author BassamJ
 */
public class PortProperties {

    private Long opackets;
    private Long obytes;
    private Long ipackets;
    private Long ibytes;
    private Long ierrors;
    private Long oerrors;
    private Double mTotoalTxBps;
    private Double mTotalTxPps;
    private Double mTotalRxBps;
    private Double mTotalRxPps;

    /**
     * @param opackets
     * @param obytes
     * @param ipackets
     * @param ibytes
     * @param ierrors
     * @param oerrors
     * @param mTotoalTxBps
     * @param mTotalTxPps
     * @param mTotalRxBps
     * @param mTotalRxPps
     */
    public PortProperties(Long opackets, Long obytes, Long ipackets, Long ibytes, Long ierrors, Long oerrors, Double mTotoalTxBps, Double mTotalTxPps, Double mTotalRxBps, Double mTotalRxPps) {

        this.opackets = opackets;
        this.obytes = obytes;
        this.ipackets = ipackets;
        this.ibytes = ibytes;
        this.ierrors = ierrors;
        this.oerrors = oerrors;
        this.mTotoalTxBps = mTotoalTxBps;
        this.mTotalTxPps = mTotalTxPps;
        this.mTotalRxBps = mTotalRxBps;
        this.mTotalRxPps = mTotalRxPps;
    }

    /**
     * @return
     */
    public Long getOpackets() {
        return opackets;
    }

    /**
     * @param opackets
     */
    public void setOpackets(Long opackets) {
        this.opackets = opackets;
    }

    /**
     * @return
     */
    public Long getObytes() {
        return obytes;
    }

    /**
     * @param obytes
     */
    public void setObytes(Long obytes) {
        this.obytes = obytes;
    }

    /**
     * @return
     */
    public Long getIpackets() {
        return ipackets;
    }

    /**
     * @param ipackets
     */
    public void setIpackets(Long ipackets) {
        this.ipackets = ipackets;
    }

    /**
     * @return
     */
    public Long getIbytes() {
        return ibytes;
    }

    /**
     * @param ibytes
     */
    public void setIbytes(Long ibytes) {
        this.ibytes = ibytes;
    }

    /**
     * @return
     */
    public Long getIerrors() {
        return ierrors;
    }

    /**
     * @param ierrors
     */
    public void setIerrors(Long ierrors) {
        this.ierrors = ierrors;
    }

    /**
     * @return
     */
    public Long getOerrors() {
        return oerrors;
    }

    /**
     * @param oerrors
     */
    public void setOerrors(Long oerrors) {
        this.oerrors = oerrors;
    }

    /**
     * @return
     */
    public Double getmTotoalTxBps() {
        return mTotoalTxBps;
    }

    /**
     * @param mTotoalTxBps
     */
    public void setmTotoalTxBps(Double mTotoalTxBps) {
        this.mTotoalTxBps = mTotoalTxBps;
    }

    /**
     * @return
     */
    public Double getmTotalTxPps() {
        return mTotalTxPps;
    }

    /**
     * @param mTotalTxPps
     */
    public void setmTotalTxPps(Double mTotalTxPps) {
        this.mTotalTxPps = mTotalTxPps;
    }

    /**
     * @return
     */
    public Double getmTotalRxBps() {
        return mTotalRxBps;
    }

    /**
     * @param mTotalRxBps
     */
    public void setmTotalRxBps(Double mTotalRxBps) {
        this.mTotalRxBps = mTotalRxBps;
    }

    /**
     * @return
     */
    public Double getmTotalRxPps() {
        return mTotalRxPps;
    }

    /**
     * @param mTotalRxPps
     */
    public void setmTotalRxPps(Double mTotalRxPps) {
        this.mTotalRxPps = mTotalRxPps;
    }

    @Override
    public String toString() {
        return "PortProperty{" +
                "opackets=" + opackets +
                ", obytes=" + obytes +
                ", ipackets=" + ipackets +
                ", ibytes=" + ibytes +
                ", ierrors=" + ierrors +
                ", oerrors=" + oerrors +
                ", mTotoalTxBps=" + mTotoalTxBps +
                ", mTotalTxPps=" + mTotalTxPps +
                ", mTotalRxBps=" + mTotalRxBps +
                ", mTotalRxPps=" + mTotalRxPps +
                '}';
    }
}
