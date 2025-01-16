import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserDetailComponent } from './user-detail.component';
import { UsersService } from '../../services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';
import { of, throwError } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('UserDetailComponent', () => {
  let component: UserDetailComponent;
  let fixture: ComponentFixture<UserDetailComponent>;
  let mockUsersService: any;
  let mockRouter: any;
  let mockActivatedRoute: any;
  let mockLoginService: any;

  beforeEach(async () => {
    mockUsersService = {
      getUser: jasmine.createSpy('getUser').and.returnValue(of({ id: 1, name: 'User A', image: true })),
      getLeagues: jasmine.createSpy('getLeagues').and.returnValue(of([{ id: 1, name: 'League A' }])),
      getTeams: jasmine.createSpy('getTeams').and.returnValue(of([{ id: 1, name: 'Team A' }])),
      getPlayers: jasmine.createSpy('getPlayers').and.returnValue(of([{ id: 1, name: 'Player A' }])),
      deleteUser: jasmine.createSpy('deleteUser').and.returnValue(of({})),
      deleteLeague: jasmine.createSpy('deleteLeague').and.returnValue(of({})),
      deleteTeam: jasmine.createSpy('deleteTeam').and.returnValue(of({})),
      deletePlayer: jasmine.createSpy('deletePlayer').and.returnValue(of({})),
      getImage: jasmine.createSpy('getImage').and.returnValue('image/url.jpg'),
    };

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    mockActivatedRoute = {
      snapshot: { params: { id: '1' } },
    };

    mockLoginService = {};

    await TestBed.configureTestingModule({
      declarations: [UserDetailComponent],
      providers: [
        { provide: UsersService, useValue: mockUsersService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: LoginService, useValue: mockLoginService },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(UserDetailComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load user details on initialization', () => {
    component.ngOnInit();
    expect(mockUsersService.getUser).toHaveBeenCalledWith('1');
    expect(component.user.name).toBe('User A');
  });

  it('should load leagues, teams, and players on initialization', () => {
    component.ngOnInit();
    expect(mockUsersService.getLeagues).toHaveBeenCalledWith('1');
    expect(mockUsersService.getTeams).toHaveBeenCalledWith('1');
    expect(mockUsersService.getPlayers).toHaveBeenCalledWith('1');
    expect(component.leagues.length).toBe(1);
    expect(component.teams.length).toBe(1);
    expect(component.players.length).toBe(1);
  });

  it('should handle errors when fetching user details', () => {
    mockUsersService.getUser.and.returnValue(throwError('Error fetching user'));
    component.ngOnInit();
    expect(component.errorMessage).toBe('Error fetching league details');
  });

  it('should return user image URL if available', () => {
    component.user = { id: 1, name: 'User A', image: true };
    const imageUrl = component.userImage();
    expect(imageUrl).toBe('image/url.jpg');
  });

  it('should return default image URL if no user image is available', () => {
    component.user = { id: 1, name: 'User A', image: false };
    const imageUrl = component.userImage();
    expect(imageUrl).toBe('assets/no_image.jpg');
  });

  it('should delete the user when confirmed', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.user = { id: 1, name: 'User A', image: true };
    component.removeUser();
    expect(mockUsersService.deleteUser).toHaveBeenCalledWith(component.user);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users']);
  });

  it('should not delete the user when not confirmed', () => {
    spyOn(window, 'confirm').and.returnValue(false);
    component.user = { id: 1, name: 'User A', image: true };
    component.removeUser();
    expect(mockUsersService.deleteUser).not.toHaveBeenCalled();
  });

  it('should delete a league when confirmed', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    const league = { id: 1, name: 'League A' };
    component.removeLeague(league);
    expect(mockUsersService.deleteLeague).toHaveBeenCalledWith(component.user, league);
  });

  it('should delete a team when confirmed', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    const team = { id: 1, name: 'Team A' };
    component.removeTeam(team);
    expect(mockUsersService.deleteTeam).toHaveBeenCalledWith(component.user, team);
  });

  it('should delete a player when confirmed', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    const player = { id: 1, name: 'Player A' };
    component.removePlayer(player);
    expect(mockUsersService.deletePlayer).toHaveBeenCalledWith(component.user, player);
  });

  it('should navigate to edit user page', () => {
    component.user = { id: 1, name: 'User A', image: true };
    component.editUser();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users/edit', 1]);
  });

  it('should navigate back to users list', () => {
    component.goBack();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users']);
  });
});
