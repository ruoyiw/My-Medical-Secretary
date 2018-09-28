package com.medsec.api;

import com.medsec.entity.Doctor;
import com.medsec.entity.Hospital;
import com.medsec.entity.Pathology;
import com.medsec.entity.Radiology;
import com.medsec.filter.Secured;
import com.medsec.util.ArgumentException;
import com.medsec.util.Database;
import com.medsec.util.DefaultRespondEntity;
import com.medsec.util.UserRole;
import org.glassfish.jersey.server.JSONP;

import javax.ws.rs.*;
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
    @Secured({UserRole.PATIENT,UserRole.ADMIN})
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllHospitals(){
        Database db=new Database();
        List<Hospital> results=db.selectAllHospitals();
        return Response.ok(results).build();
    }

    @GET
    @Path("generalInformation/oneHospital/{hospitalID}")
    @Secured({UserRole.ADMIN,UserRole.PATIENT})
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listOneHospital(
            @PathParam("hospitalID") String hospitalID){
        Database db=new Database();
        Hospital hospital=db.selectOneHospital(hospitalID);
        db.close();
        return Response.ok(hospital).build();
    }

    @DELETE
    @Path("generalInformation/deleteHospital/{hospitalID}")
    @Secured(UserRole.ADMIN)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHospital(
            @PathParam("hospitalID") String hospitalID){
        Database db=new Database();
        Hospital hospital=db.selectOneHospital(hospitalID);
        if(hospital==null){
            db.close();
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new DefaultRespondEntity("resource that to be deleted doesn't existed in db"))
                    .build();
        }else{
            db.deleteHospital(hospitalID);
            db.close();
            return Response.ok(new DefaultRespondEntity()).build();
        }
    }

    @PUT
    @Path("generalInformation/updateHospital")
    @Secured(UserRole.ADMIN)
    @JSONP(queryParam = "callback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateHospital(Hospital requestHospital){
        Database db=new Database();
        Hospital tempHospital=db.selectOneHospital(requestHospital.getId());
        if(tempHospital==null){
            db.close();
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new DefaultRespondEntity("resource that to be updated doesn't existed in db"))
                    .build();
        }else{
            db.updateHospital(requestHospital);
            db.close();
            return Response.ok(new DefaultRespondEntity()).build();
        }
    }

    @POST
    @Path("generalInformation/addHospital")
    @Secured(UserRole.ADMIN)
    @JSONP(queryParam = "callback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addHospital(Hospital requestHospital){
        try{
            if(requestHospital.getId()==null || requestHospital.getId().equals("")){
                throw new ArgumentException();
            }
            Database db=new Database();
            db.addHospital(requestHospital);
            db.close();
            return Response.ok(new DefaultRespondEntity()).build();
        }catch (ArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(new DefaultRespondEntity(e.getMessage())).build();
        }

    }

    /**
     * doctor apis
     *
     */
    @GET
    @Path("generalInformation/doctors")
    @Secured({UserRole.PATIENT,UserRole.ADMIN})
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllDoctors(){
        Database db=new Database();
        List<Doctor> results=db.selectAllDoctors();
        return Response.ok(results).build();
    }

    @GET
    @Path("generalInformation/oneDoctor/{doctorID}")
    @Secured({UserRole.ADMIN,UserRole.PATIENT})
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listOneDoctor(
            @PathParam("doctorID") String doctorID){
        Database db=new Database();
        Doctor doctor=db.selectOneDoctor(doctorID);
        db.close();
        return Response.ok(doctor).build();
    }

    @DELETE
    @Path("generalInformation/deleteDoctor/{doctorID}")
    @Secured(UserRole.ADMIN)
    @JSONP(queryParam = "callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDoctor(
            @PathParam("doctorID") String doctorID){
        Database db=new Database();
        Doctor doctor=db.selectOneDoctor(doctorID);
        if(doctor==null){
            db.close();
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new DefaultRespondEntity("resource that to be deleted doesn't existed in db"))
                    .build();
        }else{
            db.deleteDoctor(doctorID);
            db.close();
            return Response.ok(new DefaultRespondEntity()).build();
        }
    }

    @PUT
    @Path("generalInformation/updateDoctor")
    @Secured(UserRole.ADMIN)
    @JSONP(queryParam = "callback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDoctor(Doctor requestDoctor){
        Database db=new Database();
        Doctor tempDoctor=db.selectOneDoctor(requestDoctor.getId());
        if(tempDoctor==null){
            db.close();
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(new DefaultRespondEntity("resource that to be updated doesn't existed in db"))
                    .build();
        }else{
            db.updateDoctor(requestDoctor);
            db.close();
            return Response.ok(new DefaultRespondEntity()).build();
        }
    }

    @POST
    @Path("generalInformation/addDoctor")
    @Secured(UserRole.ADMIN)
    @JSONP(queryParam = "callback")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDoctor(Doctor requestDoctor){
        try{
            if(requestDoctor.getId()==null || requestDoctor.getId().equals("")){
                throw new ArgumentException();
            }
            Database db=new Database();
            db.addDoctor(requestDoctor);
            db.close();
            return Response.ok(new DefaultRespondEntity()).build();
        }catch (ArgumentException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(new DefaultRespondEntity(e.getMessage())).build();
        }

    }

    /**
     * pathology apis
     *
     */

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


    /**
     * radiology apis
     *
     */

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
