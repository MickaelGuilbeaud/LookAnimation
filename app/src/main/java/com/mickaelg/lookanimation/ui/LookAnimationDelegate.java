package com.mickaelg.lookanimation.ui;

import android.support.annotation.IntDef;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by mickaelg on 06/12/2015.
 */
public class LookAnimationDelegate {

    // region Properties

    private static final String TAG = LookAnimationDelegate.class.getSimpleName();

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

    private ImageView mIvLook;
    private LinearLayout mLlUpperBodyProducts;
    private LinearLayout mLlLowerBodyProducts;

    /**
     * Current state of the view.
     */
    @PictureState
    private int mCurrentPictureState = STATE_NOT_ZOOMED;

    /**
     * Animations duration in ms.
     */
    private static final int ANIMATION_DURATION = 400;
    /**
     * Picture scale to apply when we zoom in.
     */
    private static final float PICTURE_SCALE = 1.75f;
    /**
     * Multiplier to apply for the translation on the X axis during the zoomIn.
     */
    private static float pictureZoomTranslationXMultiplier = 0.25f;
    /**
     * Multiplier to apply for the translation on the Y axis during the zoomIn.
     */
    private static float pictureZoomTranslationYMultiplierUpperBody = 0.2f;
    /**
     * Multiplier to apply for the translation on the Y axis during the zoomOut, if the current picture is in the lower
     * body state. It is the sum of pictureZoomTranslationYMultiplierUpperBody and pictureTranslationYMultiplier.
     */
    private static float pictureZoomTranslationYMultiplierLowerBody = -0.2f;
    /**
     * Multiplier to apply for the translation on the Y axis when changing the body part.
     */
    private static float pictureTranslationYMultiplier = -0.4f;
    /**
     * Percentage of the view height that need to be crossed to register a fling.
     */
    private static final float SLIDE_THRESHOLD_MULTIPLIER = 0.05f;

    private LookGestureListener mGestureListener = new LookGestureListener();

    private boolean isInit = false;

    // endregion


    // region Constructors

    public LookAnimationDelegate(ImageView ivLook, LinearLayout llUpperBodyProducts, LinearLayout llLowerBodyProducts) {
        this.mIvLook = ivLook;
        this.mLlUpperBodyProducts = llUpperBodyProducts;
        this.mLlLowerBodyProducts = llLowerBodyProducts;
    }

    // endregion


    // region UI

    public void init() {
        if (isInit) {
            return;
        }
        isInit = true;

        mLlUpperBodyProducts.animate()
                .setDuration(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(1)
                .translationYBy(mLlUpperBodyProducts.getMeasuredHeight());

        mLlLowerBodyProducts.animate()
                .setDuration(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(1)
                .translationYBy(mLlLowerBodyProducts.getMeasuredHeight());
    }

    // endregion


    // region Getters and Setters

    public LookGestureListener getGestureListener() {
        return mGestureListener;
    }

    // endregion


    // region Animations

    public void zoomInPicture() {
        Log.d(TAG, "zoomInPicture");
        final float translationXBy = mIvLook.getMeasuredWidth() * pictureZoomTranslationXMultiplier;
        final float translationYBy = mIvLook.getMeasuredHeight() * pictureZoomTranslationYMultiplierUpperBody;

        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .scaleX(PICTURE_SCALE)
                .scaleY(PICTURE_SCALE)
                .translationXBy(translationXBy)
                .translationYBy(translationYBy);
    }

    public void zoomOutPicture() {
        Log.d(TAG, "zoomOutPicture");
        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0)
                .translationY(0);
    }

    public void slideDownPicture() {
        Log.d(TAG, "slideDownPicture");
        final float translationYBy = mIvLook.getMeasuredHeight() * pictureTranslationYMultiplier;

        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .translationYBy(translationYBy);
    }

    public void slideUpPicture() {
        Log.d(TAG, "slideUpPicture");
        final float translationYBy = -1 * mIvLook.getMeasuredHeight() * pictureTranslationYMultiplier;

        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .translationYBy(translationYBy);
    }

    public void slideInUpperBodyLayout() {
        Log.d(TAG, "slideInUpperBodyPicture");
        mLlUpperBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(1)
                .translationY(0);
    }

    public void slideUpUpperBodyLayout() {
        Log.d(TAG, "slideUpUpperBodyPicture");
        mLlUpperBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(0)
                .translationYBy(-1 * mLlLowerBodyProducts.getMeasuredHeight());
    }

    public void slideOutUpperBodyLayout() {
        Log.d(TAG, "slideOutUpperBodyPicture");
        mLlUpperBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(0)
                .translationYBy(mLlUpperBodyProducts.getMeasuredHeight());
    }

    public void slideInLowerBodyLayout() {
        Log.d(TAG, "slideInLowerBodyPicture");
        mLlLowerBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(1)
                .translationY(0);
    }

    public void slideOutLowerBodyLayout() {
        Log.d(TAG, "slideOutLowerBodyPicture");
        mLlLowerBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(0)
                .translationYBy(mLlLowerBodyProducts.getMeasuredHeight());
    }

    // endregion


    // region LookGestureListener

    public class LookGestureListener extends GestureDetector.SimpleOnGestureListener {

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
            if (mCurrentPictureState == STATE_NOT_ZOOMED) {
                // Fling has no effect in not zoomed mode
                return true;
            }

            final float THRESHOLD_VALUE = SLIDE_THRESHOLD_MULTIPLIER * mIvLook.getMeasuredHeight();
            boolean thresholdCrossed = Math.abs(e2.getY() - e1.getY()) > THRESHOLD_VALUE;
            if (thresholdCrossed) {
                if (e2.getY() > e1.getY() && mCurrentPictureState == STATE_LOWER_BODY) {
                    Log.d(TAG, "Slide down");
                    mCurrentPictureState = STATE_UPPER_BODY;
                    slideUpPicture();
                    slideInUpperBodyLayout();
                    slideOutLowerBodyLayout();
                } else if (e2.getY() < e1.getY() && mCurrentPictureState == STATE_UPPER_BODY) {
                    Log.d(TAG, "Slide up");
                    mCurrentPictureState = STATE_LOWER_BODY;
                    slideDownPicture();
                    slideUpUpperBodyLayout();
                    slideInLowerBodyLayout();
                }
            }
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
