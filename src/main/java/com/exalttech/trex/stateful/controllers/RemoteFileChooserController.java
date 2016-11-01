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

import com.exalttech.trex.stateful.models.connection.AppLocalConnections;
import com.exalttech.trex.stateful.service.RPCConnections;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

/**
 * @author BassamJ
 */
public class RemoteFileChooserController implements Initializable {

    private static final Logger LOG = Logger.getLogger(RemoteFileChooserController.class.getName());

    @FXML
    private TextField pathTextField;

    @FXML
    private Button okButton;

    @FXML
    private VBox filesListContainer;

    private Stack<String> path = new Stack<>();

    private String fileName;

    private String errorMessage = "Error: ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Testing: " + getClass());
        Task<List> task = new Task<List>() {
            @Override
            public List call() throws Exception {
                try {
                    return RPCConnections.getInstance().getFileListForPath(".");
                } catch (Exception e) {
                    LOG.error(errorMessage, e);
                    return new ArrayList<>();
                }
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ArrayList list = (ArrayList) task.getValue();
                if (list != null && !list.isEmpty()) {
                    updateFolderList((ArrayList<String>) list.get(0));
                    updateFilesList(filterYamlFiles((ArrayList<String>) list.get(1)));
                } else {
                    LOG.error("error while loading file list from remote file chooser!");
                }
            }
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

        okButton.setOnMouseClicked(event -> handleOKButton());
        filesListContainer.setStyle("-fx-background-color: #FFF");
    }

    @FXML
    private void handleOKButton() {
        String filePath = pathTextField.getText();
        if (!"".equals(filePath)) {
            // the value should be returned to the previous stage.
            if (filePath.endsWith(getFileName())) {
                //in this case we are sure that the path is selected, not manually added
                RPCConnections.getInstance().addFileToYamlList(filePath, getFileName());
                //add selected yaml file for connection profile
                AppLocalConnections.getInstance().addProfileToIpProfiles(
                        RPCConnections.getInstance().getIpAddress(),
                        filePath,
                        getFileName()
                );
            } else {
                // if the path is manually added to the text field we need to parse the file name
                String[] directories = filePath.split("/");
                RPCConnections.getInstance().addFileToYamlList(filePath, directories[directories.length - 1]);
                AppLocalConnections.getInstance().addProfileToIpProfiles(
                        RPCConnections.getInstance().getIpAddress(),
                        filePath,
                        directories[directories.length - 1]
                );
            }
            // close the current stage
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();
        }
    }

    /**
     *
     */
    @FXML
    public void goToRootDirectory() {
        pathTextField.setText("");
        path.clear();
        clearItemList();
        Task<List> task = new Task<List>() {
            @Override
            public List call() throws Exception {
                try {
                    return RPCConnections.getInstance().getFileListForPath(".");
                } catch (Exception e) {
                    LOG.error(errorMessage, e);
                    return new ArrayList();
                }
            }
        };
        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ArrayList list = (ArrayList) task.getValue();
                if (list != null && !list.isEmpty()) {
                    updateFolderList((ArrayList<String>) list.get(0));
                    updateFilesList(filterYamlFiles((ArrayList<String>) list.get(1)));
                } else {
                    LOG.error("error while loading file list from remote file chooser!");
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
    public void goToParentDirectory() {
        pathTextField.setText("");
        if (!path.isEmpty()) {
            path.pop();
        }
        clearItemList();
        Task<List> task = new Task<List>() {
            @Override
            public List call() throws Exception {
                try {
                    return RPCConnections.getInstance().getFileListForPath(generateFilePath());
                } catch (Exception e) {
                    LOG.error(errorMessage, e);
                    return new ArrayList();
                }
            }
        };

        task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                ArrayList list = (ArrayList) task.getValue();
                if (list != null && !list.isEmpty()) {
                    updateFolderList((ArrayList<String>) list.get(0));
                    updateFilesList(filterYamlFiles((ArrayList<String>) list.get(1)));
                } else {
                    LOG.error("error while loading file list from remote file chooser!");
                }
            }
        });
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    private void clearItemList() {
        filesListContainer.getChildren().clear();
    }

    private void updateFolderList(ArrayList<String> foldersList) {
        for (String file : foldersList) {
            filesListContainer.getChildren().add(getListItemContainer(file, true));
        }
    }

    private void updateFilesList(ArrayList<String> filesList) {
        for (String file : filesList) {
            filesListContainer.getChildren().add(getListItemContainer(file, false));
        }
    }

    private HBox getListItemContainer(String itemName, Boolean isFolder) {
        HBox itemContainer = new HBox();
        itemContainer.setSpacing(10);
        itemContainer.setMinHeight(30);
        itemContainer.setPrefHeight(30);
        itemContainer.setMaxWidth(Double.MAX_VALUE);
        itemContainer.setCursor(Cursor.HAND);
        itemContainer.setStyle("-fx-background-color: #FFF");

        Label fileNameLabel = new Label(itemName);
        fileNameLabel.setMaxWidth(Double.MAX_VALUE);
        fileNameLabel.setMaxHeight(Double.MAX_VALUE);
        fileNameLabel.setAlignment(Pos.BASELINE_LEFT);
        fileNameLabel.setStyle("-fx-background-color: #FFF");
        HBox.setHgrow(fileNameLabel, Priority.ALWAYS);

        ImageView icon = new ImageView();
        if (isFolder) {
            icon.setImage(new Image("/images/folder_icon.png"));
        } else {
            icon.setImage(new Image("/images/file_icon.png"));
        }
        icon.setFitWidth(24);
        icon.setFitHeight(24);
        HBox.setMargin(icon, new Insets(0, 0, 0, 20));
        itemContainer.getChildren().addAll(icon, fileNameLabel);
        if (isFolder) {
            // if we have a folder , we need to navigate through the folder
            // send another RPC request to get_file_list API to load new list of items
            itemContainer.setOnMouseClicked(event -> {
                path.push(fileNameLabel.getText());
                Task<List> task = new Task<List>() {
                    @Override
                    public List call() throws Exception {
                        try {
                            return RPCConnections.getInstance().getFileListForPath(generateFilePath());
                        } catch (Exception e) {
                            LOG.error(errorMessage, e);
                            return new ArrayList();
                        }
                    }
                };
                task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent event) {
                        ArrayList list = (ArrayList) task.getValue();
                        if (list != null && !list.isEmpty()) {
                            clearItemList();
                            updateFolderList((ArrayList<String>) list.get(0));
                            updateFilesList(filterYamlFiles((ArrayList<String>) list.get(1)));
                        } else {
                            LOG.error("error while loading file list from remote file chooser!");
                        }
                    }
                });
                Thread t = new Thread(task);
                t.setDaemon(true);
                t.start();
            });
        } else {
            //display selected file path into textfield
            itemContainer.setOnMouseClicked(event -> {
                setFileName(fileNameLabel.getText());
                pathTextField.setText(generateFilePath() + fileNameLabel.getText());
            });
        }

        return itemContainer;
    }

    //Generate File Path from Stack
    private String generateFilePath() {
        StringBuilder filePath = new StringBuilder();
        filePath.append("./");
        for (String directory : path) {
            filePath.append(directory + "/");
        }
        return filePath.toString();
    }

    /**
     * @return
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private ArrayList filterYamlFiles(ArrayList<String> filesList) {
        ArrayList<String> yamlFilesList = new ArrayList<>();
        for (int i = 0; i < filesList.size(); i++) {
            if (filesList.get(i).toString().contains(".yaml")) {
                yamlFilesList.add(filesList.get(i).toString());
            }
        }
        return yamlFilesList;
    }
}
