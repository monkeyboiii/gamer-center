package com.sustech.gamercenter.service;

import com.sustech.gamercenter.config.MyException;
import com.sustech.gamercenter.dao.GameContentRepository;
//import com.sustech.gamercenter.dao.GameDiscountRepository;
import com.sustech.gamercenter.dao.GameRepository;
import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.model.GameContent;
//import com.sustech.gamercenter.model.GameDiscount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameContentRepository gameContentRepository;

//    @Autowired
//    private GameDiscountRepository gameDiscountRepository;

    private UserService userService;

    private final static String STORAGE_PREFIX = System.getProperty("user.dir");

    @Override
    public void purchase(long userId, long gameId) {
        Game game = gameRepository.findById(gameId);
        double price = game.getPrice();
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-mm-dd");
        String datetime = tempDate.format(new java.util.Date());
        if (datetime.compareTo(game.getDiscountStart()) >= 0 && datetime.compareTo(game.getDiscountEnd()) <= 0) {
            price = price * game.getDiscountRate();
        }
        userService.pay(price, userId, game.getDeveloperId());
    }


    @Override
    public void uploadFile(String type, MultipartFile uploadFile, long id) throws IOException {
        String path = File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator
                + "static" + File.separator + "game" + File.separator + type;
        String realPath = STORAGE_PREFIX + path;
        File dir = new File(realPath);
        String filename = System.currentTimeMillis() + uploadFile.getOriginalFilename();
        File fileServer = new File(dir, filename);
        uploadFile.transferTo(fileServer);

        GameContent g = new GameContent();
        g.setName(filename);
        g.setPath(path);
        g.setType(type);
        g.setGameId(id);
        gameContentRepository.save(g);
    }

    @Override
    public byte[] getFile(String url, String type) throws IOException {
        File file = new File(STORAGE_PREFIX + File.separator + "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + "static" + File.separator + "game" + File.separator + type + File.separator + url);
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public boolean existedName(String name, long id) {
        Game game = gameRepository.findByName(name);
        return game != null && game.getId() != id;
    }
//
//    @Override
//    public GameDiscount setDiscount(GameDiscount gameDiscount, long gameId) {
////        gameDiscount.setGame(gameRepository.findById(gameId).get());
//        return gameDiscountRepository.save(gameDiscount);
//    }

    @Override
    public Game findById(long id) {
        return gameRepository.findById(id);
    }

    @Override
    public void download(HttpServletResponse response, String fileName, String type) throws IOException {
        File file = new File(STORAGE_PREFIX + File.separator + "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + "static" + File.separator + "game" + File.separator + type + File.separator + fileName);

        if (!file.exists()) {
            throw new MyException(2, "下载文件不存在");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            throw new MyException(3, "下载失败");
        }
    }

    @Override
    public List<GameContent> findAllByGameId(long gameId) {
        return gameContentRepository.findAllByGameId(gameId);
    }

    @Override
    public List<Game> search(String tag, String name){
        if(tag.equals("") && name.equals("")) return gameRepository.findAll();
        else if(tag.equals("")) return gameRepository.findAllByNameLike(name);
        else if(name.equals("")) return gameRepository.findAllByTag(tag);
        else return gameRepository.findByTagOrNameLike(tag, name);
    }


}
