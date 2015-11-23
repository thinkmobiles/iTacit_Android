package com.itacit.healthcare.presentation.base.widgets.expandableTextView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Den on 20.11.15.
 */
public class ExpandableTextView extends TextView {

    private static final String ELLIPSIZE_MORE = " ...";
    private static final String SHOW_LESS = " (show less)";

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
        mFullText = getText().toString();
        mMaxLines = maxLines;
        mIndex = index;
        mLineCount = lineCount;
        getHandler().post(() -> {
            if (mLineCount <= mMaxLines) {
                setText(mFullText);
            } else {
                setMovementMethod(LinkMovementMethod.getInstance());
                showLess();
            }
        });
    }

    private void showLess() {
        String substring = mFullText.substring(0, mIndex - ELLIPSIZE_MORE.length() + 1);
        String newText = substring + ELLIPSIZE_MORE;
        SpannableStringBuilder builder = new SpannableStringBuilder(newText);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showMore();
            }
        }, newText.length() - ELLIPSIZE_MORE.length(), (newText.length()), 0);
        setText(builder, BufferType.SPANNABLE);
    }

    private void showMore() {
        SpannableStringBuilder builder = new SpannableStringBuilder(mFullText + SHOW_LESS);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                showLess();
            }
        }, builder.length() - SHOW_LESS.length(), builder.length(), 0);
        setText(builder, BufferType.SPANNABLE);
    }


}
