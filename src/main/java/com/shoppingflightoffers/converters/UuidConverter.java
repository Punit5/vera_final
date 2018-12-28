package com.shoppingflightoffers.converters;

import java.util.UUID;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import com.shoppingflightoffers.models.BasicModel;

@Configuration
public class UuidConverter extends AbstractMongoEventListener<BasicModel> {
    @Override
    public final void onBeforeConvert(final BeforeConvertEvent<BasicModel> viewableModelConvertEvent) {
        final BasicModel basicModel = viewableModelConvertEvent.getSource();
        if (basicModel.getId() == null) {
            basicModel.setId(UUID.randomUUID());
        }
    }
}
