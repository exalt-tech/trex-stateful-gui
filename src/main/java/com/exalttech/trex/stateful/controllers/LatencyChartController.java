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
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author BassamJ
 */
public class LatencyChartController implements Initializable {


    private static final Logger LOG = Logger.getLogger(LatencyChartController.class.getName());

    private static final int MAX_DATA_POINTS = 16;
    @FXML
    private LineChart latencyChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    private XYChart.Series<String, Number> clientSeries = new XYChart.Series<>();
    private XYChart.Series<String, Number> serverSeries = new XYChart.Series<>();
    private int xSeriesData = 0;

    private ConcurrentLinkedQueue<Number> clientDataQueue = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<Number> serverDataQueue = new ConcurrentLinkedQueue<>();

    private LoadDataService dataService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Testing " + getClass());
        initializeChart();
    }

    private void initializeChart() {

        xAxis.setAutoRanging(true);
        xAxis.setTickLabelGap(2);
        yAxis.setUpperBound(5);
        yAxis.setLowerBound(-5);
        yAxis.setAutoRanging(true);

        latencyChart.setCreateSymbols(false);
        latencyChart.setLegendVisible(false);
        latencyChart.setAnimated(false);
        latencyChart.setHorizontalGridLinesVisible(true);

        clientSeries.setName("client");
        serverSeries.setName("server");

        latencyChart.getData().addAll(clientSeries, serverSeries);
        initializeService();
    }

    /**
     *
     */
    public void initializeService() {
        dataService = new LoadDataService();

        dataService.setPeriod(Duration.seconds(Constants.REFRESH_HALF_INTERVAL_SECONDS));
        dataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    addDataToSeries();
                    if (AsyncResponse.getInstance().getTrexLatencyObject() != null) {
                        if (AsyncResponse.getInstance().isServerCrashed()) {
                            clientSeries.getData().clear();
                            serverSeries.getData().clear();
                            xSeriesData = 0;
                        } else {
                            // latency chart has two series one for the client which is the maximum cmax value for even ports
                            //while the server series is the maximum cmax for odd ports
                            double mClientLatency = 0;
                            double mServerLatency = 0;
                            double clientCMax;
                            double serverCMax;
                            int numOfPorts = AsyncResponse.getInstance().getTrexLatencyObject().getData().getPortProperty().size();
                            for (int i = 0; i < numOfPorts; i += 2) {
                                clientCMax = AsyncResponse.getInstance().getTrexLatencyObject().getData().getPortProperty().get(i).getCMax();
                                serverCMax = AsyncResponse.getInstance().getTrexLatencyObject().getData().getPortProperty().get(i + 1).getCMax();
                                if (clientCMax > mClientLatency) {
                                    mClientLatency = clientCMax;
                                }
                                if (serverCMax > mServerLatency) {
                                    mServerLatency = serverCMax;
                                }
                            }
                            clientDataQueue.add(mClientLatency);
                            serverDataQueue.add(mServerLatency);
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
     * Scheduler service that update UI every period of time
     */
    public void runChart() {
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
    public void stopChart() {
        try {
            clientSeries.getData().clear();
            serverSeries.getData().clear();
            xSeriesData = 0;
            dataService.cancel();
        } catch (Exception e) {
            LOG.error("Error:" + e);
        }
    }


    /**
     * Add point to series
     */
    private void addDataToSeries() {
        for (int i = 0; i < MAX_DATA_POINTS; i++) {
            if (clientDataQueue.isEmpty()) {
                break;
            }
            clientSeries.getData().add(new XYChart.Data<>((xSeriesData) + "", clientDataQueue.remove()));
            serverSeries.getData().add(new XYChart.Data<>((xSeriesData) + "", serverDataQueue.remove()));
            xSeriesData = (++xSeriesData) % 60;
        }
        // remove points to keep us at no more than MAX_DATA_POINTS
        if (clientSeries.getData().size() > MAX_DATA_POINTS) {
            clientSeries.getData().remove(0, clientSeries.getData().size() - MAX_DATA_POINTS);
        }
        if (serverSeries.getData().size() > MAX_DATA_POINTS) {
            serverSeries.getData().remove(0, serverSeries.getData().size() - MAX_DATA_POINTS);
        }
        yAxis.setUpperBound(5);
        yAxis.setLowerBound(-5);
    }

}
