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
import com.exalttech.trex.stateful.service.AsyncResponse;
import com.exalttech.trex.stateful.service.RPCConnections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author BassamJ
 */
public class EditCommandWindowController implements Initializable {

    private static final Logger LOG = Logger.getLogger(EditCommandWindowController.class.getName());

    @FXML
    private TextArea commandTextArea;

    @FXML
    private Button cancelButton;

    @FXML
    private Button executeButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Initialize : " + getClass());
        cancelButton.setOnAction(e -> cancelButtonAction());
        executeButton.setOnAction(e -> executeButtonAction());
    }

    /**
     * @param command
     */
    public void setCommandAreaTextValue(String command) {
        commandTextArea.setText(command);
    }

    private void cancelButtonAction() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void executeButtonAction() {
        // first we should parse the command , then create a hashMap of key=value pairs
        String command = commandTextArea.getText();
        command = command.trim().replaceAll(" +", " ");
        String[] parameters = command.split(" ");
        Map<String, Object> trexCmdOptions = new HashMap<String, Object>();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].charAt(0) == '-') {
                if (i + 1 < parameters.length && parameters[i + 1].charAt(0) != '-') {
                    trexCmdOptions.put(parameters[i].replaceAll("^-+", ""), parameters[i + 1]);
                    i++;
                } else {
                    trexCmdOptions.put(parameters[i].replaceAll("^-+", ""), true);
                }
            }
        }
        LogsController.getInstance().appendConsoleViewText(command.toString());
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                AsyncResponse.getInstance().setCloseControlWindow(!AsyncResponse.getInstance().getCloseControlWindow());
                RPCConnections.getInstance().handleNewTrexCommand(trexCmdOptions);
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        cancelButtonAction();
    }


}
