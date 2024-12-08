import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { routing } from '../../src/app.routing';

import { LeagueListComponent } from './components/leagues/league-list.component';
import { LeagueDetailComponent } from './components/leagues/league-detail.component';
import { LeagueFormComponent } from './components/leagues/league-form.component';

@NgModule({
  declarations: [AppComponent,
    LeagueListComponent, LeagueDetailComponent, LeagueFormComponent
  ],
  imports: [BrowserModule, FormsModule, HttpClientModule, routing],
  bootstrap: [AppComponent]
})

export class AppModule { }