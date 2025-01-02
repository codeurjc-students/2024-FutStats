import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, EMPTY, throwError } from 'rxjs';
import { League } from 'src/app/models/league.model';
import { LeaguesService } from 'src/app/services/league.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  templateUrl: './league-list.component.html'
})
export class LeagueListComponent implements OnInit {

  leagues: League[];
  public leaguePage!: number;
  errorMsg!: string;


  constructor(private router: Router, private service: LeaguesService, public loginService: LoginService) { }

  ngOnInit() {
    this.service.getLeagues().subscribe((data: League[]) => {
      this.leagues = data;
    });
  }

  newLeague() {
    this.router.navigate(['/leagues/new']);
  }
}