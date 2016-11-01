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

package com.exalttech.trex.stateful.controllers;

import com.exalttech.trex.stateful.log.LogsController;
import com.exalttech.trex.stateful.models.appearancesettings.AppearanceSettings;
import com.exalttech.trex.stateful.models.connection.AppLocalConnections;
import com.exalttech.trex.stateful.models.connection.Connection;
import com.exalttech.trex.stateful.models.connection.ConnectionsWrapper;
import com.exalttech.trex.stateful.service.AsyncResponse;
import com.exalttech.trex.stateful.service.Communicator;
import com.exalttech.trex.stateful.service.RPCConnections;
import com.exalttech.trex.stateful.utilities.Constants;
import com.exalttech.trex.stateful.utilities.LocalXMLFileManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author BassamJ
 */
public class TrexAppController implements Initializable {

    private static final Logger LOG = Logger.getLogger(TrexAppController.class.getName());
    @FXML
    private ComboBox<String> serverIPComboBox;

    @FXML
    private Label connectToServerLabel;

    @FXML
    private Label disconnectToServerLabel;

    @FXML
    private Label stopTrexLabel;

    @FXML
    private Label restartTrexLabel;

    private Communicator communicator = new Communicator();

    @FXML
    private Label connectedLabel;

    @FXML
    private ImageView connectionIcon;

    @FXML
    private Parent latencyChart;

    @FXML
    private LatencyChartController latencyChartController;

    @FXML
    private Parent rxHistogramChart;

    @FXML
    private RxHistogramChartController rxHistogramChartController;

    @FXML
    private Parent latencyV2HistogramChart;

    @FXML
    private LatencyV2HistogramChartController latencyV2HistogramChartController;

    @FXML
    private Parent globalCounterDashboard;

    @FXML
    private GlobalCounterDashboardController globalCounterDashboardController;

    @FXML
    private Parent perTemplateWindowTable;

    @FXML
    private PerTemplateWindowTableController perTemplateWindowTableController;

    @FXML
    private Parent perPortCountersTable;

    @FXML
    private PerPortCountersTableController perPortCountersTableController;

    @FXML
    private AnchorPane logContainer;

    @FXML
    private Parent controlWindowView;

    @FXML
    private ControlWindowViewController controlWindowViewController;

    @FXML
    private SplitPane controlSplitPaneContainer;

    @FXML
    private SplitPane logSplitPaneContainer;

    @FXML
    private SplitPane chartSplitContainer;

    @FXML
    private SplitPane tablesSplitPane;

    private boolean isGlobalCounterExpanded = false;
    private boolean isPerPortCountersExpanded = false;
    private boolean isPerWindowTemplateExpanded = false;
    private boolean isLatencyChartExpanded = false;
    private boolean isRxHistogramChartExpanded = false;
    private boolean isLatencyV2HistogramChartExpanded = false;

    private Stage globalCountersStage;
    private Stage perPortCountersStage;
    private Stage perWindowTemplateStage;
    private Stage latencyChartStage;
    private Stage rxHistogramStage;
    private Stage latencyV2HistogramStage;

    private String ipAddress;

    private ConnectionsWrapper connections;

    @FXML
    private VBox globalCountersContainer;

    @FXML
    private VBox perPortCountersContainer;

    @FXML
    private VBox perWindowTemplateContainer;

    @FXML
    private VBox latencyChartContainer;

    @FXML
    private VBox rxHistogramChartContainer;

    @FXML
    private VBox latencyHistogramChartContainer;

    @FXML
    private VBox tablesContainer;

    @FXML
    private VBox chartsContainer;

    @FXML
    private ImageView upDownArrowIcon;

    private String errorMessage = "Error: ";

