import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlayerMatchDetailComponent } from './playerMatch-detail.component';
import { PlayerMatchesService } from '../../services/playerMatch.service';
import { TeamsService } from '../../services/team.service';
import { PlayersService } from '../../services/player.service';
import { LoginService } from '../../services/login.service';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';
import { PlayerMatch } from '../../models/player-match.model';
import { Player } from '../../models/player.model';
import { Match } from '../../models/match.model';

describe('PlayerMatchDetailComponent', () => {
  let component: PlayerMatchDetailComponent;
  let fixture: ComponentFixture<PlayerMatchDetailComponent>;
  let mockPlayerMatchesService: any;
  let mockTeamsService: any;
  let mockPlayersService: any;
  let mockLoginService: any;
  let mockRouter: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
    mockPlayerMatchesService = {
      getPlayerMatch: jasmine.createSpy('getPlayerMatch').and.returnValue(of({
        id: 1,
        name: 'Jose',
        match: 1,
        matchName: 'El Clasico',
        score: 10,
        assists: 5,
        fouls: 2,
      } as PlayerMatch)),
      getPlayer: jasmine.createSpy('getPlayer').and.returnValue(of({
        id: 1,
        name: 'Test Player',
        image: false,
      } as Player)),
      deletePlayerMatch: jasmine.createSpy('deletePlayerMatch').and.returnValue(of({})),
      getMatch: jasmine.createSpy('getMatch').and.returnValue(of({
        id: 1,
        team1: 'Team 1',
        team2: 'Team 2',
        date: new Date(),
        place: 'Bernabeu',
      } as Match)),
    };

    mockTeamsService = {};

    mockPlayersService = {
      getImage: jasmine.createSpy('getImage').and.returnValue('assets/no_image.jpg'),
    };

    mockLoginService = {};

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    mockActivatedRoute = {
      snapshot: {
        params: { id: 1 },
      },
    };

    await TestBed.configureTestingModule({
      declarations: [PlayerMatchDetailComponent],
      providers: [
        { provide: PlayerMatchesService, useValue: mockPlayerMatchesService },
        { provide: TeamsService, useValue: mockTeamsService },
        { provide: PlayersService, useValue: mockPlayersService },
        { provide: LoginService, useValue: mockLoginService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PlayerMatchDetailComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  describe('Initialization', () => {
    it('should load playerMatch details on init', () => {
      component.ngOnInit();

      expect(mockPlayerMatchesService.getPlayerMatch).toHaveBeenCalledWith(1);
      expect(component.playerMatch).toEqual(jasmine.objectContaining({ id: 1, score: 10 }));
    });

    it('should load player details on init', () => {
      component.ngOnInit();

      expect(mockPlayerMatchesService.getPlayer).toHaveBeenCalledWith(1);
      expect(component.player).toEqual(jasmine.objectContaining({ id: 1, name: 'Test Player' }));
    });
  });

    it('should delete the playerMatch and navigate to leagues if confirmed', () => {
      spyOn(window, 'confirm').and.returnValue(true);

      component.playerMatch = { id: 1 } as PlayerMatch;
      component.removePlayerMatch();

      expect(window.confirm).toHaveBeenCalledWith('Quieres borrar este jugador?');
      expect(mockPlayerMatchesService.deletePlayerMatch).toHaveBeenCalledWith(component.playerMatch);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues']);
    });

    it('should navigate to the edit playerMatch page', () => {
      component.playerMatch = { id: 1 } as PlayerMatch;

      component.editPlayerMatch();

      expect(mockRouter.navigate).toHaveBeenCalledWith(['/playerMatch/edit', 1]);
    });
});
