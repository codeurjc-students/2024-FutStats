import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { TeamDetailComponent } from './team-detail.component';
import { TeamsService } from '../../services/team.service';
import { UsersService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';
import { TeamMatchesService } from 'src/app/services/teamMatch.service';
import { Team } from '../../models/team.model';
import { League } from '../../models/league.model';
import { Player } from '../../models/player.model';

class MockRouter {
  navigate = jasmine.createSpy('navigate');
}

class MockActivatedRoute {
  snapshot = { params: { id: 1 } };
}

describe('TeamDetailComponent', () => {
  let component: TeamDetailComponent;
  let fixture: ComponentFixture<TeamDetailComponent>;
  let teamsService: jasmine.SpyObj<TeamsService>;
  let usersService: jasmine.SpyObj<UsersService>;
  let teamMatchService: jasmine.SpyObj<TeamMatchesService>;
  let router: MockRouter;

  beforeEach(async () => {
    const teamsServiceSpy = jasmine.createSpyObj('TeamsService', [
      'getTeam',
      'getPlayersByTeam',
      'getLeagueByTeam',
      'deleteTeam',
      'getImage'
    ]);
    const usersServiceSpy = jasmine.createSpyObj('UsersService', [
      'getMe',
      'addTeam'
    ]);
    const teamMatchServiceSpy = jasmine.createSpyObj('TeamMatchesService', [
      'getPointsPerMatch'
    ]);

    await TestBed.configureTestingModule({
      declarations: [TeamDetailComponent],
      providers: [
        { provide: TeamsService, useValue: teamsServiceSpy },
        { provide: UsersService, useValue: usersServiceSpy },
        { provide: TeamMatchesService, useValue: teamMatchServiceSpy },
        { provide: Router, useClass: MockRouter },
        { provide: ActivatedRoute, useClass: MockActivatedRoute },
        LoginService
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TeamDetailComponent);
    component = fixture.componentInstance;
    teamsService = TestBed.inject(TeamsService) as jasmine.SpyObj<TeamsService>;
    usersService = TestBed.inject(UsersService) as jasmine.SpyObj<UsersService>;
    teamMatchService = TestBed.inject(TeamMatchesService) as jasmine.SpyObj<TeamMatchesService>;
    router = TestBed.inject(Router) as unknown as MockRouter;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should load team data on init', () => {
    const mockTeam: Team = { id: 1, name: 'Team A', league: 'League A', image: false };
    const mockLeague: League = { id: 1, name: 'League A' };
    const mockPlayers: Player[] = [
      { id: 1, name: 'Player 1', team: 'Team A', league: 'League A', image: false },
    ];
    const mockUser = { id: 1, username: 'User1' };

    teamsService.getTeam.and.returnValue(of(mockTeam));
    teamsService.getPlayersByTeam.and.returnValue(of(mockPlayers));
    teamsService.getLeagueByTeam.and.returnValue(of(mockLeague));
    usersService.getMe.and.returnValue(of(mockUser));

    fixture.detectChanges();

    expect(component.team).toEqual(mockTeam);
    expect(component.players).toEqual(mockPlayers);
    expect(component.league).toEqual(mockLeague);
    expect(component.user).toEqual(mockUser);
  });

  it('should handle errors while loading team data', () => {
    teamsService.getTeam.and.returnValue(throwError(() => new Error('Error fetching team')));

    fixture.detectChanges();

    expect(component.errorMessage).toBe('Error fetching team details');
  });

  it('should navigate to leagues after removing a team', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    teamsService.deleteTeam.and.returnValue(of(null));

    component.team = { id: 1, name: 'Team A', league: 'League A', image: false };
    component.removeTeam();

    expect(router.navigate).toHaveBeenCalledWith(['/leagues']);
  });

  it('should not remove a team if confirm is cancelled', () => {
    spyOn(window, 'confirm').and.returnValue(false);

    component.removeTeam();

    expect(router.navigate).not.toHaveBeenCalled();
  });

  it('should navigate to edit team page', () => {
    component.team = { id: 1, name: 'Team A', league: 'League A', image: false };
    component.editTeam();

    expect(router.navigate).toHaveBeenCalledWith(['/teams/edit', 1]);
  });

  it('should navigate to leagues page on goBack', () => {
    component.league = { id: 1, name: 'League A' };
    component.goBack();

    expect(router.navigate).toHaveBeenCalledWith(['leagues', 1]);
  });

  it('should load and create chart for team points per match', () => {
    const mockData = [
      { matchName: 'Match 1', points: 3 },
      { matchName: 'Match 2', points: 1 },
    ];
    teamMatchService.getPointsPerMatch.and.returnValue(of(mockData));

    component.cargarDatos(1);

    expect(teamMatchService.getPointsPerMatch).toHaveBeenCalledWith(1);
  });

  it('should generate team image URL', () => {
    const mockTeam: Team = { id: 1, name: 'Team A', league: 'League A', image: true };
    teamsService.getImage.and.returnValue('mock-url');

    component.team = mockTeam;
    const url = component.teamImage();

    expect(url).toBe('mock-url');
  });

  it('should return default image if team has no image', () => {
    const mockTeam: Team = { id: 1, name: 'Team A', league: 'League A', image: false };

    component.team = mockTeam;
    const url = component.teamImage();

    expect(url).toBe('assets/no_image.jpg');
  });
});
