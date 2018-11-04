package bol.business;

import bol.model.Player;
import bol.model.Session;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

@Controller
public class SessionManager {

    public Player joinSession(Session session, String email){
        if(StringUtils.isEmpty(session.getPlayerOne())){
            session.setPlayerOne(email);
            return Player.PLAYERONE;
        }
        else if(StringUtils.isEmpty(session.getPlayerTwo())){
            session.setPlayerTwo(email);
            return Player.PLAYERTWO;
        }
        return null;
    }

    public Player getPlayerBasedOnMail(Session session, String mail){
        if(session.getPlayerOne().equals(mail)){
            return Player.PLAYERONE;
        }
        if(session.getPlayerTwo().equals(mail)){
            return Player.PLAYERTWO;
        }
        return Player.NONE;
    }


}
