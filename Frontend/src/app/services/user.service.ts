import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { User } from '../models/user.model';
import { League } from '../models/league.model';
import { Team } from '../models/team.model';
import { Player } from '../models/player.model';

const BASE_URL = '/api/v1/users/';

@Injectable({ providedIn: 'root' })
export class UsersService {

    constructor(private httpClient: HttpClient) { }

    getUsers(): Observable<User[]> {
        return this.httpClient.get<User[]>(BASE_URL).pipe(
            catchError(error => this.handleError(error))
        );
    }

    getUser(id: number | undefined): Observable<User> {
        return this.httpClient.get<User>(BASE_URL + id)
    }

    getMe(): Observable<User> {
        return this.httpClient.get<User>(BASE_URL + 'me')
    }

    getLeagues(id: number): Observable<League[]> {
        return this.httpClient.get<League[]>(BASE_URL + id + '/leagues').pipe(
            catchError(error => this.handleError(error))
        );
    }

    getTeams(id: number): Observable<Team[]> {
        return this.httpClient.get<Team[]>(BASE_URL + id + '/teams').pipe(
            catchError(error => this.handleError(error))
        );
    }

    getPlayers(id: number): Observable<Player[]> {
        return this.httpClient.get<Player[]>(BASE_URL + id + '/players').pipe(
            catchError(error => this.handleError(error))
        );
    }

    getImage(id: number | undefined) {
        return this.httpClient.get<FormData>(BASE_URL + id + '/image')
            .pipe(
                catchError(error => this.handleError(error))
            );
    }

    addUser(user: User): Observable<User> {
        return this.httpClient.post<User>(BASE_URL, user).pipe(
            catchError(error => this.handleError(error))
        );
    }

    addImage(user: User, formData: FormData) {
        return this.httpClient.post(BASE_URL + user.id + '/image', formData)
            .pipe(
                catchError(error => this.handleError(error))
            );
    }

    addLeague(user: User, league: League) {
        return this.httpClient.put<User>(BASE_URL + user.id + '/leagues/' + league.id, user).pipe(
            catchError(error => this.handleError(error))
        );
    }

    addTeam(user: User, team: Team) {
        return this.httpClient.put<User>(BASE_URL + user.id + '/teams/' + team.id, user).pipe(
            catchError(error => this.handleError(error))
        );
    }

    addPlayer(user: User, player: Player) {
        return this.httpClient.put<User>(BASE_URL + user.id + '/players/' + player.id, user).pipe(
            catchError(error => this.handleError(error))
        );
    }

    deleteUser(user: User) {
        return this.httpClient.delete<User>(BASE_URL + user.id).pipe(
            catchError(error => this.handleError(error))
        );
    }

    deleteImage(user: User) {
        return this.httpClient.delete(BASE_URL + user.id + '/image').pipe(
            catchError(error => this.handleError(error))
        );
    }

    deleteLeague(user: User, league: League) {
        return this.httpClient.delete<User>(BASE_URL + user.id + '/leagues/' + league.id).pipe(
            catchError(error => this.handleError(error))
        );
    }

    deleteTeam(user: User, team: Team) {
        return this.httpClient.delete<User>(BASE_URL + user.id + '/teams/' + team.id).pipe(
            catchError(error => this.handleError(error))
        );
    }

    deletePlayer(user: User, player: Player) {
        return this.httpClient.delete<User>(BASE_URL + user.id + '/players/' + player.id).pipe(
            catchError(error => this.handleError(error))
        );
    }

    updateUser(user: User): Observable<User> {
        return this.httpClient.put<User>(BASE_URL + user.id, user).pipe(
            catchError(error => this.handleError(error))
        );
    }

    private handleError(error: any) {
        console.log("ERROR:");
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text())
    }
}