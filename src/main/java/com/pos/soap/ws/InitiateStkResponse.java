package com.pos.soap.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "InitiateStkResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class InitiateStkResponse {

    @XmlElement(required = true)
    private String responseCode;

    @XmlElement(required = true)
    private String responseMessage;

    // Getter and Setter for responseCode
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    // Getter and Setter for responseMessage
    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
