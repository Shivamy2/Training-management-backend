package com.trainingmanagementserver.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.trainingmanagementserver.entity.FileDB;
import com.trainingmanagementserver.exception.ApiRequestException;
import com.trainingmanagementserver.message.ResponseFile;
import lombok.Data;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Data
public class Utility implements UtilitySkeleton{
    @Override
    public String getUsernameFromToken(HttpServletRequest request) throws ApiRequestException {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(token);
                return decodedJWT.getSubject();
            }
        throw new ApiRequestException("Token invalid");
    }

    @Override
    public ResponseFile getResponseFile(FileDB file) {
        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/files/")
                .path(Integer.toString(file.getId()))
                .toUriString();
        return (new ResponseFile(file.getName(),fileDownloadUri, file.getType(), file.getData().length));
    }
}


