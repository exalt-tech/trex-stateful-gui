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
import com.exalttech.trex.stateful.models.connection.AppLocalConnections;
import com.exalttech.trex.stateful.models.connection.Connection;
import com.exalttech.trex.stateful.models.connection.ConnectionsWrapper;
import com.exalttech.trex.stateful.models.connection.Profile;
import com.exalttech.trex.stateful.service.AsyncResponse;
import com.exalttech.trex.stateful.service.RPCConnections;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * @author BassamJ
 */
public class ControlWindowViewController implements Initializable {

    private static final Logger LOG = Logger.getLogger(ControlWindowViewController.class.getName());

    @FXML
    private ComboBox profileComboBox;

    @FXML
    private Button remoteFileChooserButton;

    @FXML
    private CheckBox latencyCheckBox;

    @FXML
    private TextField frequencyTextField;

    @FXML
    private TextField durationTextField;

    @FXML
    private TextField multiplierTextField;

    @FXML
    private CheckBox ipv6CheckBox;

    @FXML
    private CheckBox flowFlipCheckBox;

    @FXML
    private CheckBox rxCheckCheckBox;

    @FXML
    private TextField rxCheckTextField;

    @FXML
    private VBox optionsContainer;

    @FXML
    private VBox controlOptionsController;

    @FXML
    private Label advancedOptionsLabel;

    private HBox learnModeContainer;

    private HBox learnModeComboContainer;

    private HBox hopesContainer;

    private HBox limitPortsContainer;

    private HBox ioModeContainer;

    private HBox coresContainer;

    private CheckBox learnModeCheckBox;

    private ComboBox learnModeComboBox;

    private CheckBox hopesCheckBox;

    private TextField hopesTextField;

    private CheckBox limitPortsCheckBox;

    private TextField limitPortsTextField;

    private CheckBox ioModeCheckBox;

    private ComboBox ioModeComboBox;

    private CheckBox coresCheckBox;

    private TextField coresTextField;

    private Boolean isAdvancedOptionsOpened = false;

    private StringBuilder command;

    private Map<String, Object> trexCmdOptions;

    private ObservableList<String> yamlFileList = FXCollections.observableArrayList();

    @FXML
    private ImageView showHideIcon;

    @FXML
    private Button executeButton;

    private String errorMessage = "Error: ";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LOG.info("Initialize : " + getClass());

