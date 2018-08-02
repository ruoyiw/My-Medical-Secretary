package com.medsec.Interceptor;

import com.medsec.AuthenticationException;
import com.medsec.entity.User;
import com.medsec.util.Database;
import com.medsec.util.DefaultRespondEntity;
import com.medsec.util.Token;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecuredInterceptor implements ContainerRequestFilter {

    private static final String REALM = "Project_ME";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {
            // Validate the token
            Token verifiedToken = Token.claimToken(token);

            Database db = new Database();
            User user = db.getUserById(verifiedToken.getUid());

            // JTI must match. Otherwise the token has been revoked.
            if (!verifiedToken.getUuid().equals(user.getToken())) {
                throw new AuthenticationException(AuthenticationException.TOKEN_EXPIRED);
            }

        } catch (AuthenticationException e) {
            abortWithUnauthorized(e, requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {
        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(AuthenticationException e, ContainerRequestContext requestContext) {
        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        Response.ResponseBuilder response = Response.status(Response.Status.UNAUTHORIZED)
                .header(HttpHeaders.WWW_AUTHENTICATE,
                        AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"");
        if (e != null)
            response.entity(new DefaultRespondEntity(e.getMessage()));
        requestContext.abortWith(response.build());
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {
        abortWithUnauthorized(null, requestContext);
    }
}
