package com.dongok.hello.service;

import com.dongok.hello.dto.LoginSuccessDTO;
import com.dongok.hello.dto.UpdateAvatarDTO;
import com.dongok.hello.dto.UserInfoDTO;
import com.dongok.hello.dto.UserRegisterDTO;
import com.dongok.hello.entity.AccountCustomer;
import com.dongok.hello.entity.NewsInfo;

import java.util.List;

public interface IAccountCustomerService {
    public AccountCustomer findByEmail(String email);
    public LoginSuccessDTO checkLogin(AccountCustomer accountCustomer,String password);
    public boolean RegisterUser(UserRegisterDTO userRegisterDTO);
    UserInfoDTO GetUserInfo();

    boolean UpdateAvatar(UpdateAvatarDTO updateAvatarDTO);

    boolean AddNewLove(Long id);

    boolean RemoveNewLove(Long id);

    List<NewsInfo> getAllNewsLove();

    boolean CheckExistsLove(Long id);
}
