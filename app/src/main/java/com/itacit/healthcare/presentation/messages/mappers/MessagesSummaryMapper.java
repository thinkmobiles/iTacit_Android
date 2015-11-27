package com.itacit.healthcare.presentation.messages.mappers;

import com.itacit.healthcare.data.entries.MessagesSummary;

import java.util.HashMap;
import java.util.Map;

import static com.itacit.healthcare.presentation.messages.presenters.MessagesFeedPresenter.MessagesFilter;

/**
 * Created by root on 27.11.15.
 */
public class MessagesSummaryMapper {
    public Map<MessagesFilter, String> transform(MessagesSummary dataEntry) {
        HashMap<MessagesFilter, String> messagesCount = new HashMap<>();
        messagesCount.put(MessagesFilter.ARCHIVE, dataEntry.getArchive().getMessageCount());
        messagesCount.put(MessagesFilter.ACT, dataEntry.getAct().getMessageCount());
        messagesCount.put(MessagesFilter.ALL, dataEntry.getAll().getMessageCount());
        messagesCount.put(MessagesFilter.DONE, dataEntry.getDone().getMessageCount());
        messagesCount.put(MessagesFilter.INBOX, dataEntry.getInbox().getMessageCount());
        messagesCount.put(MessagesFilter.SENT, dataEntry.getSent().getMessageCount());
        messagesCount.put(MessagesFilter.WAITING, dataEntry.getWaiting().getMessageCount());
        return messagesCount;
    }
}
