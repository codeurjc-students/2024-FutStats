import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlayerDetailComponent } from './player-detail.component';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';
import { PlayersService } from '../../services/player.service';
import { LoginService } from 'src/app/services/login.service';
import { UsersService } from 'src/app/services/user.service';
import { PlayerMatchesService } from 'src/app/services/playerMatch.service';
import { Player } from '../../models/player.model';
import { Team } from '../../models/team.model';
import { League } from 'src/app/models/league.model';
import { PlayerMatch } from 'src/app/models/player-match.model';
import { User } from 'src/app/models/user.model';
import { Chart } from 'chart.js';

describe('PlayerDetailComponent', () => {
  let component: PlayerDetailComponent;
  let fixture: ComponentFixture<PlayerDetailComponent>;
  let mockRouter: any;
  let mockActivatedRoute: any;
  let mockPlayersService: any;
  let mockLoginService: any;
  let mockUsersService: any;
  let mockPlayerMatchesService: any;

  beforeEach(async () => {
    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    mockActivatedRoute = {
      snapshot: {
        params: { id: 1 },
      },
    };

    mockPlayersService = {
      getPlayer: jasmine.createSpy('getPlayer').and.returnValue(of({ id: 1, name: 'Test Player' })),
      getLeague: jasmine.createSpy('getLeague').and.returnValue(of({ id: 1, name: 'Test League' })),
      getTeam: jasmine.createSpy('getTeam').and.returnValue(of({ id: 1, name: 'Test Team' })),
      getPlayerMatches: jasmine.createSpy('getPlayerMatches').and.returnValue(of([])),
      deletePlayer: jasmine.createSpy('deletePlayer').and.returnValue(of({})),
      getImage: jasmine.createSpy('getImage').and.returnValue('image_url'),
    };

    mockLoginService = {
      isLogged: jasmine.createSpy('isLogged').and.returnValue(true),
    };

    mockUsersService = {
      getMe: jasmine.createSpy('getMe').and.returnValue(of({ id: 1, username: 'testuser' })),
      addPlayer: jasmine.createSpy('addPlayer').and.returnValue(of({})),
    };

    mockPlayerMatchesService = {
      getGoalsPerMatch: jasmine.createSpy('getGoalsPerMatch').and.returnValue(of([])),
    };

    await TestBed.configureTestingModule({
      declarations: [PlayerDetailComponent],
      providers: [
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: PlayersService, useValue: mockPlayersService },
        { provide: LoginService, useValue: mockLoginService },
        { provide: UsersService, useValue: mockUsersService },
        { provide: PlayerMatchesService, useValue: mockPlayerMatchesService },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PlayerDetailComponent);
    component = fixture.componentInstance;
  });

  it('should fetch player details on init', () => {
    component.ngOnInit();

    expect(mockPlayersService.getPlayer).toHaveBeenCalledWith(1);
    expect(mockPlayersService.getLeague).toHaveBeenCalledWith(1);
    expect(mockPlayersService.getTeam).toHaveBeenCalledWith(1);
    expect(mockPlayersService.getPlayerMatches).toHaveBeenCalledWith(1);
    expect(mockUsersService.getMe).toHaveBeenCalled();
  });

  it('should handle error fetching player details', () => {
    mockPlayersService.getPlayer.and.returnValue(throwError(() => new Error('Error fetching player')));
    component.ngOnInit();

    expect(component.errorMessage).toBe('Error fetching player details');
  });

  it('should create chart with data', () => {
    spyOn(component as any, 'crearGrafica');
    mockPlayerMatchesService.getGoalsPerMatch.and.returnValue(of([
      { matchName: 'Match 1', goals: 3 },
    ]));

    component.cargarDatos(1);

    expect(mockPlayerMatchesService.getGoalsPerMatch).toHaveBeenCalledWith(1);
    expect((component as any).crearGrafica).toHaveBeenCalledWith(['Match 1'], [3]);
  });

  it('should return player image URL', () => {
    component.player = { id: 1, image: 'test_image' } as Player;
    expect(component.playerImage()).toBe('image_url');
  });

  it('should return default image if no player image is present', () => {
    component.player = { id: 1, image: null } as Player;
    expect(component.playerImage()).toBe('assets/no_image.jpg');
  });

  it('should navigate back on goBack', () => {
    component.team = { id: 1 } as Team;
    component.goBack();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/teams', 1]);
  });

  it('should delete player', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.player = { id: 1 } as Player;

    component.removePlayer();

    expect(mockPlayersService.deletePlayer).toHaveBeenCalledWith(component.player);
  });

  it('should not delete player if confirm is canceled', () => {
    spyOn(window, 'confirm').and.returnValue(false);
    component.removePlayer();

    expect(mockPlayersService.deletePlayer).not.toHaveBeenCalled();
  });

  it('should navigate to edit player', () => {
    component.player = { id: 1 } as Player;
    component.editPlayer();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/players/edit', 1]);
  });

  it('should add player to user', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.user = { id: 1 } as User;
    component.player = { id: 1 } as Player;

    component.addPlayer();

    expect(mockUsersService.addPlayer).toHaveBeenCalledWith(component.user, component.player);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users', 1]);
  });

  it('should not add player to user if confirm is canceled', () => {
    spyOn(window, 'confirm').and.returnValue(false);
    component.addPlayer();

    expect(mockUsersService.addPlayer).not.toHaveBeenCalled();
  });
});
