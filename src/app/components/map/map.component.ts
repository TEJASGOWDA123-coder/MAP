import { Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { ParcelService } from '../../services/parcel.service';

@Component({
  selector: 'app-map',
  standalone: true,
  template: '<div id="map" style="height:100%; width:100%;"></div>',
  styles: [`
    #map {
      width: 100%;
      height: 100%;
      border-radius: 0;
    }
  `]
})
export class MapComponent implements OnInit {
  private map: any;
  private markers: { [key: string]: any } = {};

  constructor(
    private parcelService: ParcelService,
    @Inject(PLATFORM_ID) private platformId: Object
  ) { }

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.initMap();
    }
  }

  private async initMap() {
    const L = await import('leaflet');

    this.map = L.map('map').setView([12.9716, 77.5946], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution: '© OpenStreetMap'
    }).addTo(this.map);

    this.loadParcels();
  }

  private async loadParcels() {
    const L = await import('leaflet');

    this.parcelService.getNearbyParcels().subscribe((data: any) => {
      const parcels = Array.isArray(data) ? data : [data];
      parcels.forEach(parcel => {
        if (parcel.currentLocation && parcel.currentLocation.coordinates) {
          const [lng, lat] = parcel.currentLocation.coordinates;
          const id = parcel.parcelId;

          if (this.markers[id]) {
            this.markers[id].setLatLng([lat, lng]);
          } else {
            this.markers[id] = L.marker([lat, lng])
              .addTo(this.map)
              .bindPopup(`📦 ${id}`);
          }
        }
      });
    });
  }
}
