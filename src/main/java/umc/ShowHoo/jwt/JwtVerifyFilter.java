package umc.ShowHoo.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import umc.ShowHoo.apiPayload.exception.GeneralException;

import java.io.IOException;

public class JwtVerifyFilter extends OncePerRequestFilter {
    private static final String[] whitelist = {"/login/oauth2/code/kakao", "/kakao", "/h2-console/**"};

    private final JwtTokenProvider jwtTokenProvider;

    public JwtVerifyFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestURI = request.getRequestURI();
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        try {
            JwtTokenProvider.checkAuthorizationHeader(header);
            String token = header.substring(7);

            if (!jwtTokenProvider.validateToken(token)) {
                throw new GeneralException("The token is not valid.");
            }

            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
        } catch (GeneralException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write("{\"message\": \"" + e.getMessage() + "\", \"code\": \"UNAUTHORIZED\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
