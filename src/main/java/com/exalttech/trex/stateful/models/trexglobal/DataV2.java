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

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.ToStringBuilder;

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
        "ts",
        "m_cpu_util",
        "m_bw_per_core",
        "m_rx_cpu_util",
        "m_platform_factor",
        "m_tx_bps",
        "m_rx_bps",
        "m_tx_pps",
        "m_rx_pps",
        "m_tx_cps",
        "m_tx_expected_cps",
        "m_tx_expected_pps",
        "m_tx_expected_bps",
        "m_total_alloc_error",
        "m_total_queue_full",
        "m_total_queue_drop",
        "m_rx_drop_bps",
        "m_active_flows",
        "m_open_flows",
        "m_total_tx_pkts",
        "m_total_rx_pkts",
        "m_total_tx_bytes",
        "m_total_rx_bytes",
        "m_total_clients",
        "m_total_servers",
        "m_active_sockets",
        "m_socket_util",
        "m_total_nat_time_out",
        "m_total_nat_no_fid",
        "m_total_nat_active",
        "m_total_nat_open",
        "m_total_nat_learn_error",
        "template",
        "unknown"
})
public class DataV2 {

    @JsonProperty("ts")
    private Ts ts;
    @JsonProperty("m_cpu_util")
    private Double mCpuUtil;
    @JsonProperty("m_bw_per_core")
    private Double mBwPerCore;
    @JsonProperty("m_rx_cpu_util")
    private Double mRxCpuUtil;
    @JsonProperty("m_platform_factor")
    private Double mPlatformFactor;
    @JsonProperty("m_tx_bps")
    private Double mTxBps;
    @JsonProperty("m_rx_bps")
    private Double mRxBps;
    @JsonProperty("m_tx_pps")
    private Double mTxPps;
    @JsonProperty("m_rx_pps")
    private Double mRxPps;
    @JsonProperty("m_tx_cps")
    private Double mTxCps;
    @JsonProperty("m_tx_expected_cps")
    private Double mTxExpectedCps;
    @JsonProperty("m_tx_expected_pps")
    private Double mTxExpectedPps;
    @JsonProperty("m_tx_expected_bps")
    private Double mTxExpectedBps;
    @JsonProperty("m_total_alloc_error")
    private Long mTotalAllocError;
    @JsonProperty("m_total_queue_full")
    private Long mTotalQueueFull;
    @JsonProperty("m_total_queue_drop")
    private Long mTotalQueueDrop;
    @JsonProperty("m_rx_drop_bps")
    private Double mRxDropBps;
    @JsonProperty("m_active_flows")
    private Double mActiveFlows;
    @JsonProperty("m_open_flows")
    private Double mOpenFlows;
    @JsonProperty("m_total_tx_pkts")
    private Long mTotalTxPkts;
    @JsonProperty("m_total_rx_pkts")
    private Long mTotalRxPkts;
    @JsonProperty("m_total_tx_bytes")
    private Long mTotalTxBytes;
    @JsonProperty("m_total_rx_bytes")
    private Long mTotalRxBytes;
    @JsonProperty("m_total_clients")
    private Long mTotalClients;
    @JsonProperty("m_total_servers")
    private Long mTotalServers;
    @JsonProperty("m_active_sockets")
    private Long mActiveSockets;
    @JsonProperty("m_socket_util")
    private Double mSocketUtil;
    @JsonProperty("m_total_nat_time_out")
    private Long mTotalNatTimeOut;
    @JsonProperty("m_total_nat_no_fid")
    private Long mTotalNatNoFid;
    @JsonProperty("m_total_nat_active")
    private Long mTotalNatActive;
    @JsonProperty("m_total_nat_open")
    private Long mTotalNatOpen;
    @JsonProperty("m_total_nat_learn_error")
    private Long mTotalNatLearnError;
    @JsonProperty("template")
    private List<Long> template = new ArrayList<Long>();
    @JsonProperty("unknown")
    private Long unknown;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    private List<PortProperties> portProperties = new ArrayList<PortProperties>();

    /**
     * No args constructor for use in serialization
     */
    public DataV2() {
        //constructor
    }

