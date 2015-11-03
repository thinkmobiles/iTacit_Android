package com.itacit.healthcare.presentation.base.widgets.chipsView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.itacit.healthcare.R;
import com.itacit.healthcare.global.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by root on 22.10.15.
 */
public class FiltersEditText extends EditText {

    private final Subject<VisibleFilterChip, VisibleFilterChip> mChipRemovedSubject = PublishSubject.create();
    private final float mChipHeightDp = 32;
    private final float mBgPaddingLeftDp = 12;
    private final float mBgPaddingRightDp = 8;
    private final float mBgPaddingTop = 8;
    private final float mDeleteSizeDp = 16;


    public FiltersEditText(Context context) {
        super(context);
    }

    public FiltersEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public FiltersEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static int findText(final Editable text, final int offset) {
        if (text.charAt(offset) != ' ')
            return offset;
        return -1;
    }

    public Subject<VisibleFilterChip, VisibleFilterChip> getChipRemovedSubject() {
        return mChipRemovedSubject;
    }

    public void addFilter(Filter filter) {
        final Editable editable = getText();
        CharSequence chip = createChip(filter);
        editable.append(chip);
        sanitizeBetween();
    }

    public void removeFilters() {
        final Editable editable = getText();
        editable.delete(0, editable.length());
    }

    private float calculateAvailableWidth(float paddingChips)
    {
        return getWidth()-getPaddingLeft()-getPaddingRight()-paddingChips;
    }

