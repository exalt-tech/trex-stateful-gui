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
import com.exalttech.trex.stateful.models.rxcheck.RxCheck;
import com.exalttech.trex.stateful.models.templateinfo.TemplateInfo;
import com.exalttech.trex.stateful.models.trexglobal.FilterTrexGlobalResponseUtil;
import com.exalttech.trex.stateful.models.trexglobal.TrexGlobal;
import com.exalttech.trex.stateful.models.trexlatency.FilterTrexLatencyResponseUtil;
import com.exalttech.trex.stateful.models.trexlatency.TrexLatency;
import com.exalttech.trex.stateful.models.trexlatencyv2.FilterTrexLatencyV2ResponseUtil;
import com.exalttech.trex.stateful.models.trexlatencyv2.TrexLatencyV2;
import com.exalttech.trex.stateful.models.txgenresponse.TxGen;
import com.exalttech.trex.stateful.utilities.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.log4j.Logger;

/**
 * @author BassamJ
 */
public class AsyncResponse {

    private static final Logger LOG = Logger.getLogger(AsyncResponse.class.getName());
    private static AsyncResponse instance = null;
    private StringProperty response = new SimpleStringProperty();
    private StringProperty trexGlobalResponse = new SimpleStringProperty();
    private StringProperty txGenResponse = new SimpleStringProperty();
    private StringProperty templateInfoResponse = new SimpleStringProperty();
    private StringProperty rxCheckResponse = new SimpleStringProperty();
    private StringProperty trexLatencyResponse = new SimpleStringProperty();
    private StringProperty trexLatencyV2Response = new SimpleStringProperty();
    private RxCheck rxCheckObject;
    private TemplateInfo templateInfoObject;
    private TxGen txGenObject;
    private TrexGlobal trexGlobalObject;
    private TrexLatency trexLatencyObject;
    private TrexLatencyV2 trexLatencyV2Object;
    private Boolean isServerConnected = false;
    private Boolean isServerCrashed = false;
    private String command;
    private BooleanProperty closeControlWindow = new SimpleBooleanProperty(false);
    private BooleanProperty closeLogArea = new SimpleBooleanProperty(false);
    private String errorMessage = "Error: ";

    /**
     *
     */
    protected AsyncResponse() {

    }

    /**
     * @return
     */
    public static AsyncResponse getInstance() {
        if (instance == null) {
            instance = new AsyncResponse();
        }
        return instance;
    }

    /**
     * @param instance
     */
    public static void setInstance(AsyncResponse instance) {
        AsyncResponse.instance = instance;
    }

    /**
     * @return
     */
    public String getResponse() {
        return response.get();
    }

    /**
     * @param response
     */
    public void setResponse(String response) {
        this.response.set(response);
    }

    /**
     * @return
     */
    public StringProperty responseProperty() {
        return response;
    }

    /**
     * @return
     */
    public String getTrexGlobalResponse() {
        return trexGlobalResponse.get();
    }

    /**
     * @param trexGlobalResponse
     */
    public void setTrexGlobalResponse(String trexGlobalResponse) {
        this.trexGlobalResponse.set(trexGlobalResponse);
    }

    /**
     * @return
     */
    public StringProperty trexGlobalResponseProperty() {
        return trexGlobalResponse;
    }

    /**
     * @return
     */
    public String getTxGenResponse() {
        return txGenResponse.get();
    }

    /**
     * @param txGenResponse
     */
    public void setTxGenResponse(String txGenResponse) {
        this.txGenResponse.set(txGenResponse);
    }

    /**
     * @return
     */
    public StringProperty txGenResponseProperty() {
        return txGenResponse;
    }

    /**
     * @return
     */
    public String getTemplateInfoResponse() {
        return templateInfoResponse.get();
    }

    /**
     * @param templateInfoResponse
     */
    public void setTemplateInfoResponse(String templateInfoResponse) {
        this.templateInfoResponse.set(templateInfoResponse);
    }

    /**
     * @return
     */
    public StringProperty templateInfoResponseProperty() {
        return templateInfoResponse;
    }

    /**
     * @return
     */
    public String getRxCheckResponse() {
        return rxCheckResponse.get();
    }

    /**
     * @param rxCheckResponse
     */
    public void setRxCheckResponse(String rxCheckResponse) {
        this.rxCheckResponse.set(rxCheckResponse);
    }

