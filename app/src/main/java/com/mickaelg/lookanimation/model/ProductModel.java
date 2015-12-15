package com.mickaelg.lookanimation.model;

import android.support.annotation.ColorRes;

/**
 * Created by mickaelg on 02/12/2015.
 */
public class ProductModel {

    @ColorRes
    private int productPictureResId;

    public ProductModel(int productPictureResId) {
        this.productPictureResId = productPictureResId;
    }

    public int getProductColorResId() {
        return productPictureResId;
    }

    public void setProductColorResId(int productPictureResId) {
        this.productPictureResId = productPictureResId;
    }

}
