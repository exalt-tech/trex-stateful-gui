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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.exalttech.trex.stateful.models.connection;

import com.google.gson.Gson;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

/**
 * Class that present connection model
 *
 * @author BassamJ
 */
@XmlRootElement(name = "connection")
public class Connection {

    String ip;
    ProfileWrapper profiles;

    /**
     *
     */
    public Connection() {
        //constructor
    }

    /**
     * @param ip
     */
    public Connection(String ip) {
        this.ip = ip;
        this.profiles = new ProfileWrapper();
    }

    /**
     * @param ip
     * @param profiles
     */
    public Connection(String ip, ProfileWrapper profiles) {
        this.ip = ip;
        this.profiles = profiles;
    }

    /**
     * @return
     */
    @XmlElement(name = "profiles")
    public ProfileWrapper getProfiles() {
        return profiles;
    }

    /**
     * @param profiles
     */
    public void setProfiles(ProfileWrapper profiles) {
        this.profiles = profiles;
    }

    /**
     * Return IP
     *
     * @return
     */
    @XmlElement(name = "hostname")
    public String getIp() {
        return ip;
    }

    /**
     * Set IP
     *
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }


    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.ip);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Connection other = (Connection) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }

        return true;
    }

}
