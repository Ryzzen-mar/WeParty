package fr.martin.weparty;

import com.google.firebase.firestore.Exclude;

public class Upload {
    private String mName;
    private String mDesc;
    private String mDate;
    private String mLocation;
    private String mImageUrl;
    private String mKey;


    public Upload() {
        //empty constructor needed
    }

    public Upload(String name,String desc, String location, String date ,String imageUrl) {
        if (name.trim().equals("")) {
            name = "No Name";
        } else if (date.trim().equals("")) {
            date = "No Date";
        } else if (desc.trim().equals("")) {
            desc = "No Description";
        } else if (location.trim().equals("")) {
            location = "No Location";
        }

        mDesc = desc;
        mLocation = location;
        mDate = date;
        mName = name;
        mImageUrl = imageUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String desc) {
        mDesc = desc;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}