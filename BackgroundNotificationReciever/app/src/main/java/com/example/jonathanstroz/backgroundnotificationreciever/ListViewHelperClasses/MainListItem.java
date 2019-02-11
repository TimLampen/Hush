package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

public class MainListItem {

    private String itemName;
    private int itemImage;
    private int itemAppId;

    public MainListItem(String name, int imageId, int appId){
        itemName = name;
        itemImage = imageId;
        itemAppId = appId;
    }

    /*
     * Accessor methods
     */
    public String getName(){
        return itemName;
    }

    public int getImage(){ return itemImage;}

    public int getAppId(){
        return itemAppId;
    }

    public MainListItem clone(){
        return new MainListItem(this.itemName, this.itemImage, this.itemAppId);
    }

}
