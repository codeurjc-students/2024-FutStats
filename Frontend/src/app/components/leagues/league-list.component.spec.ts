import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LeagueListComponent } from './league-list.component';
import { LeaguesService } from 'src/app/services/league.service';
import { LoginService } from 'src/app/services/login.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { NgxPaginationModule } from 'ngx-pagination';

class MockLeaguesService {
  getLeagues = jasmine.createSpy('getLeagues').and.returnValue(of([
    { id: 1, name: 'League 1', nationality: 'Country 1', president: 'President 1', teams: [], image: false },
    { id: 2, name: 'League 2', nationality: 'Country 2', president: 'President 2', teams: [], image: false }
  ]));
}

class MockLoginService {}

class MockRouter {
  navigate = jasmine.createSpy('navigate');
}

describe('LeagueListComponent', () => {
  let component: LeagueListComponent;
  let fixture: ComponentFixture<LeagueListComponent>;
  let service: MockLeaguesService;
  let router: MockRouter;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        // Asegúrate de incluirlo aquí
        NgxPaginationModule
      ],
      declarations: [LeagueListComponent],
      providers: [
        { provide: LeaguesService, useClass: MockLeaguesService },
        { provide: LoginService, useClass: MockLoginService },
        { provide: Router, useClass: MockRouter }
      ]
    });

    fixture = TestBed.createComponent(LeagueListComponent);
    component = fixture.componentInstance;
    service = TestBed.inject(LeaguesService) as unknown as MockLeaguesService;
    router = TestBed.inject(Router) as unknown as MockRouter;
  });

  it('should fetch leagues on initialization', () => {
    component.ngOnInit();

    expect(service.getLeagues).toHaveBeenCalled();
    expect(component.leagues.length).toBe(2);
    expect(component.leagues[0].name).toBe('League 1');
    expect(component.leagues[1].name).toBe('League 2');
  });

  it('should navigate to new league form on newLeague()', () => {
    component.newLeague();

    expect(router.navigate).toHaveBeenCalledWith(['/leagues/new']);
  });
});
