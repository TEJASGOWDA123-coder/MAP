export interface Parcel {
  id: string;
  trackingNumber: string;
  sender: string;
  recipient: string;
  status: 'PENDING' | 'IN_TRANSIT' | 'DELIVERED' | 'CANCELLED';
  currentLocation: {
    lat: number;
    lng: number;
  };
  lastUpdate: Date;
}

export interface ParcelUpdate {
  parcelId: string;
  location: {
    lat: number;
    lng: number;
  };
  status?: Parcel['status'];
  timestamp: Date;
}
