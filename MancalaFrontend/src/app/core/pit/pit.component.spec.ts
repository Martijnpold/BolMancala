import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MockInstance, MockBuilder, MockRender, ngMocks } from 'ng-mocks';
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
      pit: { game: fakeGame, index: 2, marbles: 5 },
    });

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(PitComponent),
    );
  });

  it('should display the marble count in a header', () => {
    const fixture = MockRender(PitComponent, {
      pit: { game: fakeGame, index: 2, marbles: 5 },
    });

    const header = ngMocks.find('h1');
    expect(header.nativeElement.innerHTML).toEqual('5');
  });

  it('should not show any buttons when hasButtons is false', () => {
    const fixture = MockRender(PitComponent, {
      pit: { game: fakeGame, index: 2, marbles: 5 },
      hasButton: false,
      showButton: true,
    });

    const button = ngMocks.findAll('button');
    expect(button.length).toBe(0);
  });

  it('should not show any buttons when showButtons is false', () => {
    const fixture = MockRender(PitComponent, {
      pit: { game: fakeGame, index: 2, marbles: 5 },
      hasButton: true,
      showButton: false,
    });

    const button = ngMocks.findAll('button');
    expect(button.length).toBe(0);
  });


  it('should show a disabled button', () => {
    const fixture = MockRender(PitComponent, {
      pit: { game: fakeGame, index: 2, marbles: 5 },
      hasButton: true,
      showButton: true,
      disabled: true,
    });

    const button = ngMocks.find('button');
    expect(button).toBeTruthy();
    expect(button.nativeElement.attributes.getNamedItem('ng-reflect-disabled')?.value).toBe('true');
  });

  it('should show an enabled button', () => {
    const fixture = MockRender(PitComponent, {
      pit: { game: fakeGame, index: 2, marbles: 5 },
      hasButton: true,
      showButton: true,
      disabled: false,
    });

    const button = ngMocks.find('button')
    expect(button).toBeTruthy();
    expect(button.nativeElement.attributes.getNamedItem('ng-reflect-disabled')?.value).toBe('false');
  });

  it('should emit an event when clicked', () => {
    const fixture = MockRender(PitComponent, {
      pit: { game: fakeGame, index: 2, marbles: 5 },
      hasButton: true,
      showButton: true,
      disabled: false,
      onMove: jasmine.createSpy(),
    });

    const button = ngMocks.find('button');
    expect(button).toBeTruthy();
    ngMocks.click(button);
    expect(fixture.componentInstance.onMove).toHaveBeenCalled();
  });
});