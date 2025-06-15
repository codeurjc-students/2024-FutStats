import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { TeamsService } from './team.service'; 
import { Team } from '../models/team.model';
import { Player } from '../models/player.model';
import { League } from '../models/league.model';

const BASE_URL = '/api/v1/teams/';

describe('TeamsService', () => {
  let service: TeamsService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [TeamsService],
    });
    service = TestBed.inject(TeamsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should fetch all teams', () => {
    const mockTeams: Team[] = [
        { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'},
        { id: 2, name: 'Team 2', trophies: 0, nationality: 'Española', trainer: 'Alcacer', secondTrainer: 'Guardiola', president: 'Iñigo', stadium: 'Camp noou', points: 1, image: false, league: 'League 1'}
    ];

    service.getTeams().subscribe(teams => {
      expect(teams).toEqual(mockTeams);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeams);
  });

  it('should fetch a team by ID', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};

    service.getTeam(1).subscribe(team => {
      expect(team).toEqual(mockTeam);
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeam);
  });

  it('should fetch a team by name', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};

    service.getTeamByName('Team 1').subscribe(team => {
      expect(team).toEqual(mockTeam);
    });

    const req = httpMock.expectOne(`${BASE_URL}name/Team 1`);
    expect(req.request.method).toBe('GET');
    req.flush(mockTeam);
  });

  it('should fetch an image for a team', () => {
    const mockImage = new FormData();

    service.getImage(1).subscribe(image => {
      expect(image).toEqual(mockImage);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/image`);
    expect(req.request.method).toBe('GET');
    req.flush(mockImage);
  });

  it('should fetch the league for a team', () => {
    const mockLeague: League = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    service.getLeagueByTeam(1).subscribe(league => {
      expect(league).toEqual(mockLeague);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/league`);
    expect(req.request.method).toBe('GET');
    req.flush(mockLeague);
  });

  it('should fetch players for a team', () => {
    const mockPlayers: Player[] = [
        { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false } , 
        { id: 2, name: 'Player 2', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false }
    ];

    service.getPlayersByTeam(1).subscribe(players => {
      expect(players).toEqual(mockPlayers);
    });

    const req = httpMock.expectOne(`${BASE_URL}1/players`);
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayers);
  });

  it('should add a team', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};

    service.addTeam(mockTeam).subscribe(team => {
      expect(team).toEqual(mockTeam);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockTeam);
    req.flush(mockTeam);
  });

  it('should delete a team', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};

    service.deleteTeam(mockTeam).subscribe(response => {
      expect(response).toBeNull();
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('DELETE');
    req.flush(null);
  });

  it('should update a team', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1 update', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};

    service.updateTeam(mockTeam).subscribe(team => {
      expect(team).toEqual(mockTeam);
    });

    const req = httpMock.expectOne(`${BASE_URL}1`);
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockTeam);
    req.flush(mockTeam);
  });
});
