import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { PlayersService } from './player.service';
import { Player } from '../models/player.model';
import { League } from '../models/league.model';
import { Team } from '../models/team.model';
import { PlayerMatch } from '../models/player-match.model';

const BASE_URL = '/api/v1/players/';

describe('PlayersService', () => {
  let service: PlayersService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [PlayersService],
    });
    service = TestBed.inject(PlayersService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve all players', () => {
    const mockPlayers: Player[] = [
        { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false } , 
        { id: 2, name: 'Player 2', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false },
    ];

    service.getPlayers().subscribe(players => {
      expect(players).toEqual(mockPlayers);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayers);
  });

  it('should retrieve a player by name', () => {
    const mockPlayer: Player = { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false };

    service.getPlayerByName('Player 1').subscribe(player => {
      expect(player).toEqual(mockPlayer);
    });

    const req = httpMock.expectOne(BASE_URL + 'name/Player 1');
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayer);
  });

  it('should retrieve a player by ID', () => {
    const mockPlayer: Player = { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false };

    service.getPlayer(1).subscribe(player => {
      expect(player).toEqual(mockPlayer);
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayer);
  });

  it('should retrieve a player image', () => {
    const mockImage = new FormData();

    service.getImage(1).subscribe(image => {
      expect(image).toEqual(mockImage);
    });

    const req = httpMock.expectOne(BASE_URL + '1/image');
    expect(req.request.method).toBe('GET');
    req.flush(mockImage);
  });

  it('should retrieve the league of a player', () => {
    const mockLeague: League =  { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    service.getLeague(1).subscribe(league => {
      expect(league).toEqual(mockLeague);
    });

    const req = httpMock.expectOne(BASE_URL + '1/league');
    expect(req.request.method).toBe('GET');
    req.flush(mockLeague);
  });

  it('should retrieve the team of a player', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' };

    service.getTeam(1).subscribe(team => {
      expect(team).toEqual(mockTeam);
    });

    const req = httpMock.expectOne(BASE_URL + '1/team');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeam);
  });

  it('should retrieve player matches', () => {
    const mockMatches: PlayerMatch[] = [
        { id: 1, match: 1 ,name: 'Vinicius Jr.', matchName: "Match 1" },
        { id: 2, match: 1, name: 'Lamine Yamal', matchName: "Match 1" },
    ];

    service.getPlayerMatches(1).subscribe(matches => {
      expect(matches).toEqual(mockMatches);
    });

    const req = httpMock.expectOne(BASE_URL + '1/playerMatches');
    expect(req.request.method).toBe('GET');
    req.flush(mockMatches);
  });

  it('should add a new player', () => {
    const mockPlayer: Player = { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false };

    service.addPlayer(mockPlayer).subscribe(player => {
      expect(player).toEqual(mockPlayer);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('POST');
    req.flush(mockPlayer);
  });

  it('should add a player image', () => {
    const formData = new FormData();
    const mockPlayer: Player = { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false };

    service.addImage(mockPlayer, formData).subscribe(response => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(BASE_URL + '1/image');
    expect(req.request.method).toBe('POST');
    req.flush({});
  });

  it('should delete a player', () => {
    const mockPlayer: Player = { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false };

    service.deletePlayer(mockPlayer).subscribe(response => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should delete a player image', () => {
    const mockPlayer: Player = { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false };

    service.deleteImage(mockPlayer).subscribe(response => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(BASE_URL + '1/image');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });

  it('should update a player', () => {
    const mockPlayer: Player = { id: 1, name: 'Player 1 update', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false };

    service.updatePlayer(mockPlayer).subscribe(player => {
      expect(player).toEqual(mockPlayer);
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('PUT');
    req.flush(mockPlayer);
  });
});
