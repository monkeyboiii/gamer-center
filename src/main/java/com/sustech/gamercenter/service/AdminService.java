package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.UnauthorizedAttemptException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.exception.UserRegisterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    SimpleTokenService tokenService;

    @Autowired
    PasswordEncoder encoder;


    public User createUser(String name, String email, String password, String role) throws UserRegisterException, UnauthorizedAttemptException {
        try {
            password = encoder.encode(password);
            User user = new User(name, email, password, role);
            userRepository.save(user);
            return user;
        } catch (Exception e) {
            throw new UserRegisterException(e.getClass().getSimpleName(), e);
        }
    }

    public void assignRole(Long id, String role) throws UserNotFoundException {
        User user = userService.queryUserById(id);
        user.setRole(role);
        userRepository.flush();
    }

    public void lockOrUnlockAccount(String method, String value, Boolean lock) throws UserNotFoundException {
        User user;
        if (method.equals("id")) {
            user = userService.queryUserById(Long.valueOf(value));
        } else if (method.equals("name")) {
            user = userService.queryUserByName(value);
        } else {
            user = userService.queryUserByEmail(value);
        }
        user.setLocked(lock);
        userRepository.flush();
    }

    //    public List<UserView> getUserList(String filter, String value, Integer pageNum, Integer pageSize) {
    public void getUserList(String filter, String value, Integer pageNum, Integer pageSize) {
        // TODO

        userRepository.findAll();
    }


    //
    //
    //
    //
    //


    private final static String STORAGE_PREFIX = System.getProperty("user.dir");
    private final static Logger logger = LoggerFactory.getLogger(AdminService.class);


    public byte[] getManual(String id) throws IOException {
        String path = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "admin" + File.separator + "manual";
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    public void uploadManual(String type, MultipartFile manual) throws IOException {
//        if(manual.getContentType().contains("exe")){
//
//        }
        // todo check malicious content

        String path = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "admin" + File.separator + "manual";
        String realPath = STORAGE_PREFIX + path;
        File dir = new File(realPath);

//        String filename = type + "_manual_" + manual.getOriginalFilename();
        String filename = "manual_" + manual.getOriginalFilename();
        File fileServer = new File(dir, filename);
        manual.transferTo(fileServer);
    }

}
