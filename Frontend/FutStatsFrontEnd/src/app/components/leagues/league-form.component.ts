import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { LeaguesService } from '../../services/league.service';
import { League } from './../../models/league.model';

@Component({
  templateUrl: './league-form.component.html'
})
export class LeagueFormComponent {

  newLeague: boolean;
  league: League;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: LeaguesService
  ) {
      this.league = { name: '', nationality: '', president: '',teams: [] }; // Valores predeterminados
      this.newLeague = true;
  }

  cancel() {
    window.history.back();
  }

  save() {
    this.service.addLeague(this.league).subscribe(
      (league: League) => this.afterSave(league),
      error => alert('Error creating new league: ' + error)
    );
  }

  private afterSave(league: League) {
    this.router.navigate(['/leagues', league.id]);
  }
}
