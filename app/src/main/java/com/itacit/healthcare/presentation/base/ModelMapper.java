package com.itacit.healthcare.presentation.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by root on 23.10.15.
 */
public abstract class ModelMapper<M, D> {
    public abstract M transform(D dataEntry);
    public List<M> transform(Collection<D> dataCollection) {
        List<M> modelsCollection;

        if (dataCollection != null && !dataCollection.isEmpty()) {
            modelsCollection = new ArrayList<>();
            for (D data : dataCollection) {
                M model = transform(data);
                if (model != null)  modelsCollection.add(model);
            }
        } else {
            modelsCollection = Collections.emptyList();
        }

        return modelsCollection;
    }
}
