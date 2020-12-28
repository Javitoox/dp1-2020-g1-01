import axios from 'axios'

export class EventService {

    getEvents(urlBase) {
        return axios.get(urlBase+'/events/all', {withCredentials: true})
    }

}