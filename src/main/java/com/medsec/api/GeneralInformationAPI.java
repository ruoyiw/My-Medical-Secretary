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
    @Path("generalInformation/hospitals")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllHospitals(){
        Database db=new Database();
        List<Hospital> results=db.selectAllHospitals();
        return Response.ok(results).build();
    }

    @GET
    @Path("generalInformation/doctors")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllDoctors(){
        Database db=new Database();
        List<Doctor> results=db.selectAllDoctors();
        return Response.ok(results).build();
    }

    @GET
    @Path("generalInformation/pathologies")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllPathologies(){
        Database db=new Database();
        List<Pathology> results=db.selectAllPathologies();
        return Response.ok(results).build();
    }

    @GET
    @Path("generalInformation/radiologies")
    @Secured(UserRole.PATIENT)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllRadiologies(){
        Database db=new Database();
        List<Radiology> results=db.selectAllRadiologies();
        return Response.ok(results).build();
    }
}
