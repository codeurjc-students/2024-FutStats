import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { LeaguesService } from '../../services/league.service';
import { League } from './../../models/league.model';

@Component({
    templateUrl: './league-form.component.html',
    standalone: false
})
export class LeagueFormComponent {

  newLeague: boolean;
  league: League;

  removeImage: boolean;

  @ViewChild("file")
  file: any;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: LeaguesService
  ) {
    const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getLeagueById(id).subscribe(
        league => this.league = league,
        error => console.error(error)
      );
      this.newLeague = false;
    } else {
      this.league = { name: '', nationality: '', president: '', teams: [], image: false }; // Valores predeterminados
      this.newLeague = true;
    }
  }

  cancel() {
    window.history.back();
  }

  save() {
    if (this.newLeague) {
      if (this.league.image && this.removeImage) {
        this.league.image = false;
      }
      this.service.addLeague(this.league).subscribe(
        (league: League) => this.uploadImage(league),
        error => alert('Error creating new league: ' + error)
      );
    } else {
      if (this.league.image && this.removeImage) {
        this.league.image = false;
      }
      this.service.updateLeague(this.league).subscribe(
        (league: League) => this.uploadImage(league),
        error => alert('Error creating new league: ' + error)
      );
    }
  }

  uploadImage(league: League): void {
    if (this.file) {
      const image = this.file.nativeElement.files[0];
      if (image) {
        let formData = new FormData();
        formData.append("imageFile", image);
        this.service.addImage(league, formData).subscribe(
          _ => this.afterUploadImage(league),
          error => alert('Error uploading user image: ' + error)
        );
      } else if (this.removeImage) {
        this.service.deleteImage(league).subscribe(
          _ => this.afterUploadImage(league),
          error => alert('Error deleting user image: ' + error)
        );
      }
    }
    this.afterUploadImage(league);

  }

  leagueImage() {
    return this.league.image ? this.service.getImage(this.league.id) : 'assets/no_image.png';
  }

  private afterUploadImage(league: League) {
    this.router.navigate(['/leagues', league.id]);
  }
}
