import { MockBuilder, MockInstance, MockRender } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';
import { Board } from 'src/app/model/board';
import { Game } from 'src/app/model/game';

import { BoardComponent } from './board.component';

describe('BoardComponent', () => {
  MockInstance.scope();

  let fakeGame: Game = { id: "test-id", name: "test-game", turn: "TOP" }
  let fakeBoard: Board = {
    game: fakeGame,
    pits: [
      { game: fakeGame, index: 0, marbles: 6 },
      { game: fakeGame, index: 1, marbles: 6 },
      { game: fakeGame, index: 2, marbles: 6 },
      { game: fakeGame, index: 3, marbles: 6 },
      { game: fakeGame, index: 4, marbles: 6 },
      { game: fakeGame, index: 5, marbles: 6 },
      { game: fakeGame, index: 6, marbles: 0 }, //End pit 1
      { game: fakeGame, index: 7, marbles: 6 },
      { game: fakeGame, index: 8, marbles: 6 },
      { game: fakeGame, index: 9, marbles: 6 },
      { game: fakeGame, index: 10, marbles: 6 },
      { game: fakeGame, index: 11, marbles: 6 },
      { game: fakeGame, index: 12, marbles: 6 },
      { game: fakeGame, index: 13, marbles: 0 }, //End pit 2
    ]
  }

  beforeEach(async () => {
    return MockBuilder(BoardComponent, AppModule)
  });

  it('should create', () => {
    const fixture = MockRender(BoardComponent, {
      board: fakeBoard
    });

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(BoardComponent),
    );
  });
});
