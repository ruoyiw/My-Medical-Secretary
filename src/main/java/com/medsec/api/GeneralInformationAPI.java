package com.medsec.api;

import com.medsec.entity.Doctor;
import com.medsec.entity.Hospital;
import com.medsec.entity.Pathology;
import com.medsec.entity.Radiology;
import com.medsec.filter.Secured;
import com.medsec.util.Database;
import com.medsec.util.UserRole;
import org.glassfish.jersey.server.JSONP;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * RESTful APIs for general information.
 *
 */
@Path("/")
public class GeneralInformationAPI {

    @GET
    @Path("generalInformation/hostipals")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllHospitals(
            @QueryParam("since") String since,
            @QueryParam("until") String until
            ){
        Database db=new Database();
        List<Hospital> results=db.listAllHospitals();
        return Response.ok(results).build();
    }

    @GET
    @Path("generalInformation/doctors")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllDoctors(
            @QueryParam("since") String since,
            @QueryParam("until") String until
        ){
        Database db=new Database();
        List<Doctor> results=db.listAllDoctors();
        return Response.ok(results).build();
    }

    @GET
    @Path("generalInformation/pathologies")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllPathologies(
            @QueryParam("since") String since,
            @QueryParam("until") String until
        ){
        Database db=new Database();
        List<Pathology> results=db.listAllPathologies();
        return Response.ok(results).build();
    }

    @GET
    @Path("generalInformation/radiologies")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllRadiologies(
            @QueryParam("since") String since,
            @QueryParam("until") String until
        ){
        Database db=new Database();
        List<Radiology> results=db.listAllRadiologies();
        return Response.ok(results).build();
    }
}
