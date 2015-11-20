package com.itacit.healthcare.presentation.messages.mappers;

import android.net.Uri;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.MessageModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Den on 13.11.15.
 */
public class MessagesMapper extends ModelMapper<MessageModel, Message> {
    @Override
    public MessageModel transform(Message dataEntry) {
        MessageModel messageModel = new MessageModel();
        try {
            if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
                return null;
            }
            messageModel.setId(dataEntry.getId());
            messageModel.setHeadlineUri(Uri.parse(dataEntry.getSenderImageUrl() != null ? dataEntry.getSenderImageUrl() : ""));
            messageModel.setSenderName(dataEntry.getSenderNameFull() != null ? dataEntry.getSenderNameFull() : "");
            messageModel.setNumberOfResponse(dataEntry.getReplyCountNew() != null ? dataEntry.getReplyCountNew() : 0);
            messageModel.setSenderRoleName(dataEntry.getSenderRoleName() != null ? dataEntry.getSenderRoleName() : "");
            messageModel.setSubject(dataEntry.getSubject() != null ? dataEntry.getSubject() : "");
            messageModel.setBody(dataEntry.getBody() != null ? dataEntry.getBody() : "");
            messageModel.setReadRequiredYn(dataEntry.getReadRequiredYn().equals("Y") ? true : false);
            messageModel.setLastTimeResponse(dataEntry.getSendDateTime() != null ? getLastTimeResponse(dataEntry.getSendDateTime()) : "");
            messageModel.setReadRequiredDate(dataEntry.getReadRequiredDate() != null ? convertData(dataEntry.getReadRequiredDate()) : "");

            return messageModel;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }        return null;

    }
    private String convertData(String inputData){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("MMM.dd,yyyy", Locale.CANADA);
        Date data = null;
        try {
            data = sdf.parse(inputData);
            return output.format(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String getLastTimeResponse(String inputTime){
        Calendar saveData = Calendar.getInstance();
        Calendar currentData = Calendar.getInstance();
        Calendar calculate = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CANADA);
        try {
            saveData.setTime(sdf.parse(inputTime));
            calculate.setTime(new Date(currentData.getTime().getTime() - saveData.getTime().getTime()));

            long calculateTime = currentData.getTimeInMillis() - saveData.getTimeInMillis();

            calculate.setTimeInMillis(calculateTime);

            if(TimeUnit.MILLISECONDS.toDays(calculateTime) > 1){
                return String.valueOf(TimeUnit.MILLISECONDS.toDays(calculateTime) + " days ago");
            }else if(TimeUnit.MILLISECONDS.toDays(calculateTime) == 1){
                return String.valueOf(TimeUnit.MILLISECONDS.toDays(calculateTime) + " day ago");
            }else {
                return String.valueOf(TimeUnit.MILLISECONDS.toHours(calculateTime) + " hours ago");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
