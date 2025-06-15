import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LeaguesService } from './league.service';
import { League } from '../models/league.model';
import { Team } from '../models/team.model';
import { Match } from '../models/match.model';

const BASE_URL = '/api/v1/leagues/';

describe('LeaguesService', () => {
  let service: LeaguesService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LeaguesService],
    });
    service = TestBed.inject(LeaguesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve all leagues', () => {
    const mockLeagues: League[] = [
      { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false },
      { id: 2, name: 'League 2', president: 'Kiko', nationality: 'Francesa', teams: [] , image: false },
    ];

    service.getLeagues().subscribe(leagues => {
      expect(leagues.length).toBe(2);
      expect(leagues).toEqual(mockLeagues);
    });

    const req = httpMock.expectOne(`${BASE_URL}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockLeagues);
  });

  it('should retrieve a league by ID', () => {
    const mockLeague: League =  { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    service.getLeagueById(1).subscribe(league => {
      expect(league).toEqual(mockLeague);
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockLeague);
  });

  it('should fetch league by name', () => {
    const mockLeague: League = { id: 1, name: 'LaLiga', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false  };

    service.getLeagueByName('LaLiga').subscribe((league) => {
      expect(league).toEqual(mockLeague);
    });

    const req = httpMock.expectOne(`${BASE_URL}` + 'name/LaLiga');
    expect(req.request.method).toBe('GET');
    req.flush(mockLeague);
  });


  it('should add a league', () => {
    const newLeague: League =  { id: 3, name: 'League 3', president: 'Perez', nationality: 'Italiana', teams: [] , image: false };

    service.addLeague(newLeague).subscribe(league => {
      expect(league).toEqual(newLeague);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(newLeague);
    req.flush(newLeague);
  });

  it('should update a league', () => {
    const updatedLeague: League = { id: 1, name: 'League 1 updated', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    service.updateLeague(updatedLeague).subscribe(league => {
      expect(league).toEqual(updatedLeague);
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedLeague);
    req.flush(updatedLeague);
  });

  it('should delete a league', () => {
    const leagueToDelete: League = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    service.deleteLeague(leagueToDelete).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should retrieve teams by league ID', () => {
    const mockLeague: League =  { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
    const mockTeams: Team[] = [
        { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'},
        { id: 2, name: 'Team 2', trophies: 0, nationality: 'Española', trainer: 'Alcacer', secondTrainer: 'Guardiola', president: 'Iñigo', stadium: 'Camp noou', points: 1, image: false, league: 'League 1'}, 
    ];

    service.getTeams(1).subscribe(teams => {
      expect(teams).toEqual(mockTeams);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/teams`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeams);
  });

  it('should retrieve teams by league name', () => {
    const mockLeague: League =  { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
    const mockTeams: Team[] = [
      { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' },
      { id: 2, name: 'Team 2', trophies: 0, nationality: 'Española', trainer: 'Alcacer', secondTrainer: 'Guardiola', president: 'Iñigo', stadium: 'Camp noou', points: 1, image: false, league: 'League 1'  }, 
    ];

    service.getTeamsByName('League 1').subscribe(teams => {
      expect(teams).toEqual(mockTeams);
    });

    const req = httpMock.expectOne(`${BASE_URL}name/League 1/teams`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeams);
  });

  it('should retrieve matches by league ID', () => {
    const mockMatches: Match[] = [
      { id: 1, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1'},
      { id: 2, team1: 'Team 3', team2: 'Team 4', date: new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '2'},
    ];

    service.getMatches(1).subscribe(matches => {
      expect(matches).toEqual(mockMatches);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/matches`);
    expect(req.request.method).toBe('GET');
    req.flush(mockMatches);
  });

  it('should retrieve an image by league ID', () => {
    const mockImage = new FormData();
    mockImage.append('image', 'imageData');

    service.getImage(1).subscribe(image => {
      expect(image).toEqual(mockImage);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/image`);
    expect(req.request.method).toBe('GET');
    req.flush(mockImage);
  });

  it('should add an image to a league', () => {
    const league: League = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
    const formData = new FormData();
    formData.append('image', 'imageData');

    service.addImage(league, formData).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${BASE_URL}1/image`);
    expect(req.request.method).toBe('POST');
    req.flush(null);
  });

  it('should delete an image from a league', () => {
    const league: League = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    service.deleteImage(league).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${BASE_URL}1/image`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });
});
