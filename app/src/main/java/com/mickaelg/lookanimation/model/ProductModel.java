package com.mickaelg.lookanimation.model;

import android.support.annotation.DrawableRes;

/**
 * Created by mickaelg on 02/12/2015.
 */
public class ProductModel {

    @DrawableRes
    private int productPictureResId;

    public ProductModel() {
        // Nothing
    }

    public ProductModel(int productPictureResId) {
        this.productPictureResId = productPictureResId;
    }

    public int getProductPictureResId() {
        return productPictureResId;
    }

    public void setProductPictureResId(int productPictureResId) {
        this.productPictureResId = productPictureResId;
    }

}
