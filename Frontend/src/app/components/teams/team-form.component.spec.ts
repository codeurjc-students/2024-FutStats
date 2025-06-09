import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TeamFormComponent } from './team-form.component';
import { TeamsService } from 'src/app/services/team.service';
import { LeaguesService } from 'src/app/services/league.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('TeamFormComponent', () => {
  let component: TeamFormComponent;
  let fixture: ComponentFixture<TeamFormComponent>;
  let mockTeamsService: any;
  let mockLeaguesService: any;
  let mockActivatedRoute: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockTeamsService = {
      getTeam: jasmine.createSpy('getTeam').and.returnValue(of({ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'})),
      addTeam: jasmine.createSpy('addTeam').and.returnValue(of({ name: 'New Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'})),
      updateTeam: jasmine.createSpy('updateTeam').and.returnValue(of({ id: 1, name: 'Update Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'})),
      addImage: jasmine.createSpy('addImage').and.returnValue(of({})),
      deleteImage: jasmine.createSpy('deleteImage').and.returnValue(of({})),
      getImage: jasmine.createSpy('getImage').and.returnValue('assets/401-background.jpg'),
    };

    mockLeaguesService = {
      getLeagues: jasmine.createSpy('getLeagues').and.returnValue(of([{ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false }])),
      getLeagueByName: jasmine.createSpy('getLeagueByName').and.returnValue(of({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false })),
    };

    mockActivatedRoute = {
      snapshot: { params: { id: 1 } },
    };

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    await TestBed.configureTestingModule({
      declarations: [TeamFormComponent],
      imports: [FormsModule],
      providers: [
        { provide: TeamsService, useValue: mockTeamsService },
        { provide: LeaguesService, useValue: mockLeaguesService },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: Router, useValue: mockRouter },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(TeamFormComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load leagues on initialization', () => {
    component.ngOnInit();
    expect(mockLeaguesService.getLeagues).toHaveBeenCalled();
    expect(component.leagues.length).toBeGreaterThan(0);
  });

  it('should load team data if ID is provided', () => {
    expect(mockTeamsService.getTeam).toHaveBeenCalledWith(1);
    expect(component.team).toEqual({ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'});
    expect(component.newTeam).toBeFalse();
  });

  it('should handle league loading errors', () => {
    mockLeaguesService.getLeagues.and.returnValue(throwError('Error loading leagues'));
    component.ngOnInit();
    expect(component.leagues.length).toBe(0);
  });

  it('should save a new team', () => {
    component.newTeam = true;
    component.team = { name: 'New Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};
    component.save();

    expect(mockTeamsService.addTeam).toHaveBeenCalledWith(component.team);
    expect(mockRouter.navigate).toHaveBeenCalled();
  });

  it('should update an existing team', () => {
    component.newTeam = false;
    component.team = { id: 1, name: 'Update Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};
    component.save();

    expect(mockTeamsService.updateTeam).toHaveBeenCalledWith(component.team);
    expect(mockRouter.navigate).toHaveBeenCalled();
  });

  it('should upload an image after saving a team', () => {
    const mockFile = new Blob([''], { type: 'assets/401-background.jpg' });
    component.fileInput = { nativeElement: { files: [mockFile] } };
    component.uploadImage({ id: 1, name: 'Update Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: true, league: 'League 1'});

    expect(mockTeamsService.addImage).toHaveBeenCalled();
  });

  it('should delete an image if removeImage is true', () => {
    component.removeImage = true;
    component.uploadImage({ id: 1, name: 'Update Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'});
  });

  it('should navigate back on cancel', () => {
    spyOn(window.history, 'back');
    component.cancel();
    expect(window.history.back).toHaveBeenCalled();
  });

  it('should return the correct team image', () => {
    component.team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: true, league: 'League 1'};
    const image = component.teamImage();
    expect(image).toBe('assets/no_image.jpg');
  });

  it('should return a default image if no image exists', () => {
    component.team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1'};
    const image = component.teamImage();
    expect(image).toBe('assets/no_image.jpg');
  });
});