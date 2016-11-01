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

import com.exalttech.trex.stateful.models.trexlatencyv2.HistogramItem;
import com.exalttech.trex.stateful.service.AsyncResponse;
import com.exalttech.trex.stateful.service.LoadDataService;
import com.exalttech.trex.stateful.utilities.Constants;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author BassamJ
 */
public class LatencyV2HistogramChartController implements Initializable {

    private static final Logger LOG = Logger.getLogger(LatencyV2HistogramChartController.class.getName());

    private static final int MAX_DATA_POINTS = 20;
    @FXML
    private AreaChart latencyV2HistogramChart;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    private XYChart.Series<Number, Number> histogramSeries = new XYChart.Series<>();
    private LoadDataService dataService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Testing " + getClass());
        initializeChart();
    }

    private void initializeChart() {
        xAxis.setLowerBound(0);
        xAxis.setUpperBound(MAX_DATA_POINTS);
        xAxis.setForceZeroInRange(false);
        xAxis.setAutoRanging(true);
        xAxis.setMinorTickVisible(false);
        xAxis.setTickUnit(3);

        yAxis.setAutoRanging(true);
        latencyV2HistogramChart.setCreateSymbols(false);
        latencyV2HistogramChart.setLegendVisible(false);
        latencyV2HistogramChart.setAnimated(false);
        latencyV2HistogramChart.setHorizontalGridLinesVisible(true);

        histogramSeries.setName("latency history");
        latencyV2HistogramChart.getData().add(histogramSeries);
        initializeService();
    }

    /**
     * @param chartItems
     */
    public void updateChartData(List<HistogramItem> chartItems) {
        //remove old data before adding new data
        histogramSeries.getData().clear();

        for (int i = 0; i < chartItems.size(); i++) {
            histogramSeries.getData().add(new XYChart.Data(chartItems.get(i).getKey(), chartItems.get(i).getVal()));
        }
    }

    /**
     *
     */
    public void initializeService() {
        dataService = new LoadDataService();

        dataService.setPeriod(Duration.seconds(Constants.REFRESH_TWO_INTERVAL_SECONDS));
        dataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    if (AsyncResponse.getInstance().getRxCheckObject() != null) {
                        if (AsyncResponse.getInstance().isServerCrashed()) {
                            histogramSeries.getData().clear();
                        } else {
                            if (!AsyncResponse.getInstance().getTrexLatencyV2Object().getData().getPortProperty().isEmpty()) {
                                updateChartData(AsyncResponse.getInstance().getTrexLatencyV2Object().getData().getPortProperty().get(0).getHist().getHistogram());
                            } else {
                                LOG.info("No data returned !");
                            }
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
            histogramSeries.getData().clear();
            dataService.cancel();
        } catch (Exception e) {
            LOG.error("Error:" + e);
        }
    }
}
