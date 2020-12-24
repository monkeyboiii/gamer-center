package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.GameCommentRepository;
import com.sustech.gamercenter.dao.UserRepository;
import com.sustech.gamercenter.dao.projection.CommentStat;
import com.sustech.gamercenter.model.GameComment;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import com.sustech.gamercenter.util.exception.UserRegisterException;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    GameCommentRepository gameCommentRepository;


    public User createUser(String name, String email, String password, String role) throws UserRegisterException {
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

    public Page<? extends Serializable> getUserList(String filter, String value, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        if (filter.equals("nameLike")) {
            return userRepository.findAllByNameLike(value, pageable);
        } else if (filter.equals("nameStartsWith")) {
            return userRepository.findAllByNameStartsWith(value, pageable);
        } else if (filter.equals("createdAfter")) {
            return userRepository.findAllByCreatedAtAfter(value, pageable);
        } else {
            return userRepository.findAll(pageable);
        }
    }


    //
    //
    //
    //
    // manual


    private static final String STORAGE = System.getProperty("user.dir");
    private static final String MANUAL = STORAGE + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
            + "static" + File.separator + "admin" + File.separator + "manual";

    public void uploadManual(String type, MultipartFile manual) throws IOException {
        File dir = new File(MANUAL);
        String filename = type + "-manual" + manual.getOriginalFilename().split("\\.")[1];
        File fileServer = new File(dir, filename);

        try {
            if (fileServer.exists())
                fileServer.delete();
            manual.transferTo(fileServer);
        } catch (IOException | IllegalStateException e) {
            throw new FileUploadException("Manual of " + type + " cannot be uploaded");
        }
    }

    public static byte[] getManual(String type) throws IOException {
        File path = new File(MANUAL);
        File[] manuals = path.listFiles();
        if (manuals.length == 0) {
            throw new FileNotFoundException("Manual of " + type + " is not ready");
        }

        List<File> valids = Arrays.stream(manuals)
                .filter(x -> x.getName().contains(type + "-manual"))
                .collect(Collectors.toList());
        if (valids.isEmpty()) {
            throw new FileNotFoundException("Manual of " + type + " is not found");
        }
        FileInputStream inputStream = new FileInputStream(valids.get((int) (Math.random() * valids.size())));
        return IOUtils.toByteArray(inputStream);
    }


    //
    //
    //
    //
    // comment


    public void setCommentVisibility(Integer comment_id, Boolean visible) {
        Optional<GameComment> gameComment = gameCommentRepository.findById(comment_id);
        if (gameComment.isPresent()) {
            gameComment.get().setVisible(visible);
            gameCommentRepository.flush();
        } else {
            throw new EntityNotFoundException("Comment of id # " + comment_id.toString() + " is not present");
        }
    }

    public void deleteCommentById(Long comment_id) {
        Optional<GameComment> gameComment = gameCommentRepository.findById(comment_id);
        if (gameComment.isPresent()) {
            gameCommentRepository.commentReportSetResovled(gameComment.get().getId());

//            gameCommentRepository.delete(gameComment.get());
            gameComment.get().setVisible(false);
            gameCommentRepository.flush();
        } else {
            throw new EntityNotFoundException("Comment of id # " + comment_id.toString() + " is not present");
        }
    }

    public GameComment getReportedCommentDetail(Long comment_id) {
        return gameCommentRepository.getOne(comment_id);
    }

    public List<CommentStat> getReportedCommentList(Integer pageNum, Integer pageSize) {
        return gameCommentRepository.getCommentStat(pageNum * pageSize, pageSize);
    }
}
