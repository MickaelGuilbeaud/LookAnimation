package com.mickaelg.lookanimation.ui;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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
    protected ImageView mIvLook;
    @Bind(R.id.look_ll_upper_body)
    protected LinearLayout mLlUpperBodyProducts;
    @Bind(R.id.look_ll_lower_body)
    protected LinearLayout mLlLowerBodyProducts;

    /**
     * Animations duration in ms.
     */
    private static final int ANIMATION_DURATION = 300;
    /**
     * Picture scale to apply when we zoom in.
     */
    private static final float PICTURE_SCALE = 1.75f;
    /**
     * Translation on the X axis to apply to the picture during the zoomIn.
     */
    private static float pictureTranslationX = 400f;
    /**
     * Translation on the Y axis to apply to the picture during the zoomIn.
     */
    private static float pictureTranslationY = 500f;


    /**
     * Look displayed by the view.
     */
    private LookModel mLook = LookModel.createLookModel();
    /**
     * Current state of the view.
     */
    @LookFragment.PictureState
    private int mCurrentPictureState = STATE_NOT_ZOOMED;
    /**
     * Detector to detect
     */
    private GestureDetectorCompat mDetector;

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        computeTransitionValues();
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

        // Hide the product layouts
        mLlUpperBodyProducts.setVisibility(View.GONE);
        mLlLowerBodyProducts.setVisibility(View.GONE);

        // Init the gesture detector
        mDetector = new GestureDetectorCompat(getActivity(), new LookGestureListener());
        mIvLook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // Send the events to our detector that will manage them
                mDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
    }

    /**
     * Compute the transition values that depend on the screen or view sizes.
     */
    private void computeTransitionValues() {
        // Compute look picture translation values
        // TODO

        // Compute product layout heights
        // TODO
    }

    // endregion


    // region UI events

    // endregion


    // region Animations

    private void zoomInPicture() {
        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .scaleX(PICTURE_SCALE)
                .scaleY(PICTURE_SCALE)
                .translationXBy(pictureTranslationX)
                .translationYBy(pictureTranslationY);
    }

    private void zoomOutPicture() {
        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .scaleX(1f)
                .scaleY(1f)
                .translationXBy(-1 * pictureTranslationX)
                .translationYBy(-1 * pictureTranslationY);
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


    // region LookGestureListener

    private class LookGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
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
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // TODO
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            // We override and set the return to true for this method because every gesture starts with a Down event so
            // we want to listen to this event
            return true;
        }

    }

    // endregion

}
