import { TestBed, ComponentFixture } from '@angular/core/testing';
import { LeagueFormComponent } from './league-form.component';
import { LeaguesService } from '../../services/league.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { FormsModule } from '@angular/forms';

class MockLeaguesService {
  getLeagueById = jasmine.createSpy('getLeagueById').and.returnValue(of({ id: 1, name: 'Test League', image: true }));
  addLeague = jasmine.createSpy('addLeague').and.returnValue(of({ id: 2, name: 'New League' }));
  updateLeague = jasmine.createSpy('updateLeague').and.returnValue(of({ id: 1, name: 'Updated League' }));
  addImage = jasmine.createSpy('addImage').and.returnValue(of(null));
  deleteImage = jasmine.createSpy('deleteImage').and.returnValue(of(null));
  getImage = jasmine.createSpy('getImage').and.returnValue('mocked_image_url');
}

class MockRouter {
  navigate = jasmine.createSpy('navigate');
}

class MockActivatedRoute {
  snapshot = { params: { id: '1' } };
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
    component.ngOnInit();
    expect(service.getLeagueById).toHaveBeenCalledWith('1');
    expect(component.newLeague).toBeFalse();
    expect(component.league.name).toBe('Test League');
  });

  it('should initialize with a new league if no id is present', () => {
    const route = TestBed.inject(ActivatedRoute) as unknown as MockActivatedRoute;
    route.snapshot.params = {};

    component = new LeagueFormComponent(router as any, route as any, service as any);

    expect(component.newLeague).toBeTrue();
    expect(component.league).toEqual({ name: '', nationality: '', president: '', teams: [], image: false });
  });

  it('should navigate back on cancel', () => {
    component.cancel();
    expect(router.navigate).not.toHaveBeenCalled();
    // Verify the call to window.history.back (mocked)
  });

  it('should call addLeague on save for new leagues', () => {
    component.newLeague = true;
    component.league = { name: 'New League', nationality: '', president: '', teams: [], image: false };
    component.save();

    expect(service.addLeague).toHaveBeenCalledWith(component.league);
    expect(service.addImage).not.toHaveBeenCalled();
  });

  it('should call updateLeague on save for existing leagues', () => {
    component.newLeague = false;
    component.league = { id: 1, name: 'Updated League', nationality: '', president: '', teams: [], image: false };
    component.save();

    expect(service.updateLeague).toHaveBeenCalledWith(component.league);
  });

  it('should handle image upload if file is present', () => {
    component.file = { nativeElement: { files: [new Blob()] } };
    const league = { id: 1, name: 'League with Image' };

    component.uploadImage(league);

    expect(service.addImage).toHaveBeenCalled();
    expect(router.navigate).toHaveBeenCalledWith(['/leagues', 1]);
  });

  it('should handle image deletion if removeImage is true', () => {
    component.removeImage = true;
    const league = { id: 1, name: 'League without Image' };

    component.uploadImage(league);

    expect(service.deleteImage).toHaveBeenCalledWith(league);
    expect(router.navigate).toHaveBeenCalledWith(['/leagues', 1]);
  });

  it('should return the correct league image URL', () => {
    component.league = { id: 1, name: 'League', image: true };
    expect(component.leagueImage()).toBe('mocked_image_url');

    component.league.image = false;
    expect(component.leagueImage()).toBe('assets/no_image.png');
  });
});
