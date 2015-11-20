package com.itacit.healthcare.presentation.base.widgets.expandableTextView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Created by Den on 20.11.15.
 */
public class ExpandableTextView extends TextView {

    private static final String ELLIPSIZE = "... ";

    private String mFullText;
    private int mMaxLines;
    private int mIndex;
    private int mLineCount;

    public ExpandableTextView(Context context) {
        super(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void makeExpandable(int maxLines, int index, int lineCount) {
        makeExpandable(getText().toString(), maxLines, index, lineCount);
    }

    public void makeExpandable(String fullText, int maxLines, int index, int lineCount) {
        mFullText = fullText;
        mMaxLines = maxLines;
        mIndex = index;
        mLineCount = lineCount;
        ViewTreeObserver vto = this.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = getViewTreeObserver();
                obs.removeOnGlobalLayoutListener(this);
                if (mLineCount <= mMaxLines) {
                    setText(mFullText);
                    Log.d("WWW", " " + mLineCount + " <= " + mMaxLines);

                } else {
                    Log.d("WWW", " " + mLineCount + " > " + mMaxLines);
                    setMovementMethod(LinkMovementMethod.getInstance());
                    showLess();
                }
            }
        });
    }

    private void showLess() {
        String newText = mFullText.substring(0, mIndex - (ELLIPSIZE.length() + 1)) + " " + ELLIPSIZE;
        SpannableStringBuilder builder = new SpannableStringBuilder(newText);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showMore();
            }
        }, newText.length() - ELLIPSIZE.length(), (newText.length()), 0);
        setText(builder, BufferType.SPANNABLE);
    }

    private void showMore() {
        SpannableStringBuilder builder = new SpannableStringBuilder(mFullText + ELLIPSIZE);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showLess();
            }
        }, builder.length() - ELLIPSIZE.length(), builder.length(), 0);
        setText(builder, BufferType.SPANNABLE);
    }


}
