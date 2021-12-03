package com.dongok.hello.config;

import com.dongok.hello.dao.AuthorRepository;
import com.dongok.hello.entity.Author;
import lombok.AllArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class AuthorUserDetailService implements UserDetailsService {

    private AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Author author=authorRepository.findData(username);
        if(author==null)
        {
            throw new UsernameNotFoundException("Could not found email");
        }
        return new AuthorUserDetail(author);
    }
}
