import { HttpClientModule } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { MockInstance, MockBuilder, MockRender } from 'ng-mocks';
import { AppModule } from 'src/app/app.module';
import { SelfInviteService } from './self.invite.service';

describe('SelfInviteService', () => {
  MockInstance.scope();

  TestBed.configureTestingModule({
    imports: [HttpClientTestingModule],
  })

  beforeEach(async () => {
    return MockBuilder(SelfInviteService, AppModule)
      .replace(HttpClientModule, HttpClientTestingModule)
  });

  it('should create', () => {
    const fixture = MockRender(SelfInviteService);

    expect(fixture.point.componentInstance).toEqual(
      jasmine.any(SelfInviteService),
    );
  });
});