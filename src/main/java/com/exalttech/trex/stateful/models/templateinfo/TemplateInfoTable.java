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

package com.exalttech.trex.stateful.models.templateinfo;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author BassamJ
 */
public class TemplateInfoTable {
    private final StringProperty name;
    private final LongProperty txPkts;
    private final LongProperty rxPkts;
    private final LongProperty rxErrors;
    private final LongProperty jitter;

    /**
     *
     */
    public TemplateInfoTable() {
        this.name = new SimpleStringProperty("cap2/dns.pcap");
        this.txPkts = new SimpleLongProperty(0);
        this.rxPkts = new SimpleLongProperty(0);
        this.rxErrors = new SimpleLongProperty(0);
        this.jitter = new SimpleLongProperty(0);
    }

    /**
     * @param name
     * @param txPkts
     * @param rxPkts
     * @param rxErrors
     * @param jitter
     */
    public TemplateInfoTable(StringProperty name, LongProperty txPkts, LongProperty rxPkts, LongProperty rxErrors, LongProperty jitter) {
        this.name = name;
        this.txPkts = txPkts;
        this.rxPkts = rxPkts;
        this.rxErrors = rxErrors;
        this.jitter = jitter;
    }

    /**
     * @return
     */
    public String getName() {
        return name.get();
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name.set(name);
    }

    /**
     * @return
     */
    public StringProperty nameProperty() {
        return name;
    }

    /**
     * @return
     */
    public double getTxPkts() {
        return txPkts.get();
    }

    /**
     * @param txPkts
     */
    public void setTxPkts(long txPkts) {
        this.txPkts.set(txPkts);
    }

    /**
     * @return
     */
    public LongProperty txPktsProperty() {
        return txPkts;
    }

    /**
     * @return
     */
    public double getRxPkts() {
        return rxPkts.get();
    }

    /**
     * @param rxPkts
     */
    public void setRxPkts(long rxPkts) {
        this.rxPkts.set(rxPkts);
    }

    /**
     * @return
     */
    public LongProperty rxPktsProperty() {
        return rxPkts;
    }

    /**
     * @return
     */
    public double getRxErrors() {
        return rxErrors.get();
    }

    /**
     * @param rxErrors
     */
    public void setRxErrors(long rxErrors) {
        this.rxErrors.set(rxErrors);
    }

    /**
     * @return
     */
    public LongProperty rxErrorsProperty() {
        return rxErrors;
    }

    /**
     * @return
     */
    public double getJitter() {
        return jitter.get();
    }

    /**
     * @param jitter
     */
    public void setJitter(long jitter) {
        this.jitter.set(jitter);
    }

    /**
     * @return
     */
    public LongProperty jitterProperty() {
        return jitter;
    }
}
