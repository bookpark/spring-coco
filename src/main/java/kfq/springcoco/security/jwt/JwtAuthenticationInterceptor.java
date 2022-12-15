package kfq.springcoco.security.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        String token = jwtTokenProvider.resolveToken(request);
        // 1
        if (token == null) {
            return true;
        }

        String[] tokens = token.split(",");

        // 2
        if (jwtTokenProvider.validateToken(tokens[0])) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;

            // 3
        } else if (tokens.length == 1) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"rescode\":100}");
            response.getWriter().flush();

            // 4
        } else if (jwtTokenProvider.validateToken(tokens[1])) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String email = jwtTokenProvider.getEmail(tokens[1]);
            String accessToken = jwtTokenProvider.createToken(email);
            String refreshToken = jwtTokenProvider.refreshToken(email);
            response.getWriter().write("{\"rescode\":101,\"accessToken\":\"" + accessToken + "\"," +
                    "\"refreshToken\":\"" + refreshToken + "\"}");
            response.getWriter().flush();

            // 5
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"rescode\":102}");
            response.getWriter().flush();
        }
        return false;

    }
}
