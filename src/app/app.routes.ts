import { Routes } from '@angular/router';
import { Dashboard } from './components/dashboard/dashboard.component';
import { MapComponent } from './components/map/map.component';
import { DriverView } from './components/driver-view/driver-view.component';

export const routes: Routes = [
    { path: 'dashboard', loadComponent: () => import('./components/dashboard/dashboard.component').then(m => m.Dashboard) },
    { path: 'map', component: MapComponent },
    { path: 'driver', loadComponent: () => import('./components/driver-view/driver-view.component').then(m => m.DriverView) },
    { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
];
