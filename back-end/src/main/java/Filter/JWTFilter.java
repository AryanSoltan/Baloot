package Filter;

import JWTTokenHandler.JwtTokenUtil;
import controllers.baloot.ReposnsePackage.Response;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Order;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Order(1)
@Component
@ComponentScan(basePackages ={"controllers.baloot"})
public class JWTFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var pathInfo = request.getServletPath();
        if(pathInfo.contains("login") || pathInfo.contains("signup") || pathInfo.contains("usernameForLogin") || pathInfo.contains("authorized"))
        {
            filterChain.doFilter(request, response);
            return;
        }
        String jwtToken = request.getHeader("Authorization");
        System.out.println("HELLOOO WORLD");
        System.out.println(jwtToken);
        if(jwtToken == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt token not found");
        }
        if(JwtTokenUtil.isTokenExpired(jwtToken))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt token expired");
        }
        if(!JwtTokenUtil.validateTokenSigneture(jwtToken))
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "jwt token signeture false");
        }
        filterChain.doFilter(request, response);
    }
}
