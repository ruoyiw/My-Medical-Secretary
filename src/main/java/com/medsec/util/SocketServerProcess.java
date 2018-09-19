package com.medsec.util;

import com.medsec.entity.Appointment;
import com.medsec.entity.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.*;

public class SocketServerProcess implements Runnable {

    private Socket connectedSocket;
    private static boolean flag = false;
    private static final Logger LOG = LogManager.getLogger();

    public SocketServerProcess(Socket s){
        try {
            this.connectedSocket = s;
            LOG.info("GenieScript client connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String msg;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connectedSocket.getInputStream()));
            while(!flag && (msg = reader.readLine()) != null) {
                String data = SymmetricEncrypt.getInstance().decrypt(msg);
                flag = processData(data);
            }
            connectedSocket.close();
           LOG.info("Client disconnected, data transfer complete");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }


    private boolean processData(String data){
        JSONObject jsonObj = parse(data);
        GScommands command = GScommands.valueOf((String) jsonObj.get("command"));

        switch (command){
            case AUTHENTICATION:
                System.out.println(jsonObj.get("secret"));
                return false;
            case APPOINTMENT:
                System.out.println(jsonObj.get("doc"));
                return apptHandler((JSONObject) jsonObj.get("doc"));
            case PATIENT:
                System.out.println(jsonObj.get("doc"));
                return userHandler((JSONObject) jsonObj.get("doc"));
            case DISCONNECTION:
                System.out.println("disconnected");
                return false;
        }
        return true;
    }

    private JSONObject parse(String jsonString) {
        try {
            JSONObject jsonObj = (JSONObject) new JSONParser().parse(jsonString);
            return jsonObj;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean userHandler(JSONObject user){
        Database db = new Database();
        String id = (String) user.get("Id");
        if (!isPatientExist(id)) {
            System.out.println("insert new patient");
            User patient = processUser(user);
            db.insertUser(patient);
        } else {
            System.out.println("update existed patient");
            User patient = processUser(user);
            db.updateUser(patient);
        }
        return false;
    }

    private boolean apptHandler(JSONObject appt){
        Database db = new Database();
        String id = (String) appt.get("Id");
        if (!isApptExist(id)) {
            System.out.println("insert new appointment");
            Appointment apptointment = processAppt(appt);
            db.insertAppointment(apptointment);
        } else {
            System.out.println("update exist appointment");
            Appointment apptointment = processAppt(appt);
            db.updateAppointment(apptointment);
        }
        return false;
    }

    private User processUser(JSONObject user){
        String id = (String) user.get("Id");
        String surname = (String) user.get("Surname");
        String firstName = (String) user.get("FirstName");
        String email = (String) user.get("EmailAddress");
        String street = (String) user.get("AddressLine1");
        String suburb = (String) user.get("Suburb");
        String state = (String) user.get("State");
        Instant instant = Instant.parse((String) user.get("DOB"));
        LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.of(ZoneOffset.UTC.getId()));
        LocalDate dob = dateTime.toLocalDate();
        User patient = new User().id(id).surname(surname).firstname(firstName).email(email)
                .street(street).suburb(suburb).state(state).dob(dob).role(UserRole.PATIENT);
        return patient;
    }

    public boolean isPatientExist(String id){
        Database db = new Database();
        User patient = db.getUserById(id);
        return patient != null;
    }

    public Appointment processAppt(JSONObject appt){
        String id = (String) appt.get("Id");
        String uid = (String) appt.get("PT_Id_Fk");
        String title = (String) appt.get("Reason");
        String detail = (String) appt.get("Comment");
        String note = (String) appt.get("Note");
        Instant dateCreate = Instant.parse((String) appt.get("CreationDate"));
        String test = (String) appt.get("StartTime");
        long startTime = Long.parseLong(test);
        Instant dateChange = updateTimeConvert((String) appt.get("LastUpdated"));
        Instant dateStart = Instant.parse((String) appt.get("StartDate"));
        Instant date = startDateConvert(dateStart, startTime);
        int duration = Integer.parseInt((String) appt.get("ApptDuration")) / 60;
        Appointment appointment = new Appointment().id(id).uid(uid).title(title).detail(detail).note(note)
                .date_create(dateCreate).date_change(dateChange).date(date).duration(duration).status(AppointmentStatus.UNCONFIRMED);
        return appointment;
    }

    public boolean isApptExist(String id){
        Database db = new Database();
        Appointment appt = db.getAppointment(id);
        return appt != null;
    }

    public Instant startDateConvert(Instant startDate, long startTime) {
        startDate = startDate.minus(Duration.ofHours(10));
        startDate = startDate.plusMillis(startTime);
        return startDate;

    }

    public Instant updateTimeConvert(String lastChnageDate) {
        String year = lastChnageDate.substring(0, 4);
        String month = lastChnageDate.substring(4, 6);
        String day = lastChnageDate.substring(6, 8);
        String hour = lastChnageDate.substring(8, 10);
        String minute = lastChnageDate.substring(10, 12);
        String second = lastChnageDate.substring(12);
        String updateTime = year + "-" + month + "-" + day + "T" + hour + ":" + minute + ":" + second
                + ".00Z";
        Instant instant = Instant.parse(updateTime);
        return instant;
    }
}
