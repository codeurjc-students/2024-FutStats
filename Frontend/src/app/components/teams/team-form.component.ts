import { Team } from 'src/app/models/team.model';
import { TeamsService } from 'src/app/services/team.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { League } from 'src/app/models/league.model';
import { LeaguesService } from 'src/app/services/league.service';

@Component({
    templateUrl: './team-form.component.html',
    standalone: false
})
export class TeamFormComponent implements OnInit {
  newTeam: boolean;
  team: Team;
  leagues: League[] = [];
  league: League;

  removeImage: boolean;

  @ViewChild("file")
  file: any;

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
      }; // Valores predeterminados
      this.newTeam = true;
    }
  }

  ngOnInit(): void {
    this.loadLeagues(); // Cargar ligas al iniciar el componente
  }

  // Cargar las ligas desde el servicio
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
      if (this.team.image && this.removeImage) {
        this.team.image = false;
      }
      this.service.addTeam(this.team).subscribe(
        (team: Team) => this.uploadImage(team),
        error => alert('Error creating new league: ' + error)
      );
    } else {
      if (this.team.image && this.removeImage) {
        this.team.image = false;
      }
      this.service.updateTeam(this.team).subscribe(
        (team: Team,) => this.uploadImage(team),
        error => alert('Error creating new league: ' + error)
      );
    }
  }

  uploadImage(team: Team): void {

    if (this.file) {
      const image = this.file.nativeElement.files[0];
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
    } else {
      this.afterUploadImage(team);
    }
  }

  private afterUploadImage(team: Team) {
    this.leagueService.getLeagueByName(this.team.league).subscribe({
      next: (league: League) => {
        this.league = league;
        this.router.navigate(['/leagues', this.league.id]); // Navigate after league is fetched
      },
      error: (error) => {
        console.error('Error fetching league:', error);
        alert('Failed to fetch league details. Please try again.');
      },
    });
  }
  

  teamImage() {
    return this.team.image ? this.service.getImage(this.team.id) : 'assets/no_image.jpg';
  }
}
