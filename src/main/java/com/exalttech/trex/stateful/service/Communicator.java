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

package com.exalttech.trex.stateful.service;

import com.exalttech.trex.stateful.log.LogsController;
import com.exalttech.trex.stateful.utilities.CompressionUtils;
import com.exalttech.trex.stateful.utilities.Constants;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;
import org.zeromq.ZMQ;
import org.zeromq.ZMQException;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.DataFormatException;

/**
 * @author BassamJ
 */
public class Communicator {

    private static final Logger LOG = Logger.getLogger(Communicator.class.getName());

    private Task task;
    private ZMQ.Context context;
    private ZMQ.Socket subscriber;
    private String errorMessage = "Error: ";

    /**
     * @param ipAddress
     * @param asyncPortNumber
     */
    public void connectToSubscriber(String ipAddress, String asyncPortNumber) {
        task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    context = ZMQ.context(1);
                    subscriber = context.socket(ZMQ.SUB);
                    subscriber.setReceiveTimeOut(1000);
                    subscriber.connect("tcp://" + ipAddress + ":" + asyncPortNumber);
                    subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
                    String res;
                    while (!Thread.currentThread().isInterrupted()) {
                        try {
                            byte[] responseBytes = subscriber.recv();
                            res = getDecompressedString(responseBytes);
                            if (res != null) {
                                AsyncResponse.getInstance().setResponse(res);
                            } else {
                                AsyncResponse.getInstance().setServerCrashed(true);
                            }
                        } catch (ZMQException e) {
                            if (e.getErrorCode() == ZMQ.Error.ETERM.getCode()) {
                                break;
                            }
                            LOG.error(errorMessage, e);
                            AsyncResponse.getInstance().setServerCrashed(true);
                        }
                    }
                    subscriber.close();
                    context.term();
                } catch (Exception ex) {
                    LOG.error(errorMessage, ex);
                    AsyncResponse.getInstance().setServerCrashed(true);
                }
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     *
     */
    public void disconnectSubscriber() {
        LOG.info("Disconnecting ZMQ...");
        LogsController.getInstance().appendConsoleViewText("Disconnecting ZMQ...");

        try {
            subscriber.close();
            context.term();
            task.cancel(true);
        } catch (Exception ex) {
            LOG.error(errorMessage, ex);
            LogsController.getInstance().appendConsoleViewText("ERROR:" + ex.getMessage());
        }
    }

//    public boolean checkConnectivityToServer(String ipAddress, String asyncPortNumber) {
//        context = ZMQ.context(1);
//        subscriber = context.socket(ZMQ.SUB);
//        subscriber.setReceiveTimeOut(1000);
//        subscriber.connect("tcp://" + ipAddress + ":" + asyncPortNumber);
//        subscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
//        String response = subscriber.recvStr();
//        subscriber.close();
//        context.term();
//        if (response != null) {
//            return true;
//        }
//        return false;
//
//    }

    private String getDecompressedString(byte[] data) {
        // if the length is larger than 8 bytes
        if (data != null && data.length > 8) {

            // Take the first 4 bytes
            byte[] magicBytes = Arrays.copyOfRange(data, 0, 4);

            String magicString = DatatypeConverter.printHexBinary(magicBytes);

                    /* check MAGIC in the first 4 bytes in case we have it, it is compressed */
            if (magicString.equals(Constants.MAGIC_STRING)) {

                // Skip another  4 bytes containing the uncompressed size of the  message
                byte[] compressedData = Arrays.copyOfRange(data, 8, data.length);

                try {
                    return new String(CompressionUtils.decompress(compressedData));
                } catch (IOException | DataFormatException ex) {
                    LOG.error(errorMessage, ex);
                }

            }

        }
        return (data != null) ? new String(data) : "";

    }
}
