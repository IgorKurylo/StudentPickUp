package com.ipq.ipq.Model;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class User extends BaseAccount
{

    @SerializedName("First_Name")
    private String FirstName;
    @SerializedName("Last_Name")
    private String LastName;
    @SerializedName("Phone")
    private String PhoneNumber;
    @SerializedName("Email")
    private String Email;
    @SerializedName("ImageUrl")
    private String ProfileImageUrl;
    @SerializedName("college")
    private College DriverCollege;
    @SerializedName("address")
    private Address address;

    public User(String uniqueId, String password)
    {
        super(uniqueId, password);

    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public College getDriverCollege() {
        return DriverCollege;
    }

    public void setDriverCollege(College driverCollege) {
        DriverCollege = driverCollege;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getProfileImageUrl() {
        return ProfileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        ProfileImageUrl = profileImageUrl;
    }
}
