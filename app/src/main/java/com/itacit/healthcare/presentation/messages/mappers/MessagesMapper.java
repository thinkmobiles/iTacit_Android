package com.itacit.healthcare.presentation.messages.mappers;

import android.net.Uri;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;

/**
 * Created by Den on 13.11.15.
 */
public class MessagesMapper extends ModelMapper<MessagesModel, Message> {
    @Override
    public MessagesModel transform(Message dataEntry) {
        MessagesModel messagesModel = new MessagesModel();
        try {
            long id = dataEntry.getId() != null ? Long.parseLong(dataEntry.getId()) : 0;
            if (id == 0) return null;
            messagesModel.setId(id);

            messagesModel.setHeadlineUri(Uri.parse(dataEntry.getSenderImageUrl() != null ? dataEntry.getSenderImageUrl() : ""));

            messagesModel.setSenderName(dataEntry.getSenderNameFull() != null ? dataEntry.getSenderNameFull() : "");
            messagesModel.setLastTimeResponse(dataEntry.getSendDateTime() != null ? dataEntry.getSendDateTime() : "");
//            messagesModel.setNumberOfResponse(dataEntry.get() != null ? dataEntry.() : "");
            messagesModel.setSenderPosition(dataEntry.getSenderRoleName() != null ? dataEntry.getSenderRoleName() : "");

            messagesModel.setSubject(dataEntry.getSubject() != null ? dataEntry.getSubject() : "");
            messagesModel.setContent(dataEntry.getBody() != null ? dataEntry.getBody() : "");

            return messagesModel;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }        return null;
    }
}
