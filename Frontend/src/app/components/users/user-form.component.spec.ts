import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UserFormComponent } from './user-form.component';
import { UsersService } from 'src/app/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { of, throwError } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { NO_ERRORS_SCHEMA } from '@angular/core';

describe('UserFormComponent', () => {
  let component: UserFormComponent;
  let fixture: ComponentFixture<UserFormComponent>;
  let mockUsersService: any;
  let mockActivatedRoute: any;
  let mockRouter: any;

  beforeEach(async () => {
    mockUsersService = {
      getUser: jasmine.createSpy('getUser').and.returnValue(of({ id: 1, name: 'testUser', password: 'pass', email:'email', image: true, roles: ['[user]'] })),
      addUser: jasmine.createSpy('addUser').and.returnValue(of({ name: 'New User', password: 'pass123', email:'email', roles: [], image: false })),
      updateUser: jasmine.createSpy('updateUser').and.returnValue(of({ id: 1, name: 'Updated User', password: 'pass123', email:'email', roles: ['user'], image: false })),
      addImage: jasmine.createSpy('addImage').and.returnValue(of({})),
      deleteImage: jasmine.createSpy('deleteImage').and.returnValue(of({})),
      getImage: jasmine.createSpy('getImage').and.returnValue('assets/401-background.jpg'),
    };

    mockActivatedRoute = {
      snapshot: { params: { id: 1 } },
    };

    mockRouter = {
      navigate: jasmine.createSpy('navigate'),
    };

    await TestBed.configureTestingModule({
      declarations: [UserFormComponent],
      imports: [FormsModule],
      providers: [
        { provide: UsersService, useValue: mockUsersService },
        { provide: ActivatedRoute, useValue: mockActivatedRoute },
        { provide: Router, useValue: mockRouter },
      ],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();

    fixture = TestBed.createComponent(UserFormComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load user data if ID is provided', () => {
    expect(mockUsersService.getUser).toHaveBeenCalledWith(1);
    expect(component.user.name).toEqual('User A');
    expect(component.newUser).toBeFalse();
  });

  it('should initialize a new user if no ID is provided', () => {
    mockActivatedRoute.snapshot.params['id'] = undefined;
    component = new UserFormComponent(mockRouter, mockActivatedRoute, mockUsersService);
    expect(component.user.name).toEqual('');
    expect(component.newUser).toBeTrue();
  });

  it('should save a new user', () => {
    component.newUser = true;
    component.user = { name: 'New User', password: 'pass123', email:'email', roles: [], image: false };
    component.save();

    expect(mockUsersService.addUser).toHaveBeenCalledWith(component.user);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users']);
  });

  it('should update an existing user', () => {
    component.newUser = false;
    component.user = { id: 1, name: 'Updated User', password: 'pass123', email:'email', roles: ['user'], image: false };
    component.save();

    expect(mockUsersService.updateUser).toHaveBeenCalledWith(component.user);
    expect(mockRouter.navigate).toHaveBeenCalledWith(['/users']);
  });

  it('should upload an image after saving a user', () => {
    const mockFile = new Blob([''], { type: 'image/png' });
    component.file = { nativeElement: { files: [mockFile] } };
    component.uploadImage({ id: 1, name: 'testUser', password: 'pass', email:'email', image: true, roles: ['[user]'] });

    expect(mockUsersService.addImage).toHaveBeenCalled();
  });

  it('should delete an image if removeImage is true', () => {
    component.removeImage = true;
    component.uploadImage({ id: 1, name: 'testUser', password: 'pass', email:'email', image: true, roles: ['[user]'] });

    expect(mockUsersService.deleteImage).toHaveBeenCalled();
  });

  it('should navigate back on cancel', () => {
    spyOn(window.history, 'back');
    component.cancel();
    expect(window.history.back).toHaveBeenCalled();
  });

  it('should return the correct user image', () => {
    component.user = { id: 1, name: 'testUser', password: 'pass', email:'email', image: false, roles: ['[user]'] };
    const imageUrl = component.userImage();
    expect(imageUrl).toBe('assets/401-background.jpg');
  });

  it('should return a default image if no image exists', () => {
    component.user = { id: 1, name: 'testUser', password: 'pass', email:'email', image: false, roles: ['[user]'] };
    const imageUrl = component.userImage();
    expect(imageUrl).toBe('assets/no_image.jpg');
  });
});
