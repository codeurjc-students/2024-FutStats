import { League } from './../models/league.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Team } from '../models/team.model';
import { Player } from '../models/player.model';

const BASE_URL = '/api/v1/teams/';

@Injectable({ providedIn: 'root' })
export class TeamsService {

	constructor(private httpClient: HttpClient) { }

	getTeams(): Observable<Team[]> {
		return this.httpClient.get<Team[]>(BASE_URL).pipe(
			catchError(error => this.handleError(error))
		);
	}

	getTeam(id: number ): Observable<Team> {
		return this.httpClient.get<Team>(BASE_URL+id).pipe(
			catchError(error => this.handleError(error))
		);
	}

	getTeamByName(name: string ): Observable<Team> {
		return this.httpClient.get<Team>(BASE_URL+ 'name/' + name).pipe(
			catchError(error => this.handleError(error))
		);
	}
	
	getLeagueByTeam(id: number): Observable<League>{
		return this.httpClient.get<League>(BASE_URL+id+'/league').pipe(
			catchError(error => this.handleError(error))
		);
	}

	getPlayersByTeam(id: number): Observable<Player[]>{
		return this.httpClient.get<Player[]>(BASE_URL+id+'/players').pipe(
			catchError(error => this.handleError(error))
		);
	}

	addTeam(team: Team): Observable<Team> {
		if (!team.id) {
		  return this.httpClient.post<Team>(BASE_URL, team).pipe(
			catchError(error => this.handleError(error))
		  );
		} else {
		  return this.httpClient.put<Team>(BASE_URL + team.id, team).pipe(
			catchError(error => this.handleError(error))
		  );
		}
	  }
	
	deleteTeam(team: Team) {
		return this.httpClient.delete<Team>(BASE_URL + team.id).pipe(
			catchError(error => this.handleError(error))
		);
	}
	
	updateTeam(team: Team): Observable<Team> {
		return this.httpClient.put<Team>(BASE_URL + team.id, team).pipe(
		catchError(error => this.handleError(error))
		);
	}

	private handleError(error: any) {
		console.log("ERROR:");
		console.error(error);
		return throwError("Server error (" + error.status + "): " + error.text())
	}
}