import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LeagueDetailComponent } from './league-detail.component';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';
import { LeaguesService } from '../../services/league.service';
import { UsersService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';
import { RouterTestingModule } from '@angular/router/testing';

class mockLeaguesService {
  getLeagueById = jasmine.createSpy('getLeagueById').and.returnValue(of({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false }));
  getTeams = jasmine.createSpy('getTeams').and.returnValue(of([{ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' }]));
  getMatches = jasmine.createSpy('getMatches').and.returnValue(of([{ id: 1, team1: 'Team 1', team2: 'Team 2', date: new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1' }]));
  deleteLeague = jasmine.createSpy('deleteLeague').and.returnValue(of());
  getImage = jasmine.createSpy('getImage').and.returnValue('assets/401-background.jpg');
}

class mockUsersService {
  getMe = jasmine.createSpy('getMe').and.returnValue(of({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false }));
  addLeague = jasmine.createSpy('addLeague').and.returnValue(of());
}

class mockActivatedRouteockLoginService {
  
};

class MockRouter {
  navigate = jasmine.createSpy('navigate');
}

class MockActivatedRoute = {
  snapshot: {
    params: {
      id: 1,
    },
  },
};

await TestBed.configureTestingModule({
  declarations: [LeagueDetailComponent],
  imports: [RouterTestingModule],
  providers: [
    { provide: LeaguesService, useValue: mockLeaguesService },
    { provide: UsersService, useValue: mockUsersService },
    { provide: LoginService, useValue: MockLoginService },
    { provide: Router, useValue: MockRouter },
    { provide: ActivatedRoute, useValue: MockActivatedRoute },
  ],
}).compileComponents();

fixture = TestBed.createComponent(LeagueDetailComponent);
component = fixture.componentInstance;


it('should fetch league details and related data on init', () => {
  const mockLeague = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false };
  const mockTeams = [{ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' }];
  const mockMatches = [{ id: 1, team1: 'Team 1', team2: 'Team 2', date: new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1' }];
  const mockUser = { id: 1, name: 'testUser', password: 'pass', image: false, roles: ['[user]'] };

  mockLeaguesService.getLeagueById.and.returnValue(of(mockLeague));
  mockLeaguesService.getTeams.and.returnValue(of(mockTeams));
  mockLeaguesService.getMatches.and.returnValue(of(mockMatches));
  mockUsersService.getMe.and.returnValue(of(mockUser));

  component.ngOnInit();

  expect(mockLeaguesService.getLeagueById).toHaveBeenCalledWith('123');
  expect(mockLeaguesService.getTeams).toHaveBeenCalledWith('123');
  expect(mockLeaguesService.getMatches).toHaveBeenCalledWith('123');
  expect(mockUsersService.getMe).toHaveBeenCalled();
  expect(component.league).toEqual(mockLeague);
  expect(component.teams).toEqual(mockTeams);
  expect(component.matches).toEqual(mockMatches);
  expect(component.user).toEqual(mockUser);
});

it('should handle errors during initialization', () => {
  mockLeaguesService.getLeagueById.and.returnValue(throwError(() => new Error('Error fetching league details')));

  component.ngOnInit();

  expect(component.errorMessage).toBe('Error fetching league details');
});

it('should return the correct league image', () => {
  component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: true };
  mockLeaguesService.getImage.and.returnValue('assets/401-background.jpg');

  const image = component.leagueImage();

  expect(image).toBe('assets/401-background.jpg');
});

it('should return default image if no league image exists', () => {
  component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false };

  const image = component.leagueImage();

  expect(image).toBe('assets/no_image.jpg');
});

it('should navigate to edit league', () => {
  component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false };

  component.editLeague();

  expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues/edit', 1]);
});

it('should confirm and delete league', () => {
  spyOn(window, 'confirm').and.returnValue(true);
  mockLeaguesService.deleteLeague.and.returnValue(of(null));

  component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false };
  component.removeLeague();

  expect(window.confirm).toHaveBeenCalledWith('Quieres borrar esta liga?');
  expect(mockLeaguesService.deleteLeague).toHaveBeenCalledWith(component.league);
  expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues']);
});

it('should navigate to create team', () => {
  component.createTeam();

  expect(mockRouter.navigate).toHaveBeenCalledWith(['/teams/new']);
});

it('should navigate to create match', () => {
  component.createMatch();

  expect(mockRouter.navigate).toHaveBeenCalledWith(['/matches/new']);
});

it('should confirm and add league to user', () => {
  spyOn(window, 'confirm').and.returnValue(true);
  mockUsersService.addLeague.and.returnValue(of(null));

  component.user = { id: 1, name: 'testUser', password: 'pass', image: false, roles: ['[user]'] };
  component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false };

  component.addLeague();

  expect(window.confirm).toHaveBeenCalledWith('Quieres añadir esta liga?');
  expect(mockUsersService.addLeague).toHaveBeenCalledWith(component.user, component.league);
  expect(mockRouter.navigate).toHaveBeenCalledWith(['/users', 1]);
});

it('should navigate back to leagues', () => {
  component.goBack();

  expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues']);
});
});
