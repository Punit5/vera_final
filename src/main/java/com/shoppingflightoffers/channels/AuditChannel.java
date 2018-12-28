package com.shoppingflightoffers.channels;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface AuditChannel {
    @Output
    MessageChannel outputchannel1();
    
}
