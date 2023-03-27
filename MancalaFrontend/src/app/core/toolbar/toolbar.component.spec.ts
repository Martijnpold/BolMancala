import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MockInstance, MockBuilder, MockRender } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';

import { ToolbarComponent } from './toolbar.component';

describe('ToolbarComponent', () => {
  MockInstance.scope();

  beforeEach(async () => {
    return MockBuilder(ToolbarComponent, AppModule)
  });

  it('should create', () => {
    const fixture = MockRender(ToolbarComponent);

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(ToolbarComponent),
    );
  });
});