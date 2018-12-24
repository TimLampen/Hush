package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

public class ListItem {

    private String itemName;
    private int itemImage;
    private int itemAppId;

    public ListItem(String name, int imageId, int appId){
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

    public ListItem clone(){
        return new ListItem(this.itemName, this.itemImage, this.itemAppId);
    }

}
