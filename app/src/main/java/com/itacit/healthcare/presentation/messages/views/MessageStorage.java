package com.itacit.healthcare.presentation.messages.views;

import com.itacit.healthcare.domain.models.CreateMessageModel;

/**
 * Created by root on 18.11.15.
 */
public interface MessageStorage {
    CreateMessageModel getMessage();

    CreateMessageModel popMessage();

    void pushCreateMessage(CreateMessageModel model);
}
