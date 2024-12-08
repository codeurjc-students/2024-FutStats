import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { League } from 'src/app/models/league.model';
import { LeaguesService } from 'src/app/services/league.service';
import { LoginService } from 'src/app/services/login.service';

@Component({
  templateUrl: './league-list.component.html'
})
export class LeagueListComponent implements OnInit {

  leagues: League[];

  constructor(private router: Router, private service: LeaguesService, public loginService: LoginService) { }

  ngOnInit() {
    this.service.getLeagues().subscribe(
      leagues => this.leagues = leagues,
      error => console.log(error)
    );
  }

  newLeague() {
    this.router.navigate(['/leagues/new']);
  }
}