    private CharSequence createChip(Filter filter) {
        String spanableText;
        String text = filter.getVisibleText();
        if (text.endsWith(" ")) {
            spanableText = text;
        } else {
            spanableText = text + " ";
        }

        int paddingTopPx = (int) AndroidUtils.convertDpToPixel(mBgPaddingTop, getContext());
        int paddingRightPx = (int) AndroidUtils.convertDpToPixel(mBgPaddingRightDp, getContext());
        int paddingLeftPx = (int) AndroidUtils.convertDpToPixel(mBgPaddingLeftDp, getContext());
        int heightPx = (int) AndroidUtils.convertDpToPixel(mChipHeightDp, getContext());
        int deleteSizePx = (int) AndroidUtils.convertDpToPixel(mDeleteSizeDp, getContext());

        Drawable delete = getContext().getResources().getDrawable(R.drawable.btn_chip_del);
        SpannableString chipText = new SpannableString(spanableText);
        final int textLength = spanableText.length() - 1;
        TextPaint paint = getPaint();
        int width = (int) (Math.floor(paint.measureText(text,0,text.length()))+paddingLeftPx+2*paddingRightPx+delete.getMinimumWidth());

        Bitmap tmpBitmap = Bitmap.createBitmap(width, heightPx, Bitmap.Config.ARGB_8888);
        float maxWidth = calculateAvailableWidth(paddingRightPx + paddingLeftPx);
        CharSequence ellipsizedText = TextUtils.ellipsize(text, paint, maxWidth, TextUtils.TruncateAt.END);

        Drawable background = getContext().getResources().getDrawable(R.drawable.bg_chips);
        background.setBounds(0, 0, width, heightPx);
        Canvas canvas = new Canvas(tmpBitmap);
        background.draw(canvas);
        paint.setColor(getContext().getResources().getColor(R.color.gray_dark));
        // Vertically center the text in the chip.
        canvas.drawText(ellipsizedText, 0, ellipsizedText.length(), paddingLeftPx, getTextYOffset((String) ellipsizedText, paint, heightPx), paint);
        delete.setBounds(width - (deleteSizePx + paddingRightPx), paddingTopPx, width - paddingRightPx, heightPx - paddingTopPx);
        delete.draw(canvas);


        final Drawable result = new BitmapDrawable(getResources(), tmpBitmap);
        result.setBounds(0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight());
        VisibleFilterChip chip = new VisibleFilterChip(result, filter);
        chipText.setSpan(chip, 0, textLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return chipText;
    }

    private static float getTextYOffset(final String text, final TextPaint paint, final int height) {
        final Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        final int textHeight = bounds.bottom - bounds.top;
        return height - (height - textHeight) / 2 - (int) paint.descent();
    }

    void removeChip(VisibleFilterChip chip) {
        Spannable spannable = getText();
        int spanStart = spannable.getSpanStart(chip);
        int spanEnd = spannable.getSpanEnd(chip);
        Editable text = getText();
        int toDelete = spanEnd;
        // Always remove trailing spaces when removing a chip.
        while (toDelete >= 0 && toDelete < text.length() && text.charAt(toDelete) == ' ') {
            toDelete++;
        }
        spannable.removeSpan(chip);
        if (spanStart >= 0 && toDelete > 0) {
            text.delete(spanStart, toDelete);
        }

        mChipRemovedSubject.onNext(chip);
    }

    @Override
    protected void onSelectionChanged(int start, int end) {
        VisibleFilterChip last = getLastChip();
        if (last != null && start < getText().getSpanEnd(last)) {
            // Grab the last chip and set the cursor to after it.
            setSelection(Math.min(getText().getSpanEnd(last) + 1, getText().length()));
        }
        super.onSelectionChanged(start, end);

    }

    private VisibleFilterChip getLastChip() {
        VisibleFilterChip last = null;
        VisibleFilterChip[] chips = getSortedChips();
        if (chips != null && chips.length > 0) {
            last = chips[chips.length - 1];
        }
        return last;
    }

    private VisibleFilterChip[] getSortedChips() {
        final Spannable spannable = getText();
        VisibleFilterChip[] chips = spannable
                .getSpans(0, getText().length(), VisibleFilterChip.class);
        ArrayList<VisibleFilterChip> chipsList = new ArrayList<VisibleFilterChip>(
                Arrays.asList(chips));

        Collections.sort(chipsList, new Comparator<VisibleFilterChip>() {

            @Override
            public int compare(VisibleFilterChip first, VisibleFilterChip second) {
                int firstStart = spannable.getSpanStart(first);
                int secondStart = spannable.getSpanStart(second);
                if (firstStart < secondStart) {
                    return -1;
                } else if (firstStart > secondStart) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return chipsList.toArray(new VisibleFilterChip[chipsList.size()]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isFocused()) {
            return super.onTouchEvent(event);
        }
        boolean handled = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                final float x = event.getX();
                final float y = event.getY();
                final int offset = putOffsetInRange(x, y);
                final VisibleFilterChip currentChip = findChip(offset);
                if (currentChip != null && getChipEnd(currentChip) == offset) {
                    removeChip(currentChip);
                    handled = true;
                }
        }
        return handled;
    }

    private int putOffsetInRange(final float x, final float y) {
        final int offset;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            offset = getOffsetForPosition(x, y);
        else offset = supportGetOffsetForPosition(x, y);
        return putOffsetInRange(offset);
    }

    private int putOffsetInRange(final int o) {
        int offset = o;
        final Editable text = getText();
        final int length = text.length();
        // Remove whitespace from end to find "real end"
        int realLength = length;
        for (int i = length - 1; i >= 0; i--)
            if (text.charAt(i) == ' ')
                realLength--;
            else break;
        // If the offset is beyond or at the end of the text,
        // leave it alone.
        if (offset >= realLength)
            return offset;
        final Editable editable = getText();
        while (offset >= 0 && findText(editable, offset) == -1 && findChip(offset) == null)
            // Keep walking backward!
            offset--;
        return offset;
    }

    private VisibleFilterChip findChip(final int offset) {
        final VisibleFilterChip[] chips = getText().getSpans(0, getText().length(), VisibleFilterChip.class);
        // Find the chip that contains this offset.
        for (int i = 0; i < chips.length; i++) {
            final VisibleFilterChip chip = chips[i];
            final int start = getChipStart(chip);
            final int end = getChipEnd(chip);
            if (offset >= start && offset <= end)
                return chip;
        }
        return null;
    }

    private int getChipStart(final VisibleFilterChip chip) {
        return getText().getSpanStart(chip);
    }

    private int getChipEnd(final VisibleFilterChip chip) {
        return getText().getSpanEnd(chip);
    }

    private int supportGetOffsetForPosition(final float x, final float y) {
        if (getLayout() == null)
            return -1;
        final int line = supportGetLineAtCoordinate(y);
        final int offset = supportGetOffsetAtCoordinate(line, x);
        return offset;
    }

    private int supportGetLineAtCoordinate(float y) {
        y -= getTotalPaddingLeft();
        // Clamp the position to inside of the view.
        y = Math.max(0.0f, y);
        y = Math.min(getHeight() - getTotalPaddingBottom() - 1, y);
        y += getScrollY();
        return getLayout().getLineForVertical((int) y);
    }

    private int supportGetOffsetAtCoordinate(final int line, float x) {
        x = supportConvertToLocalHorizontalCoordinate(x);
        return getLayout().getOffsetForHorizontal(line, x);
    }

    private float supportConvertToLocalHorizontalCoordinate(float x) {
        x -= getTotalPaddingLeft();
        // Clamp the position to inside of the view.
        x = Math.max(0.0f, x);
        x = Math.min(getWidth() - getTotalPaddingRight() - 1, x);
        x += getScrollX();
        return x;
    }

    private void sanitizeBetween() {
        final VisibleFilterChip[] chips = getSortedChips();
        if (chips != null && chips.length > 0) {
            final VisibleFilterChip last = chips[chips.length - 1];
            VisibleFilterChip beforeLast = null;
            if (chips.length > 1)
                beforeLast = chips[chips.length - 2];
            int startLooking = 0;
            final int end = getText().getSpanStart(last);
            if (beforeLast != null) {
                startLooking = getText().getSpanEnd(beforeLast);
                final Editable text = getText();
                if (startLooking == -1 || startLooking > text.length() - 1)
                    // There is nothing after this chip.
                    return;
                if (text.charAt(startLooking) == ' ')
                    startLooking++;
            }
            if (startLooking >= 0 && end >= 0 && startLooking < end)
                getText().delete(startLooking, end);
        }
    }

}
