package com.dongok.hello.jwt;

import com.dongok.hello.dao.AccountCustomerRepository;
import com.dongok.hello.dao.AuthorRepository;
import com.dongok.hello.entity.AccountCustomer;
import com.dongok.hello.entity.Author;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class JwtValidator extends OncePerRequestFilter {
    private Environment environment;
    private AuthorRepository authorRepository;
    private AccountCustomerRepository accountCustomerRepository;
    @Autowired
    public JwtValidator(Environment environment, AuthorRepository authorRepository,AccountCustomerRepository accountCustomerRepository)
    {
        this.environment=environment;
        this.authorRepository=authorRepository;
        this.accountCustomerRepository=accountCustomerRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String token=httpServletRequest.getHeader("Authorization");
        if(token!=null)
        {
            String key=environment.getProperty("token.secret");
            SecretKey secretKey= Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            Claims claims= Jwts.parser().setSigningKey(key.getBytes(Charset.forName("UTF-8")))
                    .parseClaimsJws(token)
                    .getBody();
            int id=(int)claims.get("username");
            String role_id=(String)claims.get("role");

            String role="";
            if(Integer.parseInt(role_id)==2)
            {
                role="ROLE_AUTHOR";
                Author author=authorRepository.findDataById((long) id);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(author.getEmail(),null, AuthorityUtils.commaSeparatedStringToAuthorityList(role)));
            }
            else
            {
                role="ROLE_USER";
                AccountCustomer accountCustomer=accountCustomerRepository.findDataById((long) id);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(accountCustomer.getEmail(),null, AuthorityUtils.commaSeparatedStringToAuthorityList(role)));
            }

        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/news/**");
    }
}
