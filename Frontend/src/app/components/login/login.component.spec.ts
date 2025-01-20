// import { ComponentFixture, TestBed } from '@angular/core/testing';
// import { LoginComponent } from './login.component';
// import { LoginService } from 'src/app/services/login.service';
// import { Router } from '@angular/router';
// import { FormsModule } from '@angular/forms';
// import { of } from 'rxjs';

// class MockLoginService {
//   isLogged = jasmine.createSpy('isLogged').and.returnValue(false);
//   currentUser = jasmine.createSpy('currentUser').and.returnValue({ name: 'John Doe' });
//   isAdmin = jasmine.createSpy('isAdmin').and.returnValue(false);
//   logIn = jasmine.createSpy('logIn').and.returnValue(of(true));
//   logOut = jasmine.createSpy('logOut');
// }

// class MockRouter {
//   navigate = jasmine.createSpy('navigate');
// }

// describe('LoginComponent', () => {
//   let component: LoginComponent;
//   let fixture: ComponentFixture<LoginComponent>;
//   let loginService: MockLoginService;
//   let router: MockRouter;

//   beforeEach(async () => {
//     loginService = new MockLoginService();
//     router = new MockRouter();

//     await TestBed.configureTestingModule({
//       declarations: [LoginComponent],
//       imports: [FormsModule],
//       providers: [
//         { provide: LoginService, useValue: loginService },
//         { provide: Router, useValue: router },
//       ],
//     }).compileComponents();

//     fixture = TestBed.createComponent(LoginComponent);
//     component = fixture.componentInstance;
//   });

//   it('should display login form when user is not logged in', () => {
//     loginService.isLogged.and.returnValue(false);
//     fixture.detectChanges();

//     const compiled = fixture.nativeElement as HTMLElement;
//     expect(compiled.querySelector('form')).toBeTruthy();
//     expect(compiled.querySelector('button[type="submit"]')?.textContent).toContain('Iniciar Sesión');
//   });

//   it('should call logIn() with correct parameters', () => {
//     loginService.isLogged.and.returnValue(false);
//     fixture.detectChanges();

//     const userInput = fixture.nativeElement.querySelector('input[name="username"]');
//     const passInput = fixture.nativeElement.querySelector('input[name="password"]');
//     const loginButton = fixture.nativeElement.querySelector('button[type="submit"]');

//     userInput.value = 'testUser';
//     passInput.value = 'testPassword';
//     userInput.dispatchEvent(new Event('input'));
//     passInput.dispatchEvent(new Event('input'));
//     loginButton.click();

//     expect(loginService.logIn).toHaveBeenCalledWith('testUser', 'testPassword');
//   });

//   it('should display user details when logged in', () => {
//     loginService.isLogged.and.returnValue(true);
//     fixture.detectChanges();

//     const compiled = fixture.nativeElement as HTMLElement;
//     expect(compiled.querySelector('.navbar-brand')?.textContent).toContain('Usuario Actual: John Doe');
//   });

//   it('should call logOut() when the logout button is clicked', () => {
//     loginService.isLogged.and.returnValue(true);
//     fixture.detectChanges();

//     const logOutButton = fixture.nativeElement.querySelector('button');
//     logOutButton.click();

//     expect(loginService.logOut).toHaveBeenCalled();
//   });

//   it('should navigate to the profile page on myProfile()', () => {
//     loginService.isLogged.and.returnValue(true);
//     component.myProfile();

//     expect(router.navigate).toHaveBeenCalledWith(['/profile']);
//   });

//   it('should navigate to the users page if user is admin', () => {
//     loginService.isLogged.and.returnValue(true);
//     loginService.isAdmin.and.returnValue(true);
//     component.users();

//     expect(router.navigate).toHaveBeenCalledWith(['/users']);
//   });

//   it('should call createUser() to navigate to user creation', () => {
//     const createUserButton = fixture.nativeElement.querySelector('button:nth-child(2)');
//     createUserButton.click();

//     expect(router.navigate).toHaveBeenCalledWith(['/register']);
//   });
// });