    /**
     * @param mTotalQueueDrop
     * @param mSocketUtil
     * @param ts
     * @param mCpuUtil
     * @param mTotalNatTimeOut
     * @param mOpenFlows
     * @param mTotalClients
     * @param mTotalNatNoFid
     * @param mTotalAllocError
     * @param mTxExpectedPps
     * @param mRxDropBps
     * @param mPlatformFactor
     * @param mRxCpuUtil
     * @param template
     * @param mActiveFlows
     * @param mTotalNatLearnError
     * @param mActiveSockets
     * @param mBwPerCore
     * @param mTxBps
     * @param unknown
     * @param mTotalTxPkts
     * @param mTotalQueueFull
     * @param mTxPps
     * @param mTotalRxBytes
     * @param mTotalServers
     * @param mTxExpectedCps
     * @param mTotalTxBytes
     * @param mTxCps
     * @param mRxBps
     * @param mTotalNatOpen
     * @param mTotalNatActive
     * @param mRxPps
     * @param mTotalRxPkts
     * @param mTxExpectedBps
     */
    public DataV2(Ts ts, Double mCpuUtil, Double mBwPerCore, Double mRxCpuUtil, Double mPlatformFactor, Double mTxBps, Double mRxBps, Double mTxPps, Double mRxPps, Double mTxCps, Double mTxExpectedCps, Double mTxExpectedPps, Double mTxExpectedBps, Long mTotalAllocError, Long mTotalQueueFull, Long mTotalQueueDrop, Double mRxDropBps, Double mActiveFlows, Double mOpenFlows, Long mTotalTxPkts, Long mTotalRxPkts, Long mTotalTxBytes, Long mTotalRxBytes, Long mTotalClients, Long mTotalServers, Long mActiveSockets, Double mSocketUtil, Long mTotalNatTimeOut, Long mTotalNatNoFid, Long mTotalNatActive, Long mTotalNatOpen, Long mTotalNatLearnError, List<Long> template, Long unknown) {
        this.ts = ts;
        this.mCpuUtil = mCpuUtil;
        this.mBwPerCore = mBwPerCore;
        this.mRxCpuUtil = mRxCpuUtil;
        this.mPlatformFactor = mPlatformFactor;
        this.mTxBps = mTxBps;
        this.mRxBps = mRxBps;
        this.mTxPps = mTxPps;
        this.mRxPps = mRxPps;
        this.mTxCps = mTxCps;
        this.mTxExpectedCps = mTxExpectedCps;
        this.mTxExpectedPps = mTxExpectedPps;
        this.mTxExpectedBps = mTxExpectedBps;
        this.mTotalAllocError = mTotalAllocError;
        this.mTotalQueueFull = mTotalQueueFull;
        this.mTotalQueueDrop = mTotalQueueDrop;
        this.mRxDropBps = mRxDropBps;
        this.mActiveFlows = mActiveFlows;
        this.mOpenFlows = mOpenFlows;
        this.mTotalTxPkts = mTotalTxPkts;
        this.mTotalRxPkts = mTotalRxPkts;
        this.mTotalTxBytes = mTotalTxBytes;
        this.mTotalRxBytes = mTotalRxBytes;
        this.mTotalClients = mTotalClients;
        this.mTotalServers = mTotalServers;
        this.mActiveSockets = mActiveSockets;
        this.mSocketUtil = mSocketUtil;
        this.mTotalNatTimeOut = mTotalNatTimeOut;
        this.mTotalNatNoFid = mTotalNatNoFid;
        this.mTotalNatActive = mTotalNatActive;
        this.mTotalNatOpen = mTotalNatOpen;
        this.mTotalNatLearnError = mTotalNatLearnError;
        this.template = template;
        this.unknown = unknown;
    }

    /**
     * @return The ts
     */
    @JsonProperty("ts")
    public Ts getTs() {
        return ts;
    }

    /**
     * @param ts The ts
     */
    @JsonProperty("ts")
    public void setTs(Ts ts) {
        this.ts = ts;
    }

    /**
     * @return The mCpuUtil
     */
    @JsonProperty("m_cpu_util")
    public Double getMCpuUtil() {
        return mCpuUtil;
    }

    /**
     * @param mCpuUtil The m_cpu_util
     */
    @JsonProperty("m_cpu_util")
    public void setMCpuUtil(Double mCpuUtil) {
        this.mCpuUtil = mCpuUtil;
    }

    /**
     * @return The mBwPerCore
     */
    @JsonProperty("m_bw_per_core")
    public Double getMBwPerCore() {
        return mBwPerCore;
    }

