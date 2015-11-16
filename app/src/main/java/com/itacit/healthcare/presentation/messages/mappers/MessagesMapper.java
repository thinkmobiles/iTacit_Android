package com.itacit.healthcare.presentation.messages.mappers;

import android.net.Uri;
import android.util.Log;

import com.itacit.healthcare.data.entries.Message;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.MessagesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Den on 13.11.15.
 */
public class MessagesMapper extends ModelMapper<MessagesModel, Message> {
    @Override
    public MessagesModel transform(Message dataEntry) {
        MessagesModel messagesModel = new MessagesModel();
        try {
            if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
                return null;
            }
            messagesModel.setId(dataEntry.getId());
            messagesModel.setHeadlineUri(Uri.parse(dataEntry.getSenderImageUrl() != null ? dataEntry.getSenderImageUrl() : ""));
            messagesModel.setSenderName(dataEntry.getSenderNameFull() != null ? dataEntry.getSenderNameFull() : "");
            messagesModel.setNumberOfResponse(dataEntry.getReplyCountNew() != null ? dataEntry.getReplyCountNew() : 0);
            messagesModel.setSenderRoleName(dataEntry.getSenderRoleName() != null ? dataEntry.getSenderRoleName() : "");
            messagesModel.setSubject(dataEntry.getSubject() != null ? dataEntry.getSubject() : "");
            messagesModel.setBody(dataEntry.getBody() != null ? dataEntry.getBody() : "");
            messagesModel.setReadRequiredYn(dataEntry.getReadRequiredYn().equals("Y") ? true : false);
            messagesModel.setLastTimeResponse(dataEntry.getSendDateTime() != null ? getLastTimeResponse(dataEntry.getSendDateTime()) : "");
            messagesModel.setReadRequiredDate(dataEntry.getReadRequiredDate() != null ? convertData(dataEntry.getReadRequiredDate()) : "");

            return messagesModel;
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
