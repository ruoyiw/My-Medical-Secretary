package com.medsec.api;


import com.medsec.dao.AppointmentMapper;
import com.medsec.entity.Appointment;
import com.medsec.util.Authentication;
import com.medsec.util.ConfigListener;
import com.medsec.util.Response;
import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.JSONP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import java.util.List;

@Path("appointment")
public class AppointmentAPI {

    @GET
    @JSONP(queryParam = "callback")
    @Produces({"application/javascript", "application/json"})
    public String getAppointment(
            @QueryParam("pid")      String pid,
            @QueryParam("id")       String id,
            @QueryParam("token")    String token) {

        if (Authentication.auth(pid, token)) {
            try (SqlSession session = ConfigListener.sqlSessionFactory.openSession()) {
                AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
                Appointment appointment = mapper.get(id, pid);
                String result = Response.success(appointment.toJson());
                return result;
            }
        } else {
            return Response.error("Authentication failed.");
        }

    }


    @Path("all")
    @GET
    @JSONP(queryParam = "callback")
    @Produces({"application/javascript", "application/json"})
    public String getAppointments(
            @QueryParam("pid")      String pid,
            @QueryParam("token")    String token,
            @QueryParam("from_date")String from_date,
            @QueryParam("to_date")  String to_date,
            @QueryParam("is_confirmed") Boolean is_confirmed) {

        if (Authentication.auth(pid, token)) {
            try (SqlSession session = ConfigListener.sqlSessionFactory.openSession()) {
                AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
                List<Appointment> appointments = mapper.getAll(pid, from_date, to_date, is_confirmed);

                JSONArray result = new JSONArray();
                for (Appointment a: appointments)
                    result.add(a.toJson());
                return Response.success(result);
            }
        } else {
            return Response.error("Authentication failed.");
        }

    }
}
