package com.sustech.gamercenter.service;

import com.sustech.gamercenter.api.GameContentRepository;
import com.sustech.gamercenter.api.GameDiscountRepository;
import com.sustech.gamercenter.api.GameRepository;
import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.model.GameContent;
import com.sustech.gamercenter.model.GameDiscount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class GameServiceImpl implements GameService{

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameContentRepository gameContentRespository;

    @Autowired
    private GameDiscountRepository gameDiscountRepository;

    private final static String STORAGE_PREFIX = System.getProperty("user.dir");

    @Override
    public void uploadFile(String type, MultipartFile uploadFile, long id) throws IOException {
        String path = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "game" +File.separator+ type;
        String realPath = STORAGE_PREFIX + path;
        File dir = new File(realPath);
        String filename = System.currentTimeMillis() + uploadFile.getOriginalFilename();
        File fileServer = new File(dir, filename);
        uploadFile.transferTo(fileServer);

        GameContent g = new GameContent();
        g.setName(filename);
        g.setPath(path);
        g.setType(type);
        g.setGame_id(id);
//        g.setGame(gameRepository.findById(id).get());
        gameContentRespository.save(g);
    }

    @Override
    public Game save(Game game){
        return gameRepository.save(game);
    }

    @Override
    public boolean existedName(String name){ return gameRepository.findByName(name) != null; }


    @Override
    public GameDiscount setDiscount(GameDiscount gameDiscount, long gameId){
//        gameDiscount.setGame(gameRepository.findById(gameId).get());
        return gameDiscountRepository.save(gameDiscount);
    }

}
