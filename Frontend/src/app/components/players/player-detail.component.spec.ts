import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlayerDetailComponent } from './player-detail.component';
import { Router, ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { PlayersService } from '../../services/player.service';
import { LoginService } from 'src/app/services/login.service';
import { UsersService } from 'src/app/services/user.service';
import { PlayerMatchesService } from 'src/app/services/playerMatch.service';
import { Team } from '../../models/team.model';
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
      getPlayer: jasmine
        .createSpy('getPlayer')
        .and.returnValue(
          of({
            id: 1,
            name: 'Player 1',
            age: 25,
            nationality: 'Española',
            position: 'Delantero',
            image: false,
            team: 'Team1',
            league: 'League',
          })
        ),
      getLeague: jasmine
        .createSpy('getLeague')
        .and.returnValue(
          of({
            id: 1,
            name: 'League 1',
            president: 'Florentino Perez',
            nationality: 'Española',
            teams: [],
            image: false,
          })
        ),
      getTeam: jasmine
        .createSpy('getTeam')
        .and.returnValue(
          of({
            id: 1,
            name: 'Team 1',
            trophies: 1,
            nationality: 'Española',
            trainer: 'Mourinho',
            secondTrainer: 'Pepe',
            president: 'Paco',
            stadium: 'Bernabeu',
            points: 1,
            image: false,
            league: 'League 1',
          })
        ),
      getPlayerMatches: jasmine.createSpy('getPlayerMatches').and.returnValue(of([])),
      deletePlayer: jasmine.createSpy('deletePlayer').and.returnValue(of({})),
      getImage: jasmine.createSpy('getImage').and.returnValue('assets/401-background.jpg'),
    };

    mockLoginService = {
      isLogged: jasmine.createSpy('isLogged').and.returnValue(true),
    };

    mockUsersService = {
      getMe: jasmine
        .createSpy('getMe')
        .and.returnValue(
          of({
            id: 1,
            name: 'testUser',
            password: 'pass',
            email: 'email',
            image: true,
            roles: ['[user]'],
          })
        ),
      addPlayer: jasmine.createSpy('addPlayer').and.returnValue(of({})),
    };

    mockPlayerMatchesService = {
      getGoalsPerMatch: jasmine.createSpy('getGoalsPerMatch').and.returnValue(
        of([
          { matchName: 'Match 1', goals: 3 },
          { matchName: 'Match 2', goals: 1 },
        ])
      ),
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

    const canvas = document.createElement('canvas');
    canvas.setAttribute('id', 'golesChart');
    document.body.appendChild(canvas);

    fixture = TestBed.createComponent(PlayerDetailComponent);
    component = fixture.componentInstance;
  });

  it('should fetch player details on init', () => {
    component.ngOnInit();

    expect(mockPlayersService.getPlayer).toHaveBeenCalledWith(1);
    expect(mockPlayersService.getLeague).toHaveBeenCalledWith(1);
    expect(mockPlayersService.getTeam).toHaveBeenCalledWith(1);
    expect(mockUsersService.getMe).toHaveBeenCalled();
  });

  it('should return player image', () => {
    component.player = {
      id: 1,
      name: 'Player 1',
      age: 25,
      nationality: 'Española',
      position: 'Delantero',
      image: true,
      team: 'Team1',
      league: 'League',
    };
    const image = component.playerImage();
    expect(image).toBe('api/v1/players/1/image');
  });

  it('should return default image if no player image is present', () => {
    component.player = {
      id: 1,
      name: 'Player 1',
      age: 25,
      nationality: 'Española',
      position: 'Delantero',
      image: false,
      team: 'Team1',
      league: 'League',
    };
    const image = component.playerImage();
    expect(image).toBe('assets/no_image.jpg');
  });

  it('should navigate back on goBack', () => {
    component.team = { id: 1 } as Team;
    component.goBack();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/teams', 1]);
  });

  it('should delete player', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.player = {
      id: 1,
      name: 'Player 1',
      age: 25,
      nationality: 'Española',
      position: 'Delantero',
      image: false,
      team: 'Team1',
      league: 'League',
    };

    component.removePlayer();
    expect(window.confirm).toHaveBeenCalledWith('Quieres borrar este jugador?');
    expect(mockPlayersService.deletePlayer).toHaveBeenCalledWith(component.player);
  });

  it('should not delete player if confirm is canceled', () => {
    spyOn(window, 'confirm').and.returnValue(false);
    component.removePlayer();
    expect(mockPlayersService.deletePlayer).not.toHaveBeenCalled();
  });

  it('should navigate to edit player', () => {
    component.player = {
      id: 1,
      name: 'Upodate Player 1',
      age: 25,
      nationality: 'Española',
      position: 'Delantero',
      image: false,
      team: 'Team1',
      league: 'League',
    };
    component.editPlayer();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/players/edit/', 1]);
  });

  it('should add player to user', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.user = { id: 1 } as User;
    component.player = {
      id: 1,
      name: 'Player 1',
      age: 25,
      nationality: 'Española',
      position: 'Delantero',
      image: false,
      team: 'Team1',
      league: 'League',
    };

    component.addPlayer();
    expect(window.confirm).toHaveBeenCalledWith('Quieres añadir este jugador?');
    expect(mockUsersService.addPlayer).toHaveBeenCalledWith(component.user, component.player);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users', 1]);
  });

  it('should not add player to user if confirm is canceled', () => {
    spyOn(window, 'confirm').and.returnValue(false);
    component.addPlayer();
    expect(mockUsersService.addPlayer).not.toHaveBeenCalled();
  });
});
