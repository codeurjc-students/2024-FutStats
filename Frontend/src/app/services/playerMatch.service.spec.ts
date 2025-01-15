import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PlayerMatchesService } from './playerMatch.service'; 
import { PlayerMatch } from '../models/player-match.model';
import { Player } from '../models/player.model';

const BASE_URL = '/api/v1/playerMatches/';

describe('PlayerMatchesService', () => {
  let service: PlayerMatchesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PlayerMatchesService],
    });
    service = TestBed.inject(PlayerMatchesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve a player match by ID', () => {
    const mockPlayerMatch: PlayerMatch = { id: 1, match: 1 ,name: 'Vinicius Jr.', matchName: "Match 1" };

    service.getPlayerMatch(1).subscribe(playerMatch => {
      expect(playerMatch).toEqual(mockPlayerMatch);
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayerMatch);
  });

  it('should retrieve a match by ID', () => {
    const mockPlayerMatch: PlayerMatch = { id: 1, match: 1 ,name: 'Vinicius Jr.', matchName: "Match 1" };

    service.getMatch(1).subscribe(match => {
      expect(match).toEqual(mockPlayerMatch);
    });

    const req = httpMock.expectOne(BASE_URL + '1/match');
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayerMatch);
  });

  it('should retrieve a player by player match ID', () => {
    const mockPlayer: Player = { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false };

    service.getPlayer(1).subscribe(player => {
      expect(player).toEqual(mockPlayer);
    });

    const req = httpMock.expectOne(BASE_URL + '1/player');
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayer);
  });

  it('should retrieve goals per match for a player', () => {
    const mockGoals = { goals: 5 };

    service.getGoalsPerMatch(1).subscribe(goals => {
      expect(goals).toEqual(mockGoals);
    });

    const req = httpMock.expectOne(BASE_URL + '/goals/1');
    expect(req.request.method).toBe('GET');
    req.flush(mockGoals);
  });

  it('should delete a player match', () => {
    const mockPlayerMatch: PlayerMatch = { id: 1, match: 1 ,name: 'Vinicius Jr.', matchName: "Match 1" };

    service.deletePlayerMatch(mockPlayerMatch).subscribe(response => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should update a player match', () => {
    const mockPlayerMatch: PlayerMatch = { id: 1, match: 1 ,name: 'Vinicius Jr.', matchName: "Match 1" };

    service.updatePlayerMatch(mockPlayerMatch.id, mockPlayerMatch).subscribe(updatedMatch => {
      expect(updatedMatch).toEqual(mockPlayerMatch);
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockPlayerMatch);
  });
});
