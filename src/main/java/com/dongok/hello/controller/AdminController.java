package com.dongok.hello.controller;

import com.dongok.hello.dto.NewsDTO;
import com.dongok.hello.dto.TypeDTO;
import com.dongok.hello.entity.NewsType;
import com.dongok.hello.service.IAdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/admin/app")
public class AdminController {


    private IAdminService adminService;
    @PostMapping("/addnewtype")
    public ResponseEntity AddNewType(@RequestBody TypeDTO typeDTO)
    {
        NewsType newsType=adminService.AddNewsType(typeDTO);
        if(newsType!=null)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/confirmauthor")
    public ResponseEntity ConfirmAuthor(@RequestParam String email)
    {

        boolean check=adminService.ConfirmAuthor(email);
        if(check)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
