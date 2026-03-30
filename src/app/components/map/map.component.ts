import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { ParcelService } from '../../services/parcel.service';

@Component({
  selector: 'app-map',
  standalone: true,
  template: '<div id="map" style="height:500px; width:100%;"></div>',
  styles: [`
    #map {
      width: 100%;
      height: 500px;
      border-radius: 8px;
      box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.1);
    }
  `]
})
export class MapComponent implements OnInit {
  private map: any;

  constructor(
    private parcelService: ParcelService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.initMap();
      this.loadParcels();
    }
  }

  private async initMap() {
    const L = await import('leaflet');

    this.map = L.map('map').setView([12.9716, 77.5946], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);
  }

  private async loadParcels() {
    const L = await import('leaflet');

    this.parcelService.getNearbyParcels().subscribe((data: any[]) => {
      data.forEach(parcel => {
        const [lng, lat] = parcel.currentLocation.coordinates;

        L.marker([lat, lng])
          .addTo(this.map)
          .bindPopup(`Parcel: ${parcel.parcelId}`);
      });
    });
  }
}
