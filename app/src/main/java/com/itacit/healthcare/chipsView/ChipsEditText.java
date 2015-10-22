package com.itacit.healthcare.chipsView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.EditText;

import com.itacit.healthcare.R;

/**
 * Created by root on 22.10.15.
 */
public class ChipsEditText extends EditText {

    private int mChipPadding = 5;

    public ChipsEditText(Context context) {
        super(context);
    }

    public ChipsEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChipsEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void addChip(String text) {
        final Editable editable = getText();
        CharSequence chip = createChip(text);
        editable.append(chip);
    }

    private static float getTextYOffset(final String text,final TextPaint paint,final int height)
    {
        final Rect bounds=new Rect();
        paint.getTextBounds(text,0,text.length(),bounds);
        final int textHeight=bounds.bottom-bounds.top;
        return height-(height-textHeight)/2-(int)paint.descent();
    }

    private CharSequence createChip(String text) {
        SpannableString chipText = new SpannableString(text);
        final int textLength=text.length();

        TextPaint paint = getPaint();
        Bitmap tmpBitmap = Bitmap.createBitmap(200,50,Bitmap.Config.ARGB_8888);
        float maxWidth = getWidth()-getPaddingLeft()-getPaddingRight()- mChipPadding *2;
        CharSequence ellipsizedText = TextUtils.ellipsize(text, paint, maxWidth, TextUtils.TruncateAt.END);
        int bgColor = getContext().getResources().getColor(android.R.color.holo_red_dark);
        Drawable background = new ColorDrawable(bgColor);
        background.setBounds(0, 0, 200, 50);
        Canvas canvas = new Canvas(tmpBitmap);
        background.draw(canvas);
        paint.setColor(getContext().getResources().getColor(android.R.color.black));
        // Vertically center the text in the chip.
        canvas.drawText(ellipsizedText, 0, ellipsizedText.length(), mChipPadding, getTextYOffset((String) ellipsizedText, paint, 50), paint);
        final Drawable result=new BitmapDrawable(getResources(),tmpBitmap);
        result.setBounds(0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight());
        FilterChip chip = new VisibleFilterChip(result, FilterChip.FilterType.Author);
        chipText.setSpan(chip, 0, textLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return chipText;
    }
}
