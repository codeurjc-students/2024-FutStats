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

describe('TeamDetailComponent', () => {
  let component: TeamDetailComponent;
  let fixture: ComponentFixture<TeamDetailComponent>;
  let teamsService: any;
  let usersService: any;
  let teamMatchService: any;
  let router: any;

  beforeEach(async () => {
    mockTeamsService = {
      getTeam: jasmine.createSpy('getTeam').and.returnValue(of({ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'})),
      getPlayersByTeam: jasmine.createSpy('getPlayersByTeam').and.returnValue(of([{ id: 1, name: 'Player 1', team: 'Team A', league: 'League A', image: false, nationality:'Española', age: 22, position:'Delantero' }])),
      getLeagueByTeam: jasmine.createSpy('getLeagueByTeam').and.returnValue(of({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false })),
      deleteTeam: jasmine.createSpy('deleteTeam').and.returnValue(of({})),
      getImage: jasmine.createSpy('getImage').and.returnValue('assets/401-background.jpg')

    }; 

    mockRouter {
      navigate = jasmine.createSpy('navigate');
    }
    
    mockActivatedRoute {
      snapshot = { params: { id: 1 } };
    }

    mockUsersService = {
      getMe: jasmine.createSpy('getMe').and.returnValue(of({ id: 1, name: 'testUser', password: 'pass', email: 'eamil', image: false, roles: ['[user]'] })),
      addTeam: jasmine.createSpy('addTeam').and.returnValue(of({})),
    };

    mockTeamMatchService = {
      getPointsPerMatch: jasmine.createSpy('getPointsPerMatch').and.returnValue(of([])),
    };


    await TestBed.configureTestingModule({
      declarations: [TeamDetailComponent],
      providers: [
        { provide: TeamsService, useValue: mockTeamsService },
        { provide: UsersService, useValue: mockUsersService },
        { provide: TeamMatchesService, useValue: mockTeamMatchService },
        { provide: Router, useClass: mockRouter },
        { provide: ActivatedRoute, useClass: mockActivatedRoute },
        LoginService
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(TeamDetailComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should load team data on init', () => {

    fixture.detectChanges();

    expect(component.team).toEqual({ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'});
    expect(component.players).toEqual([{ id: 1, name: 'Player 1', team: 'Team A', league: 'League A', image: false, nationality:'Española', age: 22, position:'Delantero' }]);
    expect(component.league).toEqual({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false });
    expect(component.user).toEqual({ id: 1, name: 'testUser', password: 'pass', email: 'eamil', image: false, roles: ['[user]'] });
  });

  it('should navigate to leagues after removing a team', () => {
    spyOn(window, 'confirm').and.returnValue(true);

    component.team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};
    component.removeTeam();

    expect(router.navigate).toHaveBeenCalledWith(['/leagues']);
  });

  it('should not remove a team if confirm is cancelled', () => {
    spyOn(window, 'confirm').and.returnValue(false);

    component.removeTeam();

    expect(router.navigate).not.toHaveBeenCalled();
  });

  it('should navigate to edit team page', () => {
    component.team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};
    component.editTeam();

    expect(router.navigate).toHaveBeenCalledWith(['/teams/edit', 1]);
  });

  it('should navigate to leagues page on goBack', () => {
    component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
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

  it('should generate team image', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};

    component.team = mockTeam;
    const image = component.teamImage();

    expect(image).toBe('assets/401-background.jpg');
  });

  it('should return default image if team has no image', () => {
    const mockTeam: Team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};

    component.team = mockTeam;
    const image = component.teamImage();

    expect(image).toBe('assets/no_image.jpg');
  });
});
