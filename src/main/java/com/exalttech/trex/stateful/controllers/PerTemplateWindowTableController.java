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

import com.exalttech.trex.stateful.models.templateinfo.TemplateInfoTable;
import com.exalttech.trex.stateful.service.AsyncResponse;
import com.exalttech.trex.stateful.service.LoadDataService;
import com.exalttech.trex.stateful.utilities.Constants;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import javafx.beans.property.SimpleLongProperty;
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
import javafx.util.Duration;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author BassamJ
 */
public class PerTemplateWindowTableController implements Initializable {

    private static final Logger LOG = Logger.getLogger(PerTemplateWindowTableController.class.getName());

    @FXML
    private TableView<TemplateInfoTable> templateInfoTableTable;

    @FXML
    private TableColumn<TemplateInfoTable, String> nameColumn;

    @FXML
    private TableColumn<TemplateInfoTable, Long> txPktsColumn;

    @FXML
    private TableColumn<TemplateInfoTable, Long> rxPktsColumn;

    @FXML
    private TableColumn<TemplateInfoTable, Long> rxErrorsColumn;

    @FXML
    private TableColumn<TemplateInfoTable, Long> jitterColumn;

    private ObservableList<TemplateInfoTable> templateTableData = FXCollections.observableArrayList();

    private LoadDataService dataService;

    private String errorMessage = "Error: ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Testing " + getClass());

        templateInfoTableTable.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth) {
                TableHeaderRow header = (TableHeaderRow) templateInfoTableTable.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });
        templateInfoTableTable.setEditable(false);
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        txPktsColumn.setCellValueFactory(cellData -> cellData.getValue().txPktsProperty().asObject());
        rxPktsColumn.setCellValueFactory(cellData -> cellData.getValue().rxPktsProperty().asObject());
        rxErrorsColumn.setCellValueFactory(cellData -> cellData.getValue().rxErrorsProperty().asObject());
        jitterColumn.setCellValueFactory(cellData -> cellData.getValue().jitterProperty().asObject());
        initializeService();
    }

    private void initializeService() {
        dataService = new LoadDataService();
        dataService.setPeriod(Duration.seconds(Constants.REFRESH_ONE_INTERVAL_SECONDS));
        dataService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                try {
//                    && AsyncResponse.getInstance().getRxCheckObject() != null
                    if (AsyncResponse.getInstance().getTemplateInfoObject() != null && AsyncResponse.getInstance().getTrexGlobalObject() != null) {
                        if (AsyncResponse.getInstance().isServerCrashed()) {
                            templateInfoTableTable.getItems().clear();
                        } else {
                            updatePerTemplateWindowTable();
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
     *
     */
    public void startPerTemplateWindowTask() {
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
    public void stopPerTemplateWindowTask() {
        try {
            dataService.cancel();
            templateInfoTableTable.getItems().removeAll(templateInfoTableTable.getItems());
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    public void updatePerTemplateWindowTable() {
        templateInfoTableTable.getItems().removeAll(templateInfoTableTable.getItems());

        int numOfTemplate = AsyncResponse.getInstance().getTemplateInfoObject().getData().size();

        for (int i = 0; i < numOfTemplate; i++) {
            if (AsyncResponse.getInstance().getRxCheckObject() != null) {
                addNewItem(
                        AsyncResponse.getInstance().getTemplateInfoObject().getData().get(i),
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getTemplate().get(i),
                        AsyncResponse.getInstance().getRxCheckObject().getData().getTemplate().get(i).getRxPkts(),
                        AsyncResponse.getInstance().getRxCheckObject().getData().getTemplate().get(i).getVal(),
                        AsyncResponse.getInstance().getRxCheckObject().getData().getTemplate().get(i).getJitter()
                );

            } else {
                addNewItem(
                        AsyncResponse.getInstance().getTemplateInfoObject().getData().get(i),
                        AsyncResponse.getInstance().getTrexGlobalObject().getData().getTemplate().get(i),
                        0,
                        0,
                        0
                );
            }
        }
        templateInfoTableTable.setItems(getTemplateInfoData());
    }

    /**
     * @param templateName
     * @param txPkts
     * @param rxPkts
     * @param rxErrors
     * @param jitter
     */
    public void addNewItem(String templateName, long txPkts, long rxPkts, long rxErrors, long jitter) {

        templateTableData.add(new TemplateInfoTable(
                new SimpleStringProperty(templateName),
                new SimpleLongProperty(txPkts),
                new SimpleLongProperty(rxPkts),
                new SimpleLongProperty(rxErrors),
                new SimpleLongProperty(jitter)
        ));
    }

    /**
     * @return
     */
    public ObservableList<TemplateInfoTable> getTemplateInfoData() {
        return templateTableData;
    }
}
