package bol.endpoint;

import bol.business.GameActions;
import bol.model.Player;
import bol.model.api.UserInput;
import bol.model.Board;
import bol.model.api.JoinResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value="api/gameplay", description="Gameplay operations like starting a new game, distributing stones etc.")
public class MainGameController {

    private static final String ALLOWED_ORIGIN = "http://localhost:4200";

    private GameActions gameActions;
    private SimpMessagingTemplate socket;

    @Autowired
    public MainGameController(final GameActions gameActions,
                              final SimpMessagingTemplate socket) {
        this.gameActions = gameActions;
        this.socket = socket;
    }

    @ApiOperation(value = "Starts new game")
    @CrossOrigin(origins = ALLOWED_ORIGIN)
    @RequestMapping(value = "/newGame", method = RequestMethod.GET)
    public void newGame() {
        Board board = this.gameActions.newGame();
        this.socket.convertAndSend("/topic/board", board);
    }

    @ApiOperation(value = "Joins game for player and returns name")
    @CrossOrigin(origins = ALLOWED_ORIGIN)
    @RequestMapping(value = "/joingame", method = RequestMethod.POST,  produces = "application/json")
    @ResponseBody
    public JoinResponse joinGame(@RequestBody @Valid UserInput params) {
        Player player = this.gameActions.joinGame(params.getMail());
        if(player!=Player.NONE && player==Player.PLAYERTWO) {
            Board board = this.gameActions.fetchBoard();
            this.socket.convertAndSend("/topic/board", board);
        }
        return new JoinResponse(player.name());
    }

    @ApiOperation(value = "Distributes stones among following pits")
    @CrossOrigin(origins = ALLOWED_ORIGIN)
    @RequestMapping(value = "/distributestones", method = RequestMethod.POST,  produces = "application/json")
    public void distrubuteStones(@RequestBody @Valid UserInput params) {
        Board board = gameActions.doPlayerTurn(params.getSelectedPitIndex(), params.getMail());
        this.socket.convertAndSend("/topic/board", board);
    }


}
