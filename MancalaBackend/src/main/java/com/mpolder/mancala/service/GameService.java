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
    public Game getGame(UUID id) throws NoSuchElementException {
        return gameRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Game not found"));
    }

    @Override
    public Game initGame() {
        Game game = new Game();
        gameRepository.save(game);
        boardService.initBoard(game);
        playerService.initPlayers(game);
        return game;
    }

    @Override
    public void tryDoTurn(Game game, Player player, Board board, Pit pit) {
        if (isValidTurn(game, player, pit)) {
            Pit end = boardService.executeMove(player, board, pit);
            if (!boardService.isScorePit(pit) && end.getMarbles() == 1) {
                boardService.collectOpposites(player, board, pit);
            }
            if (shouldEndTurn(end)) {
                game.setTurn(player.getSide().opponent());
                gameRepository.save(game);
            }
        }
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
