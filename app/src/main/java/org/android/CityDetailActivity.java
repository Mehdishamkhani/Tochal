package org.android;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.android.fragments.CityDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CityDetailActivity extends AppCompatActivity {


    @BindView(R.id.back)
    ImageView back;

    @BindView(R.id.head)
    TextView head;

    private boolean waitAnimations = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        ButterKnife.bind(this);

        head.setText(getIntent().getStringExtra("HEAD"));

        CityDetailFragment fragment = CityDetailFragment.getInstance(getIntent().getStringExtra("TYPE"));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.city_detail_container, fragment)
                .commit();

        if (waitAnimations) {
            fragment.waitAnimations();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getResources().getConfiguration().orientation == OrientationHelper.VERTICAL &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportFinishAfterTransition();
            initExitAnimation();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @TargetApi(21)
    private void initEnterAnimation() {
        final Transition sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();
        sharedElementEnterTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                CityDetailFragment fragment = (CityDetailFragment) getSupportFragmentManager().findFragmentById(R.id.city_detail_container);
                fragment.loadContent();
                sharedElementEnterTransition.removeListener(this);
            }
        });
    }

    @TargetApi(21)
    private void initExitAnimation() {
        final Transition elementExitTransition = getWindow().getSharedElementReturnTransition();
        elementExitTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                CityDetailFragment fragment = (CityDetailFragment) getSupportFragmentManager().findFragmentById(R.id.city_detail_container);
                fragment.clearContent();
                elementExitTransition.removeListener(this);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
            }

            @Override
            public void onTransitionCancel(Transition transition) {
            }

            @Override
            public void onTransitionPause(Transition transition) {
            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }


}
