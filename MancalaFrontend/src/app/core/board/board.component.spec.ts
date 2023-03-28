import { MockBuilder, MockInstance, MockRender, ngMocks } from 'ng-mocks';
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
      { game: fakeGame, index: 1, marbles: 5 },
      { game: fakeGame, index: 2, marbles: 4 },
      { game: fakeGame, index: 3, marbles: 3 },
      { game: fakeGame, index: 4, marbles: 2 },
      { game: fakeGame, index: 5, marbles: 1 },
      { game: fakeGame, index: 6, marbles: 0 }, //End pit 1
      { game: fakeGame, index: 7, marbles: 6 },
      { game: fakeGame, index: 8, marbles: 5 },
      { game: fakeGame, index: 9, marbles: 4 },
      { game: fakeGame, index: 10, marbles: 3 },
      { game: fakeGame, index: 11, marbles: 2 },
      { game: fakeGame, index: 12, marbles: 1 },
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

  it('should render all pits', () => {
    const fixture = MockRender(BoardComponent, {
      board: fakeBoard
    });

    const pits = ngMocks.findAll('app-pit');
    expect(pits.length).toEqual(14);
  });

  it('should pass in correct pit', () => {
    const fixture = MockRender(BoardComponent, {
      board: fakeBoard
    });

    const pits = ngMocks.findAll('app-pit');
    expect(pits.length).toEqual(14);
  });

  it('should show buttons on playing side', () => {
    const fixture = MockRender(BoardComponent, {
      board: fakeBoard,
      side: "BOTTOM"
    });

    const pits = ngMocks.findAll('app-pit');
    for (let i = 0; i < 14; i++) {
      let inst = pits[i].componentInstance;
      let side = inst.pit.index >= 7 ? "BOTTOM" : "TOP"
      expect(inst.showButton).withContext(`Checking value ${i}`).toEqual(fixture.componentInstance.side == side);
    }
  });

  it('should emit an event on move', () => {
    const fixture = MockRender(BoardComponent);
    fixture.componentInstance.board = fakeBoard;
    spyOn(fixture.componentInstance.onMove, 'emit')
    fixture.detectChanges()

    fixture.componentInstance.click(5)

    expect(fixture.componentInstance.onMove.emit).toHaveBeenCalledOnceWith(5);
  });
});
