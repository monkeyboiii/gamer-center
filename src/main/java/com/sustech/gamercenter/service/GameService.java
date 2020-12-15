package com.sustech.gamercenter.service;

import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.model.GameContent;
import com.sustech.gamercenter.util.exception.InsufficientBalanceException;
import com.sustech.gamercenter.util.exception.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//import com.sustech.gamercenter.model.GameDiscount;

public interface GameService {
    void purchase(long userId, long gameId) throws UserNotFoundException, InsufficientBalanceException;

    void uploadFile(String type, MultipartFile uploadFile, long id) throws IOException;

    Game save(Game game);

    boolean existedName(String name, long id);

//    GameDiscount setDiscount(GameDiscount gameDiscount, long gameId);

    Game findById(long id);

    List<GameContent> findAllByGameId(long gameId);

    byte[] getFile(String url, String type) throws IOException;

    void download(HttpServletResponse response, String fileName, String type) throws IOException;

    Page<Game> search(String tag, String name, int page);

    void cloudUpload(long gameId, long userId, MultipartFile uploadFile) throws IOException;

    void cloudDownload(HttpServletResponse response, long gameId, long userId, String fileName) throws IOException;

    void getCloudList(HttpServletResponse response, long gameId, long userId) throws IOException;
//    public Object findById(long id);
}
