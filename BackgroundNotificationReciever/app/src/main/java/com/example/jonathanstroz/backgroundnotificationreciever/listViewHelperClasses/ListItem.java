package com.example.jonathanstroz.backgroundnotificationreciever.listViewHelperClasses;

public class ListItem {

    private String itemName;
    private int itemImage;

    public ListItem(String name, int imageId){
        itemName = name;
        itemImage = imageId;
    }

    /*
     * Accessor methods
     */
    public String getName(){
        return itemName;
    }

    public int getImage(){ return itemImage;}

    /*public String getItem(){
        return "";
    }*/

    public ListItem clone(){
        return new ListItem(this.itemName, this.itemImage);
    }

}
