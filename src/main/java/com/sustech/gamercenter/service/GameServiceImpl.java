package com.sustech.gamercenter.service;

import com.sustech.gamercenter.dao.GameContentRepository;
import com.sustech.gamercenter.dao.GameDLCRepository;
import com.sustech.gamercenter.dao.GameRepository;
import com.sustech.gamercenter.model.Game;
import com.sustech.gamercenter.model.GameContent;
import com.sustech.gamercenter.model.GameDLC;
import com.sustech.gamercenter.model.User;
import com.sustech.gamercenter.service.token.SimpleTokenService;
import com.sustech.gamercenter.util.exception.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

//import com.sustech.gamercenter.dao.GameDiscountRepository;
//import com.sustech.gamercenter.model.GameDiscount;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameContentRepository gameContentRepository;

    @Autowired
    private GameDLCRepository gameDLCRepository;

    @Autowired
    private SimpleTokenService tokenService;

//    @Autowired
//    private GameDiscountRepository gameDiscountRepository;

    @Autowired
    private UserService userService;


    @Override
    public Game findById(long id) {
        return gameRepository.findById(id);
    }

    @Override
    public List<GameContent> findAllByGameId(long gameId) {
        return gameContentRepository.findAllByGameId(gameId);
    }

    @Override
    public Game save(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public boolean existedName(String name, long id) {
        Game game = gameRepository.findByName(name);
        return (game != null && game.getId() != id);
    }


/*    @Override
    public GameDiscount setDiscount(GameDiscount gameDiscount, long gameId) {
//        gameDiscount.setGame(gameRepository.findById(gameId).get());
        return gameDiscountRepository.save(gameDiscount);
    }*/


    //
    //
    //
    //
    // important


    @Override
    public Page<Game> search(String tag, String name, int page) {
        Pageable pageable = PageRequest.of(page, 9);
        Page<Game> games;

        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
        String today = tempDate.format(new java.util.Date());

        if (tag.equals("") && name.equals("")) games = gameRepository.findAllGame(today, pageable);
        else if (tag.equals("")) games = gameRepository.findAllByNameLike(name, today, pageable);
        else if (name.equals("")) games = gameRepository.findAllByTag(tag, today, pageable);
        else games = gameRepository.findByTagOrNameLike(tag, name, today, pageable);
        return games;

    }


    @Override
    public void purchase(long userId, long gameId) throws UserNotFoundException, InsufficientBalanceException {
        Game game = gameRepository.findById(gameId);
        double price = game.getPrice();
        SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");
        String datetime = tempDate.format(new java.util.Date());
        if (datetime.compareTo(game.getDiscount_start()) >= 0 && datetime.compareTo(game.getDiscount_end()) <= 0) {
            price = price * game.getDiscount_rate();
        }

        Long devId = game.getDeveloperId();
        userService.purchaseGame(userId, devId, gameId, price);
    }


    //
    //
    //
    //
    // file-related, upload, download


    private final static String STORAGE_PREFIX = System.getProperty("user.dir");
    private final static String RESOURCE_STATIC = STORAGE_PREFIX + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static";
    private final static String RELATIVE_GAME = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "game";
    private final static String GAME = RESOURCE_STATIC + File.separator + "game";
    private final static String CLOUD = RESOURCE_STATIC + File.separator + "cloud";
    private final static String GAME_DLC = GAME + File.separator + "dlc";


    @Override
    public void uploadFile(String type, MultipartFile uploadFile, long id) throws IOException {
        String path = GAME + File.separator + type;
        File dir = new File(path);
        String filename = System.currentTimeMillis() + uploadFile.getOriginalFilename();
        File fileServer = new File(dir, filename);
        String relative = RELATIVE_GAME + File.separator + filename;
        if (!fileServer.exists()) {
            fileServer.createNewFile();
        }
        uploadFile.transferTo(fileServer);


        GameContent g = new GameContent();
        g.setName(filename);
        g.setPath(relative);
        g.setType(type);
        g.setGameId(id);
        gameContentRepository.save(g);
        if (type.equals("image")) {
            Game game = gameRepository.findById(id);
            game.setFront_image(filename);
            gameRepository.save(game);
        }
    }


    @Override
    public void download(HttpServletResponse response, String fileName, String type) {
        File file = new File(GAME + File.separator + type + File.separator + fileName);
        getFile(response, file, fileName);
    }


    @Override
    public byte[] getFile(String url, String type) throws IOException {
        File file = new File(GAME + File.separator + type + File.separator + url);
        FileInputStream inputStream = new FileInputStream(file);
        return IOUtils.toByteArray(inputStream);
    }


    public void getFile(HttpServletResponse response, File file, String fileName) {
        if (!file.exists()) {
            throw new MyException(-1, "File doesn't exist.");
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            throw new MyException(-1, "Download fail.");
        }
    }


    //cloud


    @Override
    public void cloudUpload(long gameId, long userId, MultipartFile uploadFile) throws IOException {
        String path = CLOUD + File.separator + gameId;
        File dir = new File(path);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdir();
        }
        path = path + File.separator + userId;
        dir = new File(path);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdir();
        }
        String filename = uploadFile.getOriginalFilename();
        File fileServer = new File(dir, filename);
        uploadFile.transferTo(fileServer);
    }

    @Override
    public void cloudDownload(HttpServletResponse response, long gameId, long userId, String fileName) {
        String path = CLOUD + File.separator + gameId + File.separator + userId + File.separator + fileName;
        File file = new File(path);
        getFile(response, file, fileName);
    }

    @Override
    public void getCloudList(HttpServletResponse response, long gameId, long userId) throws IOException {
        String path = CLOUD + File.separator + gameId + File.separator;
        String txtPath = path + "tmp.txt";
        path = path + userId;
        File file = new File(txtPath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        file = new File(path);
        FileWriter fileWriter = new FileWriter(txtPath);
        if (file.exists()) {
            String[] filelist = file.list();
            for (int i = 0; i < filelist.length; i++) {
                fileWriter.write(filelist[i] + "\r\n");
            }
        }
        fileWriter.flush();
        fileWriter.close();
        file = new File(txtPath);
        getFile(response, file, userId + ".txt");
    }


    @Override
    public List<String> listCloudSave(Long gameId, Long userId) throws FileNotFoundException {
        String path = CLOUD + File.separator + gameId + File.separator + userId + File.separator;
        File file = new File(path);
        if (file.exists()) {
            return Arrays.stream(file.list()).collect(Collectors.toList());
        } else {
            throw new FileNotFoundException("No cloud record found");
        }
    }


    //
    //
    //
    //
    // DLC


    @Override
    public GameDLC createDLC(String token, Long game_id, String name, Double price, Boolean visible) throws IllegalArgumentException, InvalidTokenException {
        Game game = gameRepository.getOne(game_id);
        if (game.getDeveloperId() != tokenService.getIdByToken(token)) {
            throw new IllegalArgumentException("Game not belonging to you");
        }

        GameDLC gameDLC = new GameDLC(game_id, name, price, visible);
        gameDLCRepository.save(gameDLC);
        gameDLCRepository.flush();
        return gameDLC;
    }


    @Override
    public List<GameDLC> getGameDLCs(Long game_id) {
        return gameDLCRepository.findAllByGameId(game_id);
    }


    @Override
    public void uploadDLCContent(Long id, MultipartFile file) throws IOException {
        GameDLC gameDLC = gameDLCRepository.getOne(id);
        Long game_id = gameDLC.getId();
        String ext = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];

        String path = game_id + "-" + gameDLC.getName() + "." + ext;
        File content = new File(GAME_DLC + File.separator + path);
        gameDLC.setPath(path);

        if (content.exists()) {
            content.delete();
        }
        file.transferTo(content);
        gameDLCRepository.flush();
    }


    @Override
    public byte[] downloadDLCContent(String token, Long id) throws InvalidTokenException, ContentNotPurchasedException, IOException {
        Long user_id = tokenService.getIdByToken(token);
        if (gameDLCRepository.validateUserHasDLC(user_id, id) == 0) {
            throw new ContentNotPurchasedException("DLC not purchased for download");
        }

        GameDLC gameDLC = gameDLCRepository.getOne(id);
        if (StringUtils.isEmpty(gameDLC.getPath())) {
            throw new FileNotFoundException("DLC hasn't been uploaded");
        }

        File file = new File(GAME_DLC + File.separator + gameDLC.getPath());
        InputStream inputStream = new FileInputStream(file);
        return IOUtils.toByteArray(inputStream);
    }


    @Override
    public void purchaseDLC(String token, Long id) throws InvalidTokenException, UserNotFoundException, InsufficientBalanceException {
        User user = userService.queryUserById(tokenService.getIdByToken(token));
        GameDLC gameDLC = gameDLCRepository.getOne(id);

        userService.purchaseGameDLC(
                user.getId(),
                gameRepository.getOne(gameDLC.getGameId()).getDeveloperId(),
                gameDLC.getGameId(),
                id,
                gameDLC.getPrice());
    }

}
