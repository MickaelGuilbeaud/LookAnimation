package com.mickaelg.lookanimation.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.mickaelg.lookanimation.R;
import com.mickaelg.lookanimation.model.LookModel;
import com.mickaelg.lookanimation.model.ProductModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Fragment displaying a {@link LookModel}. Init the {@link LookAnimationDelegate} class that actually manage the
 * animations.
 * <p/>
 * Created by mickaelg on 02/12/2015.
 */
public class LookFragment extends Fragment {

    // region Properties

    private static final String TAG = LookFragment.class.getSimpleName();

    @Bind(R.id.look_iv_look_picture)
    protected ImageView mIvLook;
    @Bind(R.id.look_ll_upper_body)
    protected LinearLayout mLlUpperBodyProducts;
    @Bind(R.id.look_ll_lower_body)
    protected LinearLayout mLlLowerBodyProducts;

    /**
     * Look displayed by the view.
     */
    private LookModel mLook = LookModel.createLookModel();
    /**
     * Detector to detect gestures.
     */
    private GestureDetectorCompat mDetector;
    /**
     * Delegate applying the transitions.
     */
    private LookAnimationDelegate mLookAnimationDelegate;

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
                    .inflate(R.layout.listitem_product, mLlUpperBodyProducts, false);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                productView.setBackgroundColor(
                        getResources().getColor(product.getProductColorResId(), getActivity().getTheme()));
            } else {
                productView.setBackgroundColor(getResources().getColor(product.getProductColorResId()));
            }
            mLlUpperBodyProducts.addView(productView);
        }

        for (ProductModel product : mLook.getLowerBodyProductList()) {
            View productView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.listitem_product, mLlLowerBodyProducts, false);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                productView.setBackgroundColor(
                        getResources().getColor(product.getProductColorResId(), getActivity().getTheme()));
            } else {
                productView.setBackgroundColor(getResources().getColor(product.getProductColorResId()));
            }
            mLlLowerBodyProducts.addView(productView);
        }

        mLookAnimationDelegate = new LookAnimationDelegate(mIvLook, mLlUpperBodyProducts, mLlLowerBodyProducts);

        // Init the gesture detector
        mDetector = new GestureDetectorCompat(getActivity(), mLookAnimationDelegate.getGestureListener());
        mIvLook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Send the events to our detector that will manage them
                mDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        // Listener used to get a callback when the view finished loading. Animations can't start before this point.
        ViewTreeObserver vto = mIvLook.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "onGlobalLayout called");
                mLookAnimationDelegate.init();
            }
        });
    }

    // endregion


}
