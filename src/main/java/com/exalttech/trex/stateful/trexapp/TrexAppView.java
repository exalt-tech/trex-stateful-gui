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

package com.exalttech.trex.stateful.trexapp;

import com.exalttech.trex.stateful.models.connection.AppLocalConnections;
import com.exalttech.trex.stateful.models.connection.ConnectionsWrapper;
import com.exalttech.trex.stateful.service.RPCConnections;
import com.exalttech.trex.stateful.utilities.Constants;
import com.exalttech.trex.stateful.utilities.LocalXMLFileManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;

/**
 * @author BassamJ
 */
public class TrexAppView extends Application {

    private static final Logger LOG = Logger.getLogger(TrexAppView.class.getName());
    KeyCombination closeApplicationCombination = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
    private Stage window;
    private Scene layout;
    private String errorMessage = "Error: ";

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        speedupTooltip();
        BasicConfigurator.configure();
        this.window = primaryStage;
        window.setTitle(Constants.APPLICATION_TITLE);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/TrexAppView.fxml"));
        Parent root = loader.load();
        this.layout = new Scene(root, 800, 500);
        window.getIcons().add(new Image("/images/trex.png"));
        window.setMinWidth(1200);
        window.setMinHeight(800);
        window.setResizable(true);
        window.setScene(layout);
        window.setMaximized(true);
        window.setOnCloseRequest(e -> {
            if (RPCConnections.getInstance().isTrexRunning()) {
                RPCConnections.getInstance().stopTrexProcess();
                RPCConnections.getInstance().stopTrexDaemon();
            }
            ConnectionsWrapper wrapper = new ConnectionsWrapper();
            wrapper.setConnectionList(AppLocalConnections.getInstance().getConnectionList());
            LocalXMLFileManager.saveXML("TrexViewer.xml", wrapper, ConnectionsWrapper.class);
            try {
                stop();
                Platform.exit();
            } catch (Exception ex) {
                LOG.error(errorMessage, ex);
            }
        });
        initializeCombination();
        window.show();

    }

    /**
     * Speeding up displaying tootlip for JDK 8 ref:
     * http://stackoverflow.com/questions/26854301/control-javafx-tooltip-delay
     */
    private void speedupTooltip() {
        try {
            Tooltip tooltip = new Tooltip();
            Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
            fieldBehavior.setAccessible(true);
            Object objBehavior = fieldBehavior.get(tooltip);

            Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
            fieldTimer.setAccessible(true);
            Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

            objTimer.getKeyFrames().clear();
            objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
        } catch (Exception ex) {
            LOG.error(errorMessage, ex);
        }
    }


    private void initializeCombination() {
        window.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (closeApplicationCombination.match(event)) {
                window.close();
            }
        });

    }

}
