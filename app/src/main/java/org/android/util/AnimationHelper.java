package org.android.util;

import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

/**
 * Created by mehdi on 29/12/2017.
 */

public class AnimationHelper extends android.view.animation.Animation {


    public static class ExpandAnimation extends android.view.animation.Animation {

        private final float mStartWeight;
        private final float mDeltaWeight;
        private final View mContent2;
        private View mContent;

        public ExpandAnimation(View mContent, View mContent2, float startWeight, float endWeight) {
            mStartWeight = startWeight;
            mDeltaWeight = endWeight - startWeight;
            this.mContent = mContent;
            this.mContent2 = mContent2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContent.getLayoutParams();
            lp.weight = (mStartWeight + (mDeltaWeight * interpolatedTime));
            mContent.setLayoutParams(lp);
            final LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    0,
                    mContent.getHeight(),
                    mStartWeight

            );

            mContent2.setLayoutParams(param);


        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }


    public static class CloseAnimation extends android.view.animation.Animation {

        private final float mStartWeight;
        private final float mDeltaWeight;
        private View mContent;
        private final float endWeight;

        public CloseAnimation(View mContent, float startWeight, float endWeight) {
            mStartWeight = startWeight;
            mDeltaWeight = startWeight - endWeight;
            this.mContent = mContent;
            this.endWeight = endWeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContent.getLayoutParams();
            lp.weight = (endWeight - (mDeltaWeight * interpolatedTime));
            mContent.setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }

    }
}
