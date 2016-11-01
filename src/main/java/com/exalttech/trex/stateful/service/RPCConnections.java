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

package com.exalttech.trex.stateful.service;

import com.exalttech.trex.stateful.log.LogsController;
import com.exalttech.trex.stateful.utilities.Constants;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author BassamJ
 */
public class RPCConnections {

    private static final Logger LOG = Logger.getLogger(RPCConnections.class.getName());
    private static RPCConnections instance = null;
    private JsonRpcHttpClient masterRpcConnection;
    private JsonRpcHttpClient trexRpcConnection;
    private Integer sessionId;
    private Map yamlFileMap = new HashMap<String, String>();
    private ObservableList<String> yamlFileList;
    private String ipAddress;
    private BooleanProperty startZMQStreaming = new SimpleBooleanProperty();
    private BooleanProperty trexProcessRunning = new SimpleBooleanProperty();
    private BooleanProperty executingCommand = new SimpleBooleanProperty();
    private String errorMessage = "Error: ";

    /**
     *
     */
    protected RPCConnections() {

    }

    /**
     * @return
     */
    public static RPCConnections getInstance() {
        if (instance == null) {
            instance = new RPCConnections();
        }
        return instance;
    }

    /**
     * @return
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * connect to trex daemon server
     *
     * @param host
     * @return
     */
    public Boolean connectToMasterDaemon(String host) {
        try {
            masterRpcConnection = new JsonRpcHttpClient(new URL("http://" + host + ":" + Constants.MASTER_DAEMON_PORT + "/"));
            setIpAddress(host);
            return true;
        } catch (Exception ex) {
            LOG.error(errorMessage, ex);
            return false;
        }
    }


    /**
     * Check Master daemon for connectivity. Return True upon success
     *
     * @return
     */
    public Boolean checkConnectivityCheck() {
        try {
            return masterRpcConnection.invoke("connectivity_check", new Object[]{}, Boolean.class);
        } catch (Throwable ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            return false;
        }
    }


    /**
     * Start TRex server daemon.
     *
     * @return True if success.
     * False if TRex server daemon already running.
     */

    public Boolean startTrexDaemon() {
        try {
            return masterRpcConnection.invoke("start_trex_daemon", new Object[]{}, Boolean.class);
        } catch (Throwable ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            return false;
        }
    }

    /**
     * Restart TRex server daemon. Useful after update. Will not fail if daemon is initially stopped.
     *
     * @return
     */
    public Boolean restartTrexDaemon() {
        try {
            return masterRpcConnection.invoke("restart_trex_daemon", new Object[]{}, Boolean.class);
        } catch (Throwable ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            return false;
        }
    }

    /**
     * Check if TRex server daemon is running. Returns True/False
     *
     * @return
     */
    public Boolean isTrexDaemonRunning() {
        try {
            return masterRpcConnection.invoke("is_trex_daemon_running", new Object[]{}, Boolean.class);
        } catch (Throwable ex) {
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            LOG.error(errorMessage, ex);
            return false;
        }
    }

    /**
     * connect to trex daemon server
     *
     * @param host
     */
    public void connectToTrexDaemon(String host) {
        try {
            trexRpcConnection = new JsonRpcHttpClient(new URL("http://" + host + ":" + Constants.TREX_DAEMON_SERVER_PORT + "/"));
        } catch (Exception ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
        }
    }

    /**
     * @return
     */
    public Boolean isTrexRunning() {
        try {
            return trexRpcConnection.invoke("is_running", new Object[]{}, Boolean.class);
        } catch (Throwable ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            return false;
        }
    }

    /**
     * @param trexCmdOptions
     * @return
     */
    public Integer startTrexStatefulMode(Map<String, Object> trexCmdOptions) {
        try {
            return trexRpcConnection.invoke("start_trex", new Object[]{trexCmdOptions, "user", true, Constants.RPC_TIMEOUT, false}, Integer.class);
        } catch (Throwable ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            return -1;
        }
    }