    /**
     * @param mBwPerCore The m_bw_per_core
     */
    @JsonProperty("m_bw_per_core")
    public void setMBwPerCore(Double mBwPerCore) {
        this.mBwPerCore = mBwPerCore;
    }

    /**
     * @return The mRxCpuUtil
     */
    @JsonProperty("m_rx_cpu_util")
    public Double getMRxCpuUtil() {
        return mRxCpuUtil;
    }

    /**
     * @param mRxCpuUtil The m_rx_cpu_util
     */
    @JsonProperty("m_rx_cpu_util")
    public void setMRxCpuUtil(Double mRxCpuUtil) {
        this.mRxCpuUtil = mRxCpuUtil;
    }

    /**
     * @return The mPlatformFactor
     */
    @JsonProperty("m_platform_factor")
    public Double getMPlatformFactor() {
        return mPlatformFactor;
    }

    /**
     * @param mPlatformFactor The m_platform_factor
     */
    @JsonProperty("m_platform_factor")
    public void setMPlatformFactor(Double mPlatformFactor) {
        this.mPlatformFactor = mPlatformFactor;
    }

    /**
     * @return The mTxBps
     */
    @JsonProperty("m_tx_bps")
    public Double getMTxBps() {
        return mTxBps;
    }

    /**
     * @param mTxBps The m_tx_bps
     */
    @JsonProperty("m_tx_bps")
    public void setMTxBps(Double mTxBps) {
        this.mTxBps = mTxBps;
    }

    /**
     * @return The mRxBps
     */
    @JsonProperty("m_rx_bps")
    public Double getMRxBps() {
        return mRxBps;
    }

    /**
     * @param mRxBps The m_rx_bps
     */
    @JsonProperty("m_rx_bps")
    public void setMRxBps(Double mRxBps) {
        this.mRxBps = mRxBps;
    }

    /**
     * @return The mTxPps
     */
    @JsonProperty("m_tx_pps")
    public Double getMTxPps() {
        return mTxPps;
    }

    /**
     * @param mTxPps The m_tx_pps
     */
    @JsonProperty("m_tx_pps")
    public void setMTxPps(Double mTxPps) {
        this.mTxPps = mTxPps;
    }

    /**
     * @return The mRxPps
     */
    @JsonProperty("m_rx_pps")
    public Double getMRxPps() {
        return mRxPps;
    }

    /**
     * @param mRxPps The m_rx_pps
     */
    @JsonProperty("m_rx_pps")
    public void setMRxPps(Double mRxPps) {
        this.mRxPps = mRxPps;
    }

    /**
     * @return The mTxCps
     */
    @JsonProperty("m_tx_cps")
    public Double getMTxCps() {
        return mTxCps;
    }

    /**
     * @param mTxCps The m_tx_cps
     */
    @JsonProperty("m_tx_cps")
    public void setMTxCps(Double mTxCps) {
        this.mTxCps = mTxCps;
    }

    /**
     * @return The mTxExpectedCps
     */
    @JsonProperty("m_tx_expected_cps")
    public Double getMTxExpectedCps() {
        return mTxExpectedCps;
    }

    /**
     * @param mTxExpectedCps The m_tx_expected_cps
     */
    @JsonProperty("m_tx_expected_cps")
    public void setMTxExpectedCps(Double mTxExpectedCps) {
        this.mTxExpectedCps = mTxExpectedCps;
    }

    /**
     * @return The mTxExpectedPps
     */
    @JsonProperty("m_tx_expected_pps")
    public Double getMTxExpectedPps() {
        return mTxExpectedPps;
    }

    /**
     * @param mTxExpectedPps The m_tx_expected_pps
     */
    @JsonProperty("m_tx_expected_pps")
    public void setMTxExpectedPps(Double mTxExpectedPps) {
        this.mTxExpectedPps = mTxExpectedPps;
    }

    /**
     * @return The mTxExpectedBps
     */
    @JsonProperty("m_tx_expected_bps")
    public Double getMTxExpectedBps() {
        return mTxExpectedBps;
    }

    /**
     * @param mTxExpectedBps The m_tx_expected_bps
     */
    @JsonProperty("m_tx_expected_bps")
    public void setMTxExpectedBps(Double mTxExpectedBps) {
        this.mTxExpectedBps = mTxExpectedBps;
    }

