import { TestBed } from '@angular/core/testing';
import { MockInstance, MockBuilder, MockRender } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  MockInstance.scope();

  beforeEach(async () => {
    return MockBuilder(AppComponent, AppModule)
  });

  it('should create', () => {
    const fixture = MockRender(AppComponent);

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(AppComponent),
    );
  });
});