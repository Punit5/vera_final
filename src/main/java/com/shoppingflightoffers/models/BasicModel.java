package com.shoppingflightoffers.models;

import java.time.Instant;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings({ "checkstyle:designforextension" })
public class BasicModel {
    @Id
    @Null(groups = { Create.class })
    @NotNull(groups = { Update.class })
    private UUID id;
    
    @CreatedDate
    @JsonIgnore
    @Null(groups = { Create.class, Update.class })
    private Instant createdUTC;
    
    @LastModifiedDate
    @JsonIgnore
    @Null(groups = { Create.class, Update.class })
    private Instant updatedUTC;
    
    @Version
    @Null(groups = { Create.class })
    @NotNull(groups = { Update.class })
    private Integer version;
    
    protected BasicModel() {
        // A protected constructor can be provided prevent direct instantiation.
    }
    
    public final Instant getUpdatedUTC() {
        return this.updatedUTC;
    }
    
    public final void setUpdatedUTC(final Instant updatedUTC) {
        this.updatedUTC = updatedUTC;
    }
    
    public final Integer getVersion() {
        return this.version;
    }
    
    public final void setVersion(final Integer version) {
        this.version = version;
    }
    
    public final Instant getCreatedUTC() {
        return this.createdUTC;
    }
    
    public final void setCreatedUTC(final Instant createdUTC) {
        this.createdUTC = createdUTC;
    }
    
    public final UUID getId() {
        return this.id;
    }
    
    public final void setId(final UUID id) {
        this.id = id;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BasicModel)) {
            return false;
        }
        final BasicModel that = (BasicModel) o;
        
        return new EqualsBuilder().append(this.id, that.id).append(this.createdUTC, that.createdUTC).append(this.updatedUTC, that.updatedUTC)
                .append(this.version, that.version).isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.id).append(this.createdUTC).append(this.updatedUTC).append(this.version).toHashCode();
    }
    
    public interface Create {
        
    }
    
    public interface Update {
        
    }
}
