package com.sustech.gamercenter.util.model;

import com.sustech.gamercenter.dao.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public enum MessageType {

    INVITATION("invitation"),
    CHAT("chat"),
    SYSTEM("system"),
    FRIEND_REQUEST("friend request"),
    FRIEND_REPLY("friend reply");


    @Component
    public static class GameRepositoryInjector {
        @Autowired
        private static GameRepository gameRepository;

        /*@PostConstruct
        public void postConstruct() {
            for (MessageType mt : EnumSet.allOf(MessageType.class))
                mt.(gameRepository);
        }*/
    }

    private final String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public static String process(String type, String value) {
        if (type.equals(INVITATION.getType())) {
            return "Let's play "
                    + GameRepositoryInjector.gameRepository.findById(Long.parseLong(value)).getName()
                    + " together!";
        } else return value;
    }
}
