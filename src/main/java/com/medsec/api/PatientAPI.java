package com.medsec.api;

import com.medsec.dao.PatientMapper;
import com.medsec.entity.Patient;
import com.medsec.util.ConfigListener;
import com.medsec.util.Response;
import com.medsec.util.TokenGenerator;
import org.apache.ibatis.session.SqlSession;
import org.glassfish.jersey.server.JSONP;
import org.json.simple.JSONObject;

import javax.ws.rs.*;

@Path("patient")
public class PatientAPI {
	@POST
	@JSONP(queryParam = "callback")
	@Produces({"application/javascript","application/json"})
	public String signUp(
			@FormParam("password") String password,
			@FormParam("surname") String surname,
			@FormParam("firstname") String firstname,
			@FormParam("email") String email,
			@FormParam("middlename") String middlename
	){

		SqlSession session = ConfigListener.sqlSessionFactory.openSession();
		PatientMapper pMapper=session.getMapper(PatientMapper.class);
		Patient patient =pMapper.selectbyEmail(email);
		System.out.println(patient.toString());


		//check whether the received message is valid
		if(isFieldNull(password)||isFieldNull(surname)||isFieldNull(firstname)||isFieldNull(email)){
			return Response.error("invalid register message, please fill all the required message");
		}else{
			//check if the patient is already in the database
			if(pMapper.selectbyEmail(email)==null){
				return Response.error("the provided email is not match with what you provide with clinic");
			}
			//check whether the patient provided the correct account info
			else if(isinValidPatient(patient,surname,firstname,middlename)){
				return Response.error("your personal information is not match with what you provide with clinic");
			}else{
				patient.setPassword(password);
//				patient.setToken(TokenGenerator.generateToken(password));
//				patient.setToken_expire_date(TokenGenerator.setTokenExpireTime());
				pMapper.signUp(patient);
				session.commit();
				session.close();
				return Response.success(firstname+middlename+surname+", register successfully");
			}
		}
	}
	public boolean isFieldNull(String str){
		if(str==null || str==""){
			return true;
		}else{
			return false;
		}
	}
	public boolean isinValidPatient(Patient patient,String surname, String firstname, String middlename){
		if(!patient.getSurname().matches(surname) || !patient.getFirstname().matches(firstname) || !patient.getMiddlename().matches(middlename)){
			return true;
		}else{
			return false;
		}
	}

    @Path("login")
    @POST
    @JSONP(queryParam = "callback")
    @Produces({"application/javascript","application/json"})
    public String logIn(
            @FormParam("password") String password,
            @FormParam("email") String email
    ){
        SqlSession session = ConfigListener.sqlSessionFactory.openSession();
        PatientMapper pMapper=session.getMapper(PatientMapper.class);
        Patient patient =pMapper.selectbyEmail(email);
        System.out.println(patient.toString());

        //check whether the received message is valid
        if(isFieldNull(password)||isFieldNull(email)){
            return Response.error("invalid login message, please fill all the required message");
        }else{
            //check if the patient is already in the database
            if(patient==null){
                return Response.error("the provided email is not match with what you provide with clinic");
            }
            //check whether the patient has signed up
            else if(patient.getPassword()==null){
                return Response.error("your haven't signed up you account yet");
            }
            //check whether the patient provided the correct password
            else if(isInvalidPass(patient,password)){
                return Response.error("your password is not correct");
            }else{
				patient.setToken(TokenGenerator.generateToken(password));
				patient.setToken_expire_date(TokenGenerator.setTokenExpireTime());
				String token = patient.getToken();
                pMapper.logIn(patient);
                session.commit();
                session.close();
                JSONObject result = new JSONObject();
                result.put("token", token);
                return Response.success(result);
            }
        }
	}
	public boolean isInvalidPass(Patient patient, String password) {
        if(!patient.getPassword().matches(password)){
            return true;
        }else{
            return false;
        }
    }
}
