package com.mpolder.mancala.service;

import com.mpolder.mancala.model.*;
import com.mpolder.mancala.repository.PitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService implements IBoardService {
    @Autowired
    private PitRepository pitRepository;

    @Override
    public Pit executeMove(Player player, Board board, Pit pit) {
        int startIndex = pit.getId().getPitIndex();
        int marbles = pit.getMarbles();
        Pit current = pit;
        Side opponent = player.getSide().opponent();

        List<Pit> modified = new ArrayList<>();
        pit.setMarbles(0);
        modified.add(pit);

        for (int i = startIndex + 1; i <= startIndex + marbles; i++) {
            current = board.getPits()[i % board.getPits().length];
            if (opponent.equals(getScorePitSide(current))) {
                //Skip if the pit is the opponent's score pit
                marbles++;
                continue;
            }
            current.addMarbles(1);
            modified.add(current);
        }
        pitRepository.saveAll(modified);

        return current;
    }

    @Override
    public void collectOpposites(Player player, Board board, Pit pit) {
        Pit scorePit = getScorePit(board, player.getSide());
        Pit oppositePit = getOppositePit(board, pit);
        int tally = pit.getMarbles() + oppositePit.getMarbles();
        scorePit.addMarbles(tally);
        pit.clearMarbles();
        oppositePit.clearMarbles();
        pitRepository.saveAll(Arrays.asList(pit, oppositePit, scorePit));
    }

    @Override
    public boolean shouldCollectSides(Board board) {
        return Arrays.stream(board.getPits()).collect(Collectors.groupingBy(this::getPitSide))
                .values().stream()
                .anyMatch(sidePits -> sidePits.stream().allMatch(pit -> pit.getMarbles() <= 0 || isScorePit(pit)));
    }

    @Override
    public void collectSides(Board board) {
        for (Pit p : board.getPits()) {
            if (p.getMarbles() <= 0) continue;
            Side side = getPitSide(p);
            Pit scorePit = getScorePit(board, side);
            if (!p.equals(scorePit)) {
                scorePit.addMarbles(p.getMarbles());
                p.clearMarbles();
            }
        }
        pitRepository.saveAll(Arrays.asList(board.getPits()));
    }

    @Override
    public Side getWinner(Board board) {
        int highest = Arrays.stream(Side.values()).mapToInt(side -> getScorePit(board, side).getMarbles()).max().orElse(0);
        List<Side> winners = Arrays.stream(Side.values()).filter(side -> getScorePit(board, side).getMarbles() == highest).toList();
        if (winners.size() != 1) return null;
        return winners.get(0);
    }

    @Override
    public Board initBoard(Game game) {
        Board board = Board.build(game);
        pitRepository.saveAll(Arrays.asList(board.getPits()));
        return board;
    }

    @Override
    public Board getBoard(Game game) {
        var pits = pitRepository.findAllByIdGameId(game.getId()).stream()
                .sorted(Comparator.comparing(pit -> pit.getId().getPitIndex()))
                .toArray(Pit[]::new);
        return new Board(game, pits);
    }

    @Override
    public Side getPitSide(Pit pit) {
        return pit.getId().getPitIndex() < 7 ? Side.TOP : Side.BOTTOM;
    }

    @Override
    public boolean isScorePit(Pit pit) {
        return getScorePitSide(pit) != null;
    }

    @Override
    public Pit getScorePit(Board board, Side side) {
        int index = side.equals(Side.TOP) ? 6 : 13;
        return board.getPits()[index];
    }

    @Override
    public Side getScorePitSide(Pit pit) {
        if ((pit.getId().getPitIndex() + 1) % 7 != 0) return null;
        return getPitSide(pit);
    }

    @Override
    public Pit getOppositePit(Board board, Pit pit) {
        int index = Math.abs(pit.getId().getPitIndex() - 12);
        return board.getPits()[index];
    }
}
