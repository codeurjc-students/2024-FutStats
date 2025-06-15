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
  let mockRouter: any;
  let mockActivatedRoute: any;
  let mockPlayersService: any;
  let mockLeaguesService: any;
  let mockTeamsService: any;

  beforeEach(async () => {
    mockPlayersService = {
      getPlayer: jasmine.createSpy('getPlayer').and.returnValue(of({ id: 1, name: 'Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' })),
      addPlayer: jasmine.createSpy('addPlayer').and.returnValue(of({ name: 'New Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' })),
      updatePlayer: jasmine.createSpy('updatePlayer').and.returnValue(of({ id: 1, name: 'Update Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' })),
      addImage: jasmine.createSpy('addImage').and.returnValue(of({})),
      deleteImage: jasmine.createSpy('deleteImage').and.returnValue(of({})),
      getImage: jasmine.createSpy('getImage').and.returnValue('assets/401-background.jpg')
    };

    mockLeaguesService = {
      getLeagues: jasmine.createSpy('getLeagues').and.returnValue(of([{ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false }])),
      getTeamsByName: jasmine.createSpy('getTeamsByName').and.returnValue(of([{ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' }])),
    };

    mockTeamsService = {
      getTeamByName: jasmine.createSpy('getTeamByName').and.returnValue(of({ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' })),
    };

    mockActivatedRoute = {
      snapshot: { params: { id: 1 } },
    };

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

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

  it('should load leagues successfully', () => {

    component.loadLeagues();

    expect(component.leagues).toEqual([{ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false }]);
  });

  it('should add a new player if it is a new player', () => {
    component.newPlayer = true;
    component.player = { name: 'New Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' };
    mockPlayersService.addPlayer.and.returnValue(of({ name: 'New Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' }));

    component.save();

    expect(mockPlayersService.addPlayer).toHaveBeenCalledWith(component.player);
  });

  it('should call updatePlayer on save for existing players', () => {
    component.newPlayer = false;
    component.player = { id: 1, name: 'Updated Player', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' };
    
    mockPlayersService.updatePlayer.and.returnValue(of(component.player));
    mockPlayersService.getPlayer.and.returnValue(of(component.player));  
    
    component.save();
    
    expect(mockPlayersService.updatePlayer).toHaveBeenCalledWith(component.player);
  });

  it('should upload image if file is selected', () => {
    const mockFile = new Blob([''], { type: 'assets/401-background.jpg' });
    component.fileInput = { nativeElement: { files: [mockFile] } };
    component.uploadImage({ id: 1, name: 'Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' });

    expect(mockPlayersService.addImage).toHaveBeenCalled();
  });

  it('should delete image if removeImage is true', () => {
    component.removeImage = true;
    component.uploadImage({ id: 1, name: 'Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' });

  });

  it('should navigate back', () => {
    spyOn(window.history, 'back');

    component.cancel();

    expect(window.history.back).toHaveBeenCalled();
  });

  it('should return the player image if image exists', () => {
    component.player = { id: 1, name: 'Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: true, team: 'Team1', league: 'League' };

    const image = component.playerImage();

    expect(image).toBe('api/v1/players/1/image');
  });

  it('should return the default image if no image exists', () => {
    component.player = { id: 1, name: 'Player 1', age: 25, nationality: 'Española', position: 'Delantero', image: false, team: 'Team1', league: 'League' };

    const image = component.playerImage();

    expect(image).toBe('assets/no_image.jpg');
  });
});
