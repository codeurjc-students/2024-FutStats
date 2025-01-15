import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TeamMatchesService } from './teamMatch.service';

const BASE_URL = '/api/v1/teamMatches/';

describe('TeamMatchesService', () => {
  let service: TeamMatchesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeamMatchesService],
    });
    service = TestBed.inject(TeamMatchesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve points per match for a team', () => {
    const mockPoints = { points: 10 };

    service.getPointsPerMatch(1).subscribe(points => {
      expect(points).toEqual(mockPoints);
    });

    const req = httpMock.expectOne(BASE_URL + 'goals/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockPoints);
  });
});
