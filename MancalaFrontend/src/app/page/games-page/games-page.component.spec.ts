import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MockInstance, MockBuilder, MockRender } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';
import { GamesPageComponent } from './games-page.component';

describe('GamesPageComponent', () => {
  MockInstance.scope();

  beforeEach(async () => {
    return MockBuilder(GamesPageComponent, AppModule)
  });

  it('should create', () => {
    const fixture = MockRender(GamesPageComponent);

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(GamesPageComponent),
    );
  });
});