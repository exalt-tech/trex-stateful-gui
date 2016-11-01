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

package com.exalttech.trex.stateful.utilities;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.regex.Pattern;

/**
 * @author BassamJ
 */
public class Util {

    /**
     *
     */
    public static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
    private static final Logger LOG = Logger.getLogger(Util.class.getName());
    private static final String VERSION_PROPERTIES_FILE = "version.properties";
    private static final String VERSION_KEY = "version";
    private static DecimalFormat decimalFormatter = new DecimalFormat("0.0");
    private static String OS = System.getProperty("os.name").toLowerCase();


    private Util() {
        //constructor
    }

    /**
     * Validate IPv4 address using pattern matcher regex
     *
     * @param ip
     * @return true if Valid IPv4 address
     */
    public static boolean validateIPv4Address(final String ip) {
        return PATTERN.matcher(ip).matches();
    }

    /**
     * @param numToFormat
     * @return
     */
    public static String formatDecimal(double numToFormat) {
        return decimalFormatter.format(numToFormat);
    }

    /**
     * @param data
     * @return
     */
    public static String formattedData(double data) {
        try {
            if (data < 1) {
                if (data == 0 || data >= 1) {
                    return String.valueOf(data);
                }
                int index = -1;
                while (data < 1) {
                    index++;
                    data *= 1000;
                }
                String unit = "mμnpfaz";
                return formatDecimal(data) + " " + unit.charAt(index);
            } else {
                int unit = 1000;
                if (data < unit) {
                    return String.valueOf(data) + " ";
                }
                int exp = (int) (Math.log(data) / Math.log(unit));
                String pre = ("KMGTPE").charAt(exp - 1) + "";
                return String.format("%3.2f %s", data / Math.pow(unit, exp), pre);
            }
        } catch (Exception ex) {
            LOG.error("", ex);
            return "0";
        }
    }

    /**
     * @param data
     * @return
     */
    public static boolean isNullOrEmpty(String data) {
        return data == null || "".equals(data) || data.isEmpty() || "null".equals(data);
    }

    /**
     * @return
     */
    public static boolean isWindows() {
        return OS.indexOf("win") >= 0;
    }

    /**
     * Check whether the OS is unix
     *
     * @return
     */
    public static boolean isUnix() {
        return OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0;
    }

    /**
     * Return TRex version from version.properties file
     *
     * @return
     */
    public static String getTRexVersion() {
        try {
            Properties prop = new Properties();
            prop.load(Util.class.getClassLoader().getResourceAsStream(VERSION_PROPERTIES_FILE));
            return prop.getProperty(VERSION_KEY);
        } catch (IOException ex) {
            return "";
        }
    }

}
