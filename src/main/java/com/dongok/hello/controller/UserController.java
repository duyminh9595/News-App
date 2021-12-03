package com.dongok.hello.controller;

import com.dongok.hello.dto.*;
import com.dongok.hello.entity.AccountCustomer;
import com.dongok.hello.entity.NewsInfo;
import com.dongok.hello.service.IAccountCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user/app")
public class UserController {
    private IAccountCustomerService iAccountCustomerService;
    @PostMapping("/login")
    public ResponseEntity UserLogin(@RequestBody UserLoginDTO userLoginDTO)
    {
        AccountCustomer accountCustomer=iAccountCustomerService.findByEmail(userLoginDTO.getEmail());
        if(accountCustomer!=null)
        {
            return ResponseEntity.ok(iAccountCustomerService.checkLogin(accountCustomer,userLoginDTO.getPassword()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/register")
    public ResponseEntity UserRegister(@RequestBody UserRegisterDTO userRegisterDTO)
    {
        boolean checkRegister=iAccountCustomerService.RegisterUser(userRegisterDTO);
        if(checkRegister) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("/getinfo")
    public ResponseEntity InforUser()
    {
        UserInfoDTO userInfoDTO=iAccountCustomerService.GetUserInfo();
        if(userInfoDTO!=null)
        {
            return ResponseEntity.ok(userInfoDTO);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/updateavatar")
    public ResponseEntity UpdateUserAvatar(@RequestBody UpdateAvatarDTO updateAvatarDTO)
    {
        boolean check=iAccountCustomerService.UpdateAvatar(updateAvatarDTO);
        if(check)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/addlove")
    public ResponseEntity UserAddLove(@RequestParam Long id)
    {
        boolean check=iAccountCustomerService.AddNewLove(id);
        if(check)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/removelove")
    public ResponseEntity UserRemoveLove(@RequestParam Long id)
    {
        boolean check=iAccountCustomerService.RemoveNewLove(id);
        if(check)
        {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/danhsachlove")
    public ResponseEntity DanhSachUserLove()
    {
        List<NewsInfo> newsInfoList=iAccountCustomerService.getAllNewsLove();
        List<ReadNewDTO>readNewDTOList=new ArrayList<>();
        for(NewsInfo newsInfo:newsInfoList )
        {
            ReadNewDTO readNewDTO=new ReadNewDTO();
            readNewDTO.setId(newsInfo.getId());
            readNewDTO.setIdtype(newsInfo.getType().getId());
            readNewDTO.setDate_Created(newsInfo.getDate_Created().toString());
            readNewDTO.setAuthor(newsInfo.getAuthor().getEmail());
            readNewDTO.setTitle(newsInfo.getTitle());
            readNewDTO.setNumber_view(newsInfo.getNumber_view());
            readNewDTO.setImageThumbnail(newsInfo.getImageThumbnail());
            readNewDTO.setNumber_view(newsInfo.getNumber_view());
            readNewDTOList.add(readNewDTO);
        }
        return ResponseEntity.ok(readNewDTOList);
    }
    @GetMapping("/checklove")
    public ResponseEntity CheckUserLoveOrUnlove(@RequestParam Long id)
    {
        boolean isExists=iAccountCustomerService.CheckExistsLove(id);
        if(isExists)
            return ResponseEntity.ok().build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
