package com.medsec.api;

import com.medsec.ArgumentException;
import com.medsec.AuthenticationException;
import com.medsec.entity.User;
import com.medsec.util.Database;
import com.medsec.util.DefaultRespondEntity;
import com.medsec.util.Token;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class AuthenticationAPI {

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUser(User requestUser) {

        try {

            // Authenticate the user using the credentials provided
            User user = authenticate(requestUser);

            // Issue a token for the user
            user = issueToken(user);

            // Return the token on the response, omit some sensitive information
            User respondUser = user
                    .password(null)
                    .role(null);

            return Response.ok(respondUser).build();

        } catch (ArgumentException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(new DefaultRespondEntity(e.getMessage()))
                    .build();
        } catch (AuthenticationException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new DefaultRespondEntity(e.getMessage()))
                    .build();
        }
    }

    private User authenticate(User u) throws ArgumentException, AuthenticationException {
        if (u.getEmail() == null || u.getPassword() == null)
            throw new ArgumentException();

        Database db = new Database();
        User user = db.getUserByEmail(u.getEmail());
        if (! user.getPassword().equals( u.getPassword() )) {
            throw new AuthenticationException();
        }
        return user;
    }

    private User issueToken(User u) {
        Token token = Token.createToken(u);
        Database db = new Database();
        db.updateToken(token);
        return u;
    }
}
