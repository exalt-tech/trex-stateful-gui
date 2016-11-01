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

package com.exalttech.trex.stateful.utilities;

/**
 * @author BassamJ
 */
public final class Constants {

    /**
     *
     */
    public static final String ASYNC_PORT_NUMBER = "4500";

    /**
     *
     */
    public static final String TREX_GLOBAL_TAG = "\"trex-global\"";

    /**
     *
     */
    public static final String TX_GEN_TAG = "\"tx-gen\"";

    /**
     *
     */
    public static final String TEMPLATE_INFO_TAG = "\"template_info\"";

    /**
     *
     */
    public static final String RX_CHECK_TAG = "\"rx-check\"";

    /**
     *
     */
    public static final String TREX_LATENCY_TAG = "\"trex-latecny\"";

    /**
     *
     */
    public static final String TREX_LATENCY_V2_TAG = "\"trex-latecny-v2\"";

    /**
     *
     */
    public static final String PORT_OPACKETS_KEY = "opackets";

    /**
     *
     */
    public static final String PORT_OBYTES_KEY = "obytes";

    /**
     *
     */
    public static final String PORT_IPACKETS_KEY = "ipackets";

    /**
     *
     */
    public static final String PORT_IBYTES_KEY = "ibytes";

    /**
     *
     */
    public static final String PORT_IERROR_KEY = "ierrors";

    /**
     *
     */
    public static final String PORT_OERROR_KEY = "oerrors";

    /**
     *
     */
    public static final String PORT_M_TOTAL_TX_BPS_KEY = "m_total_tx_bps";

    /**
     *
     */
    public static final String PORT_M_TOTAL_TX_PPS_KEY = "m_total_tx_pps";

    /**
     *
     */
    public static final String PORT_M_TOTAL_RX_BPS_KEY = "m_total_rx_bps";

    /**
     *
     */
    public static final String PORT_M_TOTAL_RX_PPS_KEY = "m_total_rx_pps";

    /**
     *
     */
    public static final String TREX_LATENCY_AVG_KEY = "avg";

    /**
     *
     */
    public static final String TREX_LATENCY_MAX_KEY = "max";

    /**
     *
     */
    public static final String TREX_LATENCY_C_MAX_KEY = "c-max";

    /**
     *
     */
    public static final String TREX_LATENCY_ERROR_KEY = "error";

    /**
     *
     */
    public static final String TREX_LATENCY_JITTER_KEY = "jitter";

    /**
     *
     */
    public static final String APPLICATION_TITLE = "TRex stateful GUI";

    /**
     *
     */
    public static final long REFRESH_ONE_INTERVAL_SECONDS = 1;

    /**
     *
     */
    public static final long REFRESH_TWO_INTERVAL_SECONDS = 2;

    /**
     *
     */
    public static final Double REFRESH_HALF_INTERVAL_SECONDS = 0.5;

    /**
     *
     */
    public static final String MASTER_DAEMON_PORT = "8091";

    /**
     *
     */
    public static final String TREX_DAEMON_SERVER_PORT = "8090";

    /**
     *
     */
    public static final int HOST_TIMEOUT = 50000;

    /**
     *
     */
    public static final int RPC_TIMEOUT = 40;

    /**
     *
     */
    public static final String MAGIC_STRING = "ABE85CEA";

    private Constants() {
        //constructor
    }


}
