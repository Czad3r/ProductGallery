package productGallery;

import java.sql.Date;

public class Product {
    private int id;
    private String name;
    private float price;
    private Date addDate;
    private byte[] picture;

    public Product(int id,String name,float price,Date addDate,byte[] picture){
        this.id=id;
        this.name=name;
        this.price=price;
        this.addDate=addDate;
        this.picture=picture;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public float getPrice(){
        return price;
    }

    public Date getAddDate() {
        return addDate;
    }

    public byte[] getPicture() {
        return picture;
    }


}
