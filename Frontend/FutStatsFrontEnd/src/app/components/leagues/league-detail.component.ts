import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LeaguesService } from '../../services/league.service';
import { League } from '../../models/league.model';



@Component({
  templateUrl: './league-detail.component.html'
})
export class LeagueDetailComponent implements OnInit {

  league: League;
  errorMessage: string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: LeaguesService,
  ) { }

  ngOnInit(): void {
    const id = this.activatedRoute.snapshot.params['id'];
    this.service.getLeagueById(id).subscribe(
      (league: League) => {
        this.league = league;
      },
      (error: any) => {
        this.errorMessage = 'Error fetching league details';
      }
    );
  }
  
  removeLeague() {
    const okResponse = window.confirm('Quieres borrar esta liga?');
    if (okResponse) {
        this.service.deleteLeague(this.league).subscribe(
            _ => this.router.navigate(['/leagues']),
            error => console.error(error)
        );
    }
  }

  editLeague() {
    this.router.navigate(['/leagues/edit', this.league.id]);
  }

  gotoLeagues(): void {
    this.router.navigate(['/leagues']);
  }
}