    /**
     * @return
     */
    public Boolean killAllTrexes() {
        try {
            RPCConnections.getInstance().setTrexProcessRunning(false);
            return trexRpcConnection.invoke("kill_all_trexes", new Object[]{9}, Boolean.class);
        } catch (Throwable ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            return false;
        }
    }

    /**
     * @return
     */
    public Boolean stopTrexProcess() {
        try {
            RPCConnections.getInstance().setTrexProcessRunning(false);
            return trexRpcConnection.invoke("stop_trex", new Object[]{getSessionId()}, Boolean.class);
        } catch (Throwable ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            return false;
        }
    }

    /**
     * @return
     */
    public Boolean stopTrexDaemon() {
        try {
            return trexRpcConnection.invoke("force_trex_kill", new Object[]{}, Boolean.class);
        } catch (Throwable ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            return false;
        }
    }

    /**
     * @param yamlFileList
     */
    public void setYamlFileList(ObservableList<String> yamlFileList) {
        this.yamlFileList = yamlFileList;
    }

    /**
     * @param path
     * @return
     */
    public List getFileListForPath(String path) {
        try {
            return trexRpcConnection.invoke("get_files_list", new Object[]{path}, ArrayList.class);
        } catch (Throwable ex) {
            LogsController.getInstance().appendConsoleViewText(errorMessage + ex.getMessage());
            LOG.error(errorMessage, ex);
            return new ArrayList();
        }
    }

    /**
     * @param trexCmdOptions
     */
    public void handleNewTrexCommand(Map<String, Object> trexCmdOptions) {
        if (RPCConnections.getInstance().isTrexRunning()) {
            RPCConnections.getInstance().stopTrexProcess();
        }
        sessionId = RPCConnections.getInstance().startTrexStatefulMode(trexCmdOptions);
        LogsController.getInstance().appendConsoleViewText("command executed successfully");
        setExecutingCommand(true);
        setStartZMQStreaming(true);
        setTrexProcessRunning(true);
    }

    /**
     * @return
     */
    public Integer getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId
     */
    public void setSessionId(Integer sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @param path
     * @param fileName
     */
    public void addFileToYamlList(String path, String fileName) {
        yamlFileMap.put(fileName, path);
        yamlFileList.add(fileName);
    }

    /**
     * @param fileName
     */
    public void removeFileFromYamlList(String fileName) {
        yamlFileList.remove(fileName);
        yamlFileMap.remove(fileName);
    }

    /**
     * @param key
     * @return
     */
    public String getFilePathFromMap(String key) {
        return (String) yamlFileMap.get(key);
    }

    /**
     * @return
     */
    public boolean getStartZMQStreaming() {
        return startZMQStreaming.get();
    }

    /**
     * @param startZMQStreaming
     */
    public void setStartZMQStreaming(boolean startZMQStreaming) {
        this.startZMQStreaming.set(startZMQStreaming);
    }

    /**
     * @return
     */
    public BooleanProperty startZMQStreamingProperty() {
        return startZMQStreaming;
    }

    /**
     * @return
     */
    public boolean getTrexProcessRunning() {
        return trexProcessRunning.get();
    }

    /**
     * @param trexProcessRunning
     */
    public void setTrexProcessRunning(boolean trexProcessRunning) {
        this.trexProcessRunning.set(trexProcessRunning);
    }

    /**
     * @return
     */
    public BooleanProperty trexProcessRunningProperty() {
        return trexProcessRunning;
    }

    /**
     * @return
     */
    public boolean getExecutingCommand() {
        return executingCommand.get();
    }

    /**
     * @param executingCommand
     */
    public void setExecutingCommand(boolean executingCommand) {
        this.executingCommand.set(executingCommand);
    }

    /**
     * @return
     */
    public BooleanProperty executingCommandProperty() {
        return executingCommand;
    }
}
