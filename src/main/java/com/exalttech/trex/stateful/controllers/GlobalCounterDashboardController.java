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

import com.exalttech.trex.stateful.service.AsyncResponse;
import com.exalttech.trex.stateful.service.LoadDataService;
import com.exalttech.trex.stateful.utilities.Constants;
import com.exalttech.trex.stateful.utilities.Util;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import jfxtras.labs.scene.control.gauge.linear.SimpleMetroArcGauge;
import jfxtras.labs.scene.control.gauge.linear.elements.PercentSegment;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author BassamJ
 */
public class GlobalCounterDashboardController implements Initializable {

    private static final Logger LOG = Logger.getLogger(GlobalCounterDashboardController.class.getName());

    private SimpleMetroArcGauge simpleMetroArcGauge;

    @FXML
    private Pane gaugeContainer;

    @FXML
    private Label totalDropRXLabel;

    @FXML
    private Label expectedBPSLabel;

    @FXML
    private Label measuredBPSLabel;

    @FXML
    private Label percentExpectedBPSLabel;

    @FXML
    private Label expectedPPSLabel;

    @FXML
    private Label measuredPPSLabel;

    @FXML
    private Label percentExpectedPPSLabel;

    @FXML
    private Label expectedCPSLabel;

    @FXML
    private Label measuredCPSLabel;

    @FXML
    private Label percentExpectedCPSLabel;

    @FXML
    private Label activeFlowsLabel;

    @FXML
    private Label openFlowsLabel;

    @FXML
    private Label activeflowsFieldLabel;


    @FXML
    private Label firstValueTotalTXLabel;

    @FXML
    private Label secondValueTotalTXLabel;

    @FXML
    private Label totalRxPpsLabel;

    @FXML
    private Label totalRxBpsLabel;

    private LoadDataService dataService;

