import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlayerMatchFormComponent } from './playerMatch-form.component';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';
import { MatchesService } from 'src/app/services/match.service';
import { PlayersService } from 'src/app/services/player.service';
import { PlayerMatchesService } from 'src/app/services/playerMatch.service';
import { PlayerMatch } from 'src/app/models/player-match.model';

describe('PlayerMatchFormComponent', () => {
  let component: PlayerMatchFormComponent;
  let fixture: ComponentFixture<PlayerMatchFormComponent>;
  let mockRouter: any;
  let mockActivatedRoute: any;
  let mockMatchesService: any;
  let mockPlayersService: any;
  let mockPlayerMatchesService: any;

  beforeEach(async () => {
    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    mockActivatedRoute = {
      snapshot: {
        params: { id: null },
      },
    };

    mockMatchesService = {
      getMatches: jasmine.createSpy('getMatches').and.returnValue(of([])),
      getMatchByName: jasmine.createSpy('getMatchByName').and.returnValue(of({ id: 1 })),
      addPlayerMatch: jasmine.createSpy('addPlayerMatch').and.returnValue(of({})),
    };

    mockPlayersService = {
      getPlayers: jasmine.createSpy('getPlayers').and.returnValue(of([])),
    };

    mockPlayerMatchesService = {
      getPlayerMatch: jasmine.createSpy('getPlayerMatch').and.returnValue(of({})),
      updatePlayerMatch: jasmine.createSpy('updatePlayerMatch').and.returnValue(of({})),
    };

    await TestBed.configureTestingModule({
      declarations: [PlayerMatchFormComponent],
      providers: [
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: MatchesService, useValue: mockMatchesService },
        { provide: PlayersService, useValue: mockPlayersService },
        { provide: PlayerMatchesService, useValue: mockPlayerMatchesService },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(PlayerMatchFormComponent);
    component = fixture.componentInstance;
  });

  it('should initialize with a new playerMatch if no ID is provided', () => {
    expect(component.newPlayerMatch).toBeTrue();
    expect(component.playerMatch).toEqual(jasmine.objectContaining({
      name: '',
      match: 0,
      matchName: '',
      shoots: 0,
      goals: 0,
    }));
  });

  it('should fetch existing playerMatch if ID is provided', () => {
    mockActivatedRoute.snapshot.params.id = 1;
    mockPlayerMatchesService.getPlayerMatch.and.returnValue(of({ name: 'Test PlayerMatch', match: 1 }));

    component = new PlayerMatchFormComponent(
      mockRouter,
      mockActivatedRoute,
      mockMatchesService,
      mockPlayersService,
      mockPlayerMatchesService
    );

    expect(component.newPlayerMatch).toBeFalse();
    expect(mockPlayerMatchesService.getPlayerMatch).toHaveBeenCalledWith(1);
  });

  it('should fetch matches and players on init', () => {
    const mockMatches = [{ id: 1, name: 'Match 1', place:'Lepe', date: new Date(), team1:'Team1', team2:'Team2', league:'League' }];
    const mockPlayers = [{ id: 1, name: 'Player 1', age: 25, nationality:'Española', position:'Delantero', image:false, team:'Team1', league:'League'}];

    mockMatchesService.getMatches.and.returnValue(of(mockMatches));
    mockPlayersService.getPlayers.and.returnValue(of(mockPlayers));

    component.ngOnInit();

    expect(mockMatchesService.getMatches).toHaveBeenCalled();
    expect(mockPlayersService.getPlayers).toHaveBeenCalled();
    expect(component.matches).toEqual(mockMatches);
    expect(component.players).toEqual(mockPlayers);
  });

  it('should handle errors when fetching matches or players', () => {
    mockMatchesService.getMatches.and.returnValue(throwError(() => new Error('Error fetching matches')));
    mockPlayersService.getPlayers.and.returnValue(throwError(() => new Error('Error fetching players')));

    component.ngOnInit();

    expect(component.matches).toEqual([]);
    expect(component.players).toEqual([]);
  });

  it('should save a new playerMatch', () => {
    const mockPlayerMatch: PlayerMatch = { name: 'Test', match: 0, matchName: 'Test Match' } as PlayerMatch;
    component.newPlayerMatch = true;
    component.playerMatch = mockPlayerMatch;

    component.save();

    expect(mockMatchesService.getMatchByName).toHaveBeenCalledWith('Test Match');
    expect(mockMatchesService.addPlayerMatch).toHaveBeenCalledWith(jasmine.objectContaining({ name: 'Test' }));
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/playerMatch', null]);
  });

  it('should update an existing playerMatch', () => {
    const mockPlayerMatch: PlayerMatch = { name: 'Test', match: 1, matchName: 'Test Match' } as PlayerMatch;
    component.newPlayerMatch = false;
    component.playerMatch = mockPlayerMatch;

    component.save();

    expect(mockPlayerMatchesService.updatePlayerMatch).toHaveBeenCalledWith(1, mockPlayerMatch);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/playerMatch', null]);
  });

  it('should cancel and navigate back', () => {
    spyOn(window.history, 'back');

    component.cancel();

    expect(window.history.back).toHaveBeenCalled();
  });
});
