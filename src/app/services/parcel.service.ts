import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ParcelService {

  private apiUrl = 'http://localhost:8080/api/parcels/nearby';

  constructor(private http: HttpClient) { }

  getNearbyParcels() {
    return this.http.get(`${this.apiUrl}?lat=12.9716&lng=77.5946`);
  }
}
