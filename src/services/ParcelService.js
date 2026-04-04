import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/parcels';

export default {
    getNearbyParcels(lng, lat, distance = 5) {
        return axios.get(`${API_BASE_URL}/nearby`, {
            params: { lng, lat, distance }
        });
    }
};
