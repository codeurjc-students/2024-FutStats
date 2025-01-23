import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlayerMatchFormComponent } from './playerMatch-form.component';
import { Router, ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
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
    mockMatchesService = {
      getMatches: jasmine.createSpy('getMatches').and.returnValue(of([{ id: 1, league: "League 1" , name:'El clasico', date: new Date(), team1:'Team', team2:'Team2', place:'Lepe'}])),
      getMatchByName: jasmine.createSpy('getMatchByName').and.returnValue(of({ id: 1, league: "League 1" , name:'El clasico', date: new Date(), team1:'Team', team2:'Team2', place:'Lepe'})),
      addPlayerMatch: jasmine.createSpy('addPlayerMatch').and.returnValue(of({})),
    };

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    mockActivatedRoute = {
      snapshot: {
        params: { id: 1 },
      },
    };

    mockPlayersService = {
      getPlayers: jasmine.createSpy('getPlayers').and.returnValue(of([{ id: 1, name: 'Player 1', age: 25, nationality:'Española', position:'Delantero', image:false, team:'Team1', league:'League'}])),
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
    expect(component.playerMatch).toEqual(jasmine.objectContaining({name: '', match: 0, matchName: '', shoots: 0, goals: 0,}));
  });

  it('should fetch existing playerMatch if ID is provided', () => {
    mockActivatedRoute.snapshot.params.id = 1;
    mockPlayerMatchesService.getPlayerMatch.and.returnValue(of({ name: 'Test PlayerMatch', match: 1 } as PlayerMatch));

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

    component.ngOnInit();

    expect(mockMatchesService.getMatches).toHaveBeenCalled();
    expect(mockPlayersService.getPlayers).toHaveBeenCalled();
    expect(component.matches).toEqual([{ id: 1, league: "League 1" , name:'El clasico', date: new Date(), team1:'Team', team2:'Team2', place:'Lepe'}]);
    expect(component.players).toEqual([{ id: 1, name: 'Player 1', age: 25, nationality:'Española', position:'Delantero', image:false, team:'Team1', league:'League'}]);
  });

  it('should save a new playerMatch', () => {
    const mockPlayerMatch: PlayerMatch = { name: 'Test', match: 0, matchName: 'Test Match' } as PlayerMatch;
    component.newPlayerMatch = true;
    component.playerMatch = mockPlayerMatch;

    component.save();

    expect(mockMatchesService.getMatchByName).toHaveBeenCalledWith('Test Match');
    expect(mockMatchesService.addPlayerMatch).toHaveBeenCalledWith(jasmine.objectContaining({ name: 'Test' }));
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues']);
  });

  it('should update an existing playerMatch', () => {
    const mockPlayerMatch: PlayerMatch = { name: 'Test', match: 1, matchName: 'Test Match' } as PlayerMatch;
    component.newPlayerMatch = false;
    component.playerMatch = mockPlayerMatch;

    component.save();

    expect(mockPlayerMatchesService.updatePlayerMatch).toHaveBeenCalledWith(1, mockPlayerMatch);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues']);
  });

  it('should cancel and navigate back', () => {
    spyOn(window.history, 'back');

    component.cancel();

    expect(window.history.back).toHaveBeenCalled();
  });
});
