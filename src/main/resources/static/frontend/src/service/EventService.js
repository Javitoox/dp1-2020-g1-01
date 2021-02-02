import axios from 'axios'

export class EventService {

    getEvents(urlBase) {
        return axios.get(urlBase+'/events/all', {withCredentials: true})
    }

    getUserEvents(urlBase, nickUser) {
        return axios.get(urlBase+'/events/getByCourse/' + nickUser, {withCredentials: true})
    }
    
    updateEvent(urlBase, id, start, end){
        axios.put(urlBase+'/events/update/'+id+'/'+start+'/'+end, {}, {withCredentials: true})
    }

    getDescription(urlBase, id){
        return axios.get(urlBase+'/events/description/'+id, {withCredentials: true})
    }

    getDescriptionAlumno(urlBase, id, nickUser){
        return axios.get(urlBase+'/events/descriptionAlumno/'+id+'/'+nickUser, {withCredentials: true})
    }

    deleteEvent(urlBase, id){
        return axios.delete(urlBase+'/events/delete/'+id, {withCredentials: true})
    }

    join(urlBase, id, nick){
        return axios.put(urlBase+'/inscriptions/join/'+id+'/'+nick, {}, {withCredentials: true})
    }

    disjoin(urlBase, id, nick){
        return axios.put(urlBase+'/inscriptions/disjoin/'+id+'/'+nick, {}, {withCredentials: true})
    }
}