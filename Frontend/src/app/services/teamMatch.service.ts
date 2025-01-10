import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { Team } from '../models/team.model';
import { TeamMatch } from '../models/team-match.model';

const BASE_URL = '/api/v1/teamMatches/';

@Injectable({ providedIn: 'root' })
export class TeamMatchesService {

    constructor(private httpClient: HttpClient) { }

    getPointsPerMatch(teamId: number): Observable<any> {
        return this.httpClient.get<any>(BASE_URL + `/goals/${teamId}`).pipe(
            catchError(error => this.handleError(error))
        );
    }

    private handleError(error: any) {
        console.log("ERROR:");
        console.error(error);
        return throwError("Server error (" + error.status + "): " + error.text())
    }
}