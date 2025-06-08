import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { LeaguesService } from '../../services/league.service';
import { League } from './../../models/league.model';

@Component({
    templateUrl: './league-form.component.html',
    styleUrls: ['./league-form.component.css'],
    standalone: false
})
export class LeagueFormComponent {

  newLeague: boolean;
  league: League;

  removeImage: boolean;

  @ViewChild('uploadImage', { static: false })
  fileInput: any;
  
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

  ngOnInit(): void {
    this.leagueImage();
  }


  cancel() {
    this.router.navigate(['/leagues']);
  }

  save() {
    if (this.newLeague) {
      if (this.removeImage) {
        this.league.image = false;
      }
      this.service.addLeague(this.league).subscribe(
        (league: League) => this.uploadImage(league),
        error => alert('Error creating new league: ' + error)
      );
    } else {
      if (this.removeImage) {
        this.league.image = false;
      }
      this.service.updateLeague(this.league).subscribe(
        (league: League) => this.uploadImage(league),
        error => alert('Error updating league: ' + error)
      );
    }
  }
  
  uploadImage(league: League): void {
    if (this.fileInput) {
      const file = this.fileInput.nativeElement.files[0];
      if (file) {
        let formData = new FormData();
        formData.append('imageFile', file);
        this.service.addImage(league, formData).subscribe(
          () => this.afterUploadImage(league),
          error => alert('Error uploading image: ' + error)
        );
      } else if (this.removeImage) {
        this.service.deleteImage(league).subscribe(
          () => this.afterUploadImage(league),
          error => alert('Error deleting image: ' + error)
        );
      }
    }
    this.afterUploadImage(league);
  }
  
  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      console.log('Archivo seleccionado:', file.name);
    }
  }
  
  leagueImage() {
    return this.league.image ? "api/v1/leagues/" + this.league.id + "/image" : 'assets/no_image.jpg';
  }

  private afterUploadImage(league: League) {
    this.router.navigate(['/leagues', league.id]);
  }
}
