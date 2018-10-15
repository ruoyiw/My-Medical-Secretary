package com.medsec.util;

import com.medsec.dao.*;
import com.medsec.entity.*;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;

/**
 * This class encapsulates all database queries.
 */
public class Database {
    SqlSession session = ConfigListener.sqlSessionFactory.openSession();
    boolean keepAlive = false;

    public Database() {
    }

    public Database(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }
    /*
        User
     */
    public User getUserById(String id) {

        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.selectById(id);

        } finally {
            if (!keepAlive) close();
        }
    }

    public User getUserByEmail(String email) {

        try {

            UserMapper mapper=session.getMapper(UserMapper.class);
            return mapper.selectByEmail(email);

        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateTokenValidFromDate(String uid) {

        try {

            UserMapper mapper=session.getMapper(UserMapper.class);
            Instant now = Instant.now();
            User user = new User().id(uid).token_valid_from(now);
            mapper.updateToken(user);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateUserPassword(String uid, String password) {

        try {

            UserMapper mapper = session.getMapper(UserMapper.class);
            Instant now = Instant.now();
            User user  = new User().id(uid).password(password).token_valid_from(now);
            mapper.updatePassword(user);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    /*
        Appointment
     */
    public List<Appointment> listUserAppointments(
            String uid,
            @Nullable String since,
            @Nullable String until,
            @Nullable AppointmentStatus status){

        try {

            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.getAppointmentsByUserId(uid, since, until, status);

        } finally {
            if (!keepAlive) close();
        }
    }

    public Appointment getAppointment(String appointment_id) {

        try {

            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            return mapper.getAppointmentById(appointment_id);

        } finally {
            if (!keepAlive) close();
        }
    }

    public Appointment updateUserNote(String appointment_id, String user_note) {

        try {

            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            Appointment appointment = new Appointment()
                    .id(appointment_id)
                    .user_note(user_note);
            mapper.updateUserNoteById(appointment);
            session.commit();

            return mapper.getAppointmentById(appointment_id);

        } finally {
            if (!keepAlive) close();
        }
    }

    public void deleteUserNote(String appointment_id) {

        try {

            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            Appointment appointment = new Appointment()
                    .id(appointment_id)
                    .user_note(null);
            mapper.updateUserNoteById(appointment);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateAppointmentStatus(String appointment_id, AppointmentStatus status) {

        try {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            Appointment appointment = new Appointment()
                    .id(appointment_id)
                    .status(status);
            mapper.updateStatusById(appointment);
            session.commit();

        } finally {
            if (!keepAlive) close();
        }
    }

    /*
    Hospital
     */
    public List<Hospital> selectAllHospitals(){

        try{
            HospitalMapper mapper=session.getMapper(HospitalMapper.class);
            return mapper.selectAllHospitals();

        } finally {
            if (!keepAlive) close();
        }
    }

    /*
    Doctor
     */
    public List<Doctor> selectAllDoctors(){

        try{
            DoctorMapper mapper=session.getMapper(DoctorMapper.class);
            return mapper.selectAllDoctors();

        } finally {
            if(!keepAlive) close();
        }
    }

    /*
    Pathology
     */
    public List<Pathology> selectAllPathologies(){
        try{
            PathologyMapper mapper=session.getMapper(PathologyMapper.class);
            return mapper.selectAllPathologies();

        } finally {
            if(!keepAlive) close();
        }
    }

    /*
    Radiology
     */
    public List<Radiology> selectAllRadiologies(){
        try{
            RadiologyMapper mapper=session.getMapper(RadiologyMapper.class);
            return mapper.selectAllRadiologies();

        } finally {
            if(!keepAlive) close();
        }
    }

    public void insertUser(User user) {
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.insertUser(user);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateUser(User user) {
        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.updateUser(user);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public void insertAppointment(Appointment appointment) {
        try {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            mapper.insertAppointment(appointment);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public void updateAppointment(Appointment appointment) {
        try {
            AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
            mapper.updateAppointment(appointment);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public void insertFile(File file) {
        try {
            FileMapper mapper = session.getMapper(FileMapper.class);
            mapper.insertFile(file);
            session.commit();
        } finally {
            if (!keepAlive) close();
        }
    }

    public File selectFileById(String id) {
        try {
            FileMapper mapper = session.getMapper(FileMapper.class);
            return mapper.selectFileById(id);
        } finally {
            if(!keepAlive) close();
        }
    }

    @Override
    protected void finalize() {
        close();
    }

    public void close() {
        session.close();
    }

}