    /**
     * initialize components before starting application
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // load list of connections from XML
        loadIPList();
        initializeLog();
        initializeSplitPanes();
        initializeServerControlsToolTips();
        AsyncResponse.getInstance().closeControlWindowProperty().addListener((observable, oldValue, newValue) -> {
            //when property changes , close control window
            if (newValue) {
                hideControlWindow();
            } else {
                showControlWindow();
            }
        });
        AsyncResponse.getInstance().closeLogAreaProperty().addListener((observable, oldValue, newValue) -> {
            //when property changes , close log Area
            if (newValue) {
                logSplitPaneContainer.setDividerPosition(0, 0.5);
            } else {
                logSplitPaneContainer.setDividerPosition(0, 1);
            }
        });
        initializeContainersBindingSettings();
        VBox.setVgrow(latencyChart, Priority.ALWAYS);
        VBox.setVgrow(rxHistogramChart, Priority.ALWAYS);
        VBox.setVgrow(latencyV2HistogramChart, Priority.ALWAYS);
        VBox.setVgrow(perTemplateWindowTable, Priority.ALWAYS);
        VBox.setVgrow(perPortCountersTable, Priority.ALWAYS);
        RPCConnections.getInstance().startZMQStreamingProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue) {
                        startZMQStream();
                    } else {
                        stopTrexViewer();
                    }

                }
        );
        serverIPComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != null && !"".equals(oldValue) && AsyncResponse.getInstance().isServerConnected()) {
                handleDisconnectToServer();
                handleConnectToServer();
            }
        });
        RPCConnections.getInstance().trexProcessRunningProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                stopTrexLabel.setDisable(false);
            } else {
                stopTrexLabel.setDisable(true);
            }
        });

        RPCConnections.getInstance().executingCommandProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                connectionIcon.setImage(new Image("/images/connectedIcon.gif"));
                stopTrexLabel.setDisable(false);
                restartTrexLabel.setDisable(true);
            } else {
                connectionIcon.setImage(new Image("/images/green_dot.png"));
                stopTrexLabel.setDisable(true);
                restartTrexLabel.setDisable(false);
            }
        });

    }

    /**
     * Called when the user clicks the connect button.
     * it connect to server using IP address specified in IP address Field
     */
    @FXML
    private void handleConnectToServer() {
        if (AsyncResponse.getInstance().isServerConnected()) {
            LOG.error("Server is already connected ...");
            LogsController.getInstance().appendConsoleViewText("Server is already connected ...");
            return;
        }

        LOG.info("connecting to server...");
        LogsController.getInstance().appendConsoleViewText("connecting to server...");
        connectedLabel.setText("Connecting ");
        connectionIcon.setImage(new Image("/images/idleIcon.gif"));
        // validation for IP address
        ipAddress = serverIPComboBox.getEditor().getText();

        // add IP address to List
        if (!serverIPComboBox.getItems().contains(ipAddress)) {
            serverIPComboBox.getItems().add(ipAddress);
            AppLocalConnections.getInstance().addNewIpAddressToList(ipAddress);
        }

        String asyncPortNumber = Constants.ASYNC_PORT_NUMBER;
        final Task<Boolean> task = new Task<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    LOG.info("Connecting to master :IP address:" + ipAddress + " Port Number:" + Constants.MASTER_DAEMON_PORT);
                    LogsController.getInstance().appendConsoleViewText("Connecting to master :IP address:" + ipAddress + " Port Number:" + Constants.MASTER_DAEMON_PORT);
                    if (RPCConnections.getInstance().connectToMasterDaemon(ipAddress)) {
                        if (!RPCConnections.getInstance().isTrexDaemonRunning() && !RPCConnections.getInstance().startTrexDaemon()) {
                            displayErrorMessageWindow("Connection refused by TRex master");
                            LogsController.getInstance().appendConsoleViewText("Connection refused by TRex master");
                            return false;
                        }
                        return true;
                    } else {
                        displayErrorMessageWindow("Master daemon and TRex Server is not running !");
                        LogsController.getInstance().appendConsoleViewText("Master daemon and TRex Server is not running !");
                        return false;
                    }
                } catch (Exception e) {
                    LogsController.getInstance().appendConsoleViewText("Master daemon and TRex Server is not running !");
                    LOG.error(errorMessage, e);
                    return false;
                }
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Boolean status = task.getValue();
                if (status) {
                    RPCConnections.getInstance().connectToTrexDaemon(ipAddress);
                    //kill all existing trexes if exists
                    RPCConnections.getInstance().killAllTrexes();
                    AsyncResponse.getInstance().setServerConnected(true);
                    connectedLabel.setText("Connected");
                    connectionIcon.setImage(new Image("/images/green_dot.png"));
                    connectToServerLabel.setDisable(true);
                    disconnectToServerLabel.setDisable(false);
                    restartTrexLabel.setDisable(false);
                    controlWindowViewController.updateYamlFileList(ipAddress, connections);
                    controlWindowViewController.activateControlWindow();
                    LOG.info("TRex server connected successfully");
                    LogsController.getInstance().appendConsoleViewText("TRex server connected successfully");
                } else {
                    connectedLabel.setText("Disconnected");
                    displayErrorMessageWindow("Master daemon and TRex Server is not running !");
                }
            }
        });

        final Task<Boolean> timeoutTask = new Task<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                //check if host is reachable from client application for connectivity
                if (InetAddress.getByName(ipAddress).isReachable(Constants.HOST_TIMEOUT)) {
                    return true;
                }
                return false;
            }
        };

        timeoutTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Boolean status = timeoutTask.getValue();
                if (status) {
//                    if (communicator.checkConnectivityToServer(ipAddress, asyncPortNumber)) {
//                        LogsController.getInstance().appendConsoleViewText("INFO: Master is acquired by another user");
//                        LOG.info("INFO: Master is acquired by another user");
//                        AsyncResponse.getInstance().setServerConnected(true);
//                        connectedLabel.setText("Connected");
//                        connectionIcon.setImage(new Image("/images/connectedIcon.gif"));
//                        connectToServerLabel.setDisable(true);
//                        disconnectToServerLabel.setDisable(false);
//                        RPCConnections.getInstance().setStartZMQStreaming(!RPCConnections.getInstance().startZMQStreamingProperty().getValue());
//                    } else {
                    Thread t = new Thread(task);
                    t.setDaemon(true);
                    t.start();
//                    }
                } else {
                    connectedLabel.setText("Disconnected");
                    displayErrorMessageWindow("Host " + ipAddress + " timeout!");
                    LogsController.getInstance().appendConsoleViewText("Host " + ipAddress + " timeout!");
                }
            }
        });
        Thread t = new Thread(timeoutTask);
        t.setDaemon(true);
        t.start();
    }

    /**
     * @param message
     */
    public void displayErrorMessageWindow(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        LOG.error("Error While connecting to server");
        LogsController.getInstance().appendConsoleViewText("Error While connecting to server");
        alert.setTitle("Connection Error");
        alert.setHeaderText(null);
        alert.setContentText("Error: " + message);
        alert.showAndWait();
    }

    /**
     * Called when the user clicks the disconnect button.
     */
    @FXML
    private void handleDisconnectToServer() {

        if (!AsyncResponse.getInstance().isServerConnected()) {
            LOG.error("Server already Disconnected !");
            LogsController.getInstance().appendConsoleViewText("Server already Disconnected !");
            return;
        }
        RPCConnections.getInstance().setStartZMQStreaming(false);
        RPCConnections.getInstance().setExecutingCommand(false);
        connectedLabel.setText("Disconnected");
        connectionIcon.setImage(new Image("/images/offline.png"));
        AsyncResponse.getInstance().setServerConnected(false);
        connectToServerLabel.setDisable(false);
        disconnectToServerLabel.setDisable(true);
        stopTrexLabel.setDisable(true);
        restartTrexLabel.setDisable(true);
        controlWindowViewController.resetControlView();
        controlWindowViewController.deactivateControlWindow();
        AsyncResponse.getInstance().clearAsyncResponseObjects();
        LogsController.getInstance().clearConsoleViewText();
        final Task<Boolean> task = new Task<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if (RPCConnections.getInstance().stopTrexProcess() && RPCConnections.getInstance().stopTrexDaemon()) {
                    return true;
                }
                return false;

            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Boolean status = task.getValue();
                if (status) {
                    LOG.info("Disconnected correctly from server.");
                    LogsController.getInstance().appendConsoleViewText("Disconnected correctly from server.");
                } else {
                    LOG.error("Error while disconnecting from server");
                    LogsController.getInstance().appendConsoleViewText("Error while disconnecting from server");
                }
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    @FXML
    private void startZMQStream() {
        String asyncPortNumber = Constants.ASYNC_PORT_NUMBER;
        final Task<Boolean> task = new Task<Boolean>() {
            @Override
            public Boolean call() throws Exception {
//                if (communicator.checkConnectivityToServer(ipAddress, asyncPortNumber) || RPCConnections.getInstance().isTrexRunning()) {
                if (RPCConnections.getInstance().isTrexRunning()) {
                    return true;
                }
                return false;
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Boolean status = task.getValue();
                if (status) {
                    LogsController.getInstance().appendConsoleViewText("ZMQ connected at port " + asyncPortNumber);
                    LOG.info("ZMQ connected at port " + asyncPortNumber);
                    communicator.connectToSubscriber(ipAddress, asyncPortNumber);
                    AsyncResponse.getInstance().initializeDataBinding();
                    latencyChartController.runChart();
                    rxHistogramChartController.runChart();
                    latencyV2HistogramChartController.runChart();
                    globalCounterDashboardController.startDashboardTask();
                    perPortCountersTableController.startPerPortCounterTask();
                    perTemplateWindowTableController.startPerTemplateWindowTask();
                } else {
                    LOG.error("Error No Trex Server Found");
                    LogsController.getInstance().appendConsoleViewText("Error No Trex Server Found");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Connection Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error: no Trex Server is found");
                    alert.showAndWait();
                }
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     *
     */
    public void stopTrexViewer() {
        latencyChartController.stopChart();
        rxHistogramChartController.stopChart();
        latencyV2HistogramChartController.stopChart();
        perPortCountersTableController.stopPerPortCounterTask();
        globalCounterDashboardController.stopDashboardTask();
        perTemplateWindowTableController.stopPerTemplateWindowTask();
        closeAllExternalWindows();
        final Task<Boolean> task = new Task<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    communicator.disconnectSubscriber();
                    return true;
                } catch (Exception e) {
                    LOG.error(errorMessage, e);
                    return false;
                }
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Boolean status = task.getValue();
                if (status) {
                    LogsController.getInstance().appendConsoleViewText("Data cleared from TRex Viewer");
                    LOG.info("Data cleared from TRex Viewer");
                } else {
                    LogsController.getInstance().appendConsoleViewText("Error while disconnecting from TRex");
                    LOG.info("Error while disconnecting from TRex");
                }
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     *
     */
    @FXML
    public void handleExpandGlobalCounterWindow() {
        try {
            if (!isGlobalCounterExpanded()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/GlobalCountersView.fxml"));
                VBox page = (VBox) loader.load();
                GlobalCounterDashboardController controller = loader.getController();
                if (AsyncResponse.getInstance().isServerConnected()) {
                    controller.startDashboardTask();
                }
                globalCountersStage = new Stage();
                globalCountersStage.setTitle("Per Port Counters");
                globalCountersStage.initModality(Modality.NONE);
                Scene scene = new Scene(page);
                globalCountersStage.setScene(scene);
                globalCountersStage.getIcons().add(new Image("/images/trex.png"));
                globalCountersStage.resizableProperty().setValue(Boolean.FALSE);
                globalCountersStage.show();
                globalCountersStage.setOnCloseRequest(e -> setGlobalCounterExpanded(false));
                setGlobalCounterExpanded(true);
            }
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void handleExpandPerPortCountersWindow() {
        try {
            if (!isPerPortCountersExpanded()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/PerPortCounterTable.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                PerPortCountersTableController controller = loader.getController();
                if (AsyncResponse.getInstance().isServerConnected()) {
                    controller.startPerPortCounterTask();
                }
                perPortCountersStage = new Stage();
                perPortCountersStage.setTitle("Per Port Counters");
                perPortCountersStage.initModality(Modality.NONE);
                perPortCountersStage.setWidth(800);
                perPortCountersStage.setHeight(500);
                Scene scene = new Scene(page);
                perPortCountersStage.setScene(scene);
                perPortCountersStage.getIcons().add(new Image("/images/trex.png"));
                perPortCountersStage.show();
                perPortCountersStage.setOnCloseRequest(e -> setPerPortCountersExpanded(false));
                setPerPortCountersExpanded(true);
            }
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void handleExpandPerWindowTemplateWindow() {
        try {
            if (!isPerWindowTemplateExpanded()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/PerTemplateWindowTable.fxml"));
                AnchorPane page = (AnchorPane) loader.load();
                PerTemplateWindowTableController controller = loader.getController();
                if (AsyncResponse.getInstance().isServerConnected()) {
                    controller.startPerTemplateWindowTask();
                }
                perWindowTemplateStage = new Stage();
                perWindowTemplateStage.setTitle("Per Template Window");
                perWindowTemplateStage.initModality(Modality.NONE);
                perWindowTemplateStage.setWidth(800);
                perWindowTemplateStage.setHeight(500);
                Scene scene = new Scene(page);
                perWindowTemplateStage.setScene(scene);
                perWindowTemplateStage.getIcons().add(new Image("/images/trex.png"));
                perWindowTemplateStage.show();
                perWindowTemplateStage.setOnCloseRequest(e -> setPerWindowTemplateExpanded(false));
                setPerWindowTemplateExpanded(true);
            }
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void handleExpandLatencyChartWindow() {
        try {
            if (!isLatencyChartExpanded()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/LatencyChart.fxml"));
                AnchorPane page = new AnchorPane();
                LineChart chart = (LineChart) loader.load();
                page.getChildren().add(chart);
                AnchorPane.setTopAnchor(chart, 0.0);
                AnchorPane.setRightAnchor(chart, 0.0);
                AnchorPane.setLeftAnchor(chart, 0.0);
                AnchorPane.setBottomAnchor(chart, 0.0);
                LatencyChartController controller = loader.getController();
                if (AsyncResponse.getInstance().isServerConnected()) {
                    controller.runChart();
                }
                latencyChartStage = new Stage();
                latencyChartStage.setTitle("Latency Chart");
                latencyChartStage.initModality(Modality.NONE);
                latencyChartStage.setWidth(800);
                latencyChartStage.setHeight(500);
                Scene scene = new Scene(page);
                latencyChartStage.setScene(scene);
                latencyChartStage.getIcons().add(new Image("/images/trex.png"));
                latencyChartStage.show();
                latencyChartStage.setOnCloseRequest(e -> setLatencyChartExpanded(false));
                setLatencyChartExpanded(true);
            }
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void handleExpandRxHistogramChartWindow() {
        try {
            if (!isRxHistogramChartExpanded()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/RxHistogramChart.fxml"));
                AnchorPane page = new AnchorPane();
                AreaChart chart = (AreaChart) loader.load();
                page.getChildren().add(chart);
                AnchorPane.setTopAnchor(chart, 0.0);
                AnchorPane.setRightAnchor(chart, 0.0);
                AnchorPane.setLeftAnchor(chart, 0.0);
                AnchorPane.setBottomAnchor(chart, 0.0);
                RxHistogramChartController controller = loader.getController();
                if (AsyncResponse.getInstance().isServerConnected()) {
                    controller.runChart();
                }
                rxHistogramStage = new Stage();
                rxHistogramStage.setTitle("Rx Histogram Chart");
                rxHistogramStage.initModality(Modality.NONE);
                rxHistogramStage.setWidth(800);
                rxHistogramStage.setHeight(500);
                Scene scene = new Scene(page);
                rxHistogramStage.setScene(scene);
                rxHistogramStage.getIcons().add(new Image("/images/trex.png"));
                rxHistogramStage.show();
                rxHistogramStage.setOnCloseRequest(e -> setRxHistogramChartExpanded(false));
                setRxHistogramChartExpanded(true);
            }
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void handleExpandLatencyV2HistogramChartWindow() {
        try {
            if (!isLatencyV2HistogramChartExpanded()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/LatencyV2HistogramChart.fxml"));
                AnchorPane page = new AnchorPane();
                AreaChart chart = (AreaChart) loader.load();
                page.getChildren().add(chart);
                AnchorPane.setTopAnchor(chart, 0.0);
                AnchorPane.setRightAnchor(chart, 0.0);
                AnchorPane.setLeftAnchor(chart, 0.0);
                AnchorPane.setBottomAnchor(chart, 0.0);
                LatencyV2HistogramChartController controller = loader.getController();
                if (AsyncResponse.getInstance().isServerConnected()) {
                    controller.runChart();
                }
                latencyV2HistogramStage = new Stage();
                latencyV2HistogramStage.setTitle("Latency Histogram Chart");
                latencyV2HistogramStage.initModality(Modality.NONE);
                latencyV2HistogramStage.setWidth(800);
                latencyV2HistogramStage.setHeight(500);
                Scene scene = new Scene(page);
                latencyV2HistogramStage.setScene(scene);
                latencyV2HistogramStage.getIcons().add(new Image("/images/trex.png"));
                latencyV2HistogramStage.show();
                latencyV2HistogramStage.setOnCloseRequest(e -> setLatencyV2HistogramChartExpanded(false));
                setLatencyV2HistogramChartExpanded(true);
            }
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     * @return
     */
    public boolean isLatencyV2HistogramChartExpanded() {
        return isLatencyV2HistogramChartExpanded;
    }

    /**
     * @param latencyV2HistogramChartExpanded
     */
    public void setLatencyV2HistogramChartExpanded(boolean latencyV2HistogramChartExpanded) {
        isLatencyV2HistogramChartExpanded = latencyV2HistogramChartExpanded;
    }

    /**
     * @return
     */
    public boolean isRxHistogramChartExpanded() {
        return isRxHistogramChartExpanded;
    }

    /**
     * @param rxHistogramChartExpanded
     */
    public void setRxHistogramChartExpanded(boolean rxHistogramChartExpanded) {
        isRxHistogramChartExpanded = rxHistogramChartExpanded;
    }

    /**
     * @return
     */
    public boolean isLatencyChartExpanded() {
        return isLatencyChartExpanded;
    }

    /**
     * @param latencyChartExpanded
     */
    public void setLatencyChartExpanded(boolean latencyChartExpanded) {
        isLatencyChartExpanded = latencyChartExpanded;
    }

    /**
     * @return
     */
    public boolean isPerWindowTemplateExpanded() {
        return isPerWindowTemplateExpanded;
    }

    /**
     * @param perWindowTemplateExpanded
     */
    public void setPerWindowTemplateExpanded(boolean perWindowTemplateExpanded) {
        isPerWindowTemplateExpanded = perWindowTemplateExpanded;
    }

    /**
     * @return
     */
    public boolean isPerPortCountersExpanded() {
        return isPerPortCountersExpanded;
    }

    /**
     * @param perPortCountersExpanded
     */
    public void setPerPortCountersExpanded(boolean perPortCountersExpanded) {
        isPerPortCountersExpanded = perPortCountersExpanded;
    }

    /**
     * @return
     */
    public boolean isGlobalCounterExpanded() {
        return isGlobalCounterExpanded;
    }

    /**
     * @param globalCounterExpanded
     */
    public void setGlobalCounterExpanded(boolean globalCounterExpanded) {
        isGlobalCounterExpanded = globalCounterExpanded;
    }

    /**
     * @return
     */
    public List<String> getIPList() {
        return serverIPComboBox.getItems();
    }

    /**
     *
     */
    public void loadIPList() {
        try {
            connections = (ConnectionsWrapper) LocalXMLFileManager.loadXML("TrexViewer.xml", ConnectionsWrapper.class);
            if (connections != null) {
                List<Connection> connectionList = connections.getConnectionList();
                AppLocalConnections.getInstance().setConnectionList(connectionList);
                for (Connection c : connectionList) {
                    serverIPComboBox.getItems().add(c.getIp());
                }
            }
        } catch (Exception e) {
            // do nothing for now , IP list will appear empty
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void closeApplication() {
        if (RPCConnections.getInstance().isTrexRunning()) {
            RPCConnections.getInstance().stopTrexProcess();
            RPCConnections.getInstance().stopTrexDaemon();
        }
        ConnectionsWrapper wrapper = new ConnectionsWrapper();
        wrapper.setConnectionList(AppLocalConnections.getInstance().getConnectionList());
        LocalXMLFileManager.saveXML("TrexViewer.xml", wrapper, ConnectionsWrapper.class);
        try {
            Platform.exit();
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void showAboutWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AboutWindowView.fxml"));
            AnchorPane content = null;
            content = (AnchorPane) loader.load();
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            Stage window = new Stage();
            window.setTitle("About");
            window.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(content);
            window.setScene(scene);
            window.getIcons().add(new Image("/images/trex.png"));
            window.show();

        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }

    }

    /**
     *
     */
    public void closeAllExternalWindows() {
        if (isGlobalCounterExpanded()) {
            globalCountersStage.close();
            setGlobalCounterExpanded(false);
        }

        if (isPerPortCountersExpanded()) {
            perPortCountersStage.close();
            setPerPortCountersExpanded(false);
        }

        if (isPerWindowTemplateExpanded()) {
            perWindowTemplateStage.close();
            setPerWindowTemplateExpanded(false);
        }

        if (isLatencyChartExpanded()) {
            latencyChartStage.close();
            setLatencyChartExpanded(false);
        }

        if (isRxHistogramChartExpanded()) {
            rxHistogramStage.close();
            setRxHistogramChartExpanded(false);
        }

        if (isLatencyV2HistogramChartExpanded()) {
            latencyV2HistogramStage.close();
            setLatencyV2HistogramChartExpanded(false);
        }
    }

    private void initializeLog() {
        logContainer.getChildren().add(
                LogsController.getInstance().getConsoleLogView()
        );
        logSplitPaneContainer.setDividerPosition(0, 0.75);
    }

    private void initializeSplitPanes() {
        chartSplitContainer.setDividerPosition(0, 0);
    }

    /**
     *
     */
    public void hideControlWindow() {
        controlSplitPaneContainer.setDividerPositions(0);
    }

    /**
     *
     */
    public void showControlWindow() {
        controlSplitPaneContainer.setDividerPositions(1);
    }

    private void initializeServerControlsToolTips() {
        Tooltip connectToolTip = new Tooltip();
        connectToolTip.setText("Connect TRex application to server");
        Tooltip disconnectToolTip = new Tooltip();
        disconnectToolTip.setText("Disconnect from server");
        Tooltip stopToolTip = new Tooltip();
        stopToolTip.setText("Stop TRex process");
        Tooltip restartToolTip = new Tooltip();
        restartToolTip.setText("Restart trex daemon");
        connectToServerLabel.setTooltip(connectToolTip);
        disconnectToServerLabel.setTooltip(disconnectToolTip);
        stopTrexLabel.setTooltip(stopToolTip);
        restartTrexLabel.setTooltip(restartToolTip);
    }

    /**
     * @param event
     */
    @FXML
    public void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            // disconnect from old server and connect to the new server
            if (AsyncResponse.getInstance().isServerConnected()) {
                handleDisconnectToServer();
            }
            handleConnectToServer();
            event.consume();
        }
    }

    private void initializeContainersBindingSettings() {
        globalCountersContainer.managedProperty().bind(globalCountersContainer.visibleProperty());
        perPortCountersContainer.managedProperty().bind(perPortCountersContainer.visibleProperty());
        perWindowTemplateContainer.managedProperty().bind(perWindowTemplateContainer.visibleProperty());
        latencyChartContainer.managedProperty().bind(latencyChartContainer.visibleProperty());
        rxHistogramChartContainer.managedProperty().bind(rxHistogramChartContainer.visibleProperty());
        latencyHistogramChartContainer.managedProperty().bind(latencyHistogramChartContainer.visibleProperty());
        chartSplitContainer.managedProperty().bind(chartSplitContainer.visibleProperty());

        tablesContainer.managedProperty().bind(tablesContainer.visibleProperty());
        chartsContainer.managedProperty().bind(chartsContainer.visibleProperty());

        perPortCountersContainer.visibleProperty().addListener((observable, oldValue, newValue) -> {
            // if PerCounterTable  was closed , check if PerTemplate table is also closed , if so hide the container
            // otherwise do nothing
            if (!newValue) {
                if (!perWindowTemplateContainer.visibleProperty().get()) {
                    // in this case both are hidden , close the table container
                    tablesContainer.setVisible(false);
                    chartSplitContainer.setDividerPosition(0, 0);
                    if (!chartsContainer.visibleProperty().get()) {
                        chartSplitContainer.setVisible(false);
                    }
                } else {
                    tablesSplitPane.setDividerPosition(0, 0);
                }
            } else {
                tablesContainer.setVisible(true);
                chartSplitContainer.setVisible(true);
                if (chartsContainer.visibleProperty().get()) {
                    chartSplitContainer.setDividerPosition(0, 0.67);
                } else {
                    chartSplitContainer.setDividerPosition(0, 1);
                }
                if (!perWindowTemplateContainer.visibleProperty().get()) {
                    tablesSplitPane.setDividerPosition(0, 1);
                } else {
                    tablesSplitPane.setDividerPosition(0, 0.5);
                }
            }
        });

        perWindowTemplateContainer.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!perPortCountersContainer.visibleProperty().get()) {
                    tablesContainer.setVisible(false);
                    chartSplitContainer.setDividerPosition(0, 0);
                    if (!chartsContainer.visibleProperty().get()) {
                        chartSplitContainer.setVisible(false);
                    }
                } else {
                    tablesSplitPane.setDividerPosition(0, 1);
                }
            } else {
                tablesContainer.setVisible(true);
                chartSplitContainer.setVisible(true);
                if (chartsContainer.visibleProperty().get()) {
                    chartSplitContainer.setDividerPosition(0, 0.67);
                } else {
                    chartSplitContainer.setDividerPosition(0, 1);
                }
                if (!perPortCountersContainer.visibleProperty().get()) {
                    tablesSplitPane.setDividerPosition(0, 0);
                } else {
                    tablesSplitPane.setDividerPosition(0, 0.5);
                }
            }
        });

        latencyChartContainer.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!rxHistogramChartContainer.visibleProperty().get() && !latencyHistogramChartContainer.visibleProperty().get()) {
                    chartsContainer.setVisible(false);
                    chartSplitContainer.setDividerPosition(0, 1);
                    if (!tablesContainer.visibleProperty().get()) {
                        chartSplitContainer.setVisible(false);
                    }
                }
            } else {
                chartsContainer.setVisible(true);
                chartSplitContainer.setVisible(true);
                if (tablesContainer.visibleProperty().get()) {
                    chartSplitContainer.setDividerPosition(0, 0.67);
                } else {
                    chartSplitContainer.setDividerPosition(0, 0);
                }
            }
        });

        rxHistogramChartContainer.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!latencyChartContainer.visibleProperty().get() && !latencyHistogramChartContainer.visibleProperty().get()) {
                    chartsContainer.setVisible(false);
                    chartSplitContainer.setDividerPosition(0, 1);
                    if (!tablesContainer.visibleProperty().get()) {
                        chartSplitContainer.setVisible(false);
                    }
                }
            } else {
                chartsContainer.setVisible(true);
                chartSplitContainer.setVisible(true);
                if (tablesContainer.visibleProperty().get()) {
                    chartSplitContainer.setDividerPosition(0, 0.67);
                } else {
                    chartSplitContainer.setDividerPosition(0, 0);
                }
            }
        });

        latencyHistogramChartContainer.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!rxHistogramChartContainer.visibleProperty().get() && !latencyChartContainer.visibleProperty().get()) {
                    chartsContainer.setVisible(false);
                    chartSplitContainer.setDividerPosition(0, 1);
                    if (!tablesContainer.visibleProperty().get()) {
                        chartSplitContainer.setVisible(false);
                    }
                }
            } else {
                chartsContainer.setVisible(true);
                chartSplitContainer.setVisible(true);
                if (tablesContainer.visibleProperty().get()) {
                    chartSplitContainer.setDividerPosition(0, 0.67);
                } else {
                    chartSplitContainer.setDividerPosition(0, 0);
                }
            }
        });

        AppearanceSettings.getInstance().isGlobalCountersVisibleProperty().bindBidirectional(globalCountersContainer.visibleProperty());
        AppearanceSettings.getInstance().isPerPortCountersVisibleProperty().bindBidirectional(perPortCountersContainer.visibleProperty());
        AppearanceSettings.getInstance().isPerWindowTemplateVisibleProperty().bindBidirectional(perWindowTemplateContainer.visibleProperty());
        AppearanceSettings.getInstance().isLatencyChartVisibleProperty().bindBidirectional(latencyChartContainer.visibleProperty());
        AppearanceSettings.getInstance().isRxHistogramChartVisibleProperty().bindBidirectional(rxHistogramChartContainer.visibleProperty());
        AppearanceSettings.getInstance().isLatencyHistogramChartVisibleProperty().bindBidirectional(latencyHistogramChartContainer.visibleProperty());
    }

    /**
     *
     */
    @FXML
    public void closeGlobalCountersContainer() {
        globalCountersContainer.setVisible(false);
    }

    /**
     *
     */
    @FXML
    public void closePerPortCountersContainer() {
        perPortCountersContainer.setVisible(false);
    }

    /**
     *
     */
    @FXML
    public void closePerWindowTemplatecontainer() {
        perWindowTemplateContainer.setVisible(false);
    }

    /**
     *
     */
    @FXML
    public void closeLatencyChartContainer() {
        latencyChartContainer.setVisible(false);
    }

    /**
     *
     */
    @FXML
    public void closeRxHistogramChartContainer() {
        rxHistogramChartContainer.setVisible(false);
    }

    /**
     *
     */
    @FXML
    public void closeLatencyHistogramChartContainer() {
        latencyHistogramChartContainer.setVisible(false);
    }

    /**
     *
     */
    @FXML
    public void openAppearanceSettingsWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AppearanceSettingsView.fxml"));
            AnchorPane content = (AnchorPane) loader.load();
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            Stage window = new Stage(StageStyle.UNDECORATED);
            window.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(content);
            window.setScene(scene);
            window.show();

        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void closeLogArea() {
        if (!AsyncResponse.getInstance().getCloseLogArea()) {
            upDownArrowIcon.setImage(new Image("/images/arrow_down.png"));
        } else {
            upDownArrowIcon.setImage(new Image("/images/arrow_up.png"));
        }
        AsyncResponse.getInstance().setCloseLogArea(!AsyncResponse.getInstance().getCloseLogArea());
    }

    /**
     *
     */
    @FXML
    public void handleStopTrexProcess() {
        RPCConnections.getInstance().setStartZMQStreaming(false);
        RPCConnections.getInstance().setExecutingCommand(false);
        final Task<Boolean> task = new Task<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    RPCConnections.getInstance().killAllTrexes();
                    return true;
                } catch (Exception e) {
                    LOG.error(errorMessage, e);
                    return false;
                }
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Boolean status = task.getValue();
                if (status) {
                    stopTrexLabel.setDisable(true);
                    LogsController.getInstance().appendConsoleViewText("TRex Process Stopped correctly.");
                    LOG.info("TRex Process Stopped correctly.");
                } else {
                    LogsController.getInstance().appendConsoleViewText("Error while Killing TRex process");
                    LOG.info("Error while Killing TRex process");
                }
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     *
     */
    @FXML
    public void hanldeRestartTRexServer() {
        LogsController.getInstance().appendConsoleViewText("Restarting Trex daemon...");
        LOG.info("Restarting Trex daemon...");
        final Task<Boolean> task = new Task<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    return RPCConnections.getInstance().restartTrexDaemon();
                } catch (Exception e) {
                    LOG.error(errorMessage, e);
                    return false;
                }
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Boolean status = task.getValue();
                if (status) {
                    LogsController.getInstance().appendConsoleViewText("TRex daemon restarted correctly.");
                    LOG.info("TRex daemon restarted correctly.");
                } else {
                    LogsController.getInstance().appendConsoleViewText("Error while restarting daemon");
                    LOG.info("Error while restarting daemon");
                }
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
}
