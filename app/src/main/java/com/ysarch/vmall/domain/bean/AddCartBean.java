package com.ysarch.vmall.domain.bean;

import java.io.Serializable;

/**
 * Created by fysong on 2020/10/25
 **/
public class AddCartBean implements Serializable {
    /**
     * * price = 158;
     * * productAttr = "[{\"\U5c3a\U7801\":\"37\"},{\"\U989c\U8272\":\"YQ01\"}]";
     * * productBrand = 803456557;
     * * productId = 43986072195;
     * * productName = "\U79cb\U5b63\U7537\U58eb\U957f\U8896\U886c\U886b\U5168\U68c9\U514d\U70eb\U5546\U52a1\U6b63\U88c5\U4e2d\U5e74\U7eaf\U68c9\U6761\U7eb9\U5927\U7801\U804c\U4e1a\U5de5\U88c5\U886c\U8863";
     * * productPic = "//img.alicdn.com/imgextra/i3/2412317789/TB20qmfj29TBuNjy0FcXXbeiFXa_!!2412317789.jpg";
     * * productSkuId = 3640406291012;
     * * quantity = 1;
     * * source = 0;
     * * productCategoryId=""
     */

    private long productId;
    private String productSkuId;
    private int quantity;
    private double price;
    private String productName;
    private String productCategoryId;
    private int source;
    private String productAttr;
    private String productPic;

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }


    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }


    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(String productSkuId) {
        this.productSkuId = productSkuId;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(String productCategoryId) {
        this.productCategoryId = productCategoryId;
    }


    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }
}
