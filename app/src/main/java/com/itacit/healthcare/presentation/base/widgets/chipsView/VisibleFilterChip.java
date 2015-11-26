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

package com.itacit.healthcare.presentation.base.widgets.chipsView;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;


public class VisibleFilterChip extends ImageSpan {
    private final Chip chip;


    public VisibleFilterChip(final Drawable drawable, Chip chip) {
        super(drawable, DynamicDrawableSpan.ALIGN_BOTTOM);
        this.chip = chip;
    }

    public Rect getBounds() {
        return getDrawable().getBounds();
    }

    public void draw(final Canvas canvas) {
        getDrawable().draw(canvas);
    }

    @Override
    public String toString() {
        return chip.getVisibleText() + " <" + chip.getFilterType().toString() + " " + String.valueOf(chip.getId()) + ">";
    }

    public Chip getChip() {
        return chip;
    }
}
