package com.sustech.gamercenter.service;

import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.model.GameDiscount;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface GameService {

    void uploadFile(String type, MultipartFile uploadFile, long id)throws IOException;

    Game save(Game game);

    boolean existedName(String name);

    GameDiscount setDiscount(GameDiscount gameDiscount, long gameId);

//    public Object findById(long id);
}
