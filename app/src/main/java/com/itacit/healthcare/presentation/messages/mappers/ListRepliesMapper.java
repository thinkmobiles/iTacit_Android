package com.itacit.healthcare.presentation.messages.mappers;

import android.net.Uri;
import android.util.Log;

import com.itacit.healthcare.data.entries.Reply;
import com.itacit.healthcare.presentation.base.mappers.ModelMapper;
import com.itacit.healthcare.presentation.messages.models.RepliesModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Den on 16.11.15.
 */
public class ListRepliesMapper extends ModelMapper<RepliesModel, Reply> {
    @Override
    public RepliesModel transform(Reply dataEntry) {

        RepliesModel repliesModel = new RepliesModel();
        try {
            if (dataEntry.getId() == null || dataEntry.getId().isEmpty()) {
                return null;
            }
            repliesModel.setId(dataEntry.getId());

            if(dataEntry.getSender() == null){
                repliesModel.setSenderImageUri(Uri.parse(dataEntry.getSenderImageUrl() != null ? dataEntry.getSenderImageUrl() : ""));
                repliesModel.setSenderNameFull(dataEntry.getSenderNameFull() != null ? dataEntry.getSenderNameFull() : "");
                repliesModel.setSenderRoleName(dataEntry.getSenderRoleName() != null ? dataEntry.getSenderRoleName() : "");
            } else {
                repliesModel.setSenderImageUri(Uri.parse(dataEntry.getSender().getImageUrl() != null ? dataEntry.getSender().getImageUrl() : ""));
                repliesModel.setSenderNameFull(dataEntry.getSender().getNameFull() != null ? dataEntry.getSender().getNameFull() : "");
                repliesModel.setSenderRoleName(dataEntry.getSender().getRoleName() != null ? dataEntry.getSender().getRoleName() : "");
            }

            repliesModel.setBody(dataEntry.getBody() != null ? dataEntry.getBody() : "");
            repliesModel.setReplyMethodEmailYn(dataEntry.getReplyMethodEmailYn().equals("Y") ? true : false);
            repliesModel.setReadConfirmedYn(dataEntry.getReadConfirmedYn().equals("Y") ? true : false);
            repliesModel.setReplyMethodSMSYn(dataEntry.getReplyMethodSMSYn().equals("Y") ? true : false);
            repliesModel.setReplyPrivateYn(dataEntry.getReplyPrivateYn().equals("Y") ? true : false);

            repliesModel.setSendDateTime(dataEntry.getSendDateTime() != null ? getLastTimeResponse(dataEntry.getSendDateTime()) : "");

            return repliesModel;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }        return null;

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
