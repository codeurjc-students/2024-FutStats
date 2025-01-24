import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatchFormComponent } from './match-form.component';
import { MatchesService } from 'src/app/services/match.service';
import { LeaguesService } from 'src/app/services/league.service';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';
import { Match } from 'src/app/models/match.model';
import { League } from 'src/app/models/league.model';
import { Team } from 'src/app/models/team.model';

describe('MatchFormComponent', () => {
  let component: MatchFormComponent;
  let fixture: ComponentFixture<MatchFormComponent>;
  let mockMatchesService: any;
  let mockLeaguesService: any;
  let mockRouter: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
    mockMatchesService = {
      getMatch: jasmine.createSpy('getMatch').and.returnValue(of({ id: 1, league: "League 1", name: 'El clasico', date: new Date(), team1: 'Team', team2: 'Team2', place: 'Lepe' })),
      addMatch: jasmine.createSpy('addMatch').and.returnValue(of({ league: "League 1", name: ' New El clasico', date: new Date(), team1: 'Team', team2: 'Team2', place: 'Lepe' })),
      updateMatch: jasmine.createSpy('updateMatch').and.returnValue(of({ id: 1, league: "League 1", name: ' Updated El clasico', date: new Date(), team1: 'Team', team2: 'Team2', place: 'Lepe' })),
    };

    mockLeaguesService = {
      getLeagues: jasmine.createSpy('getLeagues').and.returnValue(of([{ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false }])),
      getTeamsByName: jasmine.createSpy('getTeamsByName').and.returnValue(of([{ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' },
      { id: 2, name: 'Team 2', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' }])),
      getLeagueByName: jasmine.createSpy('getLeagueByName').and.returnValue(of({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false })),
    };

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    mockActivatedRoute = {
      snapshot: {
        params: { id: 1 },
      },
    };

    await TestBed.configureTestingModule({
      declarations: [MatchFormComponent],
      providers: [
        { provide: MatchesService, useValue: mockMatchesService },
        { provide: LeaguesService, useValue: mockLeaguesService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MatchFormComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should load match details if ID is provided', () => {
    component.ngOnInit();

    expect(mockMatchesService.getMatch).toHaveBeenCalledWith(1);
    expect(component.match).toEqual({ id: 1, league: "League 1", name: 'El clasico', date: new Date(), team1: 'Team', team2: 'Team2', place: 'Lepe' });
    expect(component.newMatch).toBeFalse();
  });

  it('should create a new match if no ID is provided', () => {
    mockActivatedRoute.snapshot.params = {};
    component = new MatchFormComponent(mockRouter, mockActivatedRoute, mockMatchesService, mockLeaguesService);

    component.ngOnInit();

    expect(component.newMatch).toBeTrue();
    expect(component.match).toEqual({ place: '', name: '', date: new Date, team1: '', team2: '', league: '' });
  });

  it('should load leagues on initialization', () => {
    component.ngOnInit();

    expect(mockLeaguesService.getLeagues).toHaveBeenCalled();
    expect(component.leagues).toEqual([{ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false }]);
  });

  it('should load teams when a league is selected', () => {
    component.selectedLeagueId = '1';

    component.onLeagueChange();

    expect(mockLeaguesService.getTeamsByName).toHaveBeenCalledWith(1);
    expect(component.teams).toEqual([{ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' },
    { id: 2, name: 'Team 2', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' }]);
  });

  it('should clear teams if no league is selected', () => {
    component.selectedLeagueId = '';

    component.onLeagueChange();

    expect(component.teams).toEqual([]);
  });

  it('should add a new match if newMatch is true', () => {
    component.newMatch = true;
    component.match = { league: "League 1", name: ' New El clasico', date: new Date(), team1: 'Team', team2: 'Team2', place: 'Lepe' };
    component.selectedLeagueId = '1';

    component.save();

    expect(mockMatchesService.addMatch).toHaveBeenCalledWith(component.match);
  });

  it('should update an existing match if newMatch is false', () => {
    component.newMatch = false;
    component.match = { id: 1, league: "League 1", name: ' Updated El clasico', date: new Date(), team1: 'Team', team2: 'Team2', place: 'Lepe' };
    component.selectedLeagueId = '1';

    component.save();

    expect(mockMatchesService.updateMatch).toHaveBeenCalledWith(component.match);
  });

  it('should navigate to league details after saving', () => {
    component.match = { id: 1, league: "League 1", name: ' Updated El clasico', date: new Date(), team1: 'Team', team2: 'Team2', place: 'Lepe' };

    component.afterSave({ id: 1, league: "League 1", name: ' Updated El clasico', date: new Date(), team1: 'Team', team2: 'Team2', place: 'Lepe' });

    expect(mockLeaguesService.getLeagueByName).toHaveBeenCalledWith('Test League');
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues', 1]);
  });

  it('should navigate back when cancel is called', () => {
    spyOn(window.history, 'back');

    component.cancel();

    expect(window.history.back).toHaveBeenCalled();
  });

});
