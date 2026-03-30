import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ParcelService {
  private apiUrl = 'http://localhost:8080/api/parcels';

  constructor(private http: HttpClient) { }

  getNearbyParcels(lng: number = 77.5946, lat: number = 12.9716): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/nearby`, {
      params: {
        lng: lng.toString(),
        lat: lat.toString()
      }
    });
  }
}
