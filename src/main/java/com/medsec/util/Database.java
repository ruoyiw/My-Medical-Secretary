package com.medsec.util;

import com.medsec.dao.*;
import com.medsec.entity.*;
import javafx.scene.control.RadioMenuItem;
import org.apache.ibatis.session.SqlSession;

import javax.annotation.Nullable;
import java.nio.file.Path;
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

    public Hospital selectOneHospital(String hospitalID){
        HospitalMapper mapper=session.getMapper(HospitalMapper.class);
        return mapper.selectOneHospital(hospitalID);
    }

    public void deleteHospital(String hospitalID){
        HospitalMapper mapper=session.getMapper(HospitalMapper.class);
        mapper.deleteHospital(hospitalID);
        session.commit();

    }

    public void updateHospital(Hospital hospital){
        HospitalMapper mapper=session.getMapper(HospitalMapper.class);
        mapper.updateHospital(hospital);
        session.commit();

    }

    public void addHospital(Hospital hospital){
        HospitalMapper mapper=session.getMapper(HospitalMapper.class);
        mapper.addHospital(hospital);
        session.commit();

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

    public Doctor selectOneDoctor(String doctorID){
        DoctorMapper mapper=session.getMapper(DoctorMapper.class);
        return mapper.selectOneDoctor(doctorID);
    }

    public void deleteDoctor(String doctorID){
        DoctorMapper mapper=session.getMapper(DoctorMapper.class);
        mapper.deleteDoctor(doctorID);
        session.commit();
    }

    public void updateDoctor(Doctor doctor){
        DoctorMapper mapper=session.getMapper(DoctorMapper.class);
        mapper.updateDoctor(doctor);
        session.commit();
    }

    public void addDoctor(Doctor doctor){
        DoctorMapper mapper=session.getMapper(DoctorMapper.class);
        mapper.addDoctor(doctor);
        session.commit();
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

    @Override
    protected void finalize() {
        close();
    }

    public void close() {
        session.close();
    }

}