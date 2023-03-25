package com.mpolder.mancala.service;

import com.mpolder.mancala.exception.ResourceNotFoundException;
import com.mpolder.mancala.model.*;
import com.mpolder.mancala.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class GameService implements IGameService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private IBoardService boardService;
    @Autowired
    private IPlayerService playerService;

    @Override
    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    @Override
    public List<Game> getGames(User user) {
        return gameRepository.findAllPlayerGames(user);
    }

    @Override
    public Game getGame(UUID id) throws NoSuchElementException {
        return gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));
    }

    @Override
    public Game getGame(User user, UUID id) {
        return gameRepository.findByUserAndId(user, id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));
    }

    @Override
    public Game initGame(Invite invite) {
        Game game = new Game();
        game.setName(invite.getInviter().getName() + " vs " + invite.getInvitee().getName());
        gameRepository.save(game);
        boardService.initBoard(game);
        playerService.initPlayers(game, invite);
        return game;
    }

    @Override
    public boolean tryDoTurn(Game game, Player player, Board board, Pit pit) {
        if (isValidTurn(game, player, pit)) {
            Pit end = boardService.executeMove(player, board, pit);
            if (!boardService.isScorePit(pit) && end.getMarbles() == 1) {
                boardService.collectOpposites(player, board, end);
            }
            if (shouldEndTurn(end)) {
                game.setTurn(player.getSide().opponent());
                gameRepository.save(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidTurn(Game game, Player player, Pit pit) {
        Side side = player.getSide();
        boolean valid = side.equals(game.getTurn());
        valid &= side.equals(boardService.getPitSide(pit));
        valid &= !boardService.isScorePit(pit);
        valid &= pit.getMarbles() > 0;
        return valid;
    }

    @Override
    public boolean shouldEndTurn(Pit pit) {
        return !boardService.isScorePit(pit);
    }
}