    /**
     * @return The mTotalAllocError
     */
    @JsonProperty("m_total_alloc_error")
    public Long getMTotalAllocError() {
        return mTotalAllocError;
    }

    /**
     * @param mTotalAllocError The m_total_alloc_error
     */
    @JsonProperty("m_total_alloc_error")
    public void setMTotalAllocError(Long mTotalAllocError) {
        this.mTotalAllocError = mTotalAllocError;
    }

    /**
     * @return The mTotalQueueFull
     */
    @JsonProperty("m_total_queue_full")
    public Long getMTotalQueueFull() {
        return mTotalQueueFull;
    }

    /**
     * @param mTotalQueueFull The m_total_queue_full
     */
    @JsonProperty("m_total_queue_full")
    public void setMTotalQueueFull(Long mTotalQueueFull) {
        this.mTotalQueueFull = mTotalQueueFull;
    }

    /**
     * @return The mTotalQueueDrop
     */
    @JsonProperty("m_total_queue_drop")
    public Long getMTotalQueueDrop() {
        return mTotalQueueDrop;
    }

    /**
     * @param mTotalQueueDrop The m_total_queue_drop
     */
    @JsonProperty("m_total_queue_drop")
    public void setMTotalQueueDrop(Long mTotalQueueDrop) {
        this.mTotalQueueDrop = mTotalQueueDrop;
    }

    /**
     * @return The mRxDropBps
     */
    @JsonProperty("m_rx_drop_bps")
    public Double getMRxDropBps() {
        return mRxDropBps;
    }

    /**
     * @param mRxDropBps The m_rx_drop_bps
     */
    @JsonProperty("m_rx_drop_bps")
    public void setMRxDropBps(Double mRxDropBps) {
        this.mRxDropBps = mRxDropBps;
    }

    /**
     * @return The mActiveFlows
     */
    @JsonProperty("m_active_flows")
    public Double getMActiveFlows() {
        return mActiveFlows;
    }

    /**
     * @param mActiveFlows The m_active_flows
     */
    @JsonProperty("m_active_flows")
    public void setMActiveFlows(Double mActiveFlows) {
        this.mActiveFlows = mActiveFlows;
    }

    /**
     * @return The mOpenFlows
     */
    @JsonProperty("m_open_flows")
    public Double getMOpenFlows() {
        return mOpenFlows;
    }

    /**
     * @param mOpenFlows The m_open_flows
     */
    @JsonProperty("m_open_flows")
    public void setMOpenFlows(Double mOpenFlows) {
        this.mOpenFlows = mOpenFlows;
    }

    /**
     * @return The mTotalTxPkts
     */
    @JsonProperty("m_total_tx_pkts")
    public Long getMTotalTxPkts() {
        return mTotalTxPkts;
    }

    /**
     * @param mTotalTxPkts The m_total_tx_pkts
     */
    @JsonProperty("m_total_tx_pkts")
    public void setMTotalTxPkts(Long mTotalTxPkts) {
        this.mTotalTxPkts = mTotalTxPkts;
    }

    /**
     * @return The mTotalRxPkts
     */
    @JsonProperty("m_total_rx_pkts")
    public Long getMTotalRxPkts() {
        return mTotalRxPkts;
    }

    /**
     * @param mTotalRxPkts The m_total_rx_pkts
     */
    @JsonProperty("m_total_rx_pkts")
    public void setMTotalRxPkts(Long mTotalRxPkts) {
        this.mTotalRxPkts = mTotalRxPkts;
    }

    /**
     * @return The mTotalTxBytes
     */
    @JsonProperty("m_total_tx_bytes")
    public Long getMTotalTxBytes() {
        return mTotalTxBytes;
    }

