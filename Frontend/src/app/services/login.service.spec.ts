import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LoginService } from './login.service';
import { User } from '../models/user.model';

const BASE_URL = '/api/v1/';

describe('LoginService', () => {
  let service: LoginService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [LoginService],
    });
    service = TestBed.inject(LoginService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should initialize and check if logged in', () => {
    const mockUser: User = { id: 1, name: 'testUser', password: 'pass', image: false, roles: ['[user]'] };

    service.reqIsLogged();

    const req = httpMock.expectOne(`${BASE_URL}users/me`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);

    expect(service.logged).toBeTrue();
    expect(service.user).toEqual(mockUser);
  });

  it('should log in and update state', () => {
    spyOn(service, 'reqIsLogged').and.callThrough();

    service.logIn('testUser', 'testPass');

    const req = httpMock.expectOne(`${BASE_URL}login`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ username: 'testUser', password: 'testPass' });
    req.flush(null);

    expect(service.reqIsLogged).toHaveBeenCalled();
  });

  it('should log out and clear state', () => {
    service.logOut();

    const req = httpMock.expectOne(`${BASE_URL}logout`);
    expect(req.request.method).toBe('POST');
    req.flush(null);

    expect(service.logged).toBeFalse();
    expect(service.user).toBeUndefined();
  });

  it('should return logged status', () => {
    service.logged = true;
    expect(service.isLogged()).toBeTrue();

    service.logged = false;
    expect(service.isLogged()).toBeFalse();
  });

  it('should check if user is admin', () => {
    const mockUser: User = { id: 1, name: 'testUser', password: 'pass', image: false, roles: ['[admin]','[user]'] };
    expect(service.isAdmin()).toBeTrue();

    service.user = { id: 2, name: 'normalUser', password: 'pass', image: false, roles: ['[user]'] };
    expect(service.isAdmin()).toBeFalse();

    service.user = undefined;
    expect(service.isAdmin()).toBeFalse();
  });

  it('should return the current user', () => {
    const mockUser: User = { id: 2, name: 'normalUser', password: 'pass', image: false, roles: ['[user]'] };
    service.user = mockUser;

    expect(service.currentUser()).toEqual(mockUser);
  });
});
