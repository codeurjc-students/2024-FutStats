import { Team } from 'src/app/models/team.model';
import { TeamsService } from 'src/app/services/team.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { League } from 'src/app/models/league.model';
import { LeaguesService } from 'src/app/services/league.service';

@Component({
    templateUrl: './team-form.component.html',
    styleUrls: ['./team-form.component.css'],
    standalone: false
})
export class TeamFormComponent implements OnInit {
  newTeam: boolean;
  team: Team;
  leagues: League[] = [];
  league: League;

  removeImage: boolean;

  @ViewChild('uploadImage', { static: false })
  fileInput: any;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private service: TeamsService,
    private leagueService: LeaguesService
  ) {
    const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getTeam(id).subscribe(
        team => this.team = team,
        error => console.error(error)
      );
      this.newTeam = false;
    } else {
      this.team = {
        name: '',
        image: false,
        trophies: 0,
        nationality: '',
        trainer: '',
        secondTrainer: '',
        president: '',
        stadium: '',
        points: 0,
        league: '',
      };
      this.newTeam = true;
    }
  }

  ngOnInit(): void {
    this.loadLeagues(); 
    this.teamImage();
  }

  private loadLeagues() {
    this.leagueService.getLeagues().subscribe({
      next: (leagues) => (this.leagues = leagues),
      error: (error) => console.error('Error loading leagues:', error),
    });
  }

  cancel() {
    window.history.back();
  }

  save() {
    if (this.newTeam) {
      if (this.removeImage) {
        this.team.image = false;
      }
      this.service.addTeam(this.team).subscribe(
        (team: Team) => this.uploadImage(team),
        error => alert('Error creating new team: ' + error)
      );
    } else {
      if (this.removeImage) {
        this.team.image = false;
      }
      this.service.updateTeam(this.team).subscribe(
        (team: Team,) => this.uploadImage(team),
        error => alert('Error creating new team: ' + error)
      );
    }
  }

  uploadImage(team: Team): void {
    if (this.fileInput) {
      const image = this.fileInput.nativeElement.files[0];
      if (image) {
        let formData = new FormData();
        formData.append("imageFile", image);
        this.service.addImage(team, formData).subscribe(
          _ => this.afterUploadImage(team),
          error => alert('Error uploading team image: ' + error)
        );
      } else if (this.removeImage) {
        this.service.deleteImage(team).subscribe(
          _ => this.afterUploadImage(team),
          error => alert('Error deleting team image: ' + error)
        );
      }
    }
    this.afterUploadImage(team);
  }

  onFileSelected(event: any): void {
    const fileInput = event.target.files[0];
    if (fileInput) {
      console.log('Archivo seleccionado:', fileInput.name);
    }
  }

  private afterUploadImage(team: Team) {
    this.router.navigate(['/teams', this.team.id]);
  }
  
  teamImage() {
    return this.team.image ? "api/v1/teams/" + this.team.id + "/image" : 'assets/no_image.jpg';
  }
}
