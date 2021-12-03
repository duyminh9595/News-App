package com.dongok.hello.controller;

import com.dongok.hello.config.AuthorUserDetailService;
import com.dongok.hello.dao.AuthorRepository;
import com.dongok.hello.dao.NewsInfoRepository;
import com.dongok.hello.dao.NewsTypeRepository;
import com.dongok.hello.dao.VideoRepository;
import com.dongok.hello.dto.*;
import com.dongok.hello.entity.Author;
import com.dongok.hello.entity.NewsInfo;
import com.dongok.hello.entity.Video;
import com.dongok.hello.jwt.JwtGeneration;
import com.dongok.hello.service.IAuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.util.Strings;
import lombok.AllArgsConstructor;
import org.apache.http.auth.AUTH;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
@RequestMapping("/author/app")
public class AuthorController {

    private IAuthorService iAuthorService;
    private JwtGeneration jwtGeneration;
    private final AuthenticationManager authenticationManager;
    private final AuthorUserDetailService authorUserDetailService;
    private final VideoRepository videoRepository;

    @PostMapping("/addnews")
    public ResponseEntity AddNewsToDb(@RequestBody NewsDTO newsDTO)
    {
        boolean newsInfo=iAuthorService.generateNew(newsDTO);
        if(newsInfo)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/addvideo")
    public ResponseEntity AddVideoToDb(@RequestBody AddVideoDTO addVideoDTO)
    {
        boolean newsInfo=iAuthorService.generateVideo(addVideoDTO);
        if(newsInfo)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/register")
    public ResponseEntity  AuthorRegister(@RequestBody AuthorRegisterDTO authorRegisterDTO)
    {
        Author author=iAuthorService.registerAuthor(authorRegisterDTO);
        if(author!=null)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/login")
    public ResponseEntity  AuthorLogin(@RequestBody AuthorLoginDTO authorLoginDTO) throws UnsupportedEncodingException {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorLoginDTO.getEmail(),authorLoginDTO.getPassword()));
        String jwt=jwtGeneration.tokenJwt(authentication,authorLoginDTO.getEmail());
        if(!Strings.isNullOrEmpty(jwt))
        {
            TokeDTO tokeDTO=new TokeDTO();
            tokeDTO.setToken(jwt);
            tokeDTO.setEmail("jwtGeneration.getUserNameFromJwtToken(jwt)");
            return ResponseEntity.ok(tokeDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
