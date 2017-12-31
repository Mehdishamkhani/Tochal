package org.android.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;

import org.android.R;
import org.android.util.AnimationHelper;
import org.android.util.FontHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class feedback extends Fragment {

    public int MailClickCounter = 1;
    public int CallClickCounter = 0;

    public feedback() {

    }

    @BindView(R.id.mail)
    RelativeLayout mail;

    @BindView(R.id.mail_txt)
    TextView mtxt;

    @BindView(R.id.call)
    RelativeLayout call;

    @BindView(R.id.call_txt)
    TextView ctxt;


    public static feedback newInstance() {
        feedback fragment = new feedback();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        ButterKnife.bind(this, v);


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MailClickCounter = 0;
                CallClickCounter++;


                if (CallClickCounter < 2) {

                    float cweight = ((LinearLayout.LayoutParams) call.getLayoutParams()).weight;
                    float mweight = ((LinearLayout.LayoutParams) mail.getLayoutParams()).weight;


                    mtxt.setVisibility(View.GONE);
                    Animation a = new AnimationHelper.ExpandAnimation(call, mail, cweight, mweight);
                    a.setDuration(400);
                    a.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            ctxt.setVisibility(View.VISIBLE);
                            //call.setTypeface(FontHelper.get(getActivity(),"isans"));
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    call.startAnimation(a);


                }

                if (CallClickCounter >= 2) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(getString(R.string.tel)));
                    startActivity(callIntent);
                }


            }
        });


        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CallClickCounter = 0;
                MailClickCounter++;

                if (MailClickCounter >= 2) {

                    Toast.makeText(getActivity(), R.string.twice, Toast.LENGTH_LONG).show();
                }


                if (MailClickCounter < 2) {


                    float cweight = ((LinearLayout.LayoutParams) call.getLayoutParams()).weight;
                    float mweight = ((LinearLayout.LayoutParams) mail.getLayoutParams()).weight;

                    ctxt.setVisibility(View.GONE);
                    Animation a2 = new AnimationHelper.ExpandAnimation(mail, call, mweight, cweight);
                    a2.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            mtxt.setVisibility(View.VISIBLE);
                            //mail.setTypeface(FontHelper.get(getActivity(),"isans"));

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    a2.setDuration(400);
                    mail.startAnimation(a2);


                }


            }
        });


        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStart() {
        super.onStart();

        MailClickCounter = 1;
        CallClickCounter = 0;
    }
}