    /**
     * @return
     */
    public StringProperty rxCheckResponseProperty() {
        return rxCheckResponse;
    }

    /**
     * @return
     */
    public String getTrexLatencyResponse() {
        return trexLatencyResponse.get();
    }

    /**
     * @param trexLatencyResponse
     */
    public void setTrexLatencyResponse(String trexLatencyResponse) {
        this.trexLatencyResponse.set(trexLatencyResponse);
    }

    /**
     * @return
     */
    public StringProperty trexLatencyResponseProperty() {
        return trexLatencyResponse;
    }

    /**
     * @return
     */
    public String getTrexLatencyV2Response() {
        return trexLatencyV2Response.get();
    }

    /**
     * @param trexLatencyV2Response
     */
    public void setTrexLatencyV2Response(String trexLatencyV2Response) {
        this.trexLatencyV2Response.set(trexLatencyV2Response);
    }

    /**
     * @return
     */
    public StringProperty trexLatencyV2ResponseProperty() {
        return trexLatencyV2Response;
    }

    /**
     * @return
     */
    public RxCheck getRxCheckObject() {
        return rxCheckObject;
    }

    /**
     * @param rxCheckObject
     */
    public void setRxCheckObject(RxCheck rxCheckObject) {
        this.rxCheckObject = rxCheckObject;
    }

    /**
     * @return
     */
    public TemplateInfo getTemplateInfoObject() {
        return templateInfoObject;
    }

    /**
     * @param templateInfoObject
     */
    public void setTemplateInfoObject(TemplateInfo templateInfoObject) {
        this.templateInfoObject = templateInfoObject;
    }

    /**
     * @return
     */
    public TxGen getTxGenObject() {
        return txGenObject;
    }

    /**
     * @param txGenObject
     */
    public void setTxGenObject(TxGen txGenObject) {
        this.txGenObject = txGenObject;
    }

    /**
     * @return
     */
    public TrexGlobal getTrexGlobalObject() {
        return trexGlobalObject;
    }

    /**
     * @param trexGlobalObject
     */
    public void setTrexGlobalObject(TrexGlobal trexGlobalObject) {
        this.trexGlobalObject = trexGlobalObject;
    }

    /**
     * @return
     */
    public TrexLatency getTrexLatencyObject() {
        return trexLatencyObject;
    }

    /**
     * @param trexLatencyObject
     */
    public void setTrexLatencyObject(TrexLatency trexLatencyObject) {
        this.trexLatencyObject = trexLatencyObject;
    }

    /**
     * @return
     */
    public TrexLatencyV2 getTrexLatencyV2Object() {
        return trexLatencyV2Object;
    }

    /**
     * @param trexLatencyV2Object
     */
    public void setTrexLatencyV2Object(TrexLatencyV2 trexLatencyV2Object) {
        this.trexLatencyV2Object = trexLatencyV2Object;
    }

    /**
     * @return
     */
    public Boolean isServerConnected() {
        return isServerConnected;
    }

    /**
     * @param serverConnected
     */
    public void setServerConnected(Boolean serverConnected) {
        isServerConnected = serverConnected;
    }

    /**
     * @return
     */
    public Boolean isServerCrashed() {
        return isServerCrashed;
    }

    /**
     * @param serverCrashed
     */
    public void setServerCrashed(Boolean serverCrashed) {
        isServerCrashed = serverCrashed;
    }

