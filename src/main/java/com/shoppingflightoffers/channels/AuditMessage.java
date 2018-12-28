package com.shoppingflightoffers.channels;

import java.util.Date;

public final class AuditMessage {
    // Link Customer Id
    private String linkCustomerId;
    // Link User Id
    private String linkUserId;
    // a free form string representing what was done to the object.
    private String message;
    // unique id of the object
    private String objectId;
    // object type
    private String objectType;
    private String microserviceId;
    // the date the audit history was created, this is set when the item is created, not when it is save into the datasource
    private Date createDate;
    
    public AuditMessage() {
        super();
    }
    
    public AuditMessage(final String linkCustomerId, final String linkUserId, final String message, final String objectId, final String objectType,
            final String microserviceId, final Date createDate) {
        super();
        this.linkCustomerId = linkCustomerId;
        this.linkUserId = linkUserId;
        this.message = message;
        this.objectId = objectId;
        this.objectType = objectType;
        this.microserviceId = microserviceId;
        this.createDate = createDate;
    }
    
    /*
     * "linkCustomerId" : Link Customer Id
     * "linkUserId" : Link User Id
     * "message" : a free form string representing what was done to the object.
     * "actionType" : examples include, "Add", "Edit", "Delete", "Login Success", "Login Failure", etc...
     * “objectId” : unique id of the object
     * "objectType" : type of the object, example: "Merchant Account", "Processor", "Financial Transaction"
     * "microserviceId" : unique id of the microservice. Each microservice in the UNIFI environment will have an unique identifier
     * "createDate" :
     */
    
    public String getLinkCustomerId() {
        return this.linkCustomerId;
    }
    
    public void setLinkCustomerId(final String linkCustomerId) {
        this.linkCustomerId = linkCustomerId;
    }
    
    public String getLinkUserId() {
        return this.linkUserId;
    }
    
    public void setLinkUserId(final String linkUserId) {
        this.linkUserId = linkUserId;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void setMessage(final String message) {
        this.message = message;
    }
    
    public String getObjectId() {
        return this.objectId;
    }
    
    public void setObjectId(final String objectId) {
        this.objectId = objectId;
    }
    
    public String getObjectType() {
        return this.objectType;
    }
    
    public void setObjectType(final String objectType) {
        this.objectType = objectType;
    }
    
    public String getMicroserviceId() {
        return this.microserviceId;
    }
    
    public void setMicroserviceId(final String microserviceId) {
        this.microserviceId = microserviceId;
    }
    
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(final Date createDate) {
        this.createDate = createDate;
    }
}
