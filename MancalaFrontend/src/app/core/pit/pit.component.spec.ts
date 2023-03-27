import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MockInstance, MockBuilder, MockRender } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';
import { Game } from 'src/app/model/game';
import { PitComponent } from './pit.component';

describe('PitComponent', () => {
  MockInstance.scope();

  let fakeGame: Game = { id: "test-id", name: "test-game", turn: "TOP" }

  beforeEach(async () => {
    return MockBuilder(PitComponent, AppModule)
  });

  it('should create', () => {
    const fixture = MockRender(PitComponent, {
      pit: { game: fakeGame, index: 2, marbles: 5 }
    });

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(PitComponent),
    );
  });
});