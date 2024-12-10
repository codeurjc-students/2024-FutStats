import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User } from '../models/user.model';

const BASE_URL = '/api/v1/';

@Injectable({ providedIn: 'root' })
export class LoginService {

    logged: boolean = false; // Inicializamos el estado
    user: User | undefined;

    constructor(private http: HttpClient) {
        this.reqIsLogged();
    }

    reqIsLogged() {
        this.http.get<User>(BASE_URL + 'users/me', { withCredentials: true }).subscribe(
            response => {
                this.user = response; // Asignamos el usuario devuelto
                this.logged = true;  // Establecemos como "logueado"
            },
            error => {
                if (error.status === 404) {
                    console.log("No user is logged in.");
                } else {
                    console.error('Error when asking if logged: ', error);
                }
                this.logged = false; // Aseguramos que no está logueado
            }
        );
    }

    logIn(user: string, pass: string) {
        this.http.post(BASE_URL + "login", { username: user, password: pass }, { withCredentials: true })
            .subscribe(
                () => this.reqIsLogged(),
                (error) => alert("Wrong credentials")
            );
    }

    logOut() {
        this.http.post(BASE_URL + 'logout', {}, { withCredentials: true })
            .subscribe(() => {
                console.log("LOGOUT: Successfully");
                this.logged = false;
                this.user = undefined;
            }, (error) => {
                console.error("Error during logout: ", error);
            });
    }

    isLogged() {
        return this.logged;
    }

    isAdmin() {
        return this.user && Array.isArray(this.user.roles) && this.user.roles.includes('admin');
    }

    currentUser() {
        return this.user;
    }
}
