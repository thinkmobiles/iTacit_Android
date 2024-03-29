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
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.itacit.healthcare.R;
import com.itacit.healthcare.global.utils.AndroidUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.subjects.PublishSubject;
import rx.subjects.Subject;

/**
 * Created by root on 22.10.15.
 */
public class FiltersEditText extends AutoCompleteTextView {

    public static final String MORE_CHIP = "moreChip";
    private final Subject<Chip, Chip> mChipRemovedSubject = PublishSubject.create();
    private final float mChipHeightDp = 32;
    private final float mBgPaddingLeftDp = 12;
    private final float mBgPaddingRightDp = 8;
    private final float mBgPaddingTop = 8;
    private final float mDeleteSizeDp = 16;
    private FiltersTextWatcher mTextWatcher;
    private int moreChips;
    private boolean showMore;
    private boolean isDirtyTouch;
    private int chipLayoutId;

    public void setChipLayoutId(int chipLayoutId) {
        this.chipLayoutId = chipLayoutId;
    }

    public FiltersEditText(Context context) {
        super(context);
    }

    public FiltersEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTextWatcher = new FiltersTextWatcher();
        addTextChangedListener(mTextWatcher);
    }

    private static int findText(final Editable text, final int offset) {
        if (text.charAt(offset) != ' ')
            return offset;
        return -1;
    }

    private static float getTextYOffset(final String text, final TextPaint paint, final int height) {
        final Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        final int textHeight = bounds.bottom - bounds.top;
        return height - (height - textHeight) / 2 - (int) paint.descent();
    }

    public Subject<Chip, Chip> getChipRemovedSubject() {
        return mChipRemovedSubject;
    }

    public void addFilter(Chip filter, boolean showDelete) {
        getHandler().post(() -> {
            float width = 0;
            for (VisibleFilterChip chip : getSortedChips()) {
                width += getPaint().measureText(chip.getChip().getVisibleText());
                if (chip.getChip().equals(filter)) return;
            }

            removeInputText();
            final Editable editable = getText();
            width += getPaint().measureText(filter.getVisibleText());
            CharSequence chip;
            if (width > getWidth() && showMore) {
                //Todo create more chips
                removeFilter(new Chip(MORE_CHIP, "+" + String.valueOf(moreChips) + "...", Chip.FilterType.Author));
                chip = createChip(new Chip(MORE_CHIP, "+" + String.valueOf(++moreChips) + "...", Chip.FilterType.Author), false);
            } else {
                chip = createChipFromView(filter);
            }
            editable.append(chip);
        });
    }

    private CharSequence createChipFromView(Chip filter) {
        LinearLayout chipLayout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.chip_user, null, false);
        ImageView usetImage = (ImageView) chipLayout.findViewById(R.id.iv_userAvatar_CU);
        TextView userName = (TextView) chipLayout.findViewById(R.id.tv_userName_CU);
        if (filter.getImage() != null) {
            usetImage.setImageBitmap(filter.getImage());
        }

        userName.setText(filter.getVisibleText());
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        chipLayout.measure(spec, spec);
        chipLayout.layout(0, 0, chipLayout.getMeasuredWidth(), chipLayout.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(chipLayout.getMeasuredWidth(), chipLayout.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-chipLayout.getScrollX(), -chipLayout.getScrollY());
        chipLayout.draw(c);
        chipLayout.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = chipLayout.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        chipLayout.destroyDrawingCache();
        final Drawable result = new BitmapDrawable(getResources(), viewBmp);
        result.setBounds(0, 0, viewBmp.getWidth(), viewBmp.getHeight());
        VisibleFilterChip chip = new VisibleFilterChip(result, filter);


        String text = filter.getVisibleText();
        if (!text.endsWith(" ")) {
            text += " ";
        }

        final int textLength = text.length() - 1;

        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(chip, 0, textLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    private void removeInputText() {
        if (getInputText().isEmpty()) {
            return;
        }

        VisibleFilterChip lastChip = getLastChip();
        int chipEnd;
        if (lastChip != null) {
            chipEnd = getText().getSpanEnd(lastChip) + 1;
        } else {
            chipEnd = 0;
        }

        if (chipEnd >= 0 && getText().length() > chipEnd) {
            getText().delete(chipEnd, getText().length());
        }
    }

    private CharSequence createChip(Chip filter, boolean showDelete) {
        int paddingTopPx = (int) AndroidUtils.convertDpToPixel(mBgPaddingTop, getContext());
        int paddingRightPx = (int) AndroidUtils.convertDpToPixel(mBgPaddingRightDp, getContext());
        int paddingLeftPx = (int) AndroidUtils.convertDpToPixel(mBgPaddingLeftDp, getContext());
        int heightPx = (int) AndroidUtils.convertDpToPixel(mChipHeightDp, getContext());
        int deleteSizePx = (int) AndroidUtils.convertDpToPixel(mDeleteSizeDp, getContext());

        String text = filter.getVisibleText();
        if (!text.endsWith(" ")) {
            text += " ";
        }


        final int textLength = text.length() - 1;
        TextPaint paint = getPaint();
        int width;

        Drawable delete = null;
        if (showDelete) {
            delete = getContext().getResources().getDrawable(R.drawable.btn_chip_del);
            width = (int) (Math.floor(paint.measureText(text, 0, text.length())) + paddingLeftPx + 2 * paddingRightPx + delete.getMinimumWidth());
            delete.setBounds(width - (deleteSizePx + paddingRightPx), paddingTopPx, width - paddingRightPx, heightPx - paddingTopPx);

        } else {
            width = (int) (Math.floor(paint.measureText(text, 0, text.length())) + paddingLeftPx + paddingRightPx);
        }

        Bitmap tmpBitmap = Bitmap.createBitmap(width, heightPx, Bitmap.Config.ARGB_8888);
        float maxWidth = calculateAvailableWidth(paddingRightPx + paddingLeftPx);
        CharSequence ellipsizedText = TextUtils.ellipsize(text, paint, maxWidth, TextUtils.TruncateAt.END);

        Drawable background = getContext().getResources().getDrawable(R.drawable.bg_chips);
        background.setBounds(0, 0, width, heightPx);
        Canvas canvas = new Canvas(tmpBitmap);
        background.draw(canvas);
        paint.setColor(getContext().getResources().getColor(R.color.gray_dark));
        // Vertically center the text in the chip.
        canvas.drawText(ellipsizedText, 0, ellipsizedText.length(), paddingLeftPx, getTextYOffset((String) ellipsizedText, paint, heightPx) + 4, paint);
        if (showDelete) {
            delete.draw(canvas);
        }


        final Drawable result = new BitmapDrawable(getResources(), tmpBitmap);
        result.setBounds(0, 0, tmpBitmap.getWidth(), tmpBitmap.getHeight());
        VisibleFilterChip chip = new VisibleFilterChip(result, filter);

        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        builder.setSpan(chip, 0, textLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                removeFilter(filter);
            }
        };

        builder.setSpan(clickableSpan , 0, textLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setMovementMethod(LinkMovementMethod.getInstance());
        return builder;
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

    private VisibleFilterChip[] getSortedChips() {
        final Spannable spannable = getText();
        VisibleFilterChip[] chips = spannable
                .getSpans(0, getText().length(), VisibleFilterChip.class);
        ArrayList<VisibleFilterChip> chipsList = new ArrayList<VisibleFilterChip>(
                Arrays.asList(chips));

        Collections.sort(chipsList, (first, second) -> {
            int firstStart = spannable.getSpanStart(first);
            int secondStart = spannable.getSpanStart(second);
            if (firstStart < secondStart) {
                return -1;
            } else if (firstStart > secondStart) {
                return 1;
            } else {
                return 0;
            }
        });
        return chipsList.toArray(new VisibleFilterChip[chipsList.size()]);
    }

    private float calculateAvailableWidth(float paddingChips) {
        return getWidth() - getPaddingLeft() - getPaddingRight() - paddingChips;
    }

    public void removeFilters() {
        for (VisibleFilterChip chip : getSortedChips()) {
            removeChip(chip);
        }

        getText().clear();
    }

    private void removeChip(VisibleFilterChip chip) {
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

        mChipRemovedSubject.onNext(chip.getChip());
    }

    public String getInputText() {
        Spannable spannable = getText();
        VisibleFilterChip last = getLastChip();
        int endSpans = getText().getSpanEnd(last) + 1;
        if (last != null && 0 < endSpans) {
            if (endSpans >= spannable.length()) {
                return "";
            }
            return spannable.subSequence(endSpans, spannable.length()).toString();
        }

        return spannable.subSequence(0, spannable.length()).toString();
    }

    private VisibleFilterChip getLastChip() {
        VisibleFilterChip last = null;
        VisibleFilterChip[] chips = getSortedChips();
        if (chips != null && chips.length > 0) {
            last = chips[chips.length - 1];
        }
        return last;
    }

//    @Override
//    protected void onSelectionChanged(int start, int end) {
//        VisibleFilterChip last = getLastChip();
//        if (last != null && start < getText().getSpanEnd(last)) {
//            // Grab the last chip and set the cursor to after it.
//            setSelection(Math.min(getText().getSpanEnd(last) + 1, getText().length()));
//        }
//        super.onSelectionChanged(start, end);
//
//    }

    public void removeFilter(Chip filter) {
        for (VisibleFilterChip chip : getSortedChips()) {
            if (chip.getChip().equals(filter)) {
                removeChip(chip);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isFocused()) {
            return super.onTouchEvent(event);
        }
        boolean handled = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                isDirtyTouch = true;
                break;
            case MotionEvent.ACTION_UP:
                final float x = event.getX();
                final float y = event.getY();
                final int offset = putOffsetInRange(x, y);
                final VisibleFilterChip currentChip = findChip(offset);
                if (currentChip != null && getChipEnd(currentChip) == offset) {
                    if (!isDirtyTouch) {
                        removeChip(currentChip);
                        handled = true;
                    }
                }
                isDirtyTouch = false;
                break;
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
        // Find the chip that containsRecipient this offset.
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

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

    }

    public List<Chip> getSelectedFilters() {
        ArrayList<Chip> chips = new ArrayList<>();
        for (VisibleFilterChip chip : getSortedChips()) {
            chips.add(chip.getChip());
        }
        return chips;
    }

    public void setShowMore(boolean showMore) {
        this.showMore = showMore;
    }


    private class FiltersTextWatcher implements TextWatcher {
        private boolean remove;
        private int chipStart;

        @Override
        public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {
            remove = getInputText().isEmpty() && count - after == 1;
//            if (remove) {
//                chipStart = getText().getSpanStart(getLastChip());
//            }
        }

        @Override
        public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
            // The user deleted some text OR some text was replaced; check to
            // see if the insertion point is on a space
            // following a chip.
//
//            if (before - count == 1) {
//                // If the item deleted is a space, and the thing before the
//                // space is a chip, delete the entire span.
//                final int selStart = getSelectionStart() - 1 ;
//                final VisibleFilterChip[] repl = getText().getSpans(selStart, selStart, VisibleFilterChip.class);
//                if (repl.length > 0) {
//                    getText().removeSpan(repl[0]);
//                }
//            }
        }

        @Override
        public void afterTextChanged(final Editable s) {
            // If the text has been set to null or empty, make sure we remove
            // all the spans we applied.
            if (TextUtils.isEmpty(s)) {
                // Remove all the chips spans.
                final Spannable spannable = getText();
                final VisibleFilterChip[] chips = spannable.getSpans(0, getText().length(), VisibleFilterChip.class);
                for (final VisibleFilterChip chip : chips)
                    spannable.removeSpan(chip);
            }

            if (remove) {
//                s.delete(chipStart, s.length());
                removeChip(getLastChip());
            }

        }
    }

}
