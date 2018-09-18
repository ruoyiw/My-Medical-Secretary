package com.medsec.util;

import com.google.gson.JsonObject;
//import com.medsec.entity.Appointment;

import java.io.IOException;


public class PushNotification {

    public String sendNotification(String recipientToken, JsonObject newAppointment) throws IOException {

        JsonObject notificationObject = new JsonObject();
        notificationObject.addProperty("title", "Medical Secretary");
        notificationObject.addProperty("body", "There is a new appointment.");

        JsonObject dataObject = new JsonObject();
        dataObject.add("appointment_details", newAppointment);

        FCMHelper fcm = FCMHelper.getInstance();
        String response = fcm.sendNotifictaionAndData(FCMHelper.TYPE_TO, recipientToken, notificationObject, dataObject);
        return response;

    }


}
