import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'login',
  templateUrl: './login.component.html'
})
export class LoginComponent {

  constructor(private router: Router, public loginService: LoginService) { }

  logIn(event: any, user: string, pass: string) {

    event.preventDefault();

    this.loginService.logIn(user, pass);
  }

  users(){
    this.router.navigate(['/users']);
  }

  createUser(){
    this.router.navigate(['/users/new']);
}

  logOut() {
    this.loginService.logOut();
  }

}