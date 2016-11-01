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

import com.exalttech.trex.stateful.models.appearancesettings.AppearanceSettings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author BassamJ
 */
public class AppearanceSettingsViewController implements Initializable {

    private static final Logger LOG = Logger.getLogger(AppearanceSettingsViewController.class.getName());

    @FXML
    private CheckBox globalCounterCheckBox;

    @FXML
    private CheckBox perPortCountersCheckBox;

    @FXML
    private CheckBox perTemplateWindowCheckBox;

    @FXML
    private CheckBox latencyCheckBox;

    @FXML
    private CheckBox rxHistogramCheckBox;

    @FXML
    private CheckBox latencyHistogramCheckBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Initialize : " + getClass());
        globalCounterCheckBox.setSelected(AppearanceSettings.getInstance().isGlobalCountersVisible());
        perPortCountersCheckBox.setSelected(AppearanceSettings.getInstance().isPerPortCountersVisible());
        perTemplateWindowCheckBox.setSelected(AppearanceSettings.getInstance().isPerWindowTemplateVisible());
        latencyCheckBox.setSelected(AppearanceSettings.getInstance().isLatencyChartVisible());
        rxHistogramCheckBox.setSelected(AppearanceSettings.getInstance().isRxHistogramChartVisible());
        latencyHistogramCheckBox.setSelected(AppearanceSettings.getInstance().isLatencyHistogramChartVisible());
    }

    /**
     *
     */
    @FXML
    public void saveAppearanceSettingsWindow() {
        LOG.info("Saving appearance settings ...");
        AppearanceSettings.getInstance().setGlobalCountersVisible(globalCounterCheckBox.isSelected());
        AppearanceSettings.getInstance().setPerPortCountersVisible(perPortCountersCheckBox.isSelected());
        AppearanceSettings.getInstance().setPerWindowTemplateVisible(perTemplateWindowCheckBox.isSelected());
        AppearanceSettings.getInstance().setLatencyChartVisible(latencyCheckBox.isSelected());
        AppearanceSettings.getInstance().setRxHistogramChartVisible(rxHistogramCheckBox.isSelected());
        AppearanceSettings.getInstance().setLatencyHistogramChartVisible(latencyHistogramCheckBox.isSelected());
        closeAppearanceSettingsWindow();
    }

    /**
     *
     */
    @FXML
    public void closeAppearanceSettingsWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
