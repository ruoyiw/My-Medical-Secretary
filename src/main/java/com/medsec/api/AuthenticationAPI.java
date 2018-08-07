package com.medsec.api;

import com.medsec.util.ArgumentException;
import com.medsec.util.AuthenticationException;
import com.medsec.filter.Secured;
import com.medsec.entity.User;
import com.medsec.util.Database;
import com.medsec.util.DefaultRespondEntity;
import com.medsec.util.Token;
import com.medsec.util.UserRole;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.time.Instant;

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
                    .token_valid_from(null)
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

    @POST
    @Secured
    @Path("renewToken")
    @Produces(MediaType.APPLICATION_JSON)
    public Response renewToken(@Context SecurityContext securityContext) {

        // Get ingredient from interceptor (uid and role)
        User user = (User)securityContext.getUserPrincipal();
        String uid = user.getId();
        UserRole role = user.getRole();

        // generate new token
        Token token = Token.createToken(uid, role);

        // respond response entity
        User response = new User()
                .token(token.getToken())
                .token_expire_date(token.getExp());
        return Response.ok(response).build();
    }

    @POST
    @Secured()
    @Path("revokeAll")
    @Produces(MediaType.APPLICATION_JSON)
    public Response revokeAllToken(@Context SecurityContext securityContext) {

        // Get user id from interceptor
        String uid = securityContext.getUserPrincipal().getName();

        // Reset token_valid_from
        Database db = new Database();
        db.updateTokenValidFromDate(uid, Instant.now());
        return Response.ok(new DefaultRespondEntity("Success")).build();
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
        return Token.createTokenForUser(u);
    }
}
