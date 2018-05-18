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

/**
 * RESTful APIs for appointments.
 *
 */
@Path("appointment")
public class AppointmentAPI {

    /**
     * <b>Get an appointment with its ID.</b>
     * <br>Parameters are passed via QueryParams.
     * <br>JSONP supported, subject to specified Accept Header.
     * <pre>{@code
     * Method:
     *  [GET] /appointment
     *
     * Sample response:
     *  {
     *   "result": {
     *     "date": "2018-06-12",
     *     "duration": 60,
     *     "note": "Looking after yourself during chemotherapy",
     *     "date_create": "2018-05-16 15:23:41.0",
     *     "is_confirmed": false,
     *     "date_change": "2018-05-16 15:23:41.0",
     *     "pid": "1",
     *     "id": 1,
     *     "detail": "Education Session",
     *     "title": "Day Oncology Unit",
     *     "is_cancelled": false,
     *     "status": false
     *   },
     *   "response": "success"
     * }
     * }</pre>
     * @param pid patient id.
     * @param id appointment id.
     * @param token token for authentication
     * @return Response
     */
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


    /**
     * <b>Get all appointments (filters apply).</b>
     * <br>Parameters are passed via QueryParams.
     * <br>JSONP supported, subject to specified Accept Header.
     * <pre>{@code
     * Method:
     *  [GET] /appointment/all
     *
     * Sample response:
     *  {
     *   "result": [{Appointment..}],
     *   "response": "success"
     * }
     * }</pre>
     * @param pid patient id.
     * @param token token for authentication.
     * @param from_date [optional] only retrieve appointments scheduled after that date.
     * @param to_date [optional] only retrieve appointments scheduled before that date.
     * @param is_confirmed [optional] value={true|false} retrieve (un)confirmed appointments.
     * @return Response
     */
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
