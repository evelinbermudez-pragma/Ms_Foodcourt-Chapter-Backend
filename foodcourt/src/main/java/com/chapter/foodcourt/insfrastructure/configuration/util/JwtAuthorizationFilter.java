package com.chapter.foodcourt.insfrastructure.configuration.util;

import com.chapter.foodcourt.insfrastructure.configuration.RolesConfig;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final RolesConfig rolesConfig;  // ← inyecta roles desde yaml

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String endpoint = request.getRequestURI();

        try {
            String token = getToken(request);
            if (token == null || !jwtUtil.validateJwtToken(token)) {
                sendUnauthorizedError(response);
                return;
            }

            List<String> roles = jwtUtil.getRoles(token);
            if (!isRoleAuthorizedForEndpoint(roles, endpoint)) {
                sendUnauthorizedError(response);
                return;
            }

            Integer userId = jwtUtil.getUserId(token);
            request.setAttribute("userId", userId);
            filterChain.doFilter(request, response);

        } catch (RuntimeException e) {
            sendUnauthorizedError(response, e.getMessage());
        }
    }

    private Map<String, List<String>> buildRolesMap() {
        Map<String, List<String>> map = new HashMap<>();

        map.put("ROLE_" + rolesConfig.getAdmin(), Arrays.asList(
                "/restaurant/create",
                "/restaurant/{id}"
        ));
        map.put("ROLE_" + rolesConfig.getOwner(), Arrays.asList(
                "/restaurant/{id}",
                "/dish/create",
                "/dish/{id}",
                "/dish/state/{id}",
                "/dish/{id}/toggle",
                "/dish/update/{id}",
                "/restaurant/create/employee"
        ));
        map.put("ROLE_" + rolesConfig.getClient(), Arrays.asList(
                "/order",
                "/order/cancel/{orderId}",
                "/restaurant/listRestaurants",
                "/dish/restaurant/{restaurantId}"
        ));
        map.put("ROLE_" + rolesConfig.getEmployee(), Arrays.asList(
                "/order/state/{state}",
                "/order/assign/{orderId}",
                "/order/ready/{orderId}",
                "/order/deliver/{orderId}"
        ));

        return map;
    }

    private boolean isRoleAuthorizedForEndpoint(List<String> roles, String endpoint) {
        Map<String, List<String>> rolesMap = buildRolesMap();
        for (String role : roles) {
            List<String> roleEndpoints = rolesMap.get(role);
            if (roleEndpoints != null) {
                for (String allowedEndpoint : roleEndpoints) {
                    if (isEndpointMatch(allowedEndpoint, endpoint)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isEndpointMatch(String allowedEndpoint, String actualEndpoint) {
        Pattern pattern = Pattern.compile(allowedEndpoint
                .replaceAll("\\{id\\}", "[^/]+")
                .replaceAll("\\{restaurantId\\}", "[^/]+")
                .replaceAll("\\{state\\}", "[^/]+")
                .replaceAll("\\{orderId\\}", "[^/]+")
        );
        return pattern.matcher(actualEndpoint).matches();
    }

    private String getToken(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private void sendUnauthorizedError(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

    private void sendUnauthorizedError(HttpServletResponse response, String message) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), message);
    }
}