    /**
     * Initialization for current UI binding to test data binding
     */
    public void initializeDataBinding() {
        getInstance().responseProperty().addListener((e, oldValue, newValue) -> {
            setServerCrashed(false);
            if (newValue.contains(Constants.TREX_GLOBAL_TAG)) {
                getInstance().setTrexGlobalResponse(newValue);
            } else if (newValue.contains(Constants.TX_GEN_TAG)) {
                getInstance().setTxGenResponse(newValue);
            } else if (newValue.contains(Constants.TEMPLATE_INFO_TAG)) {
                getInstance().setTemplateInfoResponse(newValue);
            } else if (newValue.contains(Constants.RX_CHECK_TAG)) {
                getInstance().setRxCheckResponse(newValue);
            } else if (newValue.contains(Constants.TREX_LATENCY_TAG)) {
                getInstance().setTrexLatencyResponse(newValue);
            } else if (newValue.contains(Constants.TREX_LATENCY_V2_TAG)) {
                getInstance().setTrexLatencyV2Response(newValue);
            } else {
                LOG.error("Unknown response" + newValue);
                setServerCrashed(true);
                //Duration finished
                RPCConnections.getInstance().setExecutingCommand(false);
                LOG.info("Command duration finished.");
                LogsController.getInstance().appendConsoleViewText("Command duration finished.");
            }
        });

        getInstance().trexGlobalResponseProperty().addListener((e, oldValue, newValue) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                TrexGlobal tempObject = mapper.readValue(newValue, TrexGlobal.class);
                getInstance().setTrexGlobalObject(tempObject);
                //Jackson will handle attributes that are commonly obtained from JSON
                //we need to filter out the port data and set them to TrexGlobal Object
                getInstance().getTrexGlobalObject().getData().setPortProperties(
                        FilterTrexGlobalResponseUtil.createPortPropertiesArray(newValue)
                );
            } catch (Exception ex) {
                LOG.error(errorMessage, ex);
            }
        });
        getInstance().txGenResponseProperty().addListener((e, oldValue, newValue) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                TxGen tempObject = mapper.readValue(newValue, TxGen.class);
                getInstance().setTxGenObject(tempObject);
            } catch (Exception ex) {
                LOG.error(errorMessage, ex);
            }
        });
        getInstance().templateInfoResponseProperty().addListener((e, oldValue, newValue) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                TemplateInfo tempObject = mapper.readValue(newValue, TemplateInfo.class);
                getInstance().setTemplateInfoObject(tempObject);
            } catch (Exception ex) {
                LOG.error(errorMessage, ex);
            }
        });
        getInstance().rxCheckResponseProperty().addListener((e, oldValue, newValue) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                RxCheck tempObject = mapper.readValue(newValue, RxCheck.class);
                getInstance().setRxCheckObject(tempObject);
            } catch (Exception ex) {
                LOG.error(errorMessage, ex);
            }
        });
        getInstance().trexLatencyResponseProperty().addListener((e, oldValue, newValue) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                TrexLatency tempObject = mapper.readValue(newValue, TrexLatency.class);
                getInstance().setTrexLatencyObject(tempObject);
                getInstance().getTrexLatencyObject().getData().setPortProperty(
                        FilterTrexLatencyResponseUtil.filterPortPropertiesArray(newValue)
                );
            } catch (Exception ex) {
                LOG.error(errorMessage, ex);
            }
        });
        getInstance().trexLatencyV2ResponseProperty().addListener((e, oldValue, newValue) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                TrexLatencyV2 tempObject = mapper.readValue(newValue, TrexLatencyV2.class);
                getInstance().setTrexLatencyV2Object(tempObject);
                getInstance().getTrexLatencyV2Object().getData().setPortProperty(
                        FilterTrexLatencyV2ResponseUtil.filterPortPropertiesArray(newValue)
                );
            } catch (Exception ex) {
                LOG.error(errorMessage, ex);
            }
        });
    }

    /**
     * @return
     */
    public String getCommand() {
        return command;
    }

    /**
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     *
     */
    public void clearAsyncResponseObjects() {
        response.setValue("");
        trexGlobalResponse.set("");
        txGenResponse.setValue("");
        templateInfoResponse.setValue("");
        rxCheckResponse.setValue("");
        trexLatencyResponse.setValue("");
        trexLatencyV2Response.setValue("");
    }

    /**
     * @return
     */
    public boolean getCloseControlWindow() {
        return closeControlWindow.get();
    }

    /**
     * @param closeControlWindow
     */
    public void setCloseControlWindow(boolean closeControlWindow) {
        this.closeControlWindow.set(closeControlWindow);
    }

    /**
     * @return
     */
    public BooleanProperty closeControlWindowProperty() {
        return closeControlWindow;
    }

    /**
     * @return
     */
    public boolean getCloseLogArea() {
        return closeLogArea.get();
    }

    /**
     * @param closeLogArea
     */
    public void setCloseLogArea(boolean closeLogArea) {
        this.closeLogArea.set(closeLogArea);
    }

    /**
     * @return
     */
    public BooleanProperty closeLogAreaProperty() {
        return closeLogArea;
    }
}