    /**
     * @param mTotalTxBytes The m_total_tx_bytes
     */
    @JsonProperty("m_total_tx_bytes")
    public void setMTotalTxBytes(Long mTotalTxBytes) {
        this.mTotalTxBytes = mTotalTxBytes;
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
     * @return The mTotalClients
     */
    @JsonProperty("m_total_clients")
    public Long getMTotalClients() {
        return mTotalClients;
    }

    /**
     * @param mTotalClients The m_total_clients
     */
    @JsonProperty("m_total_clients")
    public void setMTotalClients(Long mTotalClients) {
        this.mTotalClients = mTotalClients;
    }

    /**
     * @return The mTotalServers
     */
    @JsonProperty("m_total_servers")
    public Long getMTotalServers() {
        return mTotalServers;
    }

    /**
     * @param mTotalServers The m_total_servers
     */
    @JsonProperty("m_total_servers")
    public void setMTotalServers(Long mTotalServers) {
        this.mTotalServers = mTotalServers;
    }

    /**
     * @return The mActiveSockets
     */
    @JsonProperty("m_active_sockets")
    public Long getMActiveSockets() {
        return mActiveSockets;
    }

    /**
     * @param mActiveSockets The m_active_sockets
     */
    @JsonProperty("m_active_sockets")
    public void setMActiveSockets(Long mActiveSockets) {
        this.mActiveSockets = mActiveSockets;
    }

    /**
     * @return The mSocketUtil
     */
    @JsonProperty("m_socket_util")
    public Double getMSocketUtil() {
        return mSocketUtil;
    }

    /**
     * @param mSocketUtil The m_socket_util
     */
    @JsonProperty("m_socket_util")
    public void setMSocketUtil(Double mSocketUtil) {
        this.mSocketUtil = mSocketUtil;
    }

    /**
     * @return The mTotalNatTimeOut
     */
    @JsonProperty("m_total_nat_time_out")
    public Long getMTotalNatTimeOut() {
        return mTotalNatTimeOut;
    }

    /**
     * @param mTotalNatTimeOut The m_total_nat_time_out
     */
    @JsonProperty("m_total_nat_time_out")
    public void setMTotalNatTimeOut(Long mTotalNatTimeOut) {
        this.mTotalNatTimeOut = mTotalNatTimeOut;
    }

    /**
     * @return The mTotalNatNoFid
     */
    @JsonProperty("m_total_nat_no_fid")
    public Long getMTotalNatNoFid() {
        return mTotalNatNoFid;
    }

    /**
     * @param mTotalNatNoFid The m_total_nat_no_fid
     */
    @JsonProperty("m_total_nat_no_fid")
    public void setMTotalNatNoFid(Long mTotalNatNoFid) {
        this.mTotalNatNoFid = mTotalNatNoFid;
    }

    /**
     * @return The mTotalNatActive
     */
    @JsonProperty("m_total_nat_active")
    public Long getMTotalNatActive() {
        return mTotalNatActive;
    }

    /**
     * @param mTotalNatActive The m_total_nat_active
     */
    @JsonProperty("m_total_nat_active")
    public void setMTotalNatActive(Long mTotalNatActive) {
        this.mTotalNatActive = mTotalNatActive;
    }

    /**
     * @return The mTotalNatOpen
     */
    @JsonProperty("m_total_nat_open")
    public Long getMTotalNatOpen() {
        return mTotalNatOpen;
    }

    /**
     * @param mTotalNatOpen The m_total_nat_open
     */
    @JsonProperty("m_total_nat_open")
    public void setMTotalNatOpen(Long mTotalNatOpen) {
        this.mTotalNatOpen = mTotalNatOpen;
    }

    /**
     * @return The mTotalNatLearnError
     */
    @JsonProperty("m_total_nat_learn_error")
    public Long getMTotalNatLearnError() {
        return mTotalNatLearnError;
    }

    /**
     * @param mTotalNatLearnError The m_total_nat_learn_error
     */
    @JsonProperty("m_total_nat_learn_error")
    public void setMTotalNatLearnError(Long mTotalNatLearnError) {
        this.mTotalNatLearnError = mTotalNatLearnError;
    }

    /**
     * @return The template
     */
    @JsonProperty("template")
    public List<Long> getTemplate() {
        return template;
    }

    /**
     * @param template The template
     */
    @JsonProperty("template")
    public void setTemplate(List<Long> template) {
        this.template = template;
    }

    /**
     * @return The unknown
     */
    @JsonProperty("unknown")
    public Long getUnknown() {
        return unknown;
    }

    /**
     * @param unknown The unknown
     */
    @JsonProperty("unknown")
    public void setUnknown(Long unknown) {
        this.unknown = unknown;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
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

    /**
     * @return The portProperties
     */
    public List<PortProperties> getPortProperties() {
        return portProperties;
    }

    /**
     * @param portProperties The portProperties
     */
    public void setPortProperties(List<PortProperties> portProperties) {
        this.portProperties = portProperties;
    }

}
