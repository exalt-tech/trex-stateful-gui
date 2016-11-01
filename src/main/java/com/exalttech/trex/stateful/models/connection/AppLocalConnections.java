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

package com.exalttech.trex.stateful.models.connection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author BassamJ
 */
public class AppLocalConnections {

    private static AppLocalConnections instance = null;
    private List<Connection> connectionList;

    /**
     *
     */
    protected AppLocalConnections() {
        connectionList = new ArrayList<>();
    }

    /**
     * @return
     */
    public static AppLocalConnections getInstance() {
        if (instance == null) {
            instance = new AppLocalConnections();
        }
        return instance;
    }

    /**
     * @return
     */
    public List<Connection> getConnectionList() {
        return connectionList;
    }

    /**
     * @param connectionList
     */
    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    /**
     * @param ip
     * @param profiles
     */
    public void addConnectionToConnectionList(String ip, ProfileWrapper profiles) {
        //add connection to connection list if the connection does not exist or it does not coontain any profile
        for (int i = 0; i < connectionList.size(); i++) {
            if (connectionList.get(i).getIp().equals(ip)) {
                connectionList.get(i).setProfiles(profiles);
                return;
            }
        }
        connectionList.add(new Connection(ip, profiles));
    }

    /**
     * @param ip
     * @param path
     * @param name
     */
    public void addProfileToIpProfiles(String ip, String path, String name) {
        //Search for the connection list for specific IP then create new profile and add it
        //to the profile list for that specific IP address
        for (int i = 0; i < connectionList.size(); i++) {
            if (connectionList.get(i).getIp().equals(ip)) {
                ProfileWrapper tempProfile = connectionList.get(i).getProfiles();
                tempProfile.addProfileToProfileList(new Profile(name, path));
                connectionList.get(i).setProfiles(tempProfile);
                break;
            }
        }
    }

    /**
     * @param ip
     * @param name
     */
    public void removeProfileFromIpProfiles(String ip, String name) {
        for (int i = 0; i < connectionList.size(); i++) {
            if (connectionList.get(i).getIp().equals(ip)) {
                connectionList.get(i).getProfiles().removeProfileFromProfileList(name);
                break;
            }
        }
    }

    /**
     * @param ipAddress
     */
    public void addNewIpAddressToList(String ipAddress) {
        connectionList.add(new Connection(ipAddress));
    }
}
