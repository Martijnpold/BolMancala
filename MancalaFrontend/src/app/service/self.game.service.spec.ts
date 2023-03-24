import { TestBed } from '@angular/core/testing';

import { SelfGameService } from './self.game.service';

describe('SelfGameService', () => {
  let service: SelfGameService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SelfGameService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
