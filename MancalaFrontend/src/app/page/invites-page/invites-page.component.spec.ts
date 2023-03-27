import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MockInstance, MockBuilder, MockRender } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';
import { InvitesPageComponent } from './invites-page.component';

describe('InvitesPageComponent', () => {
  MockInstance.scope();

  beforeEach(async () => {
    return MockBuilder(InvitesPageComponent, AppModule)
  });

  it('should create', () => {
    const fixture = MockRender(InvitesPageComponent);

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(InvitesPageComponent),
    );
  });
});