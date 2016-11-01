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

package com.exalttech.trex.stateful.log;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * @author BassamJ
 */
public class ConsoleLogView extends AnchorPane {

    TextArea logsContent;

    public ConsoleLogView() {
        setTopAnchor(this, 0d);
        setLeftAnchor(this, 0d);
        setBottomAnchor(this, 0d);
        setRightAnchor(this, 0d);
        buildUI();
    }

    private void buildUI() {
        logsContent = new TextArea();
        logsContent.setWrapText(true);
        logsContent.getStyleClass().add("consoleLogsContainer");
        setTopAnchor(logsContent, 0d);
        setLeftAnchor(logsContent, 0d);
        setBottomAnchor(logsContent, 0d);
        setRightAnchor(logsContent, 0d);
        logsContent.textProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
                logsContent.setScrollTop(Double.MAX_VALUE);
            }
        });
        getChildren().add(logsContent);
    }

    /**
     * @param textToAppend
     */
    public void append(String textToAppend) {
        if (textToAppend != null) {

            String msgToAdd = textToAppend + "\n";
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    logsContent.appendText(msgToAdd);
                }
            });

        }
    }

    /**
     *
     */
    public void clear() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                logsContent.setText("");
            }
        });
    }
}
