package com.chapter.foodcourt.insfrastructure.output.jpa.adapter;

import com.chapter.foodcourt.domain.spi.IAuthenticationPort;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuthenticationAdapter implements IAuthenticationPort {
    @Override
    public Integer getAuthenticatedUserId() {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes == null) {
            throw new RuntimeException("No request context available");
        }

        return (Integer) attributes.getRequest().getAttribute("userId");
    }
}
