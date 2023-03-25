import { Game } from "./game";
import { Board } from "./board";
import { Player } from "./player";

export class FullGame {
    game: Game;
    board: Board;
    self: Player;
    players: Player[];
}
