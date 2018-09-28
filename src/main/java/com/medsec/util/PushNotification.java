package com.medsec.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.medsec.entity.Appointment;

import java.io.IOException;
import java.util.ArrayList;


public class PushNotification {
    private JsonObject notificationObject;
    private JsonObject dataObject;

    /*
    To send a notification of new appointment with recipient tokens to the FCM API
     */
    public ArrayList<String> sendNotification(Appointment newAppointment) throws IOException {

        String uid = newAppointment.getUid();
        Database db = new Database();
        ArrayList<String> recipientTokens = db.getFcmTokenByUid(uid);
        generateNotificationRequest(newAppointment);

        FCMHelper fcm = FCMHelper.getInstance();
        ArrayList<String> responseList = new ArrayList<>();

        for (String fcmToken : recipientTokens) {
            String response = fcm.sendNotifictaionAndData(FCMHelper.TYPE_TO, fcmToken, notificationObject, dataObject);
            responseList.add(response);
        }
        return responseList;
    }

    /*
    Convert Java object to JsonObject
     */
    public JsonObject objectToJsonObject(Object object) {
        Gson gson = new Gson();
        JsonObject jsonObject = (JsonObject) gson.toJsonTree(object);
        jsonObject.remove("property");
        return jsonObject;

    }

    /*
    Construct a notification and data request
     */
    public void generateNotificationRequest(Appointment newAppointment) {

        /*
         Construct notification message
         Notification message: FCM automatically displays the message to end-user devices on behalf of the client app.
          */
        JsonObject notificationObject = new JsonObject();
        notificationObject.addProperty("title", "Medical Secretary");
        notificationObject.addProperty("body", "There is a new appointment.");
        this.notificationObject = notificationObject;

        /*
         Construct data message
         Data message: Client app is responsible for processing data messages. Data messages have only custom key-value pairs.
          */
        JsonObject dataObject = new JsonObject();

        /*
        Need to choose the form of data message:
         */
        //1. Convert the whole Appointment object to JsonObject, then add the appointment JsonObject as the value of
        //   appointment property in the dataObject
        JsonObject appointmentJson = objectToJsonObject(newAppointment);
        dataObject.add("appointment", appointmentJson);

        //2. Add appointment attributes as key-value pairs in the dataObject one by one
        dataObject.addProperty("id", newAppointment.getId());
        dataObject.addProperty("uid", newAppointment.getUid());
        dataObject.addProperty("title", newAppointment.getTitle());
        dataObject.addProperty("date_create", newAppointment.getDate_create().toString());
        dataObject.addProperty("date_change", newAppointment.getDate_change().toString());
        dataObject.addProperty("date", newAppointment.getDate().toString());
        dataObject.addProperty("duration", newAppointment.getDuration().toString());
        dataObject.addProperty("detail", newAppointment.getDetail());
        dataObject.addProperty("note", newAppointment.getNote());
        dataObject.addProperty("user_note", newAppointment.getUser_note());
        dataObject.addProperty("status", newAppointment.getStatus().toString());

        this.dataObject = dataObject;

    }

}
