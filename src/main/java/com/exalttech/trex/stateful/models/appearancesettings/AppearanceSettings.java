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

package com.exalttech.trex.stateful.models.appearancesettings;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * @author BassamJ
 */
public class AppearanceSettings {

    private static AppearanceSettings instance = null;
    private BooleanProperty isGlobalCountersVisible = new SimpleBooleanProperty(true);
    private BooleanProperty isPerPortCountersVisible = new SimpleBooleanProperty(false);
    private BooleanProperty isPerWindowTemplateVisible = new SimpleBooleanProperty(false);
    private BooleanProperty isLatencyChartVisible = new SimpleBooleanProperty(true);
    private BooleanProperty isRxHistogramChartVisible = new SimpleBooleanProperty(false);
    private BooleanProperty isLatencyHistogramChartVisible = new SimpleBooleanProperty(true);

    protected AppearanceSettings() {
    }

    /**
     * @return
     */
    public static AppearanceSettings getInstance() {
        if (instance == null) {
            instance = new AppearanceSettings();
        }
        return instance;
    }

    /**
     * @return
     */
    public boolean isGlobalCountersVisible() {
        return isGlobalCountersVisible.get();
    }

    /**
     * @param isGlobalCountersVisible
     */
    public void setGlobalCountersVisible(boolean isGlobalCountersVisible) {
        this.isGlobalCountersVisible.set(isGlobalCountersVisible);
    }

    /**
     * @return
     */
    public BooleanProperty isGlobalCountersVisibleProperty() {
        return isGlobalCountersVisible;
    }

    /**
     * @return
     */
    public boolean isPerPortCountersVisible() {
        return isPerPortCountersVisible.get();
    }

    /**
     * @param isPerPortCountersVisible
     */
    public void setPerPortCountersVisible(boolean isPerPortCountersVisible) {
        this.isPerPortCountersVisible.set(isPerPortCountersVisible);
    }

    /**
     * @return
     */
    public BooleanProperty isPerPortCountersVisibleProperty() {
        return isPerPortCountersVisible;
    }

    /**
     * @return
     */
    public boolean isPerWindowTemplateVisible() {
        return isPerWindowTemplateVisible.get();
    }

    /**
     * @param isPerWindowTemplateVisible
     */
    public void setPerWindowTemplateVisible(boolean isPerWindowTemplateVisible) {
        this.isPerWindowTemplateVisible.set(isPerWindowTemplateVisible);
    }

    /**
     * @return
     */
    public BooleanProperty isPerWindowTemplateVisibleProperty() {
        return isPerWindowTemplateVisible;
    }

    /**
     * @return
     */
    public boolean isLatencyChartVisible() {
        return isLatencyChartVisible.get();
    }

    /**
     * @param isLatencyChartVisible
     */
    public void setLatencyChartVisible(boolean isLatencyChartVisible) {
        this.isLatencyChartVisible.set(isLatencyChartVisible);
    }

    /**
     * @return
     */
    public BooleanProperty isLatencyChartVisibleProperty() {
        return isLatencyChartVisible;
    }

    /**
     * @return
     */
    public boolean isRxHistogramChartVisible() {
        return isRxHistogramChartVisible.get();
    }

    /**
     * @param isRxHistogramChartVisible
     */
    public void setRxHistogramChartVisible(boolean isRxHistogramChartVisible) {
        this.isRxHistogramChartVisible.set(isRxHistogramChartVisible);
    }

    /**
     * @return
     */
    public BooleanProperty isRxHistogramChartVisibleProperty() {
        return isRxHistogramChartVisible;
    }

    /**
     * @return
     */
    public boolean isLatencyHistogramChartVisible() {
        return isLatencyHistogramChartVisible.get();
    }

    /**
     * @param isLatencyHistogramChartVisible
     */
    public void setLatencyHistogramChartVisible(boolean isLatencyHistogramChartVisible) {
        this.isLatencyHistogramChartVisible.set(isLatencyHistogramChartVisible);
    }

    /**
     * @return
     */
    public BooleanProperty isLatencyHistogramChartVisibleProperty() {
        return isLatencyHistogramChartVisible;
    }
}
