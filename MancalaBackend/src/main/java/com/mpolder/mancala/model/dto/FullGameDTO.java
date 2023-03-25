package com.mpolder.mancala.model.dto;

import com.mpolder.mancala.model.Board;
import com.mpolder.mancala.model.Game;
import com.mpolder.mancala.model.Player;
import lombok.Data;

import java.util.List;

@Data
public class FullGameDTO {
    private Game game;
    private Board board;
    private Player self;
    private List<Player> players;

    public FullGameDTO() {
    }

    public FullGameDTO(Game game, Board board, Player self, List<Player> players) {
        this.game = game;
        this.board = board;
        this.self = self;
        this.players = players;
    }

}
