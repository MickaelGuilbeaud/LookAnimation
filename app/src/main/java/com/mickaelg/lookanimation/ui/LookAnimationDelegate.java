package com.mickaelg.lookanimation.ui;

import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;

/**
 * Created by mickaelg on 06/12/2015.
 */
public class LookAnimationDelegate {

    // region Properties

    /**
     * Animations duration in ms.
     */
    private static final int ANIMATION_DURATION = 300;
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
    private static float pictureZoomTranslationYMultiplier = 0.2f;
    /**
     * Multiplier to apply for the translation on the X axis when changing the body part.
     */
    private static float pictureTranslationYMultiplier = -0.3f;

    // endregion


    // region Constructors

    public LookAnimationDelegate() {
        // Nothing
    }

    // endregion


    // region Animations

    public void zoomInPicture(View view) {
        final float translationXBy = view.getMeasuredWidth() * pictureZoomTranslationXMultiplier;
        final float translationYBy = view.getMeasuredHeight() * pictureZoomTranslationYMultiplier;

        view.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .scaleX(PICTURE_SCALE)
                .scaleY(PICTURE_SCALE)
                .translationXBy(translationXBy)
                .translationYBy(translationYBy);
    }

    public void zoomOutPicture(View view) {
        final float translationXBy = -1 * view.getMeasuredWidth() * pictureZoomTranslationXMultiplier;
        final float translationYBy = -1 * view.getMeasuredHeight() * pictureZoomTranslationYMultiplier;

        view.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .scaleX(1f)
                .scaleY(1f)
                .translationXBy(translationXBy)
                .translationYBy(translationYBy);
    }

    public void slideDownPicture(View view) {
        final float translationYBy = view.getMeasuredHeight() * pictureTranslationYMultiplier;

        view.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .translationYBy(translationYBy);
    }

    public void slideUpPicture(View view) {
        final float translationYBy = -1 * view.getMeasuredHeight() * pictureTranslationYMultiplier;

        view.animate()
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new FastOutSlowInInterpolator())
                .translationYBy(translationYBy);
    }

    public void slideInUpperBodyLayout() {
        // TODO
    }

    public void slideInLowerBodyLayout() {
        // TODO
    }

    public void slideOutUpperBodyLayout() {
        // TODO
    }

    public void slideOutLowerBodyLayout() {
        // TODO
    }

    // endregion

}
