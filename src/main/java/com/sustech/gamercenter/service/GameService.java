package com.sustech.gamercenter.service;

import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.model.GameContent;
//import com.sustech.gamercenter.model.GameDiscount;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface GameService {
    void purchase(long userId, long gameId);

    void uploadFile(String type, MultipartFile uploadFile, long id) throws IOException;

    Game save(Game game);

    boolean existedName(String name, long id);

//    GameDiscount setDiscount(GameDiscount gameDiscount, long gameId);

    Game findById(long id);

    List<GameContent> findAllByGameId(long gameId);

    byte[] getFile(String url, String type) throws IOException;

    void download(HttpServletResponse response, String fileName, String type) throws IOException;

    List<Game> search(String tag, String name, int page);

//    public Object findById(long id);
}
