import axios from 'axios'

export class EventService {

    getEvents(urlBase) {
        return axios.get(urlBase+'/events/all')
    }

    getUserEvents(urlBase, nickUser) {
        return axios.get(urlBase+'/events/getByCourse/' + nickUser)
    }
    
    updateEvent(urlBase, id, start, end){
        axios.put(urlBase+'/events/update/'+id+'/'+start+'/'+end)
    }

    getDescription(urlBase, id){
        return axios.get(urlBase+'/events/description/'+id)
    }

    getDescriptionAlumno(urlBase, id, nickUser){
        return axios.get(urlBase+'/events/descriptionAlumno/'+id+'/'+nickUser)
    }

    deleteEvent(urlBase, id){
        return axios.delete(urlBase+'/events/delete/'+id)
    }

    join(urlBase, id, nick){
        return axios.put(urlBase+'/inscriptions/join/'+id+'/'+nick)
    }

    disjoin(urlBase, id, nick){
        return axios.put(urlBase+'/inscriptions/disjoin/'+id+'/'+nick)
    }
}