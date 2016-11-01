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

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author BassamJ
 */
@XmlRootElement(name = "profiles")
public class ProfileWrapper {

    private static final Logger LOG = Logger.getLogger(ProfileWrapper.class.getName());
    private List<Profile> profileList;

    /**
     *
     */
    public ProfileWrapper() {
        this.profileList = new ArrayList<>();
    }

    /**
     * @return
     */
    @XmlElement(name = "profile")
    public List<Profile> getProfileList() {
        return profileList;
    }

    /**
     * @param profileList
     */
    public void setProfileList(List<Profile> profileList) {
        this.profileList = profileList;
    }

    /**
     * @param profile
     */
    public void addProfileToProfileList(Profile profile) {
        if (this.profileList == null) {
            this.profileList = new ArrayList<>();
        }
        boolean isFound = false;
        //we need to check if the profile is already exist in the profile list before adding it to the list
        for (int i = 0; i < this.profileList.size(); i++) {
            if (this.profileList.get(i).getName().equals(profile.getName()) && this.profileList.get(i).getPath().equals(profile.getPath())) {
                LOG.info("Profile already selected!");
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            this.profileList.add(profile);
        }
    }

    /**
     * @param profileName
     */
    public void removeProfileFromProfileList(String profileName) {
        for (int i = 0; i < this.profileList.size(); i++) {
            if (this.profileList.get(i).getName().equals(profileName)) {
                this.profileList.remove(i);
                break;
            }
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
