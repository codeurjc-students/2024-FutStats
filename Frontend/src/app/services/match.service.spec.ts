import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MatchesService } from './match.service'; 
import { Match } from '../models/match.model';
import { Team } from '../models/team.model';
import { League } from '../models/league.model';
import { PlayerMatch } from '../models/player-match.model';

const BASE_URL = '/api/v1/matches/';

describe('MatchesService', () => {
  let service: MatchesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MatchesService],
    });
    service = TestBed.inject(MatchesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch matches', () => {
    const mockMatches: Match[] = [
        { id: 1, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1'},
        { id: 2, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '2'},
    ];

    service.getMatches().subscribe(matches => {
      expect(matches).toEqual(mockMatches);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('GET');
    req.flush(mockMatches);
  });

  it('should fetch a single match by ID', () => {
    const mockMatch: Match = { id: 1, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1'};

    service.getMatch(1).subscribe(match => {
      expect(match).toEqual(mockMatch);
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockMatch);
  });

  it('should fetch a match by name', () => {
    const mockMatch: Match = { id: 1, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1'};

    service.getMatchByName('Match 1').subscribe(match => {
      expect(match).toEqual(mockMatch);
    });

    const req = httpMock.expectOne(`${BASE_URL}name/Match 1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockMatch);
  });

  it('should fetch the league of a match', () => {
    const mockLeague: League = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    service.getLeague(1).subscribe(league => {
      expect(league).toEqual(mockLeague);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/league`);
    expect(req.request.method).toBe('GET');
    req.flush(mockLeague);
  });

  it('should fetch team1 of a match', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' };

    service.getTeam1(1).subscribe(team => {
      expect(team).toEqual(mockTeam);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/team1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeam);
  });

  it('should fetch team2 of a match', () => {
    const mockTeam: Team = { id: 2, name: 'Team 2', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' };

    service.getTeam2(1).subscribe(team => {
      expect(team).toEqual(mockTeam);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/team2`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeam);
  });

  it('should fetch player matches of a match', () => {
    const mockPlayerMatches: PlayerMatch[] = [
      { id: 1, match: 1 ,name: 'Vinicius Jr.', matchName: "Match 1" },
      { id: 2, match: 1, name: 'Lamine Yamal', matchName: "Match 1" },
    ];

    service.getPlayerMatches(1).subscribe(playerMatches => {
      expect(playerMatches).toEqual(mockPlayerMatches);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/playerMatches`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayerMatches);
  });

  it('should add a match', () => {
    const mockMatch: Match = { id: 1, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1'};

    service.addMatch(mockMatch).subscribe(match => {
      expect(match).toEqual(mockMatch);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockMatch);
    req.flush(mockMatch);
  });

  it('should delete a match', () => {
    const mockMatch: Match = { id: 1, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1'};

    service.deleteMatch(mockMatch).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should update a match', () => {
    const mockMatch: Match = { id: 1, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1'};

    service.updateMatch(mockMatch).subscribe(match => {
      expect(match).toEqual(mockMatch);
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockMatch);
    req.flush(mockMatch);
  });

  it('should add a player match', () => {
    const mockPlayerMatch: PlayerMatch = { id: 1, match: 1 ,name: 'Vinicius Jr.', matchName: "Match 1" };

    service.addPlayerMatch(mockPlayerMatch).subscribe(playerMatch => {
      expect(playerMatch).toEqual(mockPlayerMatch);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/playerMatches`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockPlayerMatch);
    req.flush(mockPlayerMatch);
  });
});
