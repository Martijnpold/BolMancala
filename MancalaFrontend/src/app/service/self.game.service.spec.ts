import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { MockInstance, MockBuilder, MockRender } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';
import { SelfGameService } from './self.game.service';

describe('SelfGameService', () => {
  MockInstance.scope();

  beforeEach(async () => {
    return MockBuilder(SelfGameService, AppModule)
      .replace(HttpClientModule, HttpClientTestingModule)
  });

  it('should create', () => {
    const fixture = MockRender(SelfGameService);

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(SelfGameService),
    );
  });
});