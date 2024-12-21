import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UsersService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
    templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {

    users: User[];

    constructor(private router: Router, private service: UsersService, public loginService: LoginService) { }

    ngOnInit() {
        this.service.getUsers().subscribe(
            users => this.users = users,
            error => console.log(error)
        );
    }

    newUser() {
        this.router.navigate(['/users/new']);
      }

    goBack() {
        this.router.navigate(['/leagues']);
    }
}