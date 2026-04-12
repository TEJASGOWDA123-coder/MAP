import axios from 'axios';

const API_URL = 'http://localhost:8080/api/batch';

export default {
    async getAnalyticsResults() {
        try {
            const response = await axios.get(`${API_URL}/results`);
            return response.data;
        } catch (error) {
            console.error('Error fetching batch results:', error);
            throw error;
        }
    },

    async runBatchJob() {
        try {
            const response = await axios.post(`${API_URL}/run`);
            return response.data;
        } catch (error) {
            console.error('Error running batch job:', error);
            throw error;
        }
    }
}
