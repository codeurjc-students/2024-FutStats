import { TestBed, ComponentFixture } from '@angular/core/testing';
import { LeagueFormComponent } from './league-form.component';
import { LeaguesService } from '../../services/league.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';

class MockLeaguesService {
  getLeagueById = jasmine.createSpy('getLeagueById').and.returnValue(of({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false }));
  addLeague = jasmine.createSpy('addLeague').and.returnValue(of({ name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false }));
  updateLeague = jasmine.createSpy('updateLeague').and.returnValue(of({ id: 1, name: 'Updated League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false }));
  addImage = jasmine.createSpy('addImage').and.returnValue(of(null));
  deleteImage = jasmine.createSpy('deleteImage').and.returnValue(of(null));
  getImage = jasmine.createSpy('getImage').and.returnValue('assets/401-background.jpg');
}

class MockRouter {
  navigate = jasmine.createSpy('navigate');
}

class MockActivatedRoute {
  snapshot = { params: { id: 1 } };
}

describe('LeagueFormComponent', () => {
  let component: LeagueFormComponent;
  let fixture: ComponentFixture<LeagueFormComponent>;
  let service: MockLeaguesService;
  let router: MockRouter;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeagueFormComponent],
      imports: [FormsModule],
      providers: [
        { provide: LeaguesService, useClass: MockLeaguesService },
        { provide: Router, useClass: MockRouter },
        { provide: ActivatedRoute, useClass: MockActivatedRoute }
      ]
    });

    fixture = TestBed.createComponent(LeagueFormComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(LeaguesService) as unknown as MockLeaguesService;
    router = TestBed.inject(Router) as unknown as MockRouter;
  });

  it('should initialize with an existing league if id is present', () => {
    expect(service.getLeagueById).toHaveBeenCalledWith(1);
    expect(component.newLeague).toBeFalse();
    expect(component.league).toEqual({ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false });
  });

  it('should initialize with a new league if no id is present', () => {
    const route = TestBed.inject(ActivatedRoute) as unknown as MockActivatedRoute;

    component = new LeagueFormComponent(router as any, route as any, service as any);

    expect(component.newLeague).toBeTrue();
    expect(component.league).toEqual({ name: '', nationality: '', president: '', teams: [], image: false });
  });

  it('should navigate back on cancel', () => {
    component.cancel();
    expect(router.navigate).not.toHaveBeenCalled();
  });

  it('should call addLeague on save for new leagues', () => {
    component.newLeague = true;
    component.league = { name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
    component.save();

    expect(service.addLeague).toHaveBeenCalledWith(component.league);
    expect(service.addImage).not.toHaveBeenCalled();
  });

  it('should call updateLeague on save for existing leagues', () => {
    component.newLeague = false;
    component.league = { id: 1, name: 'Updated League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
    component.save();

    expect(service.updateLeague).toHaveBeenCalledWith(component.league);
  });

  it('should handle image upload if file is present', () => {
    const mockFile = new Blob([''], { type: 'assets/401-background.jpg' });
    component.file = { nativeElement: { files: [mockFile] } };
    const league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: true };

    component.uploadImage(league);

    expect(service.addImage).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/leagues', 1]);
  });

  it('should handle image deletion if removeImage is true', () => {
    component.removeImage = true;
    const league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };

    component.uploadImage(league);

    expect(service.deleteImage).toHaveBeenCalledWith(league);
    expect(router.navigate).toHaveBeenCalledWith(['/leagues', 1]);
  });

  it('should return the correct league image', () => {
    component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: true };
    image = component.leagueImage();
    expect(image).toBe('assets/401-background.jpg');
  });

  it('should return a default image if no image exists', () => {
    component.league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [] , image: false };
    image = component.leagueImage();
    expect(image).toBe('assets/no_image.jpg');
  });
});
