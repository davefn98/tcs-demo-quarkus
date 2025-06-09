package com.tcs.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;

import io.smallrye.jwt.build.Jwt;

@Path("/test-key")
public class KeyTestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/public")
    public String testPublicKey() {
        return loadKeyFromClasspath("public_key.pem");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/private")
    public String testPrivateKey() {
        return loadKeyFromClasspath("private_key.pem");
    }

    private String loadKeyFromClasspath(String fileName) {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream(fileName)) {

            if (is == null) {
                return "❌ No se encontró " + fileName + " en el classpath.";
            }

            byte[] bytes = is.readAllBytes();
            String key = new String(bytes, StandardCharsets.UTF_8);
            return "✅ Clave " + fileName + " cargada correctamente:\n\n" + key;

        } catch (Exception e) {
            return "❌ Error al leer " + fileName + ": " + e.getMessage();
        }
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/generate-token")
    public String generateJwtToken() {
        try {
            // Cargar clave privada
            InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("private_key.pem");

            if (is == null) {
                return "❌ private_key.pem no encontrado en el classpath.";
            }

            String keyPem = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(keyPem);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(spec);

            // Construir token
            //String jwt = Jwt.claims()
            //.claim("sub", "david")
            //        .claim("iss", "quarkus-app")
            //        .claim("exp", (System.currentTimeMillis() / 1000) + 3600) // 1 hora
            //        .sign(privateKey);

            String jwt = Jwt.claims()
                .claim("sub", "juan")
                .claim("iss", "quarkus-app")
                .claim("groups", List.of("user"))
                .claim("exp", (System.currentTimeMillis() / 1000) + 3600)
                .sign(privateKey);

            
            return "✅ JWT generado:\n\n" + jwt;

        } catch (Exception e) {
            return "❌ Error generando token: " + e.getMessage();
        }
    }

    @GET
    @Path("/generate-admin-token")
    @Produces(MediaType.TEXT_PLAIN)
    public String generateAdminJwtToken() {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("private_key.pem")) {

            if (is == null) return "❌ No se encontró private_key.pem.";

            String keyPem = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(keyPem);
            PrivateKey privateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));

            String jwt = Jwt.claims()
                    .claim("sub", "admin-user")
                    .claim("iss", "quarkus-app")
                    .claim("groups", List.of("admin"))
                    .claim("exp", (System.currentTimeMillis() / 1000) + 3600)
                    .sign(privateKey);

            return "✅ JWT Admin generado:\n\n" + jwt;

        } catch (Exception e) {
            return "❌ Error generando admin token: " + e.getMessage();
        }
    }

    @GET
    @Path("/generate-multi-role-token")
    @Produces(MediaType.TEXT_PLAIN)
    public String generateMultiRoleToken() {
        try (InputStream is = Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("private_key.pem")) {

            if (is == null) return "❌ No se encontró private_key.pem.";

            String keyPem = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s", "");

            byte[] keyBytes = Base64.getDecoder().decode(keyPem);
            PrivateKey privateKey = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(keyBytes));

            String jwt = Jwt.claims()
                    .claim("sub", "multi-role-user")
                    .claim("iss", "quarkus-app")
                    .claim("groups", List.of("user", "admin"))
                    .claim("exp", (System.currentTimeMillis() / 1000) + 3600)
                    .sign(privateKey);

            return "✅ JWT multirol generado:\n\n" + jwt;

        } catch (Exception e) {
            return "❌ Error generando token: " + e.getMessage();
        }
    }
}
