import axios from 'axios'

export class EventService {

    getEvents(urlBase) {
        return axios.get(urlBase+'/events/all', {withCredentials: true})
    }

    updateEvent(urlBase, id, start, end){
        axios.put(urlBase+'/events/update/'+id+'/'+start+'/'+end, {}, {withCredentials: true})
    }

    getDescription(urlBase, id){
        return axios.get(urlBase+'/events/description/'+id, {withCredentials: true})
    }

    deleteEvent(urlBase, id){
        return axios.delete(urlBase+'/events/delete/'+id, {withCredentials: true})
    }

}