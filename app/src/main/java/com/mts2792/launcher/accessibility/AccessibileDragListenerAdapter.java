/*
 * Copyright (C) 2016 The Android Open Source Project
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

package com.mts2792.launcher.accessibility;

import android.view.ViewGroup;

import com.mts2792.launcher.CellLayout;
import com.mts2792.launcher.DropTarget.DragObject;
import com.mts2792.launcher.Launcher;
import com.mts2792.launcher.dragndrop.DragController.DragListener;
import com.mts2792.launcher.dragndrop.DragOptions;

/**
 * Utility listener to enable/disable accessibility drag flags for a ViewGroup
 * containing CellLayouts
 */
public class AccessibileDragListenerAdapter implements DragListener {

    private final ViewGroup mViewGroup;
    private final int mDragType;

    /**
     * @param parent
     * @param dragType either {@link CellLayout#WORKSPACE_ACCESSIBILITY_DRAG} or
     *                 {@link CellLayout#FOLDER_ACCESSIBILITY_DRAG}
     */
    public AccessibileDragListenerAdapter(ViewGroup parent, int dragType) {
        mViewGroup = parent;
        mDragType = dragType;
    }

    @Override
    public void onDragStart(DragObject dragObject, DragOptions options) {
        enableAccessibleDrag(true);
    }

    @Override
    public void onDragEnd() {
        enableAccessibleDrag(false);
        Launcher.getLauncher(mViewGroup.getContext()).getDragController().removeDragListener(this);
    }

    protected void enableAccessibleDrag(boolean enable) {
        for (int i = 0; i < mViewGroup.getChildCount(); i++) {
            setEnableForLayout((CellLayout) mViewGroup.getChildAt(i), enable);
        }
    }

    protected final void setEnableForLayout(CellLayout layout, boolean enable) {
        layout.enableAccessibleDrag(enable, mDragType);
    }
}
