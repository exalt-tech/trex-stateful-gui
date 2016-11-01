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

/**
 * @author BassamJ
 */
public class LogsController {

    private static LogsController instance = null;
    private ConsoleLogView consoleLogView;

    protected LogsController() {
        consoleLogView = new ConsoleLogView();
    }

    /**
     * @return
     */
    public static synchronized LogsController getInstance() {
        if (instance == null) {
            instance = new LogsController();
        }
        return instance;
    }

    /**
     * @param textToAppend
     */
    public void appendConsoleViewText(String textToAppend) {
        consoleLogView.append(textToAppend.trim());
    }

    /**
     *
     */
    public void clearConsoleViewText() {
        consoleLogView.clear();
    }

    /**
     * @return
     */
    public ConsoleLogView getConsoleLogView() {
        return consoleLogView;
    }

}
