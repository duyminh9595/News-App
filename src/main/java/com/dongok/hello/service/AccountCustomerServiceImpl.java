package com.dongok.hello.service;

import com.dongok.hello.dao.AccountCustomerRepository;
import com.dongok.hello.dao.LoveRepository;
import com.dongok.hello.dao.NewsInfoRepository;
import com.dongok.hello.dto.LoginSuccessDTO;
import com.dongok.hello.dto.UpdateAvatarDTO;
import com.dongok.hello.dto.UserInfoDTO;
import com.dongok.hello.dto.UserRegisterDTO;
import com.dongok.hello.entity.AccountCustomer;
import com.dongok.hello.entity.Love;
import com.dongok.hello.entity.NewsInfo;
import com.dongok.hello.jwt.JwtGeneration;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.persistence.EntityNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class AccountCustomerServiceImpl implements IAccountCustomerService{

    private AccountCustomerRepository accountCustomerRepository;
    private PasswordEncoder passwordEncoder;
    private NewsInfoRepository newsInfoRepository;
    private JwtGeneration jwtGeneration;
    private AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper;
    private LoveRepository   loveRepository;
    private Environment environment;

    @Override
    public AccountCustomer findByEmail(String email) {
        AccountCustomer accountCustomer=accountCustomerRepository.findByEmail(email);
        return accountCustomer;
    }


    @Override
    public LoginSuccessDTO checkLogin(AccountCustomer accountCustomer,String password) {
        if(passwordEncoder.matches(password,accountCustomer.getPassword()))
        {
            LoginSuccessDTO loginSuccessDTO=new LoginSuccessDTO();
            String key=environment.getProperty("token.secret");
            SecretKey secretKey= Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
            loginSuccessDTO.setToken(
                    Jwts.builder()
                            .setSubject("1")
                            .claim("username",accountCustomer.getId())
                            .claim("role","1")
                            .setIssuedAt(new Date())
                            .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(Long.parseLong(environment.getProperty("token.expirationday")))))
                            .signWith(SignatureAlgorithm.HS512, key.getBytes(Charset.forName("UTF-8")))
                            .compact());
            return loginSuccessDTO;
        }
        return null;
    }

    @Override
    public boolean RegisterUser(UserRegisterDTO userRegisterDTO) {
        AccountCustomer accountCustomer=objectMapper.convertValue(userRegisterDTO,AccountCustomer.class);
        accountCustomer.setFullName(userRegisterDTO.getFull_name());
        AccountCustomer accountInDb=accountCustomerRepository.findByEmail(accountCustomer.getEmail());
        if(accountInDb==null)
        {
            accountCustomer.setConfirm_email(false);
            accountCustomer.setPassword(passwordEncoder.encode(accountCustomer.getPassword()));
            accountCustomerRepository.save(accountCustomer);
            return true;
        }
        return false;
    }

    @Override
    public UserInfoDTO GetUserInfo() {
        AccountCustomer accountCustomer=accountCustomerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(accountCustomer!=null)
        {
            UserInfoDTO userInfoDTO=new UserInfoDTO();
            userInfoDTO.setEmail(accountCustomer.getEmail());
            userInfoDTO.setUsername(accountCustomer.getFullName());
            userInfoDTO.setId(accountCustomer.getId());
            userInfoDTO.setImage_url(accountCustomer.getImage_url());
            return userInfoDTO;
        }
        return null;
    }

    @Override
    public boolean UpdateAvatar(UpdateAvatarDTO updateAvatarDTO) {
        AccountCustomer accountCustomer=accountCustomerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(accountCustomer!=null)
        {
            accountCustomer.setImage_url(updateAvatarDTO.getImageurl());
            accountCustomerRepository.save(accountCustomer);
            return true;
        }
        return false;
    }

    @Override
    public boolean AddNewLove(Long id) {
        AccountCustomer accountCustomer=accountCustomerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        NewsInfo newsInfo=newsInfoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));
        if(accountCustomer!=null && newsInfo!=null)
        {
            Love lovedb=loveRepository.findByUserIdAndNewId(newsInfo.getId(),accountCustomer.getId());
            if(lovedb!=null)
            {
                lovedb.setStatus(true);
                lovedb.setDateCreated(new Date());
                loveRepository.save(lovedb);
                return true;
            }
            Love love=new Love();
            love.setDateCreated(new Date());
            accountCustomer.AddLove(love);
            newsInfo.AddLove(love);
            love.setStatus(true);
            loveRepository.save(love);
            return true;
        }
        return false;
    }

    @Override
    public boolean RemoveNewLove(Long id) {
        AccountCustomer accountCustomer=accountCustomerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        NewsInfo newsInfo=newsInfoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id.toString()));

        if(accountCustomer!=null && newsInfo!=null)
        {
            Love love=loveRepository.findByUserIdAndNewId(newsInfo.getId(),accountCustomer.getId());
            if(love!=null)
            {
                love.setStatus(false);
                love.setDateCreated(new Date());
                loveRepository.save(love);
                return true;
            }
            return  false;
        }
        return false;
    }

    @Override
    public List<NewsInfo> getAllNewsLove() {
        AccountCustomer accountCustomer=accountCustomerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<NewsInfo>newsInfos=new ArrayList<>();
        if(accountCustomer!=null)
        {
            newsInfos=loveRepository.findByUserIdLolve(accountCustomer.getId(),true);
            return newsInfos;
        }
        return newsInfos;
    }

    @Override
    public boolean CheckExistsLove(Long id) {
        AccountCustomer accountCustomer=accountCustomerRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        if(accountCustomer!=null)
        {
            Love love=loveRepository.findByUserIdLolveAndNewId(accountCustomer.getId(),true,id);
            if(love!=null)
                return true;
            return false;
        }
        return false;
    }
}
