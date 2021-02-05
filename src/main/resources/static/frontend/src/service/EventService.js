import axios from 'axios'
import AuthenticationService from './AuthenticationService'

export class EventService {

    getEvents(urlBase) {
        return axios.get(urlBase+'/events/all', { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } })
    }

    getUserEvents(urlBase, nickUser) {
        return axios.get(urlBase+'/events/getByCourse/' + nickUser, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } })
    }
    
    updateEvent(urlBase, id, start, end){
        axios.put(urlBase+'/events/update/'+id+'/'+start+'/'+end, {}, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } })
    }

    getDescription(urlBase, id){
        return axios.get(urlBase+'/events/description/'+id, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } })
    }

    getDescriptionAlumno(urlBase, id, nickUser){
        return axios.get(urlBase+'/events/descriptionAlumno/'+id+'/'+nickUser, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } })
    }

    deleteEvent(urlBase, id){
        return axios.delete(urlBase+'/events/delete/'+id, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } })
    }

    join(urlBase, id, nick){
        return axios.put(urlBase+'/inscriptions/join/'+id+'/'+nick, {}, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } })
    }

    disjoin(urlBase, id, nick){
        return axios.put(urlBase+'/inscriptions/disjoin/'+id+'/'+nick, {}, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } })
    }
}