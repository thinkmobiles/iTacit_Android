package com.itacit.healthcare.presentation.base.mappers;

import android.net.Uri;

import com.itacit.healthcare.presentation.base.model.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by root on 23.10.15.
 */
public abstract class ModelMapper<M extends BaseModel, D> {
    public abstract M transform(D dataEntry);
    public List<M> transform(Collection<D> dataCollection) {
        List<M> modelsCollection;

        if (dataCollection != null && !dataCollection.isEmpty()) {
            modelsCollection = new ArrayList<>();
            for (D data : dataCollection) {
                M model = transform(data);
                if (model != null && !modelsCollection.contains(model))  modelsCollection.add(model);
            }
        } else {
            modelsCollection = Collections.emptyList();
        }

        return modelsCollection;
    }

    protected String convertData(String inputData, String inputFormat, String outputFormat, Locale locale){
        SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
        sdf.setTimeZone(TimeZone.getDefault());
        SimpleDateFormat output = new SimpleDateFormat(outputFormat, locale);
        Date data;
        if (!inputData.equals("")){
            try {
                data = sdf.parse(inputData);
                return output.format(data);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }

    protected boolean convertYn(String Yn) {
        return Yn.equals("Y");
    }

    protected Uri convertUri(String stringUri) {
        return Uri.parse(stringUri != null ? stringUri : "");
    }

    protected String convertString(String string) {
        return string != null ? string : "";
    }
}
