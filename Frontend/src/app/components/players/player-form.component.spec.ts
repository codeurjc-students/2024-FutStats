import { ComponentFixture, TestBed } from '@angular/core/testing';
import { PlayerFormComponent } from './player-form.component';
import { ActivatedRoute, Router } from '@angular/router';
import { PlayersService } from 'src/app/services/player.service';
import { LeaguesService } from 'src/app/services/league.service';
import { TeamsService } from 'src/app/services/team.service';
import { of, throwError } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { FormsModule } from '@angular/forms';

describe('PlayerFormComponent', () => {
  let component: PlayerFormComponent;
  let fixture: ComponentFixture<PlayerFormComponent>;
  let mockRouter: jasmine.SpyObj<Router>;
  let mockActivatedRoute: any;
  let mockPlayersService: jasmine.SpyObj<PlayersService>;
  let mockLeaguesService: jasmine.SpyObj<LeaguesService>;
  let mockTeamsService: jasmine.SpyObj<TeamsService>;

  beforeEach(async () => {
    mockRouter = jasmine.createSpyObj('Router', ['navigate']);
    mockActivatedRoute = {
      snapshot: {
        params: { id: null },
      },
    };
    mockPlayersService = jasmine.createSpyObj('PlayersService', ['getPlayer', 'addPlayer', 'updatePlayer', 'addImage', 'deleteImage', 'getImage']);
    mockLeaguesService = jasmine.createSpyObj('LeaguesService', ['getLeagues', 'getTeamsByName']);
    mockTeamsService = jasmine.createSpyObj('TeamsService', ['getTeamByName']);

    await TestBed.configureTestingModule({
      declarations: [PlayerFormComponent],
      imports: [FormsModule],
      providers: [
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: PlayersService, useValue: mockPlayersService },
        { provide: LeaguesService, useValue: mockLeaguesService },
        { provide: TeamsService, useValue: mockTeamsService },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(PlayerFormComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should call loadLeagues on init', () => {
      spyOn(component as any, 'loadLeagues');
      component.ngOnInit();
      expect((component as any).loadLeagues).toHaveBeenCalled();
    });
  });

  describe('loadLeagues', () => {
    it('should load leagues successfully', () => {
      const leaguesMock = [{ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false }];
      mockLeaguesService.getLeagues.and.returnValue(of(leaguesMock));

      (component as any).loadLeagues();

      expect(component.leagues).toEqual(leaguesMock);
    });

    it('should log an error if loading leagues fails', () => {
      spyOn(console, 'error');
      mockLeaguesService.getLeagues.and.returnValue(throwError(() => new Error('Error loading leagues')));

      (component as any).loadLeagues();

      expect(console.error).toHaveBeenCalledWith('Error loading leagues:', jasmine.any(Error));
    });
  });

  describe('onLeagueChange', () => {
    it('should load teams for the selected league', () => {
      const teamsMock = [{ id: 1, name: 'Team 1', president:'Pepe', trophies: 1, nationality:'Española', trainer:'Jose', secondTrainer:'Paco', stadium:'Lepe', image: false, points: 1, league:'League' }];
      component.selectedLeagueId = '1';
      mockLeaguesService.getTeamsByName.and.returnValue(of(teamsMock));

      component.onLeagueChange();

      expect(component.teams).toEqual(teamsMock);
    });

    it('should clear teams if no league is selected', () => {
      component.selectedLeagueId = '';

      component.onLeagueChange();

      expect(component.teams).toEqual([]);
    });
  });

  describe('save', () => {
    it('should add a new player and upload image if it is a new player', () => {
      component.newPlayer = true;
      component.player = { name: 'Player 1', image: true } as any;
      mockPlayersService.addPlayer.and.returnValue(of({ id: '1' } as any));
      spyOn(component, 'uploadImage');

      component.save();

      expect(mockPlayersService.addPlayer).toHaveBeenCalledWith(component.player);
      expect(component.uploadImage).toHaveBeenCalledWith({ id: '1' } as any);
    });

    it('should update an existing player and upload image', () => {
      component.newPlayer = false;
      component.player = { name: 'Player 1', image: true } as any;
      mockPlayersService.updatePlayer.and.returnValue(of({ id: '1' } as any));
      spyOn(component, 'uploadImage');

      component.save();

      expect(mockPlayersService.updatePlayer).toHaveBeenCalledWith(component.player);
      expect(component.uploadImage).toHaveBeenCalledWith({ id: '1' } as any);
    });
  });

  describe('uploadImage', () => {
    it('should upload image if file is selected', () => {
      const fileMock = { nativeElement: { files: [new Blob()] } };
      component.file = fileMock;

      component.uploadImage({ id: 1 } as any);

      expect(mockPlayersService.addImage).toHaveBeenCalled();
    });

    it('should delete image if removeImage is true', () => {
      component.file = null;
      component.removeImage = true;

      component.uploadImage({ id: 1 } as any);

      expect(mockPlayersService.deleteImage).toHaveBeenCalled();
    });
  });

  describe('cancel', () => {
    it('should navigate back', () => {
      spyOn(window.history, 'back');

      component.cancel();

      expect(window.history.back).toHaveBeenCalled();
    });
  });

  describe('playerImage', () => {
    it('should return the player image URL if image exists', () => {
      component.player = { id: 1, image: true } as any;

      const result = component.playerImage();

      expect(result).toBe('http://image.url');
    });

    it('should return the default image URL if no image exists', () => {
      component.player = { id: '1', image: false } as any;

      const result = component.playerImage();

      expect(result).toBe('assets/no_image.png');
    });
  });
});
