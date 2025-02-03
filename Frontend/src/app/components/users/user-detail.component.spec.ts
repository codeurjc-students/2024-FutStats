import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserDetailComponent } from './user-detail.component';
import { UsersService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';
import { Router, ActivatedRoute } from '@angular/router';
import { of, throwError } from 'rxjs';

describe('UserDetailComponent', () => {
  let component: UserDetailComponent;
  let fixture: ComponentFixture<UserDetailComponent>;
  let mockUserService: any;
  let mockLoginService: any;
  let mockRouter: any;
  let mockActivatedRoute: any;

  beforeEach(async () => {
    mockUserService = {
      getUser: jasmine.createSpy('getUser').and.returnValue(of({ id: 1, name: 'User A', password:'123',email:'example@gmail.com', roles: ['user'], image: false })),
      getLeagues: jasmine.createSpy('getLeagues').and.returnValue(of([{ id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false }])),
      getTeams: jasmine.createSpy('getTeams').and.returnValue(of([{ id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' }])),
      getPlayers: jasmine.createSpy('getPlayers').and.returnValue(of([{ id: 1, name: 'Player 1', team: 'Team A', league: 'League A', image: false, nationality: 'Española', age: 22, position: 'Delantero' }])),
      deleteLeague: jasmine.createSpy('deleteLeague').and.returnValue(of({})),
      deleteTeam: jasmine.createSpy('deleteTeam').and.returnValue(of({})),
      deletePlayer: jasmine.createSpy('deletePlayer').and.returnValue(of({})),
      deleteUser: jasmine.createSpy('removeUser').and.returnValue(of({})),
      getImage: jasmine
        .createSpy('getImage')
        .and.returnValue('assets/401-background.jpg')
    };

    mockLoginService = {
      isLogged: jasmine.createSpy('isLogged').and.returnValue(true),
    };

    mockActivatedRoute = {
        snapshot: {
          params: { id: 1 },
        },
      };

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    await TestBed.configureTestingModule({
      declarations: [UserDetailComponent],
      providers: [
        { provide: UsersService, useValue: mockUserService },
        { provide: LoginService, useValue: mockLoginService },
        { provide: Router, useValue: mockRouter },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(UserDetailComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should load user details on init', () => {
    component.ngOnInit();

    expect(mockUserService.getUser).toHaveBeenCalled();
    expect(mockUserService.getLeagues).toHaveBeenCalled();
    expect(mockUserService.getTeams).toHaveBeenCalled();
    expect(mockUserService.getPlayers).toHaveBeenCalled();
  });


  it('should return user image', () => {
    component.user = { id: 1, name: 'User A', password:'123', email:'example@gmail.com', roles: ['user'], image: true };
    const image = component.userImage();
    expect(image).toBe('assets/401-background.jpg');
  });

  it('should return default image if no user image is present', () => {
    component.user = { id: 1, name: 'User A', password:'123', email:'example@gmail.com', roles: ['user'], image: false };
    const image = component.userImage();
    expect(image).toBe('assets/no_image.jpg');
  });

//   it('should remove a league from favorites', () => {
//     spyOn(window, 'confirm').and.returnValue(true);
//     const league = { id: 1, name: 'League 1', president: 'Florentino Perez', nationality: 'Española', teams: [], image: false };
//     component.removeLeague(league);
//     expect(window.confirm).toHaveBeenCalledWith('Quieres borrar esta liga?');
//     expect(mockUserService.removeLeague).toHaveBeenCalledWith(league);
//   });

//   it('should remove a team from favorites', () => {
//     spyOn(window, 'confirm').and.returnValue(true);
//     const team = { id: 1, name: 'Team 1', trophies: 1, nationality: 'Española', trainer: 'Mourinho', secondTrainer: 'Pepe', president: 'Paco', stadium: 'Bernabeu', points: 1, image: false, league: 'League 1' };
//     component.removeTeam(team);
//     expect(window.confirm).toHaveBeenCalledWith('Quieres borrar este equipo?');
//     expect(mockUserService.removeTeam).toHaveBeenCalledWith(team);
//   });

  it('should remove a player from favorites', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    const player = { id: 1, name: 'Player 1', team: 'Team A', league: 'League A', image: false, nationality: 'Española', age: 22, position: 'Delantero' };
    component.removePlayer(player);
    expect(window.confirm).toHaveBeenCalledWith('Quieres borrar este Jugador');
  });

  it('should navigate to edit user page', () => {
    component.user = { id: 1, name: 'User A', password:'123', email:'example@gmail.com', roles: ['user'], image: true };
    component.editUser();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users/edit', 1]);
  });

  it('should navigate back when goBack is called', () => {
    component.user = { id: 1, name: 'User A', password:'123', email:'example@gmail.com', roles: ['user'], image: true };
    component.goBack();
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users']);
  });

  it('should delete the user and navigate away', () => {
    spyOn(window, 'confirm').and.returnValue(true);
    component.removeUser();
    expect(window.confirm).toHaveBeenCalledWith('Quieres borrar este usuario');
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users']);
  });

  it('should not delete user if confirmation is cancelled', () => {
    spyOn(window, 'confirm').and.returnValue(false);
    component.removeUser();
  });
});
