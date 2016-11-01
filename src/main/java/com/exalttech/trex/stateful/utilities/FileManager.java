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

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

/**
 * File manager utility class/
 *
 * @author BassamJ
 */
public class FileManager {

    private static final Logger LOG = Logger.getLogger(FileManager.class.getName());
    private static final String APP_DATA_PATH = File.separator + "TRex" + File.separator + "trex" + File.separator;


    private FileManager() {
        //constructor
    }

    /**
     * Return local file path
     *
     * @return
     */
    public static String getLocalFilePath() {
        String path = System.getProperty( "user.home" );
        if (Util.isWindows()) {
            if (!Util.isNullOrEmpty(System.getenv("LOCALAPPDATA"))) {
                path = System.getenv("LOCALAPPDATA") ;
            }
        }
        return path + APP_DATA_PATH;
    }

    /**
     * Copy the select file to the local directory
     *
     * @param srcFile
     * @return
     * @throws IOException
     */
    public static File copyFile(File srcFile) throws IOException {
        File destFile;
        String srcFileDirectory = "";
        if (srcFile.getParentFile() != null) {
            srcFileDirectory = srcFile.getParentFile().getName();
        }
        destFile = new File(createDirectoryIfNotExists(getLocalFilePath() + srcFileDirectory) + File.separator + srcFile.getName());
        FileUtils.copyFile(srcFile, destFile);
        return destFile;
    }

    /**
     * Create directory if not exists
     *
     * @param directoryPath
     * @return
     */
    public static String createDirectoryIfNotExists(String directoryPath) {
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return directoryPath;
    }

    /**
     * Delete file from local directory
     *
     * @param fileToDelete
     */
    public static void deleteFile(File fileToDelete) {
        if (fileToDelete != null) {
            FileUtils.deleteQuietly(fileToDelete);
        }
    }

    /**
     * @param data
     * @param file
     * @throws IOException
     */
    public static void writeStringToFile(String data, File file) throws IOException {
        FileUtils.writeStringToFile(file, data);
    }
}
