package com.derteuffel.publicationNotes.helpers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;

public class MessageSending {

    @Value("${derteuffel.twilio.sms.sid}")
    private String ACCOUNT_SID;

    @Value("${derteuffel.twilio.sms.auth}")
    private String AUTH_TOKEN;
    @Value("${derteuffel.twilio.number}")
    private String PHONE_NUMBER;

    public void sendMessage(String receiverPhone, String body){

        String phone = "";
        if (receiverPhone.startsWith("0")){
            phone = "+243"+receiverPhone.substring(1);
        }else if (receiverPhone.startsWith("+243")){
            phone = receiverPhone;
        }
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message message = Message.creator(
            new com.twilio.type.PhoneNumber(phone),
            new com.twilio.type.PhoneNumber(PHONE_NUMBER),
            body)
            .create();

        System.out.println(message.getSid());
    }
}
