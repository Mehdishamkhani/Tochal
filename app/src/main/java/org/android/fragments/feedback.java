package org.android.fragments;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconButton;
import com.joanzapata.iconify.widget.IconTextView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Digits;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import org.android.R;
import org.android.rest.MyNetworkListener;
import org.android.rest.NetworkExceptionHandler;
import org.android.rest.RequestRepository;
import org.android.rest.models.NoResponse;
import org.android.util.AnimationHelper;
import org.android.util.FontHelper;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class feedback extends Fragment implements Validator.ValidationListener {

    public int MailClickCounter = 1;
    public int CallClickCounter = 0;
    private Validator validator;


    @BindView(R.id.mail)
    RelativeLayout mail;

    @BindView(R.id.mail_txt)
    TextView mtxt;

    @BindView(R.id.call)
    RelativeLayout call;

    @BindView(R.id.call_txt)
    TextView ctxt;

    @BindView(R.id.phone)
    @NotEmpty(message = "پر کردن این فیلد اجباری است")
    @Digits(integer = 15, message = "فقط اعداد مجاز هستند")
    @Length(min = 5, max = 12, message = "عنوان باید بیشتر از پنج حرف باشد")
    EditText phone;

    @BindView(R.id.subject)
    @NotEmpty(message = "پر کردن این فیلد اجباری است")
    @Length(min = 5, max = 300, message = "عنوان باید بیشتر از پنج حرف باشد")
    EditText subject;

    @BindView(R.id.message)
    @NotEmpty(message = "پر کردن این فیلد اجباری است")
    @Length(min = 5, max = 300, message = "متن پیام باید بین 5 تا 300 حرف باشد")
    EditText message;
    private boolean Lock = false;

    public feedback() {

    }

    public static feedback newInstance() {
        return new feedback();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feedback, container, false);

        ButterKnife.bind(this, v);


        validator = new Validator(this);
        validator.setValidationListener(this);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Lock)
                    return;

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

                            Lock = true;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            ctxt.setVisibility(View.VISIBLE);
                            //call.setTypeface(FontHelper.get(getActivity(),"isans"));
                            Lock = false;
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    call.startAnimation(a);


                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse(getString(R.string.tel)));
                    startActivity(callIntent);
                }


            }
        });


        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (Lock)
                    return;

                CallClickCounter = 0;
                MailClickCounter++;


                if (MailClickCounter < 2) {


                    float cweight = ((LinearLayout.LayoutParams) call.getLayoutParams()).weight;
                    float mweight = ((LinearLayout.LayoutParams) mail.getLayoutParams()).weight;

                    ctxt.setVisibility(View.GONE);
                    Animation a2 = new AnimationHelper.ExpandAnimation(mail, call, mweight, cweight);
                    a2.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            Lock = true;
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            mtxt.setVisibility(View.VISIBLE);
                            //mail.setTypeface(FontHelper.get(getActivity(),"isans"));
                            Lock = false;

                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    a2.setDuration(400);
                    mail.startAnimation(a2);


                } else
                    validator.validate();


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
        Lock = false;
    }

    @Override
    public void onValidationSucceeded() {


        RequestRepository rr = new RequestRepository();
        rr.sendFeedback(phone.getText().toString(), subject.getText().toString(), message.getText().toString(), new MyNetworkListener<NoResponse>() {
            @Override
            public void getResult(NoResponse result) {
                //Log.d(feedback.class.getSimpleName(),result);
                Toast.makeText(getActivity(), R.string.msg_sending_ok, Toast.LENGTH_LONG).show();

            }

            @Override
            public void getException(NetworkExceptionHandler error) {
                Toast.makeText(getActivity(), R.string.msg_sending_failed, Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
