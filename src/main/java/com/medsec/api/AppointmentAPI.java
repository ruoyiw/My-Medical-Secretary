package com.medsec.api;


import com.medsec.dao.AppointmentMapper;
import com.medsec.entity.Appointment;
import com.medsec.util.Authentication;
import com.medsec.util.ConfigListener;
import com.medsec.util.Response;
import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.JSONP;

import javax.ws.rs.*;

@Path("appointment")
public class AppointmentAPI {

    @Path("getOne")
    @GET
    @JSONP(queryParam = "callback")
    @Produces({"application/javascript", "application/json"})
    public String getAppointmentById(
            @QueryParam("pid")      String pid,
            @QueryParam("id")       String id,
            @QueryParam("token")    String token) {

        if (Authentication.auth(pid, token)) {
            try (SqlSession session = ConfigListener.sqlSessionFactory.openSession()) {
                AppointmentMapper mapper = session.getMapper(AppointmentMapper.class);
                Appointment appointment = mapper.getAppointmentById(id, pid);
                String result = Response.success(appointment.toJson());
                return result;
            }
        } else {
            return Response.error("Authentication failed.");
        }

    }
}