    private String errorMessage = "Error: ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Initialize : " + getClass());
        initializeGauge();
        initializeService();
    }

    private void initializeService() {
        dataService = new LoadDataService();

        dataService.setPeriod(Duration.seconds(Constants.REFRESH_HALF_INTERVAL_SECONDS));
        dataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
                        if (AsyncResponse.getInstance().isServerCrashed()) {
                            resetDashboard();
                        } else {
                            updateDashBoard();
                        }
                    }
                } catch (Exception ex) {
                    LOG.error("No data returned", ex);
                }
            }
        });

        dataService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                LOG.trace("Error is occurred");
            }
        });
    }

    /**
     *
     */
    public void startDashboardTask() {
        dataService.reset();
        if (!dataService.isRunning()) {
            dataService.start();
        } else {
            dataService.restart();
        }
    }

    /**
     *
     */
    public void stopDashboardTask() {
        try {
            dataService.cancel();
            resetDashboard();
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    private void updateDashBoard() {
        updateGaugeSegmentsAndColoring(
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMCpuUtil().doubleValue()
        );

        updateDashboardView(
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMRxDropBps().doubleValue()
        );

        updateBpsCounters(
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxExpectedBps().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxBps().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxBps().doubleValue() * 100 /
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxExpectedBps().doubleValue()
        );

        updatePpsCounters(
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxExpectedPps().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxPps().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxPps().doubleValue() * 100 /
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxExpectedPps().doubleValue()
        );

        updateCpsCounters(
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxExpectedCps().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxCps().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxCps().doubleValue() * 100 /
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxExpectedCps().doubleValue()
        );
        updateFlowCounters(
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMActiveFlows().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMOpenFlows().doubleValue()
        );
        updateTotalTxCounters(
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxBps().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMTxPps().doubleValue()
        );

        updateTotalRxCounters(
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMRxBps().doubleValue(),
                AsyncResponse.getInstance().getTrexGlobalObject().getData().getMRxPps().doubleValue()
        );

    }


    private void initializeGauge() {
        simpleMetroArcGauge = new SimpleMetroArcGauge();
        simpleMetroArcGauge.setId("cpuIndicator");
        simpleMetroArcGauge.setMinValue(0.0);
        simpleMetroArcGauge.setMaxValue(1.0);
        simpleMetroArcGauge.setMaxHeight(100);
        gaugeContainer.getChildren().add(simpleMetroArcGauge);
    }

    /**
     * @param value
     */
    public void updateGaugeSegmentsAndColoring(double value) {
        simpleMetroArcGauge.segments().clear();
        simpleMetroArcGauge.getStyleClass().removeAll("colorscheme-red-to-grey-2", "colorscheme-green-to-grey-2");
        if (value >= 90) {
            simpleMetroArcGauge.getStyleClass().add("colorscheme-red-to-grey-2");
        } else {
            simpleMetroArcGauge.getStyleClass().add("colorscheme-green-to-grey-2");
        }
        simpleMetroArcGauge.setValue(value / 100);
        simpleMetroArcGauge.segments().add(new PercentSegment(simpleMetroArcGauge, 0.0, value));
        simpleMetroArcGauge.segments().add(new PercentSegment(simpleMetroArcGauge, value, 100.0));
    }

    /**
     * @param rxdropBps
     */
    public void updateDashboardView(double rxdropBps) {
        // if drop > 1 change color to red
        totalDropRXLabel.setText(Util.formattedData(rxdropBps) + "bps");
        if (rxdropBps > 1) {
            totalDropRXLabel.setTextFill(Color.RED);
        } else {
            totalDropRXLabel.setTextFill(Color.BLACK);
        }
    }

    /**
     * @param expectedBps
     * @param measuredBps
     * @param percentExpectedBps
     */
    public void updateBpsCounters(double expectedBps, double measuredBps, double percentExpectedBps) {
        expectedBPSLabel.setText(Util.formattedData(expectedBps) + "bps");
        measuredBPSLabel.setText(Util.formattedData(measuredBps) + "bps");
        percentExpectedBPSLabel.setText(Math.round(percentExpectedBps) + "");
    }

    /**
     * @param expectedPps
     * @param measuredPps
     * @param percentExpectedPps
     */
    public void updatePpsCounters(double expectedPps, double measuredPps, double percentExpectedPps) {
        expectedPPSLabel.setText(Util.formattedData(expectedPps) + "pps");
        measuredPPSLabel.setText(Util.formattedData(measuredPps) + "pps");
        percentExpectedPPSLabel.setText(Math.round(percentExpectedPps) + "");
    }

    /**
     * @param expectedCps
     * @param measuredCps
     * @param percentExpectedCps
     */
    public void updateCpsCounters(double expectedCps, double measuredCps, double percentExpectedCps) {
        expectedCPSLabel.setText(Util.formattedData(expectedCps) + "cps");
        measuredCPSLabel.setText(Util.formattedData(measuredCps) + "cps");
        percentExpectedCPSLabel.setText(Math.round(percentExpectedCps) + "");
    }

    /**
     * @param activeFlows
     * @param openFlows
     */
    public void updateFlowCounters(double activeFlows, double openFlows) {
        activeflowsFieldLabel.setText((int) activeFlows + "");
        activeFlowsLabel.setText(Util.formattedData(activeFlows));
        openFlowsLabel.setText((int) openFlows + "");
    }

    /**
     * @param firstTotalTx
     * @param secondTotalTx
     */
    public void updateTotalTxCounters(double firstTotalTx, double secondTotalTx) {
        firstValueTotalTXLabel.setText(Util.formattedData(firstTotalTx) + "bps");
        secondValueTotalTXLabel.setText(Util.formattedData(secondTotalTx) + "pps");
    }

    /**
     * @param firstTotalRx
     * @param secondTotalRx
     */
    public void updateTotalRxCounters(double firstTotalRx, double secondTotalRx) {
        totalRxBpsLabel.setText(Util.formattedData(firstTotalRx) + "bps");
        totalRxPpsLabel.setText(Util.formattedData(secondTotalRx) + "pps");
    }

    /**
     *
     */
    public void resetDashboard() {
        updateGaugeSegmentsAndColoring(0);
        expectedBPSLabel.setText("");
        measuredBPSLabel.setText("");
        percentExpectedBPSLabel.setText("");

        expectedPPSLabel.setText("");
        measuredPPSLabel.setText("");
        percentExpectedPPSLabel.setText("");

        expectedCPSLabel.setText("");
        measuredCPSLabel.setText("");
        percentExpectedCPSLabel.setText("");

        activeflowsFieldLabel.setText("");
        activeFlowsLabel.setText("");
        openFlowsLabel.setText("");

        firstValueTotalTXLabel.setText("");
        secondValueTotalTXLabel.setText("");
        totalDropRXLabel.setText("");

        totalRxBpsLabel.setText("");
        totalRxPpsLabel.setText("");
    }
}
