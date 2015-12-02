package com.mickaelg.lookanimation.ui;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mickaelg.lookanimation.R;
import com.mickaelg.lookanimation.model.LookModel;
import com.mickaelg.lookanimation.model.ProductModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mickaelg on 02/12/2015.
 */
public class LookFragment extends Fragment {

    // region Properties

    /**
     * Possible states of a the main picture.
     */
    @IntDef({STATE_NOT_ZOOMED, STATE_UPPER_BODY, STATE_LOWER_BODY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PictureState {
    }

    private static final int STATE_NOT_ZOOMED = 0;
    private static final int STATE_UPPER_BODY = 1;
    private static final int STATE_LOWER_BODY = 2;

    @Bind(R.id.look_iv_look_picture)
    private ImageView mIvLook;
    @Bind(R.id.look_ll_upper_body)
    private LinearLayout mLlUpperBodyProducts;
    @Bind(R.id.look_ll_lower_body)
    private LinearLayout mLlLowerBodyProducts;

    /**
     * Look displayed by the view.
     */
    private LookModel mLook = LookModel.createLookModel();

    @LookFragment.PictureState
    private int mCurrentPictureState = STATE_NOT_ZOOMED;

    // endregion


    // region Constructors

    public static LookFragment newInstance() {
        return new LookFragment();
    }

    // endregion


    // region Lifecycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_look, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    // endregion


    // region UI

    private void initUI() {
        // Set the main picture
        Glide.with(this)
                .load(mLook.getLookPictureResId())
                .centerCrop()
                .into(mIvLook);

        // Create a view for each product composing the look, first for the upper body then for the lower body
        for (ProductModel product : mLook.getUpperBodyProductList()) {
            View productView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.listitem_product, mLlUpperBodyProducts);
            productView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }

        for (ProductModel product : mLook.getLowerBodyProductList()) {
            View productView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.listitem_product, mLlLowerBodyProducts);
            productView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }

        mLlUpperBodyProducts.setVisibility(View.GONE);
        mLlLowerBodyProducts.setVisibility(View.GONE);
    }

    @OnClick(R.id.look_iv_look_picture)
    public void onLookPictureClicked() {
        switch (mCurrentPictureState) {
            case STATE_NOT_ZOOMED:
                mCurrentPictureState = STATE_UPPER_BODY;
                zoomInPicture();
                slideInUpperBodyLayout();
                break;
            case STATE_UPPER_BODY:
                mCurrentPictureState = STATE_NOT_ZOOMED;
                zoomOutPicture();
                slideOutUpperBodyLayout();
                break;
            case STATE_LOWER_BODY:
                mCurrentPictureState = STATE_NOT_ZOOMED;
                zoomOutPicture();
                slideOutLowerBodyLayout();
        }
    }

    // endregion


    // region Animations

    private void zoomInPicture() {
        // TODO
    }

    private void zoomOutPicture() {
        // TODO
    }

    private void slideInUpperBodyLayout() {
        // TODO
    }

    private void slideOutUpperBodyLayout() {
        // TODO
    }

    private void slideInLowerBodyLayout() {
        // TODO
    }

    private void slideOutLowerBodyLayout() {
        // TODO
    }

    // endregion

}
