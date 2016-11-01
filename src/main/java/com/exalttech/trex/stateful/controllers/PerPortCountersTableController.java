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
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author BassamJ
 */
public class PerPortCountersTableController implements Initializable {

    private static final Logger LOG = Logger.getLogger(PerPortCountersTableController.class.getName());
    @FXML
    private TableView perPortCountersTable;

    private LoadDataService dataService;

    private boolean isFirstRequest = true;

    private String errorMessage = "Error: ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Testing Controller :" + getClass());
        perPortCountersTable.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) {
                TableHeaderRow header = (TableHeaderRow) perPortCountersTable.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });

        perPortCountersTable.setEditable(false);
        initializeService();
    }

    private void initializeService() {
        dataService = new LoadDataService();

        dataService.setPeriod(Duration.seconds(Constants.REFRESH_ONE_INTERVAL_SECONDS));
        dataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
                    if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
                        if (AsyncResponse.getInstance().isServerCrashed()) {
                            perPortCountersTable.getItems().clear();
                        } else {
                            if (isFirstRequest) {
                                initializeTable();
                                isFirstRequest = false;
                            }
                            updatePerPortCounterTableData();
                        }
                    }
                } catch (Exception ex) {
                    LOG.error(errorMessage, ex);
                }
            }
        });

        dataService.setOnFailed(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                LOG.trace("Error is occurred during running the server");
            }
        });
    }


    /**
     * Scheduler service that update UI every period of time
     */
    public void startPerPortCounterTask() {
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
    public void stopPerPortCounterTask() {
        try {
            clearTableData();
            dataService.cancel();
        } catch (Exception ex) {
            LOG.error(errorMessage, ex);
        }
    }

    /**
     *
     */
    public void initializeTable() {
        List<String> columns = getColumnList();

        for (int i = 0; i < columns.size(); i++) {
            final int j = i;
            TableColumn col = new TableColumn(columns.get(i));

            col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                    try {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    } catch (Exception ex) {
                        LOG.error(errorMessage, ex);
                        return new SimpleStringProperty("");
                    }
                }
            });

            col.setSortable(false);
            col.setMinWidth(100);
            if (i != 0) {
                col.setId("alignedColumn");
            }
            perPortCountersTable.getColumns().addAll(col);
        }
        TableHeaderRow header = (TableHeaderRow) perPortCountersTable.lookup("TableHeaderRow");
        header.setVisible(true);
    }

    /**
     * clear the table data
     */
    public void clearTableData() {
        isFirstRequest = true;
        perPortCountersTable.getItems().clear();
        perPortCountersTable.getColumns().clear();
        TableHeaderRow header = (TableHeaderRow) perPortCountersTable.lookup("TableHeaderRow");
        header.setVisible(false);
    }

    /**
     * this method update the data inside per port counters table dynamically
     * this method also re-create table if required
     * if number of interfaces change we need to re-create the table otherwise we need only to refresh the table
     */
    public void updatePerPortCounterTableData() {
        perPortCountersTable.getItems().clear();
        perPortCountersTable.setItems(
                FXCollections.observableArrayList(
                        getOPacketsInterfacesData(),
                        getOBytesInterfacesData(),
                        getIPacketsInterfacesData(),
                        getIBytesInterfacesData(),
                        getIErrorsInterfacesData(),
                        getOErrorsInterfacesData()
                )
        );
    }

    /**
     * create dynamic row for opackets and return observable list of elements inside that row
     *
     * @return
     */
    private ObservableList<String> getOPacketsInterfacesData() {
        ObservableList<String> oPacketsRow = FXCollections.observableArrayList();
        oPacketsRow.add("opackets");
        if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
            int numOfInterfaces = AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().size();
            for (int i = 0; i < numOfInterfaces; i++) {
                oPacketsRow.add(
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().get(i).getOpackets().toString()
                );
            }
        }
        return oPacketsRow;
    }

    /**
     * create dynamic row for oBytes and return observable list of elements inside that row
     *
     * @return
     */
    private ObservableList<String> getOBytesInterfacesData() {
        ObservableList<String> oBytesRow = FXCollections.observableArrayList();
        oBytesRow.add("obytes");
        if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
            int numOfInterfaces = AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().size();
            for (int i = 0; i < numOfInterfaces; i++) {
                oBytesRow.add(
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().get(i).getObytes().toString()
                );
            }
        }
        return oBytesRow;
    }

    /**
     * create dynamic row for ipackets and return observable list of elements inside that row
     *
     * @return
     */
    private ObservableList<String> getIPacketsInterfacesData() {
        ObservableList<String> iPacketsRow = FXCollections.observableArrayList();
        iPacketsRow.add("ipackets");
        if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
            int numOfInterfaces = AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().size();
            for (int i = 0; i < numOfInterfaces; i++) {
                iPacketsRow.add(
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().get(i).getIpackets().toString()
                );
            }
        }
        return iPacketsRow;
    }

    /**
     * create dynamic row for iBytes and return observable list of elements inside that row
     *
     * @return
     */
    private ObservableList<String> getIBytesInterfacesData() {
        ObservableList<String> iBytesRow = FXCollections.observableArrayList();
        iBytesRow.add("ibytes");
        if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
            int numOfInterfaces = AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().size();
            for (int i = 0; i < numOfInterfaces; i++) {
                iBytesRow.add(
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().get(i).getIbytes().toString()
                );
            }
        }
        return iBytesRow;
    }

    /**
     * create dynamic row for iErrors and return observable list of elements inside that row
     *
     * @return
     */
    private ObservableList<String> getIErrorsInterfacesData() {
        ObservableList<String> iErrorsRow = FXCollections.observableArrayList();
        iErrorsRow.add("ierrors");
        if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
            int numOfInterfaces = AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().size();
            for (int i = 0; i < numOfInterfaces; i++) {
                iErrorsRow.add(
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().get(i).getIerrors().toString()
                );
            }
        }
        return iErrorsRow;
    }

    /**
     * create dynamic row for oErrors and return observable list of elements inside that row
     *
     * @return
     */
    private ObservableList<String> getOErrorsInterfacesData() {
        ObservableList<String> oErrorsRow = FXCollections.observableArrayList();
        oErrorsRow.add("oerrors");
        if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
            int numOfInterfaces = AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().size();
            for (int i = 0; i < numOfInterfaces; i++) {
                oErrorsRow.add(
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().get(i).getOerrors().toString()
                );
            }
        }
        return oErrorsRow;
    }

    /**
     * return the columns of per port counter table depending on the number of interfaces in the response
     *
     * @return
     */
    private List<String> getColumnList() {
        List<String> columns = new ArrayList<String>();
        // First column always called counter
        columns.add("Counter");

        //check if there is a current object inside the data model
        if (AsyncResponse.getInstance().getTrexGlobalObject() != null) {
            int numOfInterfaces = AsyncResponse.getInstance().getTrexGlobalObject().getData().getPortProperties().size();
            for (int i = 0; i < numOfInterfaces; i++) {
                columns.add("If-" + i);
            }
        }
        return columns;
    }

}