        initializeComponents();
        initializeControlOptions();
        initializeAdvancedOptions();
        addFotmattersToTextFields();
        initializeControlOptionsToolTips();
        command = new StringBuilder("");
        trexCmdOptions = new HashMap<>();
        RPCConnections.getInstance().executingCommandProperty().bindBidirectional(executeButton.disableProperty());

    }

    private void initializeControlOptions() {
        advancedOptionsLabel.setOnMouseClicked(event -> {
            if (!isAdvancedOptionsOpened) {
                optionsContainer.getChildren().addAll(learnModeContainer, learnModeComboContainer, hopesContainer,
                        limitPortsContainer, ioModeContainer, coresContainer);
                advancedOptionsLabel.setText("Hide Advanced options");
                isAdvancedOptionsOpened = true;
            } else {
                optionsContainer.getChildren().removeAll(learnModeContainer, learnModeComboContainer, hopesContainer,
                        limitPortsContainer, ioModeContainer, coresContainer);
                advancedOptionsLabel.setText("Show Advanced options");
                isAdvancedOptionsOpened = false;
            }
        });
    }

    private void initializeComponents() {
        latencyCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    frequencyTextField.setDisable(false);
                } else {
                    frequencyTextField.setDisable(true);
                }
            }
        });

        rxCheckCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    rxCheckTextField.setDisable(false);
                } else {
                    rxCheckTextField.setDisable(true);
                }
            }
        });
        remoteFileChooserButton.setOnMouseClicked(event -> openRemoteFileChooserWindow());
    }

    /**
     *
     */
    @FXML
    public void handleExecuteFunction() {
        if (generateTrexCommand()) {
            //initialize task handler when execute command
            Task task = new Task<Void>() {
                @Override
                public Void call() {
                    closeControlWindow();
                    RPCConnections.getInstance().handleNewTrexCommand(trexCmdOptions);
                    return null;
                }
            };
            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in execution");
            alert.setHeaderText(null);
            alert.setContentText("Error: No YAML file is selected");
            alert.showAndWait();
        }
    }

    private Boolean generateTrexCommand() {
        Boolean status = true;
        command.setLength(0);
        trexCmdOptions.clear();
        if ("".equals(profileComboBox.getValue())) {
            status = false;
        } else {
            String path = RPCConnections.getInstance().getFilePathFromMap((String) profileComboBox.getValue());
            command.append(" -f " + path);
            trexCmdOptions.put("f", path);
        }
        if (latencyCheckBox.isSelected()) {
            if ("".equals(frequencyTextField.getText())) {
                command.append(" -l 1000");
                trexCmdOptions.put("l", 1000);
            } else {
                command.append(" -l " + frequencyTextField.getText());
                trexCmdOptions.put("l", frequencyTextField.getText());
            }
        }
        if (durationTextField.getText() != null) {
            if ("".equals(durationTextField.getText())) {
                command.append(" -d 0");
                trexCmdOptions.put("d", 0);
            } else {
                command.append(" -d " + durationTextField.getText());
                trexCmdOptions.put("d", durationTextField.getText());
            }
        }
        if (multiplierTextField.getText() != null) {
            if ("".equals(multiplierTextField.getText())) {
                command.append(" -m 1");
                trexCmdOptions.put("m", 1);
            } else {
                command.append(" -m " + multiplierTextField.getText());
                trexCmdOptions.put("m", multiplierTextField.getText());
            }
        }
        if (ipv6CheckBox.isSelected()) {
            command.append(" --ipv6");
            trexCmdOptions.put("ipv6", true);
        }

        if (flowFlipCheckBox.isSelected()) {
            command.append(" -p");
            trexCmdOptions.put("p", true);
        }

        if (rxCheckCheckBox.isSelected() && rxCheckTextField.getText() != null) {
            if ("".equals(rxCheckTextField.getText())) {
                command.append(" --rx-check 1");
                trexCmdOptions.put("rx-check", 1);
            } else {
                command.append(" --rx-check " + rxCheckTextField.getText());
                trexCmdOptions.put("rx-check", rxCheckTextField.getText());
            }
        }

        if (isAdvancedOptionsOpened) {
            if (learnModeCheckBox.isSelected() && learnModeComboBox.getValue() != null) {
                command.append(" --learn-" + learnModeComboBox.getValue());
                trexCmdOptions.put("learn-mode", learnModeComboBox.getSelectionModel().getSelectedIndex());
            }

            if (hopesCheckBox.isSelected() && hopesTextField.getText() != null) {
                if ("".equals(hopesTextField.getText())) {
                    // Number of hops in the setup (default is one hop)
                    command.append(" --hops " + 1);
                    trexCmdOptions.put("hops", 1);
                } else {
                    command.append(" --hops " + hopesTextField.getText());
                    trexCmdOptions.put("hops", hopesTextField.getText());
                }
            }

            if (limitPortsCheckBox.isSelected() && limitPortsTextField.getText() != null) {
                if ("".equals(limitPortsTextField.getText())) {
                    command.append(" --limit-ports 4");
                    trexCmdOptions.put("limit-ports", 4);
                } else {
                    command.append(" --limit-ports " + limitPortsTextField.getText());
                    trexCmdOptions.put("limit-ports", limitPortsTextField.getText());
                }

            }

            if (ioModeCheckBox.isSelected() && ioModeComboBox.getValue() != null) {
                command.append(" --iom " + ioModeComboBox.getValue());
                trexCmdOptions.put("iom", ioModeComboBox.getValue());
            }

            if (coresCheckBox.isSelected() && coresTextField.getText() != null) {
                if ("".equals(coresTextField.getText())) {
                    command.append(" -c " + 4);
                    trexCmdOptions.put("c", 4);
                } else {
                    command.append(" -c " + coresTextField.getText());
                    trexCmdOptions.put("c", coresTextField.getText());
                }
            }
        }
        LOG.info("Execute: " + command.toString());
        LogsController.getInstance().getConsoleLogView().append("Executing command : " + command.toString());
        return status;
    }

    private void initializeAdvancedOptions() {

        learnModeContainer = getOptionsHBoxContainer();
        learnModeCheckBox = new CheckBox("LEARN-MODE");
        learnModeContainer.getChildren().addAll(learnModeCheckBox);

        learnModeComboContainer = getOptionsHBoxContainer();
        Label learnModeLabel = new Label("Mode");
        learnModeLabel.setMaxHeight(Double.MAX_VALUE);
        HBox.setMargin(learnModeLabel, new Insets(3, 0, 0, 20));
        learnModeComboBox = new ComboBox();
        learnModeComboBox.getItems().addAll("mode 1", "mode 2");
        learnModeComboBox.setDisable(true);
        learnModeComboBox.setPrefWidth(90);
        HBox.setMargin(learnModeComboBox, new Insets(0, 0, 0, 23));
        learnModeComboContainer.getChildren().addAll(
                learnModeLabel,
                learnModeComboBox
        );

        learnModeCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    learnModeComboBox.setDisable(false);
                } else {
                    learnModeComboBox.setDisable(true);
                }
            }
        });

        hopesContainer = getOptionsHBoxContainer();
        hopesCheckBox = new CheckBox("HOPES");
        hopesCheckBox.setMaxHeight(Double.MAX_VALUE);
        hopesTextField = new TextField();
        hopesTextField.setDisable(true);
        hopesTextField.setPrefWidth(90);
        hopesTextField.setText("1");
        HBox.setMargin(hopesTextField, new Insets(3, 0, 0, 15));
        hopesContainer.getChildren().addAll(hopesCheckBox, hopesTextField);

        hopesCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    hopesTextField.setDisable(false);
                } else {
                    hopesTextField.setDisable(true);
                }
            }
        });

        limitPortsContainer = getOptionsHBoxContainer();
        limitPortsTextField = new TextField();
        limitPortsTextField.setPrefWidth(65);
        limitPortsTextField.setText("4");
        limitPortsTextField.setDisable(true);
        HBox.setMargin(limitPortsTextField, new Insets(3, 0, 0, 6));

        limitPortsCheckBox = new CheckBox("LIMIT-PORTS");
        limitPortsCheckBox.setMaxHeight(Double.MAX_VALUE);
        limitPortsContainer.getChildren().addAll(limitPortsCheckBox, limitPortsTextField);
        limitPortsCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    limitPortsTextField.setDisable(false);
                } else {
                    limitPortsTextField.setDisable(true);
                }
            }
        });

        ioModeContainer = getOptionsHBoxContainer();
        ioModeCheckBox = new CheckBox("I/O MODE");
        ioModeCheckBox.setMaxHeight(Double.MAX_VALUE);
        ioModeComboBox = new ComboBox();
        ioModeComboBox.getItems().addAll("0", "1", "2");
        ioModeComboBox.setPrefWidth(80);
        ioModeComboBox.setDisable(true);
        HBox.setMargin(ioModeComboBox, new Insets(0, 0, 0, 7));
        ioModeContainer.getChildren().addAll(ioModeCheckBox, ioModeComboBox);
        ioModeCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    ioModeComboBox.setDisable(false);
                } else {
                    ioModeComboBox.setDisable(true);
                }
            }
        });

        coresContainer = getOptionsHBoxContainer();
        coresCheckBox = new CheckBox("CORES");
        coresCheckBox.setMaxHeight(Double.MAX_VALUE);
        coresTextField = new TextField();
        coresTextField.setPrefWidth(90);
        coresTextField.setText("4");
        coresTextField.setDisable(true);

        HBox.setMargin(coresTextField, new Insets(0, 0, 0, 16));
        coresContainer.getChildren().addAll(coresCheckBox, coresTextField);

        coresCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    coresTextField.setDisable(false);
                } else {
                    coresTextField.setDisable(true);
                }
            }
        });
    }

    private HBox getOptionsHBoxContainer() {
        HBox container = new HBox();
        container.setSpacing(10);
        container.setMinHeight(30);
        container.setPrefHeight(30);
        container.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(container, new Insets(5, 0, 0, 10));
        return container;
    }

    /**
     *
     */
    @FXML
    public void openEditCommandWindow() {
        generateTrexCommand();
        AsyncResponse.getInstance().setCommand(command.toString());
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/EditCommandView.fxml"));
            AnchorPane content = (AnchorPane) loader.load();
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            Stage window = new Stage(StageStyle.UNDECORATED);
            window.setTitle("Edit Command");
            window.initModality(Modality.APPLICATION_MODAL);
            window.setMinWidth(420);
            window.setMinHeight(220);
            window.setMaxWidth(420);
            window.setMaxHeight(220);
            Scene scene = new Scene(content);
            window.setScene(scene);
            window.getIcons().add(new Image("/images/trex.png"));
            window.show();
            EditCommandWindowController controller = loader.getController();
            controller.setCommandAreaTextValue(
                    AsyncResponse.getInstance().getCommand()
            );
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     * @param ipAddress
     * @param connections
     */
    public void updateYamlFileList(String ipAddress, ConnectionsWrapper connections) {
        // combo box will take file name from remote file chooser
        yamlFileList.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                while (change.next()) {
                    if (change.wasPermutated() || change.wasUpdated() || change.wasReplaced()) {
                        LOG.info("A permutation or update or replacement is detected .");
                    } else {
                        if (change.wasRemoved()) {
                            LOG.info("A removal is detected.");
                        } else if (change.wasAdded()) {
                            LOG.info("An addiiton is detected." + change.getAddedSubList().get(0));
                            profileComboBox.getSelectionModel().select(change.getAddedSubList().get(0));
                        }
                    }
                }

            }
        });
        RPCConnections.getInstance().setYamlFileList(yamlFileList);
        profileComboBox.setItems(yamlFileList);
        if (connections != null && connections.getConnectionList() != null) {
            for (Connection connection : connections.getConnectionList()) {
                if (ipAddress.equals(connection.getIp()) && connection.getProfiles() != null && connection.getProfiles().getProfileList() != null) {
                    for (Profile profile : connection.getProfiles().getProfileList()) {
                        RPCConnections.getInstance().addFileToYamlList(profile.getPath(), profile.getName());
                    }
                }
            }
        }
    }

    /**
     *
     */
    public void activateControlWindow() {
        controlOptionsController.setDisable(false);
    }

    /**
     *
     */
    public void deactivateControlWindow() {
        controlOptionsController.setDisable(true);
    }

    /**
     *
     */
    public void resetControlView() {
        profileComboBox.getSelectionModel().clearSelection();
        profileComboBox.getItems().clear();

        frequencyTextField.setText("1000");
        frequencyTextField.setDisable(true);
        latencyCheckBox.setSelected(false);

        durationTextField.setText("0");
        multiplierTextField.setText("1");
        ipv6CheckBox.setSelected(false);
        flowFlipCheckBox.setSelected(false);

        rxCheckCheckBox.setSelected(false);
        rxCheckTextField.setDisable(true);
        rxCheckTextField.setText("1");

        learnModeCheckBox.setSelected(false);
        learnModeComboBox.setDisable(true);
        learnModeComboBox.getSelectionModel().clearSelection();

        hopesCheckBox.setSelected(false);
        hopesTextField.setDisable(true);
        hopesTextField.setText("1");

        limitPortsCheckBox.setSelected(false);
        limitPortsTextField.setDisable(true);
        limitPortsTextField.setText("4");

        ioModeCheckBox.setSelected(false);
        ioModeComboBox.setDisable(true);
        ioModeComboBox.getSelectionModel().clearSelection();

        coresCheckBox.setSelected(false);
        coresTextField.setDisable(true);
        coresTextField.setText("4");
    }

    /**
     *
     */
    public void addFotmattersToTextFields() {
        frequencyTextField.setTextFormatter(getNumberFilter(10));
        durationTextField.setTextFormatter(getNumberFilter(10));
        multiplierTextField.setTextFormatter(getNumberFilter(10));
        rxCheckTextField.setTextFormatter(getNumberFilter(10));
        hopesTextField.setTextFormatter(getNumberFilter(10));
        limitPortsTextField.setTextFormatter(getNumberFilter(10));
        coresTextField.setTextFormatter(getNumberFilter(10));
    }

    /**
     * @param numOfChar
     * @return
     */
    public String numberRegex(int numOfChar) {
        String partialBlock = "(([0-9]{0," + numOfChar + "}))";
        return "^" + partialBlock;
    }

    /**
     * Return hex filter
     *
     * @param numOfChar
     * @return
     */
    public TextFormatter getNumberFilter(int numOfChar) {
        UnaryOperator<TextFormatter.Change> filter = getTextChangeFormatter(numberRegex(numOfChar));
        return new TextFormatter<>(filter);
    }

    /**
     * Return textChange formatter
     *
     * @param regex
     * @return
     */
    public UnaryOperator<TextFormatter.Change> getTextChangeFormatter(String regex) {
        final UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (text.matches(regex)) {
                return c;
            } else {
                return null;
            }
        };
        return filter;
    }

    private void initializeControlOptionsToolTips() {
        Tooltip profileToolTip = new Tooltip();
        profileToolTip.setText("-f=TRAFIC_YAML_FILE\n" +
                "Traffic YAML configuration file.");
        profileComboBox.setTooltip(profileToolTip);

        Tooltip latencyToolTip = new Tooltip();
        latencyToolTip.setText("-l=HZ\n" +
                "Run the latency daemon in this Hz rate.\n" +
                "Example: -l 1000 runs 1000 pkt/sec from each interface.\n" +
                " A value of zero (0) disables the latency check.");
        latencyCheckBox.setTooltip(latencyToolTip);
        frequencyTextField.setTooltip(latencyToolTip);

        Tooltip durationToolTip = new Tooltip();
        durationToolTip.setText("-d=DURATION\n" +
                "Duration of the test (sec), Default: 0");
        durationTextField.setTooltip(durationToolTip);

        Tooltip multiplierToolTip = new Tooltip();
        multiplierToolTip.setText("-m=MUL\n" +
                "Factor for bandwidth (multiply the CPS of each template by this value).");
        multiplierTextField.setTooltip(multiplierToolTip);

        Tooltip ipv6ToolTip = new Tooltip();
        ipv6ToolTip.setText("--ipv6\n" +
                "Convert template to IPv6 mode.");
        ipv6CheckBox.setTooltip(ipv6ToolTip);

        Tooltip flowFlipToolTip = new Tooltip();
        flowFlipToolTip.setText("-p\n" +
                "Flow-flip. Sends all flow packets from the same interface.\n" +
                "This can solve the flow order. Does not work with any router configuration.");
        flowFlipCheckBox.setTooltip(flowFlipToolTip);

        Tooltip rxCheckToolTip = new Tooltip();
        rxCheckToolTip.setText("-rx-check=SAMPLE_RATE\n" +
                "Enable Rx check module.\n" +
                "Using this each thread samples flows (1/sample) and checks order, latency, and additional statistics.\n" +
                "Note: This feature operates as an additional thread.");
        rxCheckCheckBox.setTooltip(rxCheckToolTip);
        rxCheckTextField.setTooltip(rxCheckToolTip);

        Tooltip learnModeToolTip = new Tooltip();
        learnModeToolTip.setText("--learn-mode <mode (1-2)>\n" +
                "Learn the dynamic NAT translation.\n" +
                "1 - Use TCP ACK in first SYN to pass NAT translation information.\n" +
                " Will work only for TCP streams. Initial SYN packet must be present in stream.\n" +
                "2 - Add special IP option to pass NAT translation information.\n" +
                " Will not work on certain firewalls if they drop packets with IP options.");
        learnModeCheckBox.setTooltip(learnModeToolTip);
        learnModeComboBox.setTooltip(learnModeToolTip);

        Tooltip hopesToolTip = new Tooltip();
        hopesToolTip.setText("--hops=HOPES\n" +
                "Number of hops in the setup (default is one hop).\n" +
                "Relevant only if the Rx check is enabled.");
        hopesCheckBox.setTooltip(hopesToolTip);
        hopesTextField.setTooltip(hopesToolTip);

        Tooltip limitPortsToolTip = new Tooltip();
        limitPortsToolTip.setText("--limit-ports=PORTS\n" +
                "Limit number of ports. Configure this in the --cfg file.\n" +
                "Possible values (number of ports): 2, 4, 6, 8. (Default: 4)");
        limitPortsCheckBox.setTooltip(limitPortsToolTip);
        limitPortsTextField.setTooltip(limitPortsToolTip);

        Tooltip ioModeToolTip = new Tooltip();
        ioModeToolTip.setText("--iom=MODE\n" +
                "I/O mode for interactive mode.\n" +
                "Possible values: 0 (silent), 1 (normal), 2 (short)");
        ioModeCheckBox.setTooltip(ioModeToolTip);
        ioModeComboBox.setTooltip(ioModeToolTip);

        Tooltip coresToolTip = new Tooltip();
        coresToolTip.setText("-c=CORES\n" +
                "Number of cores per dual interface. Use 4 for TRex 40Gb/sec. Monitor the CPU% of TRex - it should be ~50%.\n" +
                "TRex uses 2 cores for inner needs, the rest of cores can be used divided by number of dual interfaces.\n" +
                "For virtual NICs the limit is -c=1.");
        coresCheckBox.setTooltip(coresToolTip);
        coresTextField.setTooltip(coresToolTip);

    }

    private void openRemoteFileChooserWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/RemoteFileChooserView.fxml"));
            AnchorPane content = (AnchorPane) loader.load();
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            Stage window = new Stage();
            window.resizableProperty().setValue(Boolean.FALSE);
            window.setTitle("Select Yaml File");
            window.initModality(Modality.APPLICATION_MODAL);
            Scene scene = new Scene(content);
            window.setScene(scene);
            window.getIcons().add(new Image("/images/trex.png"));
            window.show();
        } catch (Exception e) {
            LOG.error(errorMessage, e);
        }
    }

    /**
     *
     */
    @FXML
    public void closeControlWindow() {
        if (!AsyncResponse.getInstance().getCloseControlWindow()) {
            showHideIcon.setImage(new Image("/images/arrow_right.png"));
        } else {
            showHideIcon.setImage(new Image("/images/arrow_left.png"));
        }
        AsyncResponse.getInstance().setCloseControlWindow(!AsyncResponse.getInstance().getCloseControlWindow());
    }

    /**
     *
     */
    @FXML
    public void deleteSelectedProfile() {
        AppLocalConnections.getInstance().removeProfileFromIpProfiles(
                RPCConnections.getInstance().getIpAddress(),
                (String) profileComboBox.getValue()
        );
        RPCConnections.getInstance().removeFileFromYamlList((String) profileComboBox.getValue());
    }
}
