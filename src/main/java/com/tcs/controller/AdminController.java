package com.tcs.controller;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.inject.Inject;

@RolesAllowed("admin")
@Path("/admin-only")
public class AdminController {

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String adminAccess() {
        return "🔐 Acceso permitido para: " + jwt.getName() + " | Roles: " + jwt.getGroups();
    }
}