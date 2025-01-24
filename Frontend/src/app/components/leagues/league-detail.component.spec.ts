import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LeagueDetailComponent } from './league-detail.component';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';
import { LeaguesService } from '../../services/league.service';
import { UsersService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';
import { RouterTestingModule } from '@angular/router/testing';

describe('LeagueDetailComponent', () => {
  let component: LeagueDetailComponent;
  let fixture: ComponentFixture<LeagueDetailComponent>;
  let mockLeaguesService: any;
  let mockUsersService: any;
  let mockLoginService: any;
  let mockRouter: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
    mockLeaguesService = jasmine.createSpyObj('LeaguesService', [
      'getLeagueById',
      'getTeams',
      'getMatches',
      'deleteLeague',
      'getImage'
    ]);

    mockUsersService = jasmine.createSpyObj('UsersService', [
      'getMe',
      'addLeague'
    ]);

    mockLoginService = {};

    mockRouter = jasmine.createSpyObj('Router', ['navigate']);

    mockActivatedRoute = {
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
        { provide: LoginService, useValue: mockLoginService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(LeagueDetailComponent);
    component = fixture.componentInstance;
  });

  it('should fetch league details and related data on init', () => {
    const mockLeague = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
    const mockTeams = [{ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'}];
    const mockMatches = [{ id: 1, team1: 'Team 1', team2: 'Team 2', date:  new Date('2023-01-01'), place: 'Bernabeu', league: 'League 1', name: '1'}];
    const mockUser = { id: 1, name: 'testUser', password: 'pass', email: '', image: false, roles: ['[user]'] };

    mockLeaguesService.getLeagueById.and.returnValue(of(mockLeague));
    mockLeaguesService.getTeams.and.returnValue(of(mockTeams));
    mockLeaguesService.getMatches.and.returnValue(of(mockMatches));
    mockUsersService.getMe.and.returnValue(of(mockUser));

    component.ngOnInit();

    expect(mockLeaguesService.getLeagueById).toHaveBeenCalledWith(1);
    expect(mockLeaguesService.getTeams).toHaveBeenCalledWith(1);
    expect(mockLeaguesService.getMatches).toHaveBeenCalledWith(1);
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
    component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
    mockLeaguesService.getImage.and.returnValue('assets/no_image.jpg');

    const result = component.leagueImage();

    expect(result).toBe('assets/no_image.jpg');
  });

  it('should return default image if no league image exists', () => {
    component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    const result = component.leagueImage();

    expect(result).toBe('assets/no_image.jpg');
  });

  it('should navigate to edit league', () => {
    component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    component.editLeague();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues/edit', 1]);
  });

  it('should confirm and delete league', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    mockLeaguesService.deleteLeague.and.returnValue(of(null));

    component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
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

    component.user = { id: 1, name: 'testUser', password: 'pass', email:'', image: false, roles: ['[user]'] };
    component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

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
