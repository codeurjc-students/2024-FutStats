import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { UsersService } from 'src/app/services/user.service';
import { LoginService } from 'src/app/services/login.service';
import { catchError, EMPTY, throwError } from 'rxjs';

@Component({
    templateUrl: './user-list.component.html'
})
export class UserListComponent implements OnInit {

    users: User[];
    errorMsg!: string;

    constructor(private router: Router, private service: UsersService, public loginService: LoginService) { }

    ngOnInit() {
        this.service.getUsers().subscribe(
            users => this.users = users,
        );
    }

    newUser() {
        this.router.navigate(['/users/new']);
      }

    goBack() {
        this.router.navigate(['/leagues']);
    }
}