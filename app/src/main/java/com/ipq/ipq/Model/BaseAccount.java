package com.ipq.ipq.Model;

import com.google.gson.annotations.SerializedName;

/**
 *  Base account
 */

public class BaseAccount
{
    @SerializedName("Guid")
    private String UniqueId;
    @SerializedName("PassWord")
    private String Password;
    public BaseAccount(String uniqueId, String password) {
        UniqueId = uniqueId;
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }
}
