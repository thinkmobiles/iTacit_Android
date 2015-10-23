package com.itacit.healthcare.presentation.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by root on 23.10.15.
 */
public abstract class ModelDataMapper<M, D> {
    public abstract M transform(D dataEntry);
    public List<M> transform(Collection<D> dataCollection) {
        List<M> modelsCollection;

        if (dataCollection != null && !dataCollection.isEmpty()) {
            modelsCollection = new ArrayList<>();
            for (D data : dataCollection) {
                modelsCollection.add(transform(data));
            }
        } else {
            modelsCollection = Collections.emptyList();
        }

        return modelsCollection;
    }
}
