package com.mpolder.mancala.controller;

import com.mpolder.mancala.model.Board;
import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Player;
import com.mpolder.mancala.model.User;
import com.mpolder.mancala.model.dto.FullGameDTO;
import com.mpolder.mancala.model.dto.MoveDTO;
import com.mpolder.mancala.service.IBoardService;
import com.mpolder.mancala.service.IGameService;
import com.mpolder.mancala.service.IPlayerService;
import com.mpolder.mancala.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/current/games")
public class SelfGameController {
    @Autowired
    private IGameService gameService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPlayerService playerService;
    @Autowired
    private IBoardService boardService;

    @GetMapping()
    public List<Game> getGames(@AuthenticationPrincipal OAuth2User oAuth2User) {
        return gameService.getGames(getUser(oAuth2User));
    }

    @GetMapping("/{id}")
    public FullGameDTO gameDetails(@AuthenticationPrincipal OAuth2User principal, @PathVariable UUID id) {
        User self = getUser(principal);
        Game game = getGame(self, id);
        List<Player> players = playerService.getPlayers(game);
        Board board = boardService.getBoard(game);
        return new FullGameDTO(game, board, players);
    }

    @PostMapping("/{id}/move")
    public FullGameDTO inviteAccept(@AuthenticationPrincipal OAuth2User principal, @PathVariable UUID id, @RequestBody MoveDTO move) {
        User self = getUser(principal);
        Game game = getGame(self, id);
        List<Player> players = playerService.getPlayers(game);
        Board board = boardService.getBoard(game);

        Player mover = players.stream().filter(x -> x.getUser().equals(self)).findFirst().orElseThrow();
        gameService.tryDoTurn(game, mover, board, board.getPits()[move.getPitIndex()]);

        return new FullGameDTO(game, board, players);
    }

    private User getUser(OAuth2User principal) {
        return userService.getByEmail(principal.getAttribute("email"));
    }

    private Game getGame(User user, UUID id) {
        return gameService.getGame(user, id);
    }
}
