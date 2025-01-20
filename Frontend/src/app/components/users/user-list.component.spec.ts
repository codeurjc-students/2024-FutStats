import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserListComponent } from './user-list.component';
import { UsersService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';
import { Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { User } from 'src/app/models/user.model';

describe('UserListComponent', () => {
  let component: UserListComponent;
  let fixture: ComponentFixture<UserListComponent>;
  let mockUsersService: any;
  let mockLoginService: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockUsersService = {
      getUsers: jasmine.createSpy('getUsers').and.returnValue(of([
        { id: 1, name: 'testUser', password: 'pass', image: false, roles: ['[user]'] },
        { id: 2, name: 'testUser', password: 'pass', image: false, roles: ['[user]'] },
      ] as User[])),
    };

    mockLoginService = {};

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    await TestBed.configureTestingModule({
      declarations: [UserListComponent],
      providers: [
        { provide: UsersService, useValue: mockUsersService },
        { provide: LoginService, useValue: mockLoginService },
        { provide: Router, useValue: mockRouter },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(UserListComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  describe('ngOnInit', () => {
    it('should load users on init', () => {
      component.ngOnInit();

      expect(mockUsersService.getUsers).toHaveBeenCalled();
      expect(component.users).toEqual([
        { id: 1, name: 'testUser', password: 'pass', image: false, roles: ['[user]'] },
        { id: 2, name: 'testUser', password: 'pass', image: false, roles: ['[user]'] },
      ]);
    });
  });

  describe('newUser', () => {
    it('should navigate to the new user creation page', () => {
      component.newUser();

      expect(mockRouter.navigate).toHaveBeenCalledWith(['/users/new']);
    });
  });

  describe('goBack', () => {
    it('should navigate to the leagues page', () => {
      component.goBack();

      expect(mockRouter.navigate).toHaveBeenCalledWith(['/leagues']);
    });
  });
});
