import { Routes, RouterModule } from '@angular/router';
import { LeagueDetailComponent } from './app/components/leagues/league-detail.component';
import { LeagueListComponent } from './app/components/leagues/league-list.component';
import { LeagueFormComponent } from './app/components/leagues/league-form.component';

export const appRoutes: Routes = [
    { path: 'leagues', component: LeagueListComponent },
    { path: 'leagues/new', component: LeagueFormComponent},
    { path: 'leagues/:id', component: LeagueDetailComponent},
    { path: 'leagues/edit/:id', component: LeagueFormComponent},
    { path: '', redirectTo: 'leagues', pathMatch: 'full' }
];

export const routing = RouterModule.forRoot(appRoutes);