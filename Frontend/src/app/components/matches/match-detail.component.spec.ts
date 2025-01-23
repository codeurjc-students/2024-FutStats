import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatchDetailComponent } from './match-detail.component';
import { Router, ActivatedRoute } from '@angular/router';
import { MatchesService } from '../../services/match.service';
import { LoginService } from 'src/app/services/login.service';
import { TeamsService } from 'src/app/services/team.service';
import { of, throwError } from 'rxjs';

describe('MatchDetailComponent', () => {
  let component: MatchDetailComponent;
  let fixture: ComponentFixture<MatchDetailComponent>;
  let mockMatchesService: any;
  let mockTeamsService: any;
  let mockLoginService: any;
  let mockRouter: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
    mockMatchesService = {
      getMatch: jasmine.createSpy('getMatch').and.returnValue(of({ id: 1, leagueId: 1 })),
      getLeague: jasmine.createSpy('getLeague').and.returnValue(of({ id: 1, name: false })),
      getTeam1: jasmine.createSpy('getTeam1').and.returnValue(of({ id: 1, image: false })),
      getTeam2: jasmine.createSpy('getTeam2').and.returnValue(of({ id: 2, image: false })),
      getPlayerMatches: jasmine.createSpy('getPlayerMatches').and.returnValue(of([])),
      deleteMatch: jasmine.createSpy('deleteMatch').and.returnValue(of(null)),
    };

    mockTeamsService = {
      getImage: jasmine.createSpy('getImage').and.returnValue('http://example.com/team1.jpg'),
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
      declarations: [MatchDetailComponent],
      providers: [
        { provide: MatchesService, useValue: mockMatchesService },
        { provide: TeamsService, useValue: mockTeamsService },
        { provide: LoginService, useValue: mockLoginService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(MatchDetailComponent);
    component = fixture.componentInstance;
  });

  it('should fetch match details and related data on init', () => {
    component.ngOnInit();

    expect(mockMatchesService.getMatch).toHaveBeenCalledWith(1);
    expect(mockMatchesService.getLeague).toHaveBeenCalledWith(1);
    expect(mockMatchesService.getTeam1).toHaveBeenCalledWith(1);
    expect(mockMatchesService.getTeam2).toHaveBeenCalledWith(1);
    expect(mockMatchesService.getPlayerMatches).toHaveBeenCalledWith(1);
    expect(component.match).toEqual({ id: 1, league: "League 1" , name:'El clasico', date: new Date(), team1:'Team', team2:'Team2', place:'Lepe'});
    expect(component.league).toEqual({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false });
  });

  it('should return team1 image URL if image exists', () => {
    component.team1 = { id: 1, image: true } as any;
    const result = component.team1Image();

    expect(result).toBe('http://example.com/401-background.jpg');
    expect(mockTeamsService.getImage).toHaveBeenCalledWith('1');
  });

  it('should return default image if team1 image does not exist', () => {
    component.team1 = { id: 1, image: false } as any;
    const result = component.team1Image();

    expect(result).toBe('assets/no_image.jpg');
  });

  it('should return team2 image URL if image exists', () => {
    component.team2 = { id: 2, image: true} as any;
    const result = component.team2Image();

    expect(result).toBe('http://example.com/401-background.jpg');
    expect(mockTeamsService.getImage).toHaveBeenCalledWith(2);
  });

  it('should return default image if team2 image does not exist', () => {
    component.team2 = { id: 2, image: false } as any;
    const result = component.team2Image();

    expect(result).toBe('assets/no_image.jpg');
  });

  it('should navigate back to the league details page', () => {
    component.league = { id: 1 } as any;
    component.goBack();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues', 1]);
  });

  it('should navigate to edit match page', () => {
    component.match = { id:1 } as any;
    component.editMatch();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/matches/edit', 1]);
  });

  it('should confirm and delete match', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.match = { id: 1 } as any;

    component.removeMatch();

    expect(window.confirm).toHaveBeenCalledWith('Quieres borrar este partido?');
    expect(mockMatchesService.deleteMatch).toHaveBeenCalledWith(component.match);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues']);
  });

  it('should navigate to create player match page', () => {
    component.createPlayerMatch();

    expect(mockRouter.navigate).toHaveBeenCalledWith(['/playerMatch/new']);
  });
});
