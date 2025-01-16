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
      getMatch: jasmine.createSpy('getMatch').and.returnValue(of({
        id: '1',
        place: 'Test Place',
        name: 'Test Match',
        date: new Date(),
        team1: '1',
        team2: '2',
        league: '1',
      } as Match)),
      addMatch: jasmine.createSpy('addMatch').and.returnValue(of({ id: '1' } as Match)),
      updateMatch: jasmine.createSpy('updateMatch').and.returnValue(of({ id: '1' } as Match)),
    };

    mockLeaguesService = {
      getLeagues: jasmine.createSpy('getLeagues').and.returnValue(of([{ id: '1', name: 'Test League' }] as League[])),
      getTeamsByName: jasmine.createSpy('getTeamsByName').and.returnValue(of([{ id: '1', name: 'Team 1' }, { id: '2', name: 'Team 2' }] as Team[])),
      getLeagueByName: jasmine.createSpy('getLeagueByName').and.returnValue(of({ id: '1', name: 'Test League' } as League)),
    };

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    mockActivatedRoute = {
      snapshot: {
        params: { id: '1' }, // Simula un partido existente
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

  describe('Initialization', () => {
    it('should load match details if ID is provided', () => {
      component.ngOnInit();

      expect(mockMatchesService.getMatch).toHaveBeenCalledWith('1');
      expect(component.match).toEqual(jasmine.objectContaining({ id: '1', name: 'Test Match' }));
      expect(component.newMatch).toBeFalse();
    });

    it('should create a new match if no ID is provided', () => {
      mockActivatedRoute.snapshot.params = {}; // Simula creación de un nuevo partido
      component = new MatchFormComponent(mockRouter, mockActivatedRoute, mockMatchesService, mockLeaguesService);

      component.ngOnInit();

      expect(component.newMatch).toBeTrue();
      expect(component.match).toEqual(jasmine.objectContaining({
        place: '',
        name: '',
        date: jasmine.any(Date),
        team1: '',
        team2: '',
        league: '',
      }));
    });

    it('should load leagues on initialization', () => {
      component.ngOnInit();

      expect(mockLeaguesService.getLeagues).toHaveBeenCalled();
      expect(component.leagues).toEqual([{ id: '1', name: 'Test League' }]);
    });
  });

  describe('onLeagueChange', () => {
    it('should load teams when a league is selected', () => {
      component.selectedLeagueId = '1';

      component.onLeagueChange();

      expect(mockLeaguesService.getTeamsByName).toHaveBeenCalledWith('1');
      expect(component.teams).toEqual([{ id: '1', name: 'Team 1' }, { id: '2', name: 'Team 2' }]);
    });

    it('should clear teams if no league is selected', () => {
      component.selectedLeagueId = '';

      component.onLeagueChange();

      expect(component.teams).toEqual([]);
    });
  });

  describe('save', () => {
    it('should add a new match if newMatch is true', () => {
      component.newMatch = true;
      component.match = {
        place: 'New Place',
        name: 'New Match',
        date: new Date(),
        team1: '1',
        team2: '2',
        league: '',
      };
      component.selectedLeagueId = '1';

      component.save();

      expect(mockMatchesService.addMatch).toHaveBeenCalledWith(component.match);
    });

    it('should update an existing match if newMatch is false', () => {
      component.newMatch = false;
      component.match = {
        id: '1',
        place: 'Updated Place',
        name: 'Updated Match',
        date: new Date(),
        team1: '1',
        team2: '2',
        league: '',
      };
      component.selectedLeagueId = '1';

      component.save();

      expect(mockMatchesService.updateMatch).toHaveBeenCalledWith(component.match);
    });
  });

  describe('afterSave', () => {
    it('should navigate to league details after saving', () => {
      component.match = { league: 'Test League' } as Match;

      component.afterSave({ id: '1' } as Match);

      expect(mockLeaguesService.getLeagueByName).toHaveBeenCalledWith('Test League');
      expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues', '1']);
    });
  });

  describe('cancel', () => {
    it('should navigate back when cancel is called', () => {
      spyOn(window.history, 'back');

      component.cancel();

      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
