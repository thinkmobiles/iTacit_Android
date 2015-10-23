/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.itacit.healthcare.chipsView;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;


public class VisibleFilterChip extends ImageSpan implements FilterChip {
    private CharSequence mDisplay;
    private CharSequence mValue;
    private FilterChip.FilterType mFilterType;


    public VisibleFilterChip(final Drawable drawable, String displayText, FilterChip.FilterType filterType) {
        super(drawable, DynamicDrawableSpan.ALIGN_BOTTOM);
        mFilterType = filterType;
        mDisplay = displayText;
    }

    @Override
    public Rect getBounds() {
        return getDrawable().getBounds();
    }

    @Override
    public void draw(final Canvas canvas) {
        getDrawable().draw(canvas);
    }

    @Override
    public CharSequence getDisplay() {
        return mDisplay;
    }

    @Override
    public CharSequence getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return mDisplay + " <" + mValue + ">";
    }

    @Override
    public FilterType getFilterType() {
        return mFilterType;
    }

    @Override
    public void setFilterType(FilterType filterType) {
        mFilterType = filterType;
    }
}
