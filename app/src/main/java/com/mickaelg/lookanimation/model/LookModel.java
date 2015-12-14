package com.mickaelg.lookanimation.model;

import android.support.annotation.DrawableRes;

import com.mickaelg.lookanimation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mickaelg on 02/12/2015.
 */
public class LookModel {

    @DrawableRes
    private int lookPictureResId;
    private List<ProductModel> upperBodyProductList;
    private List<ProductModel> lowerBodyProductList;

    public LookModel(int lookPictureResId, List<ProductModel> upperBodyProductList,
                     List<ProductModel> lowerBodyProductList) {
        this.lookPictureResId = lookPictureResId;
        this.upperBodyProductList = upperBodyProductList;
        this.lowerBodyProductList = lowerBodyProductList;
    }

    public int getLookPictureResId() {
        return lookPictureResId;
    }

    public void setLookPictureResId(int lookPictureResId) {
        this.lookPictureResId = lookPictureResId;
    }

    public List<ProductModel> getUpperBodyProductList() {
        return upperBodyProductList;
    }

    public void setUpperBodyProductList(List<ProductModel> upperBodyProductList) {
        this.upperBodyProductList = upperBodyProductList;
    }

    public List<ProductModel> getLowerBodyProductList() {
        return lowerBodyProductList;
    }

    public void setLowerBodyProductList(List<ProductModel> lowerBodyProductList) {
        this.lowerBodyProductList = lowerBodyProductList;
    }

    /**
     * Create a test look.
     *
     * @return Test look model
     */
    public static LookModel createLookModel() {
        int lookPictureResId = R.drawable.suit;

        List<ProductModel> upperBodyProductModelList = new ArrayList<>(3);
        upperBodyProductModelList.add(new ProductModel(R.color.red));
        upperBodyProductModelList.add(new ProductModel(R.color.purple));
        upperBodyProductModelList.add(new ProductModel(R.color.indigo));

        List<ProductModel> lowerBodyProductModelList = new ArrayList<>(2);
        lowerBodyProductModelList.add(new ProductModel(R.color.green));
        lowerBodyProductModelList.add(new ProductModel(R.color.amber));

        return new LookModel(lookPictureResId, upperBodyProductModelList, lowerBodyProductModelList);
    }

}
