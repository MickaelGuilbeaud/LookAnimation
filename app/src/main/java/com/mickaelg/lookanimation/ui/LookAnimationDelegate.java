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
 * This class manage the state of the view and execute animations based on it and on user interactions.
 * <p/>
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

    /**
     * Image of the look.
     */
    private ImageView mIvLook;
    /**
     * Layout containing upper body products.
     */
    private LinearLayout mLlUpperBodyProducts;
    /**
     * Layout containing lower body products.
     */
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
     * Multiplier to apply for the translation on the Y axis when changing the body part.
     */
    private static float pictureTranslationYMultiplier = -0.4f;
    /**
     * Percentage of the view height that need to be crossed to register a fling.
     */
    private static final float SLIDE_THRESHOLD_MULTIPLIER = 0.05f;

    /**
     * Gesture listener giving us callbacks when selected gestures are executed.
     */
    private LookGestureListener mGestureListener = new LookGestureListener();
    /**
     * We want to init the delegate only once.
     */
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

    /**
     * Apply the first animations to prepare the views.
     */
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

    /**
     * Zoom in the look picture.
     */
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

    /**
     * Zoom out the look picture, restoring it to its default state.
     */
    public void zoomOutPicture() {
        Log.d(TAG, "zoomOutPicture");
        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .scaleX(1)
                .scaleY(1)
                .translationX(0)
                .translationY(0);
    }

    /**
     * Slide down the picture, so the view focus more on the lower body part.
     */
    public void slideDownPicture() {
        Log.d(TAG, "slideDownPicture");
        final float translationYBy = mIvLook.getMeasuredHeight() * pictureTranslationYMultiplier;

        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .translationYBy(translationYBy);
    }

    /**
     * Slide up the picture, so the view focus more on the upper body part.
     */
    public void slideUpPicture() {
        Log.d(TAG, "slideUpPicture");
        final float translationYBy = -1 * mIvLook.getMeasuredHeight() * pictureTranslationYMultiplier;

        mIvLook.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .translationYBy(translationYBy);
    }

    /**
     * Make the upper body layout enters the view, from the outside to the view to the bottom.
     */
    public void slideInUpperBodyLayout() {
        Log.d(TAG, "slideInUpperBodyPicture");
        mLlUpperBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(1)
                .translationY(0);
    }

    /**
     * Make the upper body layout quits the view, from the bottom of the view to the top.
     */
    public void slideUpUpperBodyLayout() {
        Log.d(TAG, "slideUpUpperBodyPicture");
        mLlUpperBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(0)
                .translationYBy(-1 * mLlLowerBodyProducts.getMeasuredHeight());
    }

    /**
     * Make the upper body layout quits the view, from the bottom to the outside of the view.
     * We need to replace the layout at its default place in case the picture is zoomed out while focusing the lower
     * body.
     */
    public void slideOutUpperBodyLayout() {
        Log.d(TAG, "slideOutUpperBodyPicture");

        // In case the layout was above its default place, replace it before sliding it out
        mLlUpperBodyProducts.animate()
                .setDuration(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(0)
                .translationY(0)
                .start();

        mLlUpperBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(0)
                .translationYBy(mLlUpperBodyProducts.getMeasuredHeight());
    }

    /**
     * Make the lower body layout enters the view, from the outside to the view to the bottom.
     */
    public void slideInLowerBodyLayout() {
        Log.d(TAG, "slideInLowerBodyPicture");
        mLlLowerBodyProducts.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .alpha(1)
                .translationY(0);
    }

    /**
     * Make the lower body layout quits the view, from the bottom to the outside of the view.
     */
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
                    slideOutUpperBodyLayout();
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
