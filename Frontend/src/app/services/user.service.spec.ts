import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UsersService } from './user.service';
import { User } from '../models/user.model';
import { League } from '../models/league.model';
import { Team } from '../models/team.model';
import { Player } from '../models/player.model';

const BASE_URL = '/api/v1/users/';

describe('UsersService', () => {
  let service: UsersService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UsersService],
    });
    service = TestBed.inject(UsersService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should retrieve all users', () => {
    const mockUsers: User[] = [
        { id: 1, name: 'testUser', password: 'pass', email:'', image: false, roles: ['[user]'] },
        { id: 2, name: 'testUser 2', password: 'pass', email:'', image: false, roles: ['[user]'] },
    ];

    service.getUsers().subscribe(users => {
      expect(users).toEqual(mockUsers);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('GET');
    req.flush(mockUsers);
  });

  it('should retrieve a user by ID', () => {
    const mockUser: User = { id: 1, name: 'testUser', password: 'pass', email:'', image: false, roles: ['[user]'] };

    service.getUser(1).subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should retrieve the logged-in user', () => {
    const mockUser: User = { id: 1, name: 'testUser', password: 'pass', email:'', image: false, roles: ['[user]'] };

    service.getMe().subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne(BASE_URL + 'me');
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should retrieve leagues for a user', () => {
    const mockLeagues: League[] = [
        { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false },
        { id: 2, name: 'League 2', president: 'Kiko', nationality: 'Francesa', teams: [] , image: false },
    ];

    service.getLeagues(1).subscribe(leagues => {
      expect(leagues).toEqual(mockLeagues);
    });

    const req = httpMock.expectOne(BASE_URL + '1/leagues');
    expect(req.request.method).toBe('GET');
    req.flush(mockLeagues);
  });

  it('should retrieve teams for a user', () => {
    const mockTeams: Team[] = [
        { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'},
        { id: 2, name: 'Team 2', trophies: 0, nationality: 'Española', trainer: 'Alcacer', secondTrainer: 'Guardiola', president: 'Iñigo', stadium: 'Camp noou', points: 1, image: false, league: 'League 1'},
    ];

    service.getTeams(1).subscribe(teams => {
      expect(teams).toEqual(mockTeams);
    });

    const req = httpMock.expectOne(BASE_URL + '1/teams');
    expect(req.request.method).toBe('GET');
    req.flush(mockTeams);
  });

  it('should retrieve players for a user', () => {
    const mockPlayers: Player[] = [
        { id: 1, name: 'Player 1', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false } , 
        { id: 2, name: 'Player 2', age: 22, position: 'delantero', nationality: 'española', league: 'LaLiga', team: 'Real Madrid', image: false },
    ];

    service.getPlayers(1).subscribe(players => {
      expect(players).toEqual(mockPlayers);
    });

    const req = httpMock.expectOne(BASE_URL + '1/players');
    expect(req.request.method).toBe('GET');
    req.flush(mockPlayers);
  });

  it('should add a new user', () => {
    const newUser: User = { id: 1, name: 'testUser', password: 'pass', email:'', image: false, roles: ['[user]'] };

    service.addUser(newUser).subscribe(user => {
      expect(user).toEqual(newUser);
    });

    const req = httpMock.expectOne(BASE_URL);
    expect(req.request.method).toBe('POST');
    req.flush(newUser);
  });

  it('should update a user', () => {
    const updatedUser: User = { id: 1, name: 'testUser', password: 'pass', email:'', image: false, roles: ['[user]'] };

    service.updateUser(updatedUser).subscribe(user => {
      expect(user).toEqual(updatedUser);
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('PUT');
    req.flush(updatedUser);
  });

  it('should delete a user', () => {
    service.deleteUser({ id: 1, name: 'testUser', password: 'pass', email:'', image: false, roles: ['[user]'] }).subscribe(response => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(BASE_URL + '1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});
  });
});
